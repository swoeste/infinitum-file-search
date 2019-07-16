/*-
 * Copyright (C) 2018 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestByteArrayUtils {

    public void testStartsWithDifferentSize() {
        final byte[] source = new byte[] { 0, 0, 0, 1, 1, 1, 1 };
        final byte[] match = new byte[] { 0, 0, 0, 1 };
        Assert.assertTrue(ByteArrayUtils.startsWith(source, match));
    }

    public void testStartsWithDifferentSizeNonMatching() {
        final byte[] source = new byte[] { 0, 0, 0, 0, 0, 0, 0 };
        final byte[] match = new byte[] { 0, 0, 0, 1 };
        Assert.assertFalse(ByteArrayUtils.startsWith(source, match));
    }

    public void testStartsWithEqualSize() {
        final byte[] source = new byte[] { 0, 0, 0, 1 };
        final byte[] match = new byte[] { 0, 0, 0, 1 };
        Assert.assertTrue(ByteArrayUtils.startsWith(source, match));
    }

    public void testStartsWithSingleElement() {
        final byte[] source = new byte[] { 0 };
        final byte[] match = new byte[] { 0 };
        Assert.assertTrue(ByteArrayUtils.startsWith(source, match));
    }

    public void testStartsWithSingleElementNonMatching() {
        final byte[] source = new byte[] { 0 };
        final byte[] match = new byte[] { 1 };
        Assert.assertFalse(ByteArrayUtils.startsWith(source, match));
    }

}
