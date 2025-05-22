package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Coche;
import ies.torredelrey.jfma.appgestionparking.modelo.FacturaPago;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.util.TamanoImagenes;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class clienteController {

    ObservableList<String> modoBusqueda = FXCollections.observableArrayList("DNI");
    ObservableList<Coche> listaCoches = null;
    private int posicionCocheTabla ;
    private String matricula ;
    private String marca ;
    private String modelo;
    private String color;
    private String tipo ;

    @FXML
    private TextField txtDniBuscar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgCancelar;

    @FXML
    private ImageView imgGuardar;

    @FXML
    private ImageView imgBuscar;

    @FXML
    private ImageView imgAddCliente;

    @FXML
    private ComboBox<String> cbxBuscar;

    @FXML
    private TableView<Coche> tblCoche;

    @FXML
    private TableColumn<Coche,String> colColor;

    @FXML
    private TableColumn<Coche, String> colMarca;

    @FXML
    private TableColumn<Coche, String> colMatricula;

    @FXML
    private TableColumn<Coche, String> colModelo;

    @FXML
    private TableColumn<Coche, Integer> colID;

    @FXML
    private TableColumn<Coche, String> colTipo;
    @FXML
    private Button btnCancelarClientes;

    @FXML
    private DatePicker dpFechaNacCliente;

    @FXML
    private TextField txtApellidosCliente;

    @FXML
    private TextField txtDireccionCliente;

    @FXML
    private TextField txtDniCliente;

    @FXML
    private TextField txtEmailCliente;

    @FXML
    private TextField txtNombreCliente;

    @FXML
    private TextField txtTelefonoCliente;

    @FXML
    void cancelarClientes(ActionEvent event) {
        Stage stage = (Stage) btnCancelarClientes.getScene().getWindow();
        stage.close();
    }

    @FXML
    void guardaClientes(ActionEvent event) {
        String nombre = txtNombreCliente.getText();
        String apellido = txtApellidosCliente.getText();
        String dni = txtDniCliente.getText();
        LocalDate fecha = dpFechaNacCliente.getValue();
        String direccion = txtDireccionCliente.getText();
        String email = txtEmailCliente.getText();
        String telefono = txtTelefonoCliente.getText();

        if (!Validaciones.validarNombre(nombre)) {

            System.out.println("Nombre inválido");
            return;
        }

        if (!Validaciones.validarNombre(apellido)) {

            System.out.println("Nombre inválido");
            return;
        }

        // Validación teléfono
        if (!Validaciones.validarTelefono(telefono)) {

            System.out.println("Teléfono inválido");
            return;
        }

        if (!Validaciones.validarDNI(dni)) {

            System.out.println("DNI inválido");
            return;
        }

        // Validación Fecha de Nacimiento (comprobamos que la fecha no esté en el futuro)
        if (!Validaciones.validarFechaNacimiento(fecha)) {

            System.out.println("Fecha de nacimiento inválida");
            return;
        }

        if (!Validaciones.validarEmail(email)) {

            System.out.println("Email inválido");
            return;
        }

        if(ClienteDao.buscarEmailYdni(email,dni)){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Ese cliente ya existe.");
            return;
        }

        // Si todas las validaciones son correctas, guardar los datos

        Cliente nuevocliente = new Cliente(direccion,nombre,apellido,dni,fecha,email,telefono);
        ClienteDao cliente = new ClienteDao();
        boolean exito = cliente.guardarCliente(nuevocliente);

        if (exito) {
            Alert mensajeExito = new Alert(Alert.AlertType.INFORMATION);

            mensajeExito.setTitle("Éxito");
            mensajeExito.setContentText("Se ha guardado correctamente el Cliente");

            ButtonType btnVale = new ButtonType("Vale");


            mensajeExito.getButtonTypes().setAll(btnVale);

            Optional<ButtonType> opcion = mensajeExito.showAndWait();

            if (opcion.get() == btnVale) {
                mensajeExito.close();
            }
        } else {
            Cliente busqueda = ClienteDao.buscarDniEnBBDD(dni);


            if(busqueda.getDni().equals(dni) ){
                Alert mensajeError = new Alert(Alert.AlertType.INFORMATION);

                mensajeError.setTitle("Error");
                mensajeError.setContentText("El cliente con dni "+ dni+" ya está registrado.");

                ButtonType btnVale = new ButtonType("Vale");


                mensajeError.getButtonTypes().setAll(btnVale);

                Optional<ButtonType> opcion = mensajeError.showAndWait();

                if (opcion.get() == btnVale) {
                    mensajeError.close();
                }
            }
        }
        limpiarCampos();
    }

    @FXML
    void onClickBuscar(ActionEvent event) {
        String dni = txtDniBuscar.getText();
        String comboBuscar = cbxBuscar.getValue();



        if (dni.isEmpty() && comboBuscar == null){
            FuncionesReutilizables.mostrarAlertaInformacion("Información","Ambos campos están vacíos, rellénalos por favor.");
            return;
        }

        if(dni.isEmpty()){
            FuncionesReutilizables.mostrarAlertaInformacion("Información","Rellena el campo dni, no puedes dejarlo vacío");
            return;
        }

        if(!Validaciones.validarDNI(dni)){
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "El dni insertado no es correcto.");
        }

        if(comboBuscar == null){
            FuncionesReutilizables.mostrarAlertaInformacion("Información","Selecciona un modo de búsqueda");
            return;
        }


        if(ClienteDao.buscarDniEnBBDD(dni)!=null){

            Cliente clienteBuscado = ClienteDao.buscarDniEnBBDD(dni);

            int id = clienteBuscado.getIdCliente();

            ObservableList<Coche> coches = CocheDao.listarCochesCliente(id);

            listaCoches = FXCollections.observableArrayList();

            for(Coche c : coches){
                int CiD = c.getIdCoche();
                String matricula = c.getMatricula();
                String marca = c.getMarca();
                String modelo = c.getModelo();
                String color = c.getColor();
                String tipo = c.getTipo();

                listaCoches.add(new Coche(matricula,marca,modelo,color,tipo,CiD));
            }

            if(!coches.isEmpty()){
                this.colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
                this.colMarca.setCellValueFactory(new PropertyValueFactory<>("Marca"));
                this.colModelo.setCellValueFactory(new PropertyValueFactory<>("Modelo"));
                this.colColor.setCellValueFactory(new PropertyValueFactory<>("Color"));
                this.colTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
                this.colID.setCellValueFactory(new PropertyValueFactory<>("idCoche"));
                this.tblCoche.setItems(coches);


            }else{
                FuncionesReutilizables.mostrarAlertaInformacion("Error","No existen coches asociados al siguiente dni "+dni);
                tblCoche.getItems().clear(); //limpio los valores de la tabla
            }
        }


    }

    @FXML
    void OnClickEliminar(ActionEvent event) throws IOException {

        Coche cocheSeleccionado = tblCoche.getSelectionModel().getSelectedItem();

        if (cocheSeleccionado == null) {
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "Debes seleccionar un coche de la tabla.");
            return;
        }
        if(FuncionesReutilizables.mostrarAlertaConfirmacionCancelar("Eliminar","¿Estás seguro que deseas eliminar el coche seleccionado?")){
            if(CocheDao.eliminarCoche(cocheSeleccionado)){
                FuncionesReutilizables.mostrarAlertaInformacion("Éxito","Coche eliminado correctamente.");
            }else{
                FuncionesReutilizables.mostrarAlertaInformacion("Error","No se ha podido eliminar el coche.");
            }
        }

    }

    @FXML
    void OnClickModificar(ActionEvent event) throws IOException {
        FXMLLoader loader = new  FXMLLoader(GestorParking.class.getResource(Rutas.MODIFICARCOCHE));
        AnchorPane root = loader.load();
        cocheController controller = loader.getController();
        Coche cocheSeleccionado = tblCoche.getSelectionModel().getSelectedItem();
        System.out.println(cocheSeleccionado);

        if (cocheSeleccionado == null) {
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "Debes seleccionar un coche de la tabla.");
            return;
        }

        controller.setCoche(cocheSeleccionado);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Modificar");
        stage.setScene(scene);
        FuncionesReutilizables.cambiarIconoVentana(Rutas.IMAGENMODIFICAR,stage);
        stage.show();
    }

    @FXML
    private void listarModosDeBusqueda(){
        GestorParking.llenarCombo(cbxBuscar,modoBusqueda);
    }

    private void limpiarCampos(){

        txtNombreCliente.setText("");
        txtApellidosCliente.setText("");
        txtDniCliente.setText("");
        dpFechaNacCliente.setValue(null);
        txtDireccionCliente.setText("");
        txtEmailCliente.setText("");
        txtTelefonoCliente.setText("");
    }

    public void setImagenAddCliente(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgAddCliente,TamanoImagenes.ANCHURAIMAGENGRANDECLIENTE,TamanoImagenes.ALTURAIMAGENGRANDECLIENTE);
    }

    public void setImagenBuscar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgBuscar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenModificar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgModificar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenEliminar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgEliminar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenGuardar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgGuardar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenCancelar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgCancelar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

}
