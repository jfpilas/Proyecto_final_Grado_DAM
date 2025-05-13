package ies.torredelrey.jfma.appgestionparking.util;

import ies.torredelrey.jfma.appgestionparking.controlador.rolesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class FuncionesReutilizables {

    public static void mostrarAlertaInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarAlertaConfirmacionSalir(String titulo, String mensaje, Stage actual) throws IOException {
        Alert mensajeSalir = new Alert(Alert.AlertType.CONFIRMATION);

        mensajeSalir.setTitle(titulo);
        mensajeSalir.setContentText(mensaje);

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        mensajeSalir.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> opcion = mensajeSalir.showAndWait();

        if (opcion.get() == btnSi) {
            if(actual == null){
                System.exit(0);
            }else{
                FXMLLoader loader = new FXMLLoader(rolesController.class.getResource(Rutas.ROLES));
                AnchorPane root = loader.load();
                Scene nuevaEscena = new Scene(root);
                actual.setScene(nuevaEscena);
            }

        } else {
            mensajeSalir.close();
        }
    }


}
