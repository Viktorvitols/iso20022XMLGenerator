module com.turiba.iso20022xmlgenerator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.turiba.iso20022xmlgenerator to javafx.fxml;
    exports com.turiba.iso20022xmlgenerator;
}