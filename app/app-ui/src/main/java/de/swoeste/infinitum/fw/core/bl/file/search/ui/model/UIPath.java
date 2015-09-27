/*
 * Copyright (C) 2015 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author swoeste
 */
public class UIPath {

    private final StringProperty path;

    /**
     * Default constructor.
     */
    public UIPath() {
        this(null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param firstName
     * @param lastName
     */
    public UIPath(final String path) {
        this.path = new SimpleStringProperty(path);

    }

    /**
     * @return the path
     */
    public StringProperty getPath() {
        return this.path;
    }

    /**
     * @param path
     */
    public void setPath(final String path) {
        this.path.set(path);
    }

}
