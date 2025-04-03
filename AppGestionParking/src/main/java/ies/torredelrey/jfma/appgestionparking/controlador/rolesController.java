package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.UsuarioDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class rolesController {

    @FXML
    private Button rolAdmin;

    @FXML
    private Button rolClientes;

    @FXML
    private Button rolRecepcionista;

    @FXML
    private Button btnLoginCancelar;

    @FXML
    private Label lblError;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtUser;


    private Button botonSeleccionado;

    @FXML
    void selectorBoton(ActionEvent event) {
        botonSeleccionado = (Button) event.getSource();

        if(botonSeleccionado == rolAdmin || botonSeleccionado == rolRecepcionista){
            cargarLogin();
        }

    }

    @FXML
    void btnRolClientes(ActionEvent event) {
       cargarHomeCliente();
    }

    @FXML
    void OnClickIniciarSesion(ActionEvent event) {

        String user = txtUser.getText();
        String contrasena = txtContrasena.getText();

        if (user.isEmpty() || contrasena.isEmpty()) {
            lblError.setTextFill(Color.RED);
            lblError.setText("Por favor, ingrese usuario y contraseña.");
            return;
        }

        Usuario nuevo = UsuarioDao.verificacion_usuario(user,contrasena);
        if(nuevo == null){
            lblError.setTextFill(Color.RED);
            lblError.setText("Usuario o contraseña incorrectas");
            return;
        }

        if(nuevo != null){
            String rol = nuevo.getRol(); // Obtener el rol del usuario

            switch (rol) {
                case "Administrador":
                    cargarHome();
                    break;
                case "Recepcionista":
                    cargarHomeRecepcionista();
                    break;
                case "Cliente":
                    cargarHomeCliente();
                    break;
                default:
                    System.out.println("Rol no reconocido.");
                    break;
            }
        } else {
            System.out.println("Usuario no encontrado o escrito incorrectamente.");
        }
    }

    @FXML
    void onClickLoginCancelar(ActionEvent event) {
        cargarSelectorRoles();
    }


    private void cargarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Rutas.LOGIN));
            AnchorPane root = loader.load();

            // Obtener el escenario actual y cambiar la escena
            Stage stage = (Stage) rolAdmin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesión");
            stage.setResizable(false);
            stage.setOnCloseRequest(cerrar -> {
                cerrar.consume();

            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarSelectorRoles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Rutas.ROLES));
            AnchorPane root = loader.load();

            // Obtener el escenario actual y cambiar la escena
            Stage stage = (Stage) btnLoginCancelar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestor de Parking");
            stage.setOnCloseRequest(cerrar -> {});
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Rutas.HOME));
            AnchorPane root = loader.load();

            // Obtener el escenario actual y cambiar la escena
            Stage stage = (Stage) btnLoginCancelar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.setOnCloseRequest(cerrar -> {
                cerrar.consume();

            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarHomeRecepcionista(){

    }

    private void cargarHomeCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Rutas.HOME));
            AnchorPane root = loader.load();

            // Obtener el escenario actual y cambiar la escena
            Stage stage = (Stage) rolClientes.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.setOnCloseRequest(cerrar -> {
                cerrar.consume();

            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


