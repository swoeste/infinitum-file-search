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
package de.swoeste.infinitum.fw.core.bl.file.search.resource.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.AbstractResource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;

/**
 * This represents a concrete file from a file storage.
 *
 * @author swoeste
 */
public class SimpleFile extends AbstractResource {

    // TODO umbenennen in FileResource ?

    // TODO java doc
    // FIXME logging!

    private static final Logger LOG       = LoggerFactory.getLogger(SimpleFile.class);

    private static final String READ_MODE = "r";                                                                                                                                                                                                                                                                            //$NON-NLS-1$

    /**
     * Constructor for a new SimpleFile.
     *
     * @param path
     *            the full qualified path representing this file (including the
     *            file name)
     */
    public SimpleFile(final ResourceType type, final Path path) {
        super(null, type, path.toAbsolutePath().toString(), path.toAbsolutePath().getFileName().toString());
    }

    /** {@inheritDoc} */
    @Override
    public String getContentAsStringInternal() throws IOException {
        final byte[] content = getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, getEncoding());
        } else {
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContentAsByteArray() throws IOException {
        final Path path = Paths.get(getPath());
        final File file = path.toFile();

        if (file.exists()) {
            try (final InputStream in = new FileInputStream(file)) {
                return IOUtils.toByteArray(in);
            }
        }

        return new byte[0];
    }

    /** {@inheritDoc} */
    @Override
    public byte[] getContentAsByteArray(final int length) throws IOException {
        final Path path = Paths.get(getPath());
        return extracted(length, path);
    }

    private static byte[] extracted(final int length, final Path path) throws IOException {
        final File file = path.toFile();

        if (file.exists()) {
            try (final RandomAccessFile raf = new RandomAccessFile(file, READ_MODE)) {
                final byte[] magicNumbers = new byte[length];
                raf.read(magicNumbers);
                return magicNumbers;
            } catch (IOException ex) {
                LOG.error("Unable to check if the file \"{}\" is an archive", path, ex); //$NON-NLS-1$
                throw ex;
            }
        }

        return new byte[0];
    }

    public static ResourceType determineType(final Path path) throws IOException {
        Validate.notNull(path, "The 'path' may not be null!"); //$NON-NLS-1$

        final int maxMagicNumberLength = ResourceType.getMaxMagicNumberLength();
        final byte[] magicNumbers = extracted(maxMagicNumberLength, path); // TODO
                                                                           // implement

        // SimpleFile.getContentAsByteArray(...)
        // Da wird schon das benötigte gemacht ...

        // FIXME

        return ResourceType.determineResourceType(magicNumbers);
    }

}
