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
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
public class ArchivFileTypeSelector {

    private static final Logger LOG       = LoggerFactory.getLogger(ArchivFileTypeSelector.class);

    private static final String READ_MODE = "r";

    private ArchivFileTypeSelector() {
        // hidden
    }

    // TODO change to abstract resource?

    public static ArchiveFileType determineArchiveEntryType(final Path path) throws IOException {

        try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), READ_MODE)) {
            final byte[] magicNumbers = new byte[ArchiveFileType.ZIP.getPrefixLength()];
            raf.read(magicNumbers);
            if (Arrays.equals(magicNumbers, ArchiveFileType.ZIP.getPrefix())) {
                LOG.debug("Identified ZIP: {}", path); //$NON-NLS-1$
                return ArchiveFileType.ZIP;
            }
        } catch (IOException ex) {
            LOG.error("Unable to check if the file \"{}\" is an archive", path, ex); //$NON-NLS-1$
            throw ex;
        }

        return null;

    }

    // TODO wir brauchen noch was für zip in zip ...
    public static ArchiveFileType determineArchiveFileType(final Resource archiveEntry) throws IOException {
        // TODO

        return null;
    }

}
