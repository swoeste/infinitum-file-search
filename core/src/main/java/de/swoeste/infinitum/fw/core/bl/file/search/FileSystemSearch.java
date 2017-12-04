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
package de.swoeste.infinitum.fw.core.bl.file.search;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;

/**
 * @author swoeste
 */
public class FileSystemSearch {

    // TODO java doc

    private final FileSystemSearchConfiguration configuration;
    private final FileSystemCrawler             crawler;

    /**
     * Constructor for a new SearchEngine.
     */
    public FileSystemSearch(final FileSystemSearchConfiguration configuration) {
        this.configuration = configuration;
        this.crawler = createFileSystemCrawler();
    }

    public void search() {
        try {
            Files.walkFileTree(this.configuration.getPath(), Collections.emptySet(), this.configuration.getDepth(), this.crawler);
        } catch (final IOException e) {
            // FIXME, implement some nice error handling
            e.printStackTrace();
        }
    }

    public Queue<Resource> getFiles() {
        return this.crawler.getFiles();
    }

    public Queue<Resource> getFailedFiles() {
        return this.crawler.getFailedFiles();
    }

    private FileSystemCrawler createFileSystemCrawler() {
        final List<ResourceFilter> filters = this.configuration.getFilters();
        final boolean searchArchives = this.configuration.isSearchArchives();

        if (searchArchives) {
            return new FileSystemArchiveAwareCrawler(filters);
        } else {
            return new FileSystemCrawler(filters);
        }
    }

}