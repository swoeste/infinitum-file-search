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

import de.swoeste.infinitum.fw.core.bl.file.search.resource.AbstractResource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;

/**
 * This represents a concrete file within an archive.
 *
 * @author swoeste
 */
public abstract class ArchiveEntry extends AbstractResource {

    // TODO JAVADOC

    private static final String DELIMITER = "!";                                         //$NON-NLS-1$

    /**
     * Constructor for a new ArchiveEntry.
     *
     * @param archivePath
     *            the full qualified path of the archive
     * @param archiveEntryPath
     *            the full qualified path of the file within the archive
     */
    public ArchiveEntry(final Resource parent, final ResourceType archiveEntryType, final String archiveEntryPath, final String archiveEntryName) {
        super(parent, archiveEntryType, archiveEntryPath, archiveEntryName);
    }

    // /**
    // * @return the archiveFileType
    // */
    // public ResourceType getArchiveFileType() {
    // return this.archiveFileType;
    // }

    public String getXPath() {
        return getParent().getPath() + DELIMITER + this.getPath();
    }

    /** {@inheritDoc} */
    @Override
    public String getContentAsStringInternal() throws IOException {
        return new String(getContentAsByteArray(), getEncoding());
    }

    // /** {@inheritDoc} */
    // @Override
    // public InputStream getInputStream() throws IOException {
    // return new ByteArrayInputStream(getContentAsByteArray());
    // }
    //
    // @Override
    // protected abstract byte[] getContentAsByteArray() throws IOException;

    @Override
    public String toString() {
        // TODO rename method
        // return "AbstractArchiveFile [archiveFileType=" + this.archiveFileType
        // + ", getXPath()=" + this.getXPath() + "]";

        return "";
    }

}
