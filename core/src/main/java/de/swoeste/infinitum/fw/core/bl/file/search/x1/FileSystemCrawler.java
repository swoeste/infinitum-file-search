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
import java.text.MessageFormat;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SimpleFile;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;

/**
 * @author swoeste
 */
public class FileSystemCrawler extends SimpleFileVisitor<Path> {

    private final List<ResourceFilter> filters;
    private final Queue<Resource>      files;
    private final Queue<Resource>      failedFiles;

    /**
     * Constructor for a new FileSystemCrawler.
     *
     * @param filter
     */
    public FileSystemCrawler(final List<ResourceFilter> filters) {
        this.filters = filters;
        this.files = new ConcurrentLinkedQueue<>();
        this.failedFiles = new ConcurrentLinkedQueue<>();
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        final Resource currentFile = new SimpleFile(file);
        if (accept(currentFile)) {
            this.files.add(currentFile);
        }
        return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
        this.failedFiles.add(new SimpleFile(file));
        return FileVisitResult.CONTINUE;
    }

    /**
     * @return the filters
     */
    public List<ResourceFilter> getFilters() {
        return this.filters;
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

    protected final boolean accept(final Resource resource) {
        for (ResourceFilter filter : this.filters) {
            if (!filter.accept(resource)) {
                return false;
            }
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("FileSystemCrawler [filters={0}, files={1}, failedFiles={2}]",  //$NON-NLS-1$
                this.filters, this.files, this.failedFiles);
    }

}
