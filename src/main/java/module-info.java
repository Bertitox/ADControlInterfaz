/**
 * Módulo que contiene toda la información que requiere el proyecto ADControlInterfaz
 */
module org.example.adcontrol {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires com.google.gson;
    requires javafx.media;
    requires jasperreports;
    requires jsch;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.example.adcontrol to javafx.fxml;
    opens BBDD.DTO to org.hibernate.orm.core;

    exports org.example.adcontrol;
    exports BBDD.DTO;
}