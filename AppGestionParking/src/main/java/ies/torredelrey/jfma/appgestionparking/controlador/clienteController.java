package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Coche;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class clienteController {

    ObservableList<String> modoBusqueda = FXCollections.observableArrayList("DNI");

    @FXML
    private TextField txtDniBuscar;

    @FXML
    private Button btnBuscar;

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
            Cliente busqueda = buscarDniEnBBDD(dni);


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
            mostrarAlerta("Información","Ambos campos están vacíos, rellénalos por favor.");
            return;
        }

        if(dni.isEmpty()){
            mostrarAlerta("Información","Rellena el campo dni, no puedes dejarlo vacío");
            return;
        }

        if(comboBuscar == null){
            mostrarAlerta("Información","Selecciona un modo de búsqueda");
            return;
        }


        if(buscarDniEnBBDD(dni)!=null){

            Cliente clienteBuscado = buscarDniEnBBDD(dni);

            int id = clienteBuscado.getIdCliente();

            ObservableList<Coche> coches = CocheDao.listarCochesCliente(id);

            if(!coches.isEmpty()){
                this.colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
                this.colMarca.setCellValueFactory(new PropertyValueFactory<>("Marca"));
                this.colModelo.setCellValueFactory(new PropertyValueFactory<>("Modelo"));
                this.colColor.setCellValueFactory(new PropertyValueFactory<>("Color"));
                this.colTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
                this.tblCoche.setItems(coches);


            }else{
                mostrarAlerta("Error","No existen coches asociados al siguiente dni "+dni);
                tblCoche.getItems().clear(); //limpio los valores de la tabla
            }
        }


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

    private Cliente buscarDniEnBBDD(String dni){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            //Hago la consulta
            String consulta = "SELECT ID_Cliente,Nombre, Apellido, Dni, Direccion, Fecha_Nacimiento, Email, Telefono " +
                    "FROM Cliente " +
                    "WHERE Dni = ?";

            try (PreparedStatement cliente = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                cliente.setString(1, dni);



                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = cliente.executeQuery();
                if(resultado.next()){
                    return new Cliente(
                            resultado.getInt("ID_Cliente"),
                            resultado.getString("Direccion"),
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Dni"),
                            resultado.getDate("Fecha_Nacimiento").toLocalDate(),
                            resultado.getString("Email"),
                            resultado.getString("Telefono"));

                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


}
