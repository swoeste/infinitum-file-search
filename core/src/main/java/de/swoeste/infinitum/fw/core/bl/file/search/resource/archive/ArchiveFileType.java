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

/**
 * @author swoeste
 */
public enum ArchiveFileType {

    ZIP(new byte[] { (byte) 0x50, (byte) 0x4B, (byte) 0x03, (byte) 0x04 });

    final byte[] prefix;

    private ArchiveFileType(final byte[] prefix) {
        this.prefix = prefix;
    }

    public byte[] getPrefix() {
        return this.prefix;
    }

    public int getPrefixLength() {
        return this.prefix.length;
    }

}
