package ies.torredelrey.jfma.appgestionparking.vista;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class GestorParking extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane fxmlLoader = FXMLLoader.load(GestorParking.class.getResource(Rutas.ROLES));
        Scene scene = new Scene(fxmlLoader);
        stage.setTitle("Gestor de Parking");
        stage.setScene(scene);
        stage.show();

    }

    public static void llenarCombo(ComboBox<String> llenarcombo, ObservableList<String> infoCombo){
        llenarcombo.setItems(infoCombo);
    }

    public static void main(String[] args) {
        launch();
    }
}