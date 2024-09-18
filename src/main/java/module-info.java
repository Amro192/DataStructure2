module com.othman.structures {
    requires javafx.controls;
    requires javafx.fxml;
    requires atlantafx.base;


    opens com.othman.structures to javafx.fxml;
    exports com.othman.structures;
    exports com.othman.ui;
}