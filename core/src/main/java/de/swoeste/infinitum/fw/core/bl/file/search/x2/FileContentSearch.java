/*
 * Copyright (C) 2015 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.x2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import de.swoeste.infinitum.fw.core.bl.file.search.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;

/**
 * @author swoeste
 */
public class FileContentSearch {

    private final FileContentSearchConfiguration   configuration;
    private final List<FileContentCrawler>         crawlers;
    private final List<Future<List<SearchResult>>> futures;

    /**
     * Constructor for a new FileContentSearch.
     *
     * @param configuration
     * @param crawler
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
        for (Future<List<SearchResult>> future : this.futures) {
            try {
                final List<SearchResult> futureResult = future.get();
                System.out.println("finished");
                result.addAll(futureResult);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
