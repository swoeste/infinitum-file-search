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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.view;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.NavigationActions.SelectionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFileContent;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFilePath;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.task.SearchContentTask;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.task.SearchFileTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * This class controlls the behaviour of all nodes/elements in the UI.
 *
 * @author swoeste
 */
public class FileSearchViewController {

    private static final Logger      LOG = LoggerFactory.getLogger(FileSearchViewController.class);

    private Stage                    primaryStage;
    private Executor                 executor;

    @FXML
    private TextField                searchFileTextPath;
    @FXML
    private TextField                searchFileTextFile;
    @FXML
    private CheckBox                 searchFileCheckBoxIncludeSubDirectories;
    @FXML
    private CheckBox                 searchFileCheckBoxIncludeArchives;
    @FXML
    private Button                   searchFileButton;
    @FXML
    private TableView<UIFilePath>    searchFileTable;
    @FXML
    private TableColumn<?, ?>        searchFileTableColumnFilePath;

    @FXML
    private TextField                searchContentTextPath;
    @FXML
    private TextField                searchContentTextFile;
    @FXML
    private TextField                searchContentTextContent;
    @FXML
    private CheckBox                 searchContentCheckBoxIncludeSubDirectories;
    @FXML
    private CheckBox                 searchContentCheckBoxIncludeArchives;
    @FXML
    private Button                   searchContentButton;
    @FXML
    private TableView<UIFileContent> searchContentTable;
    @FXML
    private TableColumn<?, ?>        searchContentTableColumnFilePath;
    @FXML
    private TableColumn<?, ?>        searchContentTableColumnResultPositionsCount;
    @FXML
    private CodeArea                 searchContentSelectedFilePathContent;

    @FXML
    private Label                    searchStatusProgressMessage;
    @FXML
    private ProgressBar              searchStatusProgress;

    @FXML
    void initialize() {
        // display line numbers in code area
        this.searchContentSelectedFilePathContent.setParagraphGraphicFactory(LineNumberFactory.get(this.searchContentSelectedFilePathContent));
    }

    public void setPrimaryStage(final Stage stage) {
        this.primaryStage = stage;
    }

    public void setExecutor(final Executor executor) {
        this.executor = executor;
    }

    @FXML
    void searchFileBrowse(final ActionEvent event) {
        final String directory = browseDirectory();
        this.searchFileTextPath.setText(directory);
    }

    @FXML
    void searchFile(final ActionEvent event) {
        this.searchFileButton.setDisable(true);
        this.searchFileTable.getItems().clear();

        final SearchFileTask task = new SearchFileTask(Arrays.asList(this.searchFileButton, this.searchContentButton), this.searchFileTable.getItems(),
                this.searchFileTextPath.getText(), this.searchFileTextFile.getText(), this.searchFileCheckBoxIncludeArchives.isSelected(),
                this.searchFileCheckBoxIncludeSubDirectories.isSelected(), this.executor);

        this.searchStatusProgress.progressProperty().bind(task.progressProperty());
        this.searchStatusProgressMessage.textProperty().bind(task.messageProperty());

        this.executor.submit(task);
    }

    @FXML
    void searchFileTableOnKeyPressed(final KeyEvent event) {
        if (event.isControlDown() && (event.getCode() == KeyCode.C)) {
            final UIFilePath selectedItem = this.searchFileTable.getSelectionModel().getSelectedItem();
            pasteToClipboard(selectedItem.getFilePath());
        }
    }

    @FXML
    void searchContentBrowse(final ActionEvent event) {
        final String directory = browseDirectory();
        this.searchContentTextPath.setText(directory);
    }

    @FXML
    void searchContent(final ActionEvent event) {
        this.searchContentTable.getItems().clear();

        final SearchContentTask task = new SearchContentTask(Arrays.asList(this.searchFileButton, this.searchContentButton), this.searchContentTable.getItems(),
                this.searchContentTextPath.getText(), this.searchContentTextFile.getText(), this.searchContentTextContent.getText(),
                this.searchContentCheckBoxIncludeArchives.isSelected(), this.searchContentCheckBoxIncludeSubDirectories.isSelected(), this.executor);

        this.searchStatusProgress.progressProperty().bind(task.progressProperty());
        this.searchStatusProgressMessage.textProperty().bind(task.messageProperty());

        this.executor.submit(task);
    }

    @FXML
    void searchContentTableOnMouseClicked(final MouseEvent event) {
        final UIFileContent selectedItem = this.searchContentTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // set content
            final String fileContent = StringUtils.defaultString(selectedItem.getFileContent(), "");
            final int minStart = 0;
            final int maxEnd = fileContent.length() - 1;

            this.searchContentSelectedFilePathContent.replaceText(fileContent);

            // set style
            for (Pair<Long, Long> pair : selectedItem.getResultPositions()) {
                final int currStart = pair.getKey().intValue();
                final int currEnd = pair.getValue().intValue();

                if ((currStart >= minStart) && (currStart <= currEnd) && (currEnd <= maxEnd)) {
                    this.searchContentSelectedFilePathContent.setStyleClass(currStart, currEnd, "highlight"); //$NON-NLS-1$
                } else {
                    LOG.warn("Unable to mark start {} and end {} in a string of length {}", currStart, currEnd, maxEnd);
                }
            }

            this.searchContentSelectedFilePathContent.start(SelectionPolicy.CLEAR);
        }
    }

    @FXML
    void searchContentTableOnKeyPressed(final KeyEvent event) {
        if (event.isControlDown() && (event.getCode() == KeyCode.COPY)) {
            final UIFileContent selectedItem = this.searchContentTable.getSelectionModel().getSelectedItem();
            pasteToClipboard(selectedItem.getFilePath());
        }
    }

    private String browseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(this.primaryStage);
        if (selectedDirectory == null) {
            return StringUtils.EMPTY;
        } else {
            return selectedDirectory.getAbsolutePath();
        }
    }

    private void pasteToClipboard(final String value) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(value);
        clipboard.setContent(content);
    }

}
