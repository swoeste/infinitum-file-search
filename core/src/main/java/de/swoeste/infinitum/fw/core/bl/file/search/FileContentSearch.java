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
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
public class FileContentSearch {

    // TODO java doc
    // FIXME logging!

    private static final Logger                    LOG = LoggerFactory.getLogger(FileContentSearch.class);

    private final FileContentSearchConfiguration   configuration;
    private final List<FileContentCrawler>         crawlers;
    private final List<Future<List<SearchResult>>> futures;

    /**
     * Constructor for a new FileContentSearch.
     *
     * @param configuration
     *            the configuration for this search
     */
    public FileContentSearch(final FileContentSearchConfiguration configuration) {
        this.configuration = configuration;
        this.crawlers = new ArrayList<>();
        this.futures = new ArrayList<>();
    }

    public void search() {
        final Executor executor = this.configuration.getExecutor();
        for (int i = 0; i < this.configuration.getInstances(); i++) {
            final FileContentCrawler crawler = createFileContentCrawler();
            this.crawlers.add(crawler);
            final Future<List<SearchResult>> future = executor.submit(crawler);
            this.futures.add(future);
        }
    }

    public List<SearchResult> getResult() {
        final List<SearchResult> result = new ArrayList<>();
        for (final Future<List<SearchResult>> future : this.futures) {
            try {
                final List<SearchResult> futureResult = future.get();
                result.addAll(futureResult);
            } catch (final InterruptedException ex) {
                Thread.currentThread().interrupt();
                final String msg = "Thread was interrupted while fetching results"; //$NON-NLS-1$
                LOG.error(msg, ex);
                throw new IllegalStateException(msg, ex);
            } catch (final ExecutionException ex) {
                final String msg = "An exception occured while trying to fetch results"; //$NON-NLS-1$
                LOG.error(msg, ex);
                throw new IllegalStateException(msg, ex);
            }
        }
        return Collections.unmodifiableList(result);
    }

    private FileContentCrawler createFileContentCrawler() {
        final Queue<Resource> files = this.configuration.getFiles();
        final ResourceAnalyzer analyzer = this.configuration.getAnalyzer();
        return new FileContentCrawler(files, analyzer);
    }

}
