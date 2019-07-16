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
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.TestUtils;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.file.SimpleFile;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestZIPArchiveFileCrawler {

    public void testVisitArchiveNull() {
        try {
            ZIPArchiveCrawler.visitArchive(null);
            Assert.fail("An exception is expected!");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof NullPointerException);
        }
    }

    public void testVisitArchiveNonExisting() {
        try {
            final SimpleFile nonExistingParent = TestUtils.getNonExistingZIPArchiveFileParent();
            final Set<Resource> visitArchive = ZIPArchiveCrawler.visitArchive(nonExistingParent);
            Assert.assertEquals(visitArchive.size(), 0);
        } catch (IOException ex) {
            Assert.fail("An exception is not expected!", ex);
        }
    }

    public void testVisitArchive() {
        try {
            final SimpleFile existingParent = TestUtils.getExistingZIPArchiveFileParent();
            final Set<Resource> visitArchive = ZIPArchiveCrawler.visitArchive(existingParent);
            Assert.assertEquals(visitArchive.size(), 10);

            // TODO test concrete results

        } catch (IOException ex) {
            Assert.fail("An exception is not expected!", ex);
        }
    }

}
