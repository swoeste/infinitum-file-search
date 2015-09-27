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
package de.swoeste.infinitum.fw.core.bl.file.search;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Queue;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.x1.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.x1.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.x2.FileContentSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.x2.FileContentSearchConfiguration;

/**
 * @author swoeste
 */
@Test
public class FileContentCrawlerTest {

    public void testFileContentCrawler() {
        // file system crawler
        final Path directory = Paths.get("E:\\Entwicklung\\source\\infinitum-file-search\\core\\src\\test\\resources\\root");

        FileSystemSearchConfiguration configuration = new FileSystemSearchConfiguration(directory, true);
        FileSystemSearch search = new FileSystemSearch(configuration);
        search.search();
        Queue<Resource> files = search.getFiles();
        Queue<Resource> failedFiles = search.getFailedFiles();

        for (Resource iFile : files) {
            System.out.println(iFile);
        }

        Assert.assertTrue(failedFiles.isEmpty());

        // file content crawler

        FileContentSearchConfiguration configuration2 = new FileContentSearchConfiguration(files, Collections.emptyList(), "", RunnableExecutor.getInstance(), 4);
        FileContentSearch search2 = new FileContentSearch(configuration2);
        search2.search();
        search2.getResult(); // TODO
    }
}
