/*
 * Copyright (C) 2016 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.ui;

import java.io.IOException;

import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIPath;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.view.SearchFileContentController;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.view.SearchFileController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author swoeste http://code.makery.ch/library/javafx-8-tutorial/part3/
 */
public class App extends Application {

    private final ObservableList<UIPath> personData = FXCollections.observableArrayList();

    /**
     * @return the personData
     */
    public ObservableList<UIPath> getPersonData() {
        this.personData.add(new UIPath("HAHA5"));
        this.personData.add(new UIPath("HAHA4"));
        this.personData.add(new UIPath("HAHA3"));
        this.personData.add(new UIPath("HAHA2"));
        this.personData.add(new UIPath("HAHA1"));
        return this.personData;
    }

    private Stage      primaryStage;
    private BorderPane root;

    @Override
    public void start(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRoot();

        showSearchFileScene();
        // showSearchFileContentScene();
    }

    /**
     * Initializes the root layout.
     */
    public void initRoot() {
        try {
            // Load root layout from fxml file.
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/root.fxml"));
            this.root = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            final Scene scene = new Scene(this.root);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showSearchFileScene() {
        try {
            // Load person overview.
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/searchFile.fxml"));
            final AnchorPane searchFileContent = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            final SearchFileController controller = loader.getController();
            controller.setApp(this);

            // Set person overview into the center of root layout.
            this.root.setCenter(searchFileContent);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showSearchFileContentScene() {
        try {
            // Load person overview.
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/searchFileContent.fxml"));
            final AnchorPane searchFileContent = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            final SearchFileContentController controller = loader.getController();
            controller.setApp(this);

            // Set person overview into the center of root layout.
            this.root.setCenter(searchFileContent);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
