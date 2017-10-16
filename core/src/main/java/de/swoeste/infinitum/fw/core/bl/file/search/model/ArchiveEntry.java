/*
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
package de.swoeste.infinitum.fw.core.bl.file.search.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.zeroturnaround.zip.ZipUtil;

/**
 * @author swoeste
 */
public class ArchiveEntry extends AbstractResource {

    private static final String DELIMITER = "!";   //$NON-NLS-1$

    private final String        archiveEntryPath;
    private final String        archiveEntryName;

    /**
     * Constructor for a new ArchiveEntry.
     *
     * @param archivePath
     * @param archiveEntryPath
     */
    public ArchiveEntry(final Path archivePath, final String archiveEntryPath) {
        super(archivePath);
        this.archiveEntryPath = archiveEntryPath;
        this.archiveEntryName = extractName(archiveEntryPath);
    }

    /** {@inheritDoc} */
    @Override
    public String getFileName() {
        return this.archiveEntryName;
    }

    /** {@inheritDoc} */
    @Override
    public Path getFilePath() {
        return Paths.get(super.getFilePathAsString(), DELIMITER, this.archiveEntryPath);
    }

    /** {@inheritDoc} */
    @Override
    public String getFilePathAsString() {
        return super.getFilePathAsString() + DELIMITER + this.archiveEntryPath;
    }

    private final String extractName(final String path) {
        final String[] split = path.split("/"); //$NON-NLS-1$
        return split[split.length - 1];
    }

    /** {@inheritDoc} */
    @Override
    public String getContentAsStringInternal() throws IOException {
        final byte[] bytes = ZipUtil.unpackEntry(super.getFilePath().toFile(), this.archiveEntryPath);
        return new String(bytes, getEncoding());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("ArchiveEntry [archiveEntryPath={0}, archivePath={1}]", this.archiveEntryPath, //$NON-NLS-1$
                this.getFilePath());
    }

}
