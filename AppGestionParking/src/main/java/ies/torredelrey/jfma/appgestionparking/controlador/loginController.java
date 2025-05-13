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

public class loginController {

    @FXML
    private Button btnLoginCancelar;

    @FXML
    private Button btnLoginIniciar;

    @FXML
    private Label lblError;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private TextField txtUser;

    protected  Button botonSeleccionado;
    protected Stage stageSelecionada;

    public void setBotonSeleccionado(Button boton){

        this.botonSeleccionado = boton;
    }

    public void setStageSeleccionada(Stage stageRoles){
        this.stageSelecionada = stageRoles;
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

        String rol = nuevo.getRol(); // Obtener el rol del usuario

        System.out.println(rol);
        System.out.println(botonSeleccionado.getText());

        if(rol.equals(botonSeleccionado.getText())){

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
            stageSelecionada.close();
        }

    }

    @FXML
    void onClickLoginCancelar(ActionEvent event) {
        Stage stage = (Stage )btnLoginCancelar.getScene().getWindow();
        stage.close();
        stageSelecionada.show();
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
        cargarHomeCliente();
    }

    public static void cargarHomeCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(loginController.class.getResource(Rutas.HOME));
            AnchorPane root = loader.load();

            // Obtener el escenario actual y cambiar la escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
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
