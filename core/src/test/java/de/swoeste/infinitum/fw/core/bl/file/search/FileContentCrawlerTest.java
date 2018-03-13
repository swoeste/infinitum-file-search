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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceContentAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.SimpleExecutor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceNameFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class FileContentCrawlerTest extends AbstractCrawlerTest {

    public void testFileContentCrawler() {
        final Queue<Resource> files = searchForFiles(".*");

        final String exp = "1-1";
        final FileContentSearchConfiguration configuration = new FileContentSearchConfiguration(files, new ResourceContentAnalyzer(exp), SimpleExecutor.getInstance(), 4);
        final FileContentSearch search = new FileContentSearch(configuration);
        search.search();

        final List<SearchResult> result = search.getResult();

        for (SearchResult searchResult : result) {
            System.out.println(searchResult);
        }

        Assert.assertEquals(result.size(), 2);

        for (final SearchResult searchResult : result) {
            Assert.assertTrue(searchResult.getContent().equals(exp));
        }
    }

    public void testFileContentCrawlerWithWildcard() {
        final Queue<Resource> files = searchForFiles(".*.something");

        final String exp = ".*";
        final FileContentSearchConfiguration configuration = new FileContentSearchConfiguration(files, new ResourceContentAnalyzer(exp), SimpleExecutor.getInstance(), 4);
        final FileContentSearch search = new FileContentSearch(configuration);
        search.search();

        final List<SearchResult> result = search.getResult();

        for (final SearchResult searchResult : result) {
            Assert.assertTrue(searchResult.getContent().startsWith("1-"));
        }

        Assert.assertEquals(result.size(), 5);
    }

    public void testFileContentCrawlerWithMultipleMatchesInAFile() {
        final Queue<Resource> files = searchForFiles(".*.*multiple");

        final String exp = "Lorem";
        final FileContentSearchConfiguration configuration = new FileContentSearchConfiguration(files, new ResourceContentAnalyzer(exp), SimpleExecutor.getInstance(), 4);
        final FileContentSearch search = new FileContentSearch(configuration);
        search.search();

        final List<SearchResult> result = search.getResult();

        Assert.assertEquals(result.size(), 6);

        for (final SearchResult searchResult : result) {
            Assert.assertTrue(searchResult.getContent().equals(exp));
        }
    }

    public void testFileContentCrawlerWithLineBreak() {
        final Queue<Resource> files = searchForFiles(".*.*multiple");

        final String exp = "rebum\\.\\s*Stet";
        final FileContentSearchConfiguration configuration = new FileContentSearchConfiguration(files, new ResourceContentAnalyzer(exp), SimpleExecutor.getInstance(), 4);
        final FileContentSearch search = new FileContentSearch(configuration);
        search.search();

        final List<SearchResult> result = search.getResult();

        Assert.assertEquals(result.size(), 3);

        for (final SearchResult searchResult : result) {
            Assert.assertTrue(searchResult.getContent().startsWith("rebum"));
            Assert.assertTrue(searchResult.getContent().endsWith("Stet"));
        }
    }

    private Queue<Resource> searchForFiles(final String pattern) {
        final Path directory = getTestFolder();

        final List<ResourceFilter> filters = new ArrayList<>();
        filters.add(new ResourceNameFilter(pattern));

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, filters, true);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        Assert.assertFalse(files.isEmpty());
        Assert.assertTrue(failedFiles.isEmpty());

        return files;
    }

}
