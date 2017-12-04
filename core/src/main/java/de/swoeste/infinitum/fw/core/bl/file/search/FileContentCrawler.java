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

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;

/**
 * @author swoeste
 */
public class FileContentCrawler implements Callable<List<SearchResult>> {

    // TODO java doc

    private final Queue<Resource>  files;
    private final ResourceAnalyzer analyzer;

    /**
     * Constructor for a new FileContentCrawler.
     *
     * @param files
     * @param analyzer
     *            a analyzer to scan the file content
     */
    public FileContentCrawler(final Queue<Resource> files, final ResourceAnalyzer analyzer) {
        this.files = files;
        this.analyzer = analyzer;
    }

    /** {@inheritDoc} */
    @Override
    public List<SearchResult> call() throws Exception {
        final List<SearchResult> result = new ArrayList<>();
        while (!this.files.isEmpty()) {
            final Resource file = this.files.poll();
            if ((file != null)) {
                // TODO catch exception? IllegalArgumentException and create a
                // list with failed objects?
                result.addAll(searchContent(file));
            }
        }
        return result;
    }

    private List<SearchResult> searchContent(final Resource file) {
        return this.analyzer.analyze(file);
    }

}
