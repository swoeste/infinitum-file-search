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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.App;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIPath;

/**
 * @author swoeste
 */
public class SearchFileContentController {

    @FXML
    private TableView<UIPath>           tableView;
    @FXML
    private TableColumn<UIPath, String> tableColumn;
    @FXML
    private TextArea                    textArea;

    private App                         app;

    public SearchFileContentController() {

    }

    /**
     * Fills all text fields to show details about the person. If the specified
     * person is null, all text fields are cleared.
     *
     * @param person
     *            the person or null
     */
    private void showDetails(final UIPath path) {
        if (path != null) {
            this.textArea.setText(path.getPath().getValue());
        } else {
            this.textArea.setText("");
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        this.tableColumn.setCellValueFactory(cellData -> cellData.getValue().getPath());
        // lastNameColumn.setCellValueFactory(cellData ->
        // cellData.getValue().lastNameProperty());

        showDetails(null);

        this.tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showDetails(newValue));

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param app
     */
    public void setApp(final App app) {
        this.app = app;

        // Add observable list data to the table
        this.tableView.setItems(app.getPersonData());
    }

}
