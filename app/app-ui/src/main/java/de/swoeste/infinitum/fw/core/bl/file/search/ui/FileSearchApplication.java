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
package de.swoeste.infinitum.fw.core.bl.file.search.ui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.swoeste.infinitum.fw.core.bl.file.search.executor.SimpleExecutor;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.view.FileSearchViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author swoeste
 */
public class FileSearchApplication extends Application {

    // TODO java doc
    // TODO general exception handling
    // TODO code review, code clean up, java doc and sonar
    // TODO the shutdown() on executor only interrupts all threads, so we need to check for "Thread.interupted"

    private static final Logger         LOG      = LoggerFactory.getLogger(FileSearchApplication.class);

    private static final SimpleExecutor EXECUTOR = SimpleExecutor.getInstance();

    private Stage                       primaryStage;

    @Override
    public void start(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Infinitum - FileSearch"); //$NON-NLS-1$
        this.primaryStage.getIcons().add(new Image(FileSearchApplication.class.getResourceAsStream("img/infinitum.png"))); //$NON-NLS-1$

        initRoot();
    }

    /**
     * Initializes the root layout.
     */
    public void initRoot() {
        try {
            // Load root layout from fxml file.
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FileSearchApplication.class.getResource("view/root.fxml")); //$NON-NLS-1$

            final Pane root = loader.load();

            final FileSearchViewController controller = loader.getController();
            controller.setPrimaryStage(this.primaryStage);
            controller.setExecutor(EXECUTOR);

            final Scene scene = new Scene(root);
            scene.getStylesheets().add(FileSearchApplication.class.getResource("css/styles.css").toExternalForm()); //$NON-NLS-1$

            // Show the scene containing the root layout.
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (final IOException ex) {
            final String msg = "Unable to initialize user interface"; //$NON-NLS-1$
            LOG.error(msg, ex);
            throw new IllegalStateException(msg, ex);
        }
    }

    @Override
    public void stop() throws Exception {
        FileSearchApplication.EXECUTOR.shutdown();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
