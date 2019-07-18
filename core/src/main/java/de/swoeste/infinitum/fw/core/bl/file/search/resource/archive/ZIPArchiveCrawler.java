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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipEntryCallback;
import org.zeroturnaround.zip.ZipUtil;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;

/**
 * @author swoeste
 */
public class ZIPArchiveCrawler {

    private static final Logger LOG = LoggerFactory.getLogger(ZIPArchiveCrawler.class);

    public static Set<Resource> visitArchive(final Resource resource) throws IOException {
        final ZipEntryCallbackImplementation callback = new ZipEntryCallbackImplementation(resource);

        // ZipUtil.iterate(resource.getInputStream(), callback);

        ZipUtil.iterate(new ByteArrayInputStream(resource.getContentAsByteArray()), callback);

        return callback.getCollectedResources();
    }

    /**
     * @author swoeste
     */
    private static final class ZipEntryCallbackImplementation implements ZipEntryCallback {

        private final Resource      parent;
        private final Set<Resource> collectedResources;

        /**
         * Constructor for a new ZipEntryCallbackImplementation.
         *
         * @param parent
         */
        private ZipEntryCallbackImplementation(final Resource parent) {
            this.parent = parent;
            this.collectedResources = new HashSet<>();
        }

        /**
         * @return the collectedResources
         */
        public Set<Resource> getCollectedResources() {
            return this.collectedResources;
        }

        @Override
        public void process(final InputStream in, final ZipEntry zipEntry) throws IOException {
            if (!zipEntry.isDirectory()) {
                final int length = ResourceType.getMaxMagicNumberLength();
                final byte[] magicNumber = new byte[length];
                in.read(magicNumber);
                // TODO
                this.collectedResources.add(new ZIPArchiveEntry(this.parent, ResourceType.determineResourceType(magicNumber), zipEntry.getName(), zipEntry.getName()));
            }
        }
    }

}
