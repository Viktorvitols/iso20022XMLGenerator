module com.turiba.iso20022xmlgenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.turiba.iso20022xmlgenerator to javafx.fxml;
    exports com.turiba.iso20022xmlgenerator;
    exports com.turiba.iso20022xmlgenerator.database;
    opens com.turiba.iso20022xmlgenerator.database to javafx.fxml;
    exports com.turiba.iso20022xmlgenerator.model;
    opens com.turiba.iso20022xmlgenerator.model to javafx.fxml;
}