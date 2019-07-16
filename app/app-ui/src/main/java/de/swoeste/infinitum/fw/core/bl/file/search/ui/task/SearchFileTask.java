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

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFilePath;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * This task is used to perform a file search.
 *
 * @author swoeste
 */
public class SearchFileTask extends AbstractNodeDisablingTask<Void> {

    private static final Logger              LOG     = LoggerFactory.getLogger(SearchFileTask.class);

    // TODO maybe we make this configurable in the future
    public static final int                  THREADS = 8;

    private final ObservableList<UIFilePath> searchResults;
    private final String                     searchPath;
    private final String                     searchFilePattern;
    private final boolean                    includeArchives;
    private final boolean                    includeSubDirectories;
    private final Executor                   executor;

    public SearchFileTask(final List<Node> nodes, final ObservableList<UIFilePath> searchResults, final String searchPath, final String searchFilePattern,
            final boolean includeArchives, final boolean includeSubDirectories, final Executor executor) {
        super(nodes);
        this.searchResults = searchResults;
        this.searchPath = searchPath;
        this.searchFilePattern = searchFilePattern;
        this.includeArchives = includeArchives;
        this.includeSubDirectories = includeSubDirectories;
        this.executor = executor;
    }

    private FileSystemSearchConfiguration createFileSearchConfig() {
        return new FileSystemSearchConfiguration(Paths.get(this.searchPath), getSearchFilter(), this.includeArchives, this.includeSubDirectories, this.executor, THREADS);
    }

    private List<ResourceFilter> getSearchFilter() {
        final List<ResourceFilter> searchFilter = new ArrayList<>();
        searchFilter.add(new ResourcePathFilter(this.searchFilePattern));
        return searchFilter;
    }

    /** {@inheritDoc} */
    @Override
    protected Void callInternal() throws Exception {
        updateMessage("Status: Searching ...");

        final FileSystemSearch search = new FileSystemSearch(createFileSearchConfig());
        search.search();

        final Queue<Resource> searchResult = search.getFiles();

        final int maxWork = searchResult.size() + 1;
        updateMessage("Status: Preparing result(s) ...");
        updateProgress(0, maxWork);

        int currWork = 1;
        for (Resource resource : searchResult) {
            updateProgress(currWork, maxWork);
            currWork++;
            this.searchResults.add(new UIFilePath(resource.getFullQualifiedPath()));
        }

        updateMessage("Status: Complete!");
        return null;
    }

}
