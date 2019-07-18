/*-
 * Copyright (C) 2019 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.resource.archive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.zeroturnaround.zip.ZipUtil;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;

/**
 * @author swoeste
 */
public class ZIPArchiveEntry extends ArchiveEntry {

    /**
     * Constructor for a new ZIPArchiveEntry.
     *
     * @param parent
     * @param archiveEntryType
     * @param archiveEntryPath
     * @param archiveEntryName
     */
    public ZIPArchiveEntry(final Resource parent, final ResourceType archiveEntryType, final String archiveEntryPath, final String archiveEntryName) {
        super(parent, archiveEntryType, archiveEntryPath, archiveEntryName);
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContentAsByteArray() throws IOException {
        final byte[] zipArchiveContent = getParent().getContentAsByteArray();
        final ByteArrayInputStream stream = new ByteArrayInputStream(zipArchiveContent);
        final byte[] zipArchiveEntryContent = ZipUtil.unpackEntry(stream, getPath());

        if (zipArchiveEntryContent == null) {
            return new byte[0];
        }

        return zipArchiveEntryContent;
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContentAsByteArray(final int length) throws IOException {

        // FIXME TODO this is very inperformant as the full stream is read

        final byte[] zipArchiveContent = getParent().getContentAsByteArray();
        final ByteArrayInputStream stream = new ByteArrayInputStream(zipArchiveContent);
        final byte[] zipArchiveEntryContent = ZipUtil.unpackEntry(stream, getPath());

        if (zipArchiveEntryContent == null) {
            return new byte[0];
        }

        return Arrays.copyOf(zipArchiveEntryContent, length);
    }

}
