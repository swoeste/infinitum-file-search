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
package de.swoeste.infinitum.fw.core.bl.file.search.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.util.ByteArrayUtils;

/**
 * @author swoeste
 */
public enum ResourceType {

    // TODO java doc
    // FIXME logging!

    ZIP(new byte[] { (byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04 }), //
    UNKNOWN(new byte[0]);

    // TODO name of unknown => NON_ARCHIVE, SIMPLE_FILE?

    private static final Logger LOG = LoggerFactory.getLogger(ResourceType.class);

    private static int          maxMagicNumberLength;

    private final byte[]        prefix;

    private ResourceType(final byte[] prefix) {
        this.prefix = prefix;
    }

    public byte[] getPrefix() {
        return this.prefix;
    }

    public int getPrefixLength() {
        return this.prefix.length;
    }

    public static int getMaxMagicNumberLength() {
        if (maxMagicNumberLength == 0) {
            int maxLength = 0;
            for (ResourceType archiveFileType : values()) {
                if (archiveFileType.getPrefixLength() >= maxLength) {
                    maxLength = archiveFileType.getPrefixLength();
                }
            }
            maxMagicNumberLength = maxLength;
        }
        return maxMagicNumberLength;
    }

    public static ResourceType determineResourceType(final byte[] magicNumbers) {
        // TODO handle null here with unknown?

        for (ResourceType type : ResourceType.values()) {
            if (ByteArrayUtils.startsWith(magicNumbers, type.getPrefix())) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Identified {} as {}", magicNumbers, type.name()); //$NON-NLS-1$
                }
                return type;
            }
        }

        LOG.debug("Identified {} as {} by default", magicNumbers, ResourceType.UNKNOWN); //$NON-NLS-1$
        return ResourceType.UNKNOWN;
    }

}
