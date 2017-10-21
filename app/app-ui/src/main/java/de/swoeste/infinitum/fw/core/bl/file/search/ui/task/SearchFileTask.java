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

import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFilePath;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * @author swoeste
 */
public class SearchFileTask extends AbstractNodeDisablingTask<Void> {

    private final ObservableList<UIFilePath> searchResults;
    private final String                     searchPath;
    private final String                     searchFilePattern;
    private final boolean                    includeArchives;
    private final boolean                    includeSubDirectories;

    public SearchFileTask(final List<Node> nodes, final ObservableList<UIFilePath> searchResults, final String searchPath, final String searchFilePattern,
            final boolean includeArchives, final boolean includeSubDirectories) {
        super(nodes);
        this.searchResults = searchResults;
        this.searchPath = searchPath;
        this.searchFilePattern = searchFilePattern;
        this.includeArchives = includeArchives;
        this.includeSubDirectories = includeSubDirectories;
    }

    private FileSystemSearchConfiguration createFileSearchConfig() {
        return new FileSystemSearchConfiguration(Paths.get(this.searchPath), getSearchFilter(), this.includeArchives, this.includeSubDirectories);
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

        int maxWork = searchResult.size() + 1;
        updateMessage("Status: Preparing result(s) ...");
        updateProgress(0, maxWork);

        int currWork = 1;
        for (Resource resource : searchResult) {
            updateProgress(currWork, maxWork);
            currWork++;
            this.searchResults.add(new UIFilePath(resource.getFilePathAsString()));
        }

        updateMessage("Status: Complete!");
        return null;
    }

}
