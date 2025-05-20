package ies.torredelrey.jfma.appgestionparking.controlador;


import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.util.TamanoImagenes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class rolesController {

    @FXML
    private ImageView imagenAdmin;

    @FXML
    private ImageView imagenRecepcionista;

    @FXML
    private ImageView imgClientes;

    @FXML
    private Button rolAdmin;

    @FXML
    protected AnchorPane panelRoles;

    @FXML
    private Button rolClientes;

    @FXML
    private Button rolRecepcionista;


    private  Button botonSeleccionado;

    @FXML
    void selectorBoton(ActionEvent event) {
        botonSeleccionado = (Button) event.getSource();

        if(botonSeleccionado == rolAdmin || botonSeleccionado == rolRecepcionista){
            cargarLogin();

        }
    }

    @FXML
    void btnRolClientes(ActionEvent event) {
       loginController.cargarHomeCliente(false,false,false,false,false,false);
        Stage stageRoles = (Stage) rolAdmin.getScene().getWindow();
        stageRoles.close();
    }


    private void cargarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(loginController.class.getResource(Rutas.LOGIN));
            AnchorPane root = loader.load();
            loginController controller = loader.getController();
            controller.setBotonSeleccionado(botonSeleccionado);

            Stage stageRoles = (Stage) rolAdmin.getScene().getWindow();
            stageRoles.hide();
            controller.setStageSeleccionada(stageRoles);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
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

    public void setImagenAdmin(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imagenAdmin,TamanoImagenes.ANCHURAIMAGENROLES,TamanoImagenes.ALTURAIMAGENROLES);
    }

    public void setImagenRecepcionista(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imagenRecepcionista,TamanoImagenes.ANCHURAIMAGENROLES,TamanoImagenes.ALTURAIMAGENROLES);
    }

    public void setImgClientes(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgClientes,TamanoImagenes.ANCHURAIMAGENROLES,TamanoImagenes.ALTURAIMAGENROLES);
    }
}


