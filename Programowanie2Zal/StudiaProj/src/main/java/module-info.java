module com.example.studiaproj {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.studiaproj to javafx.fxml;
    exports com.example.studiaproj;
}