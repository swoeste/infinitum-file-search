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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceNameFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class FileSystemCrawlerTest extends AbstractCrawlerTest {

    public void testFileSystemCrawler() {
        final Path directory = getTestFolder();

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, Collections.emptyList(), false);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        Assert.assertFalse(files.isEmpty());
        Assert.assertTrue(failedFiles.isEmpty());
    }

    public void testFileSystemArchiveAwareCrawler() {
        final Path directory = getTestFolder();

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, Collections.emptyList(), true);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        Assert.assertFalse(files.isEmpty());
        Assert.assertTrue(failedFiles.isEmpty());
    }

    public void testFileSystemCrawlerWithResourceNameFilter() {
        final Path directory = getTestFolder();

        final List<ResourceFilter> filters = new ArrayList<>();
        filters.add(new ResourceNameFilter("1.*"));

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, filters, false);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        for (final Resource iFile : files) {
            final String fileName = iFile.getName();
            Assert.assertTrue(fileName.startsWith("1"), "Expected resource to start with '1' but found " + fileName);
        }

        Assert.assertTrue(failedFiles.isEmpty());
    }

    public void testFileSystemArchiveAwareCrawlerWithResourceNameFilter() {
        final Path directory = getTestFolder();

        final List<ResourceFilter> filters = new ArrayList<>();
        filters.add(new ResourceNameFilter("1.*"));

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, filters, true);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        for (final Resource iFile : files) {
            final String fileName = iFile.getName();
            Assert.assertTrue(fileName.startsWith("1"), "Expected resource to start with '1' but found " + fileName);
        }

        Assert.assertTrue(failedFiles.isEmpty());
    }

    public void testFileSystemCrawlerWithResourcePathFilter() {
        final Path directory = getTestFolder();

        final List<ResourceFilter> filters = new ArrayList<>();
        filters.add(new ResourcePathFilter(".*" + "\\" + File.separator + "root" + "\\" + File.separator + "5" + ".*"));

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, filters, false);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        for (final Resource file : files) {
            final String filePath = file.getPathAsString();
            final String expectedPath = File.separator + "root" + File.separator + "5";
            Assert.assertTrue(filePath.contains(expectedPath), "Expected resource to contain '" + expectedPath + "' but found " + filePath);
        }

        Assert.assertTrue(failedFiles.isEmpty());
    }

    public void testFileSystemArchiveAwareCrawlerWithResourcePathFilter() {
        final Path directory = getTestFolder();

        final List<ResourceFilter> filters = new ArrayList<>();
        filters.add(new ResourcePathFilter(".*" + "\\" + File.separator + "root" + "\\" + File.separator + "5" + ".*"));

        final FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, filters, true);
        final FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();

        final Queue<Resource> files = search.getFiles();
        final Queue<Resource> failedFiles = search.getFailedFiles();

        for (final Resource file : files) {
            final String filePath = file.getPathAsString();
            final String expectedPath = File.separator + "root" + File.separator + "5";
            Assert.assertTrue(filePath.contains(expectedPath), "Expected resource to contain '" + expectedPath + "' but found " + filePath);
        }

        Assert.assertTrue(failedFiles.isEmpty());
    }

}
