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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.task;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceContentAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFileContent;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Pair;

/**
 * This task is used to perform a file and content search.
 *
 * @author swoeste
 */
public class SearchContentTask extends AbstractNodeDisablingTask<Void> {

    private static final Logger                 LOG     = LoggerFactory.getLogger(SearchContentTask.class);

    // TODO maybe we make this configurable in the future
    public static final int                     THREADS = 8;

    private final ObservableList<UIFileContent> searchResults;
    private final String                        searchPath;
    private final String                        searchFilePattern;
    private final String                        searchContentPattern;
    private final boolean                       includeArchives;
    private final boolean                       includeSubDirectories;
    private final Executor                      executor;

    public SearchContentTask(final List<Node> nodes, final ObservableList<UIFileContent> searchResults, final String searchPath, final String searchFilePattern,
            final String searchContentPattern, final boolean includeArchives, final boolean includeSubDirectories, final Executor executor) {
        super(nodes);
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
        return new FileSystemSearchConfiguration(path, getSearchFilter(), this.includeArchives, this.includeSubDirectories, this.executor, THREADS);
    }

    private List<ResourceFilter> getSearchFilter() {
        final List<ResourceFilter> searchFilter = new ArrayList<>();
        searchFilter.add(new ResourcePathFilter(this.searchFilePattern));
        return searchFilter;
    }

    private FileContentSearchConfiguration createFileContentSearchConfig(final Queue<Resource> filesToSearchIn) {
        final ResourceAnalyzer resourceAnalyzer = new ResourceContentAnalyzer(this.searchContentPattern);
        return new FileContentSearchConfiguration(filesToSearchIn, resourceAnalyzer, this.executor, THREADS);
    }

    /** {@inheritDoc} */
    @Override
    protected Void callInternal() throws Exception {
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
            final String filePathAsString = resource.getResource().getPath();

            UIFileContent uiElement = resourceByPath.get(filePathAsString);
            if (uiElement == null) {
                uiElement = new UIFileContent(filePathAsString);
                loadContent(uiElement, resource);
                this.searchResults.add(uiElement);
                resourceByPath.put(filePathAsString, uiElement);
            }

            updateMessage("Status: Scanning file \"" + filePathAsString + "\"");
            updateProgress(currWork, maxWork);
            currWork++;

            uiElement.getResultPositions().add(new Pair<>(resource.getStartIndex(), resource.getEndIndex()));
        }

        updateMessage("Status: Complete!");
        updateProgress(currWork, maxWork);
        return null;
    }

    private void loadContent(final UIFileContent content, final SearchResult resource) {
        try {
            content.setFileContent(resource.getResource().getContentAsString());
        } catch (IOException ex) {
            content.setFileContent("### unable to read file content ###"); //$NON-NLS-1$
            LOG.error("Unable to read content of {}", resource.getResource().getPath(), ex); //$NON-NLS-1$
        }
    }

}
