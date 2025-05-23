package ies.torredelrey.jfma.appgestionparking.controlador;
import ies.torredelrey.jfma.appgestionparking.modelo.FacturaPago;
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
import javafx.scene.layout.Pane;
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
    private MenuItem mniCoche;

    @FXML
    private MenuItem mniPagos;

    @FXML
    private MenuItem mniUsuario;



    @FXML
    void OnClickCliente(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.ADDCLIENTES));
        AnchorPane root = loader.load();
        clienteController controller= loader.getController();
        controller.setImagenAddCliente(Rutas.IMAGENAGREGARCLIENTE);
        controller.setImagenBuscar(Rutas.IMAGENBUSCAR);
        controller.setImagenModificar(Rutas.IMAGENMODIFICAR);
        controller.setImagenEliminar(Rutas.IMAGENELIMINAR);
        controller.setImagenGuardar(Rutas.IMAGENGUARDAR);
        controller.setImagenCancelar(Rutas.IMAGENCANCELAR);
        Scene nuevaEscena = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Añadir Cliente");
        stage.setScene(nuevaEscena);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOADDCLIENTE,stage);
        stage.show();

        System.out.println("Estoy entrando en Clientes");
    }

    @FXML
    void onClickFactura(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.FACTURAS));
        AnchorPane root = loader.load();
        facturaController controller = loader.getController();
        controller.setImagenGuardar(Rutas.IMAGENGUARDAR);
        controller.setImagenBuscar(Rutas.IMAGENBUSCAR);
        controller.setImagenRefrescar(Rutas.IMAGENREFRESCAR);
        controller.configurarSeleccionTabla();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Facturas");
        stage.setScene(scene);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOFACTURA,stage);
        stage.show();
        System.out.println("Estoy entrando en Facturas");
    }

    @FXML
    void OnClickPagos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.PAGOS));
        AnchorPane root = loader.load(); // Esto inicializa todos los @FXML
        pagosController controller = loader.getController();
        controller.setImgBuscar(Rutas.IMAGENBUSCAR);
        controller.configurarSeleccionTabla();
        Pane panel = controller.devuelvePanelPago();
        panel.setVisible(false);
        controller.setTxtIdFactura();
        controller.setTxtFecha();
        controller.setTxtTotal();
        TableColumn<FacturaPago,Integer> columnfactura = controller.getColumnaFactura();
        columnfactura.setVisible(false);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Pagos");
        stage.setScene(scene);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOPAGOS,stage);
        stage.show();
        System.out.println("Estoy entrando en Pagos");
    }

    @FXML
    void onClickParking(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.PARKING));
        AnchorPane root = loader.load();
        parkingController controller = loader.getController();
        controller.inicializaImagenesTipoPlaza();
        controller.iniciarActualizacionAutomatica();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Parking");
        stage.setScene(scene);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOPARKING,stage);
        stage.show();
        stage.setOnCloseRequest(evento->{
            controller.detenerActualizacion();
            System.out.println("se ha parado el timer");
        });
    }


    @FXML
     void OnClickSalir(ActionEvent event) throws IOException {
        FuncionesReutilizables.mostrarAlertaConfirmacionSalir("Confirmación de salida","¿Estás seguro que deseas salir de la aplicación?",null);
    }

    @FXML
    void onClickUsuario(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.ADDUSUARIOS));
        AnchorPane root = loader.load();
        usuarioController controller = loader.getController();
        controller.setImgUsuario(Rutas.IMAGENADDUSUARIO);
        controller.setImgGuardar(Rutas.IMAGENGUARDAR);
        controller.setImgCancelar(Rutas.IMAGENCANCELAR);
        Scene nuevaEscena = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Añadir Nuevo Usuario");
        stage.setScene(nuevaEscena);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOADDCLIENTE,stage);
        stage.show();

        System.out.println("Estoy entrando en Usuario");
    }

    @FXML
    void onClickCambiaRol(ActionEvent event) throws IOException {
        Stage stage = (Stage) imgAlcazar.getScene().getWindow();
        FuncionesReutilizables.mostrarAlertaConfirmacionSalir("Cambio de rol","¿Estás seguro de que quieres cambiar de rol?",stage);

    }

    @FXML
    void OnClickCoche(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.ADDCCOCHES));
    AnchorPane panel = loader.load();
    cocheController controller = loader.getController();
    controller.setImagenGuardar(Rutas.IMAGENGUARDAR);
    controller.setImgRegistroCoche(Rutas.IMAGENREGISTROCOCHE);

    Scene escena = new Scene(panel);
    Stage stage = new Stage();
    stage.setScene(escena);
    stage.setTitle("Añadir coche");
    FuncionesReutilizables.cambiarIconoVentana(Rutas.ICONOVENTANACOCHE,stage);
    stage.show();
    }

    public void setMniCliente(boolean visibilidad){
        mniCliente.setVisible(visibilidad);
    }

    public void setMniFactura(boolean visibilidad){
        mniFactura.setVisible(visibilidad);
    }

    public void setMniParking(boolean visibilidad){
        mniParking.setVisible(visibilidad);
    }

    public  void setMniCoche(boolean visibilidad){
        mniCoche.setVisible(visibilidad);
    }

    public void setMniCambiaRol(boolean visibilidad){
        mniCambiaRol.setVisible(visibilidad);
    }

    public void setMniPagos(boolean visibilidad){
        mniPagos.setVisible(visibilidad);
    }

    public void  setMniUsuario(boolean visibilidad){
        mniUsuario.setVisible(visibilidad);
    }


}
