package de.swoeste.infinitum.fw.core.bl.file.search.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIPath;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.view.SearchFileContentController;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.view.SearchFileController;

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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/root.fxml"));
            this.root = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(this.root);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showSearchFileScene() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/searchFile.fxml"));
            AnchorPane searchFileContent = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            SearchFileController controller = loader.getController();
            controller.setApp(this);

            // Set person overview into the center of root layout.
            this.root.setCenter(searchFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showSearchFileContentScene() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/searchFileContent.fxml"));
            AnchorPane searchFileContent = (AnchorPane) loader.load();

            // Give the controller access to the main app.
            SearchFileContentController controller = loader.getController();
            controller.setApp(this);

            // Set person overview into the center of root layout.
            this.root.setCenter(searchFileContent);
        } catch (IOException e) {
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
