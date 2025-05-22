package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.UsuarioDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

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
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Usuario o contraseña incorrectas");
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
                    cargarHomeCliente(false,false,false,false,false,false);
                    break;
                default:
                    System.out.println("Rol no reconocido.");
                    break;
            }
            stageSelecionada.close();
            System.out.println(stageSelecionada);
        }
        if(!rol.equals(botonSeleccionado.getText())){
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "Estas intentando acceder con tu contraseña y usuario en otro rol diferente.");
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
            stage.setTitle("Gestor de Parking");
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOVENTANA,stage);
            stage.setOnCloseRequest(cerrar -> {
                cerrar.consume();

            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarHomeRecepcionista(){
        cargarHomeCliente(true,false,true,true,true,true);
        Stage stage = (Stage) btnLoginIniciar.getScene().getWindow();
        stage.close();
    }

    public static void cargarHomeCliente(boolean visibilidad1,boolean visibilidad2,boolean visibilidad3,boolean visibilidad4,boolean visibilidad5,boolean visibilidad6) {
        try {
            FXMLLoader loader = new FXMLLoader(loginController.class.getResource(Rutas.HOME));
            AnchorPane root = loader.load();
            menuController controller = loader.getController();
            controller.setMniCliente(visibilidad1);
            controller.setMniUsuario(visibilidad2);
            controller.setMniCoche(visibilidad3);
            controller.setMniCambiaRol(visibilidad4);
            controller.setMniFactura(visibilidad5);
            controller.setMniPagos(visibilidad6);


            // Obtener el escenario actual y cambiar la escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Gestor de Parking");
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOVENTANA,stage);
            stage.setOnCloseRequest(cerrar -> {
                cerrar.consume();

            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
