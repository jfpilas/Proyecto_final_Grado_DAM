module ies.torredelrey.jfma.appgestionparking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;


    opens ies.torredelrey.jfma.appgestionparking to javafx.fxml;
    exports ies.torredelrey.jfma.appgestionparking.vista;
    opens ies.torredelrey.jfma.appgestionparking.vista to javafx.fxml;
    exports ies.torredelrey.jfma.appgestionparking.controlador;
    opens ies.torredelrey.jfma.appgestionparking.controlador to javafx.fxml;
    exports ies.torredelrey.jfma.appgestionparking.modelo;
    opens ies.torredelrey.jfma.appgestionparking.modelo to javafx.fxml;
}