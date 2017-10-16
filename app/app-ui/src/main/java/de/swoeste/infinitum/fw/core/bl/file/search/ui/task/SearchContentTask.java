/*
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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.task;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceContentAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFileContent;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;

/**
 * @author swoeste
 */
public class SearchContentTask extends Task<Void> {

    private final ObservableList<UIFileContent> searchResults;
    private final String                        searchPath;
    private final String                        searchFilePattern;
    private final String                        searchContentPattern;
    private final boolean                       includeArchives;
    private final boolean                       includeSubDirectories;
    private final Executor                      executor;

    public SearchContentTask(final ObservableList<UIFileContent> searchResults, final String searchPath, final String searchFilePattern, final String searchContentPattern,
            final boolean includeArchives, final boolean includeSubDirectories, final Executor executor) {
        this.searchResults = searchResults;
        this.searchPath = searchPath;
        this.searchFilePattern = searchFilePattern;
        this.searchContentPattern = searchContentPattern;
        this.includeArchives = includeArchives;
        this.includeSubDirectories = includeSubDirectories;
        this.executor = executor;
    }

    private FileSystemSearchConfiguration createFileSearchConfig() {
        final Path path = Paths.get(this.searchPath);
        return new FileSystemSearchConfiguration(path, getSearchFilter(), this.includeArchives, this.includeSubDirectories);
    }

    private List<ResourceFilter> getSearchFilter() {
        final List<ResourceFilter> searchFilter = new ArrayList<>();
        searchFilter.add(new ResourcePathFilter(this.searchFilePattern));
        return searchFilter;
    }

    private FileContentSearchConfiguration createFileContentSearchConfig(final Queue<Resource> filesToSearchIn) {
        final ResourceAnalyzer resourceAnalyzer = new ResourceContentAnalyzer(this.searchContentPattern);
        // TODO threads = 8 - make me configurable
        return new FileContentSearchConfiguration(filesToSearchIn, resourceAnalyzer, this.executor, 8);
    }

    /** {@inheritDoc} */
    @Override
    protected Void call() throws Exception {
        updateMessage("Status: Searching for files ...");
        final FileSystemSearch fileSearch = new FileSystemSearch(createFileSearchConfig());
        fileSearch.search();
        final Queue<Resource> fileSearchResult = fileSearch.getFiles();

        updateMessage("Status: Searching for content ...");
        final FileContentSearch contentSearch = new FileContentSearch(createFileContentSearchConfig(fileSearchResult));
        contentSearch.search();
        final List<SearchResult> contentSearchResult = contentSearch.getResult();

        int maxWork = contentSearchResult.size() + 1;
        int currWork = 1;
        updateMessage("Status: Preparing result(s) ...");
        updateProgress(currWork, maxWork);
        currWork++;

        final Map<String, UIFileContent> resourceByPath = new HashMap<>();
        for (SearchResult resource : contentSearchResult) {
            try {
                final String filePathAsString = resource.getResource().getFilePathAsString();

                UIFileContent content = resourceByPath.get(filePathAsString);
                if (content == null) {
                    content = new UIFileContent(filePathAsString, resource.getResource().getContentAsString(), new ArrayList<>());
                    this.searchResults.add(content);
                    resourceByPath.put(filePathAsString, content);
                }

                updateMessage("Status: Scanning file \"" + filePathAsString + "\"");
                updateProgress(currWork, maxWork);
                currWork++;

                content.getResultPositions().add(new Pair<Long, Long>(resource.getStartIndex(), resource.getEndIndex()));
            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }

        updateMessage("Status: Complete!");
        updateProgress(currWork, maxWork);
        return null;
    }

}
