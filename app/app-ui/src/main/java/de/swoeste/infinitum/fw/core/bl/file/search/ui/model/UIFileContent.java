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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.model;

import java.util.Collection;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;

/**
 * @author swoeste
 */
public class UIFileContent {

    private final SimpleStringProperty                               filePath;
    private final SimpleStringProperty                               fileContent;
    private final SimpleObjectProperty<Collection<Pair<Long, Long>>> resultPositions;

    public UIFileContent() {
        this(null, null, null);
    }

    public UIFileContent(final String filePath, final String fileContent, final Collection<Pair<Long, Long>> resultPositions) {
        this.filePath = new SimpleStringProperty(filePath);
        this.fileContent = new SimpleStringProperty(fileContent);
        this.resultPositions = new SimpleObjectProperty<Collection<Pair<Long, Long>>>(resultPositions);
    }

    public String getFilePath() {
        return this.filePath.get();
    }

    public void setFilePath(final String filePath) {
        this.filePath.set(filePath);
    }

    public String getFileContent() {
        return this.fileContent.get();
    }

    public void setFileContent(final String fileContent) {
        this.fileContent.set(fileContent);
    }

    public Collection<Pair<Long, Long>> getResultPositions() {
        return this.resultPositions.get();
    }

    public void setResultPositions(final Collection<Pair<Long, Long>> resultPositions) {
        this.resultPositions.set(resultPositions);
    }

    public Integer getResultPositionsCount() {
        final Collection<Pair<Long, Long>> collection = this.resultPositions.get();
        if (collection != null) {
            return collection.size();
        } else {
            return 0;
        }

    }

}
