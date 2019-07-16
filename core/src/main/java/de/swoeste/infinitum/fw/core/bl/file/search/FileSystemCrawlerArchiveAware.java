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
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.archive.ArchiveCrawler;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.file.SimpleFile;

/**
 * @author swoeste
 */
public class FileSystemCrawlerArchiveAware extends FileSystemCrawler {

    // TODO java doc
    // FIXME logging!

    private static final Logger  LOG = LoggerFactory.getLogger(FileSystemCrawlerArchiveAware.class);

    private final ArchiveCrawler crawler;

    /**
     * Constructor for a new FileSystemArchiveAwareCrawler.
     *
     * @param filters
     */
    public FileSystemCrawlerArchiveAware(final Executor executor, final List<ResourceFilter> filters) {
        super(filters);
        this.crawler = new ArchiveCrawler(executor);
    }

    /** {@inheritDoc} */
    @Override
    public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) throws IOException {
        if (path.toFile().isFile()) {
            final ResourceType type = SimpleFile.determineType(path);
            final Resource currentFile = new SimpleFile(type, path);

            try {
                final Set<Resource> visitArchive = this.crawler.visitArchive(currentFile);
                for (Resource resource : visitArchive) {
                    addResource(resource);
                }
            } catch (Exception ex) {
                // TODO exception

                LOG.error("Unable to visit possible archive file \"{}\"", path, ex); //$NON-NLS-1$
                // re-throw the exception, it will be handled by the
                // 'visitFileFailed' method in super class
                throw new IOException("", ex);
            }

            addResource(currentFile);
        }
        return FileVisitResult.CONTINUE;
    }

}
