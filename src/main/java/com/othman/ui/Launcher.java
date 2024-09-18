package com.othman.ui;

import com.othman.app.App;
import com.othman.data.ImbalancedTagsException;
import com.othman.data.Result;
import com.othman.data.Section;
import com.othman.file_dialogs.Evaluate;
import com.othman.file_dialogs.FileOperations;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/*
 * This class is the main class of the application.
 */
public class Launcher extends Application {
    private int currentPos = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Creating the main layout of the application.
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        // Creating the header of the application.
        HBox headerHB = new HBox();
        headerHB.setPadding(new Insets(10));
        headerHB.setPrefHeight(130);

        Label fileLabel = new Label("Select file: ");
        fileLabel.setFont(new Font(15));

        TextField pathTextField = new TextField();
        pathTextField.setEditable(false);
        pathTextField.setPrefWidth(190);
        pathTextField.setPrefHeight(25);

        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(175);
        loadButton.setPrefHeight(25);
        loadButton.setDefaultButton(true);

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        TextField userTextField = new TextField();
        userTextField.setPrefWidth(190);
        userTextField.setPrefHeight(25);

        // Loading the file.
        loadButton.setOnAction(e -> {
            userTextField.setText("");
            textArea.clear();
            // Clearing the sections list.
            App.getInstance().getSections().clear();
            FileOperations fileOperations = new FileOperations(stage);
            // Opening the file.
            try {
                fileOperations.openFile();
                pathTextField.setText(fileOperations.getPath());
                // Parsing the file.
                if (!App.getInstance().getSections().isEmpty()) {

                    textArea.setFont(new Font(22));
                    textArea.setText(App.getInstance().getFirstSection().toString());

                }
                // Handling exceptions.
            } catch (ImbalancedTagsException | IOException im) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed");
                alert.setHeaderText(im.getMessage());
                alert.showAndWait();
            }

        });

        Label calculateLabel = new Label("Calculate:");
        calculateLabel.setFont(new Font(15));

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPrefWidth(89);
        comboBox.setMaxWidth(89);
        comboBox.prefHeight(25);
        comboBox.getItems().addAll("Infix", "Postfix");
        comboBox.getSelectionModel().selectFirst();
        Label validationMessage = new Label();

        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setDisable(true);
        // Validating the user input.
        userTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.isBlank()) {
                    evaluateButton.setDisable(true);
                }
                // Checking if the user input is valid.
                for (char c : newValue.toCharArray()) {
                    boolean validCharacters = Character.isDigit(c) || Character.isWhitespace(c) || c == '.';
                    boolean isValidOperator = c == '-' || c == '+' || c == '/' || c == '*' || c == '^' || c == '%' || c == '(' || c == ')';
                    if (!validCharacters && !isValidOperator) {
                        // Displaying a message if the user input is invalid.
                        userTextField.setStyle("-fx-border-color: red;");
                        evaluateButton.setDisable(true);

                    } else if (isValidOperator) {
                        // Displaying a message if the user input is invalid.
                        if (!oldValue.isEmpty() && oldValue.charAt(oldValue.length() - 1) != ' ') {
                            userTextField.setStyle("-fx-border-color: red;");
                            evaluateButton.setDisable(true);
                            validationMessage.setText("Please put spaces around operators!");

                        }
                    } else {
                        // Clearing the message if the user input is valid.
                        userTextField.setStyle("");
                        evaluateButton.setDisable(false);
                        validationMessage.setText("");

                    }
                }
            }
        });
        // Evaluating the user input.
        evaluateButton.setOnAction(e -> {
            textArea.clear();
            if (userTextField.getText().isBlank()) {
                evaluateButton.setDisable(true);
            }
            if (comboBox.getValue().trim().equals("Infix")) {
                try {
                    Result result = Evaluate.infixEvaluation(userTextField.getText().trim());
                    textArea.setText(String.valueOf(result));
                    textArea.setFont(new Font(30));

                } catch (Exception exception) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Failed");
                    alert.setHeaderText("Please put spaces around operators!");
                    alert.showAndWait();
                }
            } else {
                Result result = Evaluate.postfixEvaluation(userTextField.getText().trim());
                textArea.setText(String.valueOf(result));
                textArea.setFont(new Font(30));
            }
        });

        Pane headerPane = new Pane();
        headerPane.getChildren().addAll(fileLabel, pathTextField, loadButton, calculateLabel, userTextField, comboBox, evaluateButton, validationMessage);
        headerHB.getChildren().add(headerPane);

        fileLabel.setLayoutX(210);
        fileLabel.setLayoutY(10);

        pathTextField.setLayoutX(290);
        pathTextField.setLayoutY(10);

        loadButton.setLayoutX(490);
        loadButton.setLayoutY(10);

        calculateLabel.setLayoutX(210);
        calculateLabel.setLayoutY(50);

        userTextField.setLayoutX(290);
        userTextField.setLayoutY(50);

        comboBox.setLayoutX(490);
        comboBox.setLayoutY(50);

        evaluateButton.setLayoutX(590);
        evaluateButton.setLayoutY(50);

        validationMessage.setLayoutX(290);
        validationMessage.setLayoutY(90);

        HBox bottomHB = new HBox(20);
        bottomHB.setPrefHeight(70);
        bottomHB.setPadding(new Insets(10));

        // Creating the list of sections.
        List<Section> sections = App.getInstance().getSections();
        Button prevButton = new Button("Previous");
        prevButton.setPrefWidth(100);
        prevButton.setPrefHeight(15);

        // Displaying the previous section.
        prevButton.setOnAction(e -> {
            if (currentPos > 0) {
                textArea.setText(sections.get(--currentPos).toString());
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setPrefWidth(100);
        nextButton.setPrefHeight(15);

        // Displaying the next section.
        nextButton.setOnAction(e -> {
            if (currentPos < sections.size() - 1) {
                textArea.setText(sections.get(++currentPos).toString());
            }
        });

        // Creating the themes list.
        ComboBox<Themes> themesComboBox = new ComboBox<>();
        for (Themes value : Themes.values()) {
            themesComboBox.getItems().add(value);
        }
        Application.setUserAgentStylesheet(Themes.CUPERTINO_LIGHT.getTheme().getUserAgentStylesheet());
        themesComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTheme, newTheme) -> {
            if (newTheme != null) {
                Application.setUserAgentStylesheet(newTheme.getTheme().getUserAgentStylesheet());
            }
        });
        themesComboBox.getSelectionModel().selectFirst();

        Pane pane = new Pane();
        pane.getChildren().addAll(prevButton, nextButton, themesComboBox);
        prevButton.setLayoutX(260);
        prevButton.setLayoutY(16);

        nextButton.setLayoutX(380);
        nextButton.setLayoutY(16);

        themesComboBox.setLayoutX(625);
        themesComboBox.setLayoutY(16);

        bottomHB.getChildren().addAll(pane);
        bottomHB.setAlignment(Pos.CENTER);

        borderPane.setTop(headerHB);
        borderPane.setCenter(textArea);
        borderPane.setBottom(bottomHB);

        Scene scene = new Scene(borderPane, 900, 700);

        stage.setTitle("DS Project 2");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

    }
}
