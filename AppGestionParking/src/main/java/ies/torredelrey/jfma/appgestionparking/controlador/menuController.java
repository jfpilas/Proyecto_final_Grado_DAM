package ies.torredelrey.jfma.appgestionparking.controlador;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
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
    private MenuItem mniCambiaRol;



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
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.PARKING));
        AnchorPane root = loader.load(); // Esto inicializa todos los @FXML
        parkingController controller = loader.getController();
        controller.mostrarPlazas();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Parking");
        stage.setScene(scene);
        stage.show();
        System.out.println("Estoy entrando en parking");
    }


    @FXML
     void OnClickSalir(ActionEvent event) throws IOException {
        FuncionesReutilizables.mostrarAlertaConfirmacionSalir("Confirmación de salida","¿Estás seguro que deseas salir de la aplicación?",null);
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

    @FXML
    void onClickCambiaRol(ActionEvent event) throws IOException {
        Stage stage = (Stage) imgAlcazar.getScene().getWindow();
        FuncionesReutilizables.mostrarAlertaConfirmacionSalir("Confirmación de salida","¿Estás seguro de que quieres cambiar de rol?",stage);
    }

}
