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
package de.swoeste.infinitum.fw.core.bl.file.search.resource.archive;

import java.io.IOException;
import java.io.InputStream;

import org.zeroturnaround.zip.ZipUtil;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
public class ZIPArchiveFile extends AbstractArchiveFile {

    /**
     * Constructor for a new ZIPArchiveFile.
     *
     * @param parent
     * @param archiveEntryType
     * @param archiveEntryPath
     * @param archiveEntryName
     */
    public ZIPArchiveFile(final Resource parent, final String archiveEntryPath, final String archiveEntryName) {
        super(parent, ArchiveFileType.ZIP, archiveEntryPath, archiveEntryName);
    }

    @Override
    protected byte[] getContentAsByteArray() throws IOException {
        final InputStream inputStream = this.getParent().getInputStream();
        // Das hier darf nicht automatisch ein ZIP sein!
        return ZipUtil.unpackEntry(inputStream, getPath());
    }

}
