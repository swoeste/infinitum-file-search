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

import org.testng.annotations.Test;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestArchiveFileTypeSelector {

    // TODO der test muss sich ändern zu
    // -> ResourceType
    // -> SimpleFile.test()
    // -> ArchiveFileType.test()

    // TODO add further tests for other methods?!

    // public void testDetermineArchiveFileTypeNull() {
    // try {
    // ResourceType.determineResourceType((Resource) null);
    // Assert.fail("An exception is expected!");
    // } catch (Exception ex) {
    // Assert.assertTrue(ex instanceof NullPointerException);
    // }
    // }

    // public void testDetermineArchiveFileTypeNonExisting() {
    //
    // // TODO do we really want to behave it this way?
    //
    // try {
    // final SimpleFile nonExistingParent = TestUtils.getNonExistingZIPArchiveFileParent();
    // final ResourceType type = ResourceType.determineResourceType(nonExistingParent);
    // Assert.assertEquals(type, ResourceType.UNKNOWN);
    // } catch (IOException ex) {
    // Assert.fail("An exception is not expected!", ex);
    // }
    // }

    // public void testDetermineArchiveFileTypeUNKNOWN() {
    //
    // // TODO korrekt implementation
    //
    // try {
    // final SimpleFile zipFile = TestUtils.getExistingZIPArchiveFileParent();
    // final ResourceType type = ResourceType.determineResourceType(zipFile);
    // Assert.assertEquals(type, ResourceType.UNKNOWN);
    // } catch (IOException ex) {
    // Assert.fail("An exception is not expected!", ex);
    // }
    // }
    //
    // public void testDetermineArchiveFileTypeZIP() {
    // try {
    // final SimpleFile zipFile = TestUtils.getExistingZIPArchiveFileParent();
    // final ResourceType type = ResourceType.determineResourceType(zipFile);
    // Assert.assertEquals(type, ResourceType.ZIP);
    // } catch (IOException ex) {
    // Assert.fail("An exception is not expected!", ex);
    // }
    // }

}
