module com.example.testcase4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.testcase4 to javafx.fxml;
    exports com.example.testcase4;
}