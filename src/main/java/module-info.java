module com.turiba.iso20022xmlgenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires org.controlsfx.controls;

    opens com.turiba.iso20022xmlgenerator to javafx.fxml;
    exports com.turiba.iso20022xmlgenerator;
}
