package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class clienteController {

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
            Alert mensajeError = new Alert(Alert.AlertType.INFORMATION);

            mensajeError.setTitle("Error");
            mensajeError.setContentText("Error al guardar el nuevo Cliente");

            ButtonType btnVale = new ButtonType("Vale");


            mensajeError.getButtonTypes().setAll(btnVale);

            Optional<ButtonType> opcion = mensajeError.showAndWait();

            if (opcion.get() == btnVale) {
                mensajeError.close();
            }
        }
    }




}
