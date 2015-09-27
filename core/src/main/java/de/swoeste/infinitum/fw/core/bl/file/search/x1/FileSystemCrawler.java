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
package de.swoeste.infinitum.fw.core.bl.file.search.x1;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.File;

/**
 * @author swoeste
 */
public class FileSystemCrawler extends SimpleFileVisitor<Path> {

    private final Queue<Resource> files;
    private final Queue<Resource> failedFiles;

    /**
     * Constructor for a new FileVisitor.
     *
     * @param files
     */
    public FileSystemCrawler() {
        this.files = new ConcurrentLinkedQueue<>();
        this.failedFiles = new ConcurrentLinkedQueue<>();
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        this.files.add(new File(file));
        return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        this.failedFiles.add(new File(file));
        return FileVisitResult.CONTINUE;
    }

    /**
     * @return the collectedFiles
     */
    public Queue<Resource> getFiles() {
        return this.files;
    }

    /**
     * @return the failedFiles
     */
    public Queue<Resource> getFailedFiles() {
        return this.failedFiles;
    }

}
