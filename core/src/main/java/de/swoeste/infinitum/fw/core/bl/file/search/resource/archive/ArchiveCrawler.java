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
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;

/**
 * @author swoeste
 */
public class ArchiveCrawler {

    private final Executor executor;

    public ArchiveCrawler(final Executor executor) {
        this.executor = executor;
    }

    public Set<Resource> visitArchive(final Resource resource) throws IOException {
        final ResourceType archiveFileType = getArchiveFileType(resource);

        final Set<Resource> visitedArchives = new HashSet<>();
        if (ResourceType.ZIP.equals(archiveFileType)) {
            visitedArchives.addAll(ZIPArchiveCrawler.visitArchive(resource));
        }

        // ... add further archive types here ...

        final Set<Resource> result = new HashSet<>();
        result.addAll(visitedArchives);

        for (Resource visitedArchive : visitedArchives) {
            result.addAll(visitArchive(visitedArchive));
        }

        return result;
    }

    private ResourceType getArchiveFileType(final Resource resource) throws IOException {

        Validate.notNull(resource, "The 'resource' may not be null!"); //$NON-NLS-1$

        return resource.getType();

        // final int maxMagicNumberLength =
        // ResourceType.getMaxMagicNumberLength();
        // final byte[] magicNumbers = extracted(maxMagicNumberLength, path); //
        // TODO
        // // implement
        //
        // // SimpleFile.getContentAsByteArray(...)
        // // Da wird schon das benötigte gemacht ...
        //
        // // FIXME
        //
        // return ResourceType.determineResourceType(magicNumbers);
        //
        // return ResourceType.determineArchiveFileType(resource);

        // return ResourceType.UNKNOWN;
    }

}
