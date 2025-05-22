package ies.torredelrey.jfma.appgestionparking.util;

import ies.torredelrey.jfma.appgestionparking.controlador.rolesController;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public class FuncionesReutilizables {

    public static void mostrarAlertaInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        Stage stagealert = (Stage) alert.getDialogPane().getScene().getWindow();
        if(titulo.equals("Error")){
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOERROR,stagealert);
        }
        if(titulo.equals("Éxito")){
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOEXITO,stagealert);
        }
        alert.showAndWait();
    }

    public static void mostrarAlertaConfirmacionSalir(String titulo, String mensaje, Stage actual) throws IOException {
        Alert mensajeSalir = new Alert(Alert.AlertType.CONFIRMATION);
        mensajeSalir.setTitle(titulo);
        mensajeSalir.setContentText(mensaje);


        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        mensajeSalir.getButtonTypes().setAll(btnSi, btnNo);

        Stage alert = (Stage) mensajeSalir.getDialogPane().getScene().getWindow();
        if(titulo.equals("Cambio de rol")){
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOVENTANASALIRROL,alert);
        }

        if(titulo.equals("Confirmación de salida")){
            FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOVENTANASALIR,alert);
        }

        Optional<ButtonType> opcion = mensajeSalir.showAndWait();


        if (opcion.get() == btnSi) {
            if(actual == null){
                System.exit(0);
            }else{
                FXMLLoader loader = new FXMLLoader(rolesController.class.getResource(Rutas.ROLES));
                AnchorPane root = loader.load();
                rolesController controller = loader.getController();
                controller.setImagenAdmin(Rutas.IMAGENADMINISTRADOR);
                controller.setImagenRecepcionista(Rutas.IMAGENRECEPCIONISTA);
                controller.setImgClientes(Rutas.IMAGENCLIENTES);
                Scene nuevaEscena = new Scene(root);
                actual.setScene(nuevaEscena);
                actual.setTitle("Selector de roles");
            }

        } else {
            mensajeSalir.close();
        }
    }

    public static boolean mostrarAlertaConfirmacionCancelar(String titulo, String mensaje) throws IOException {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle(titulo);
        confirmacion.setContentText(mensaje);

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        confirmacion.getButtonTypes().setAll(btnSi, btnNo);
        Stage alert = (Stage) confirmacion.getDialogPane().getScene().getWindow();
        if(titulo.equals("Eliminar")){
            FuncionesReutilizables.cambiarIconoVentana(Rutas.IMAGENELIMINAR,alert);
        }
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        return resultado.isPresent() && resultado.get() == btnSi;
    }

    public static void ajustarImagenes(String ruta, ImageView img,int anchura,int altura){

        Image imagen = new Image(Objects.requireNonNull(FuncionesReutilizables.class.getResource(ruta)).toString());
        img.setImage(imagen);
        img.setFitWidth(anchura);
        img.setFitHeight(altura);
        img.setPreserveRatio(true);
    }

    public static void cambiarIconoVentana (String ruta,Stage stage){
        InputStream iconStream = GestorParking.class.getResourceAsStream(ruta);
        if (iconStream == null) {
            System.out.println("ICONO NO ENCONTRADO");
        } else {
            stage.getIcons().clear();
            stage.getIcons().add(new Image(iconStream));
        }
    }


}
