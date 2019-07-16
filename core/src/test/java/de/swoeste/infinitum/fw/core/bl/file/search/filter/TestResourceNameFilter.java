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
package de.swoeste.infinitum.fw.core.bl.file.search.filter;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestResourceNameFilter {

    public void testAcceptName() {
        final Resource mock = createResourceWithName("MySimpleName");
        final ResourceNameFilter filter = new ResourceNameFilter("MySimpleName");
        assertTrue(filter.accept(mock));
    }

    public void testAcceptNameNoMatch() {
        final Resource mock = createResourceWithName("MySimpleName");
        final ResourceNameFilter filter = new ResourceNameFilter("mysimplename");
        assertFalse(filter.accept(mock));
    }

    public void testAcceptNameWithRegEx() {
        final Resource mock = createResourceWithName("MySimpleName");
        final ResourceNameFilter filter = new ResourceNameFilter(".*");
        assertTrue(filter.accept(mock));
    }

    public void testAcceptNameWithEmptyFilterString() {
        final Resource mock = createResourceWithName("MySimpleName");
        final ResourceNameFilter filter = new ResourceNameFilter("");
        assertFalse(filter.accept(mock));
    }

    public void testAcceptNameWithRestrictedRegEx() {
        final Resource mock = createResourceWithName("MySimpleName");
        final ResourceNameFilter filter = new ResourceNameFilter(".*Name.*");
        assertTrue(filter.accept(mock));
    }

    public void testAcceptNameWithNull() {
        final Resource mock = createResourceWithName(null);
        final ResourceNameFilter filter = new ResourceNameFilter("MySimpleName");
        assertFalse(filter.accept(mock));
    }

    public void testAcceptNameWithEmptryString() {
        final Resource mock = createResourceWithName("");
        final ResourceNameFilter filter = new ResourceNameFilter("MySimpleName");
        assertFalse(filter.accept(mock));
    }

    private Resource createResourceWithName(final String name) {
        final Resource mock = createNiceMock(Resource.class);
        expect(mock.getName()).andReturn(name).anyTimes();
        replay(mock);
        return mock;
    }

}
