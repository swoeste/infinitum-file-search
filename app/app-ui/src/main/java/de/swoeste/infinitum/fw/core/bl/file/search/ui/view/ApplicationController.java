package de.swoeste.infinitum.fw.core.bl.file.search.ui.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.NavigationActions.SelectionPolicy;

import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileContentSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearch;
import de.swoeste.infinitum.fw.core.bl.file.search.FileSystemSearchConfiguration;
import de.swoeste.infinitum.fw.core.bl.file.search.analyzer.ResourceContentAnalyzer;
import de.swoeste.infinitum.fw.core.bl.file.search.executor.RunnableExecutor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourcePathFilter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFileContent;
import de.swoeste.infinitum.fw.core.bl.file.search.ui.model.UIFilePath;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ApplicationController {

    // TODO move search logic to separate class to keep this slim

    private final RunnableExecutor   executor = RunnableExecutor.getInstance();

    private Stage                    primaryStage;

    @FXML
    private TextField                searchFileTextPath;

    @FXML
    private TextField                searchFileTextFile;

    @FXML
    private CheckBox                 searchFileCheckBoxIncludeSubDirectories;

    @FXML
    private CheckBox                 searchFileCheckBoxIncludeArchives;

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
    private TableView<UIFileContent> searchContentTable;

    @FXML
    private TableColumn<?, ?>        searchContentTableColumnFilePath;

    @FXML
    private TableColumn<?, ?>        searchContentTableColumnResultPositionsCount;

    @FXML
    private CodeArea                 searchContentSelectedFilePathContent;

    @FXML
    void initialize() {
        // do nothing
        this.searchContentTextPath.setText("E:\\Dev\\source\\infinitum-file-search\\core");
        this.searchContentTextFile.setText(".*");
        this.searchContentTextContent.setText("Sebastian");

        // add line numbers
        this.searchContentSelectedFilePathContent.setParagraphGraphicFactory(LineNumberFactory.get(this.searchContentSelectedFilePathContent));
    }

    public void setPrimaryStage(final Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    void searchFileBrowse(final ActionEvent event) {
        final String directory = browseDirectory();
        this.searchFileTextPath.setText(directory);
    }

    @FXML
    void searchFile(final ActionEvent event) {
        // clear current results
        this.searchFileTable.getItems().clear();

        // execute search
        final Path searchPath = Paths.get(this.searchFileTextPath.getText());
        final List<ResourceFilter> searchFilter = getSearchFilter(this.searchFileTextFile.getText());
        final boolean includeArchives = this.searchFileCheckBoxIncludeArchives.isSelected();
        final int searchDepth = getSearchDepth(this.searchFileCheckBoxIncludeSubDirectories.isSelected());
        final FileSystemSearchConfiguration config = new FileSystemSearchConfiguration(searchPath, searchFilter, includeArchives, searchDepth);

        final FileSystemSearch search = new FileSystemSearch(config);
        search.search();

        final ObservableList<UIFilePath> items = this.searchFileTable.getItems();
        final Queue<Resource> searchResult = search.getFiles();
        for (Resource resource : searchResult) {
            items.add(new UIFilePath(resource.getFilePathAsString()));
        }
    }

    @FXML
    void searchContentTableOnMouseClicked(final MouseEvent event) {
        final UIFileContent selectedItem = this.searchContentTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // set content
            final String fileContent = selectedItem.getFileContent();
            this.searchContentSelectedFilePathContent.replaceText(fileContent);

            // set style
            for (Pair<Long, Long> pair : selectedItem.getResultPositions()) {
                final int currStart = pair.getKey().intValue();
                final int currEnd = pair.getValue().intValue();
                this.searchContentSelectedFilePathContent.setStyleClass(currStart, currEnd, "highlight"); //$NON-NLS-1$
            }

            this.searchContentSelectedFilePathContent.start(SelectionPolicy.CLEAR);
        }
    }

    @FXML
    void searchContent(final ActionEvent event) {
        // clear current results
        this.searchContentTable.getItems().clear();

        // execute search
        final Path searchPath = Paths.get(this.searchContentTextPath.getText());
        final List<ResourceFilter> searchFilter = getSearchFilter(this.searchContentTextFile.getText());
        final boolean includeArchives = this.searchContentCheckBoxIncludeArchives.isSelected();
        final int searchDepth = getSearchDepth(this.searchContentCheckBoxIncludeSubDirectories.isSelected());
        final FileSystemSearchConfiguration config = new FileSystemSearchConfiguration(searchPath, searchFilter, includeArchives, searchDepth);

        final FileSystemSearch search = new FileSystemSearch(config);
        search.search();
        final Queue<Resource> searchResult = search.getFiles();

        System.out.println("Found files: " + searchResult.size());

        final String contentPattern = this.searchContentTextContent.getText();
        FileContentSearchConfiguration config2 = new FileContentSearchConfiguration(searchResult, new ResourceContentAnalyzer(contentPattern), this.executor, 8);
        FileContentSearch search2 = new FileContentSearch(config2);
        search2.search();
        List<SearchResult> result = search2.getResult();

        System.out.println("Found content: " + result.size());

        Map<String, UIFileContent> xxx = new HashMap<>();

        final ObservableList<UIFileContent> items = this.searchContentTable.getItems();
        for (SearchResult resource : result) {
            try {
                final String filePathAsString = resource.getResource().getFilePathAsString();

                UIFileContent content = xxx.get(filePathAsString);
                if (content == null) {
                    content = new UIFileContent(filePathAsString, resource.getResource().getContentAsString(), new ArrayList<>());
                    items.add(content);
                    xxx.put(filePathAsString, content);
                }

                content.getResultPositions().add(new Pair<Long, Long>(resource.getStartIndex(), resource.getEndIndex()));

            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void searchContentBrowse(final ActionEvent event) {
        final String directory = browseDirectory();
        this.searchContentTextPath.setText(directory);
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

    private int getSearchDepth(final boolean includeSubFolders) {
        return includeSubFolders ? Integer.MAX_VALUE : 1;
    }

    private List<ResourceFilter> getSearchFilter(final String filterExpression) {
        final List<ResourceFilter> searchFilter = new ArrayList<>();
        searchFilter.add(new ResourcePathFilter(filterExpression));
        return searchFilter;
    }

    public void shutdown() {
        try {
            this.executor.shutdown();
        } catch (InterruptedException ex) {
            // TODO logging
            Thread.currentThread().interrupt();
        }
    }

}
