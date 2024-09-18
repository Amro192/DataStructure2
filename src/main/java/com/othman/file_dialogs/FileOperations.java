package com.othman.file_dialogs;

import com.othman.app.App;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for opening a file dialog and returning the path of the selected file.
 */
public class FileOperations {

    /**
     * The file chooser object.
     */

    private static final FileChooser fileChooser = new FileChooser();

    /**
     * The window owner.
     */

    private final Window windowOwner;

    /**
     * The path of the selected file.
     */
    private String path;


    static {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("242 files", "*.242")
        );
    }

    public FileOperations(Window windowOwner) {
        this.windowOwner = windowOwner;
    }

    public String getPath() {
        return path;
    }

    /**
     * Opens a file dialog and returns the path of the selected file.
     */
    public void openFile() throws IOException {
        File file = fileChooser.showOpenDialog(windowOwner);
        // If the user cancels the dialog, the file will be null.
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed, file not found");
            alert.setHeaderText("File failed to load");
            alert.showAndWait();
            return;
        }
        // Replace the backslashes with double backslashes.
        path = file.getAbsolutePath().replace("\\", "\\\\");
        App.getInstance().readFile(file.toPath());
    }
}