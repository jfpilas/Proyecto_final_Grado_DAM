package ies.torredelrey.jfma.appgestionparking.controlador;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class menuController {

    @FXML
    private ImageView imgAlcazar;

    @FXML
    private MenuItem mniCliente;

    @FXML
    private MenuItem mniFactura;

    @FXML
    private MenuItem mniParking;

    @FXML
    private MenuItem mniSalir;




    @FXML
    void OnClickCliente(ActionEvent event) throws IOException {
        AnchorPane panel = FXMLLoader.load(GestorParking.class.getResource(Rutas.ADDCLIENTES));
        Scene nuevaEscena = new Scene(panel);

        Stage stage = new Stage();
        stage.setTitle("Añadir Clientes");
        stage.setScene(nuevaEscena);
        stage.show();

        System.out.println("Estoy entrando en Clientes");
    }


    @FXML
    void onClickFactura(ActionEvent event) {

    }

    @FXML
    void onClickParking(ActionEvent event) throws IOException {
        AnchorPane panel = FXMLLoader.load(GestorParking.class.getResource(Rutas.PARKING));
        Scene nuevaEscena = new Scene(panel);

        Stage stage = new Stage();
        stage.setTitle("Parking");
        stage.setScene(nuevaEscena);
        stage.show();
        System.out.println("Estoy entrando en parking");
    }


    @FXML
    protected void OnClickSalir(ActionEvent event) {
        Alert mensajeSalir = new Alert(Alert.AlertType.CONFIRMATION);

        mensajeSalir.setTitle("Confirmación de salida");
        mensajeSalir.setContentText("¿Estás seguro que deseas salir de la aplicación?");

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        mensajeSalir.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> opcion = mensajeSalir.showAndWait();

        if (opcion.get() == btnSi) {
            System.exit(0);
        } else {
            mensajeSalir.close();
        }


    }


    @FXML
    void onClickUsuario(ActionEvent event) throws IOException {
        AnchorPane panel = FXMLLoader.load(GestorParking.class.getResource(Rutas.ADDUSUARIOS));
        Scene nuevaEscena = new Scene(panel);

        Stage stage = new Stage();
        stage.setTitle("Añadir Usuarios nuevos");
        stage.setScene(nuevaEscena);
        stage.show();

        System.out.println("Estoy entrando en Usuario");
    }

}
