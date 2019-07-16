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

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.TestUtils;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.SimpleExecutor;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.file.SimpleFile;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestArchiveFileCrawler {

    // TODO REWORK THIS TEST!

    public void testVisitExistingSimpleFileArchive() {
        try {
            final ArchiveCrawler crawler = new ArchiveCrawler(SimpleExecutor.getInstance());
            final SimpleFile existingFile = TestUtils.getExistingZIPArchiveFileParent();
            final Set<Resource> visitArchive = crawler.visitArchive(existingFile);
            Assert.assertFalse(visitArchive.isEmpty());
            Assert.assertEquals(visitArchive.size(), 15);

            for (Resource resource : visitArchive) {
                resource.getPath();
            }

            // TODO more concrete test

        } catch (Exception ex) {
            Assert.fail("An exception is not expected!", ex);
        }
    }

    public void testVisitExistingSimpleFileNoneArchive() {
        try {
            final ArchiveCrawler crawler = new ArchiveCrawler(SimpleExecutor.getInstance());
            final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
            final Set<Resource> visitArchive = crawler.visitArchive(existingFile);
            Assert.assertTrue(visitArchive.isEmpty());
        } catch (Exception ex) {
            Assert.fail("An exception is not expected!", ex);
        }
    }

    public void testVisitNonExistingSimpleFile() {
        try {
            final ArchiveCrawler crawler = new ArchiveCrawler(SimpleExecutor.getInstance());
            final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
            final Set<Resource> visitArchive = crawler.visitArchive(nonExistingFile);
            Assert.assertTrue(visitArchive.isEmpty());
        } catch (Exception ex) {
            Assert.fail("An exception is not expected!", ex);
        }
    }

}
