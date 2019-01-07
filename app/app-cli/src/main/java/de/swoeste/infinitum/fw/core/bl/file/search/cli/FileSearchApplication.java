/*-
 * Copyright (C) 2017 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. I license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swoeste.infinitum.fw.core.bl.file.search.cli;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceContentAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.SimpleExecutor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceNameFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceNotFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
public class FileSearchApplication {

    // TODO - replace system.out with simple logger?

    private static final String DEFAULT_THREADS     = "2"; //$NON-NLS-1$

    private static final String OPT_HELP            = "h"; //$NON-NLS-1$
    private static final String OPT_SEARCH_ARCHIVES = "a";//$NON-NLS-1$
    private static final String OPT_CONTENT         = "c";//$NON-NLS-1$
    private static final String OPT_FILE            = "f";//$NON-NLS-1$
    private static final String OPT_PATH            = "p";//$NON-NLS-1$
    private static final String OPT_THREADS         = "t";//$NON-NLS-1$

    public static void main(final String[] args) {
        final Options options = initializeOptions();
        try {
            final CommandLine cmd = new DefaultParser().parse(options, args);
            if (cmd.hasOption(OPT_HELP)) {
                printHelp(options);
                return;
            } else {
                executeSearch(cmd);
            }
        } catch (final ParseException ex) {
            printHelp(options);
            System.out.println("Error while trying to parse input parameter(s): " + ex.getMessage()); //$NON-NLS-1$
            return;
        }
    }

    private static void executeSearch(final CommandLine cmd) {
        final FileSystemSearchConfiguration fssConfiguration = createFSSConfiguration(cmd);
        final FileSystemSearch fsSearch = new FileSystemSearch(fssConfiguration);
        fsSearch.search();

        if (!cmd.hasOption(OPT_CONTENT)) {
            displayFSSResult(fsSearch);
        } else {
            final FileContentSearchConfiguration fcsConfiguration = createFCSConfiguration(cmd, fsSearch);
            final FileContentSearch fcSearch = new FileContentSearch(fcsConfiguration);
            fcSearch.search();
            displayFCSResult(fcSearch);
        }
    }

    private static void displayFCSResult(final FileContentSearch fcSearch) {
        final List<SearchResult> result = fcSearch.getResult();
        for (final SearchResult searchResult : result) {
            System.out.println(searchResult);
        }
    }

    private static void displayFSSResult(final FileSystemSearch fsSearch) {
        System.out.println("Result:");
        final Queue<Resource> files = fsSearch.getFiles();
        for (final Resource resource : files) {
            System.out.println(resource.getPath());
        }

        final Queue<Resource> failedFiles = fsSearch.getFailedFiles();
        if (!failedFiles.isEmpty()) {
            System.out.println("Failed:");
            for (final Resource resource : failedFiles) {
                System.out.println(resource.getPath());
            }
        }
    }

    private static FileContentSearchConfiguration createFCSConfiguration(final CommandLine cmd, final FileSystemSearch search) {
        final SimpleExecutor executor = SimpleExecutor.getInstance();
        final ResourceContentAnalyzer analyzer = getResourceContentAnalyzer(cmd);
        final int threads = getThreads(cmd);
        final FileContentSearchConfiguration configuration = new FileContentSearchConfiguration(search.getFiles(), analyzer, executor, threads);
        return configuration;
    }

    private static FileSystemSearchConfiguration createFSSConfiguration(final CommandLine cmd) {
        final boolean searchArchives = isSearchArchives(cmd);
        final Path searchPath = getSearchPath(cmd);
        final List<ResourceFilter> filters = getResourceFilters(cmd);
        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(searchPath, filters, searchArchives);
        return configuration;
    }

    private static boolean isSearchArchives(final CommandLine cmd) {
        return cmd.hasOption(OPT_SEARCH_ARCHIVES);
    }

    private static int getThreads(final CommandLine cmd) {
        return Integer.parseInt(cmd.getOptionValue(OPT_THREADS, DEFAULT_THREADS));
    }

    private static Path getSearchPath(final CommandLine cmd) {
        final String optionValue;
        if (cmd.hasOption(OPT_PATH)) {
            optionValue = cmd.getOptionValue(OPT_PATH);
        } else {
            optionValue = System.getProperty("user.dir"); //$NON-NLS-1$
        }
        return Paths.get(optionValue);
    }

    private static List<ResourceFilter> getResourceFilters(final CommandLine cmd) {
        if (cmd.hasOption(OPT_FILE)) {
            final List<ResourceFilter> result = new ArrayList<>();

            final String[] optionValues = cmd.getOptionValues(OPT_FILE);
            for (final String optionValue : optionValues) {
                final ResourceFilter filter;
                if (optionValue.contains("/") || optionValue.contains("\\")) { //$NON-NLS-1$ //$NON-NLS-2$
                    filter = new ResourcePathFilter(optionValue);
                } else {
                    filter = new ResourceNameFilter(optionValue);
                }
                if (optionValue.startsWith("!")) { //$NON-NLS-1$
                    result.add(new ResourceNotFilter(filter));
                } else {
                    result.add(filter);
                }
            }

            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private static ResourceContentAnalyzer getResourceContentAnalyzer(final CommandLine cmd) {
        if (cmd.hasOption(OPT_CONTENT)) {
            final String optionValue = cmd.getOptionValue(OPT_CONTENT);
            return new ResourceContentAnalyzer(optionValue);
        } else {
            return null;
        }
    }

    private static Options initializeOptions() {
        final Options options = new Options();

        // help
        final Builder helpOption = Option.builder(String.valueOf(OPT_HELP));
        helpOption.longOpt("help"); //$NON-NLS-1$
        helpOption.desc("shows this help"); //$NON-NLS-1$
        options.addOption(helpOption.build());

        // file - for filePath and fileName filter
        final Builder fileOption = Option.builder(OPT_FILE);
        fileOption.longOpt("file"); //$NON-NLS-1$
        fileOption.desc("a regular expression to match the file(s) name to search for");
        fileOption.hasArgs();
        options.addOption(fileOption.build());

        // content - for content search
        final Builder contentOption = Option.builder(OPT_CONTENT);
        contentOption.longOpt("content"); //$NON-NLS-1$
        contentOption.desc("a regular expression to match the file(s) content to search for");
        contentOption.hasArg();
        options.addOption(contentOption.build());

        // path - the root path to start searching
        final Builder searchPathOption = Option.builder(OPT_PATH);
        searchPathOption.longOpt("path"); //$NON-NLS-1$
        searchPathOption.desc("the path to start the recursive search");
        searchPathOption.hasArg();
        options.addOption(searchPathOption.build());

        // path - the root path to start searching
        final Builder searchThreadsOption = Option.builder(OPT_THREADS);
        searchThreadsOption.longOpt("threads"); //$NON-NLS-1$
        searchThreadsOption.desc(MessageFormat.format("the amount of threads used for parallel execution (default: {0})", DEFAULT_THREADS));
        searchThreadsOption.hasArg();
        options.addOption(searchThreadsOption.build());

        final Builder searchArchivesOption = Option.builder(OPT_SEARCH_ARCHIVES);
        searchArchivesOption.longOpt("search-archives"); //$NON-NLS-1$
        searchArchivesOption.desc("enables search in archives, for example in a JAR");
        options.addOption(searchArchivesOption.build());

        return options;
    }

    private static void printHelp(final Options options) {
        // TODO header + correct version
        new HelpFormatter().printHelp("myapp", "my new header in version 1.0", options, "", true);
    }

}
