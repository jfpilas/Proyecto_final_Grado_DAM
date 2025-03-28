package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;
import java.util.Optional;

public class menuController {

    @FXML
    private AnchorPane PanelCliente;

    @FXML
    private Button btnCancelarClientes;

    @FXML
    private Button btnGuardarClientes;

    @FXML
    private CheckMenuItem cmniCliente;

    @FXML
    private CheckMenuItem cmniFactura;

    @FXML
    private CheckMenuItem cmniParking;

    @FXML
    private DatePicker dpFechaNacCliente;

    @FXML
    private ImageView imgAlcazar;

    @FXML
    private MenuItem mniSalir;

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
    void OnClickParking1(ActionEvent event) {

    }

    @FXML
    void OnClickCliente(ActionEvent event) {
        if(cmniCliente.isSelected()){
            PanelCliente.setVisible(true);
            imgAlcazar.setVisible(false);
        }else{
            PanelCliente.setVisible(false);
            imgAlcazar.setVisible(true);
        }
    }

    @FXML
    void cancelarClientes(ActionEvent event) {

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

        if (!validarNombre(nombre)) {
            // Mostrar mensaje de error
            System.out.println("Nombre inválido");
            return;
        }

        if (!validarNombre(apellido)) {
            // Mostrar mensaje de error
            System.out.println("Nombre inválido");
            return;
        }

        // Validación teléfono
        if (!validarTelefono(telefono)) {
            // Mostrar mensaje de error
            System.out.println("Teléfono inválido");
            return;
        }

        if (!validarDNI(dni)) {
            // Mostrar mensaje de error
            System.out.println("DNI inválido");
            return;
        }

        // Validación Fecha de Nacimiento (comprobamos que la fecha no esté en el futuro)
        if (!validarFechaNacimiento(fecha)) {
            // Mostrar mensaje de error
            System.out.println("Fecha de nacimiento inválida");
            return;
        }

        // Validación del Email con una expresión regular
        if (!validarEmail(email)) {
            // Mostrar mensaje de error
            System.out.println("Email inválido");
            return;
        }

        // Si todas las validaciones son correctas, guardar los datos (puedes llamar al método de guardado aquí)
        ClienteDao cliente = new ClienteDao();
        boolean exito = cliente.guardarCliente(nombre, apellido, dni, fecha, direccion, email, telefono);

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

    private boolean validarDNI(String dni) {
        // Validamos que el DNI tenga entre 8 y 20 caracteres
        if (dni.length() < 8 || dni.length() > 20) {
            return false;
        }

        // Validamos que el DNI contenga solo caracteres alfanuméricos (letras y números)
        if (!dni.matches("[a-zA-Z0-9]+")) {
            return false;
        }

        return true;
    }

    private boolean validarNombre(String nombre) {
        // Verifica que el nombre no esté vacío y contenga solo letras y espacios
        return nombre != null && !nombre.isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    // Método para validar Teléfono (solo números, mínimo 7 y máximo 15 dígitos)
    private boolean validarTelefono(String telefono) {
        // Verifica que el teléfono contenga solo números y tenga entre 7 y 15 dígitos
        return telefono != null && telefono.matches("\\d{7,15}");
    }

    // Método para validar Fecha de Nacimiento (no debe ser una fecha futura)
    private boolean validarFechaNacimiento(LocalDate fecha) {
        if (fecha == null) {
            return false; // La fecha no debe ser nula
        }

        // Comprobamos que la fecha no esté en el futuro
        if (fecha.isAfter(LocalDate.now())) {
            return false;
        }

        // También podríamos verificar si el usuario tiene una edad mínima (por ejemplo, 18 años)
        LocalDate fechaMinima = LocalDate.now().minusYears(18);
        if (fecha.isAfter(fechaMinima)) {
            return false;
        }

        return true;
    }

    // Método para validar Email con una expresión regular
    private boolean validarEmail(String email) {
        // Expresión regular básica para validar un email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);

    }


    @FXML
    void OnClickFactura(ActionEvent event) {

    }

    @FXML
    protected void OnClickSalir(ActionEvent event) {
        Alert mensajeSalir = new Alert(Alert.AlertType.CONFIRMATION);

        mensajeSalir.setTitle("Confirmación de salida");
        mensajeSalir.setContentText("¿Estás seguro que deseas salir de la aplicación?");

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        mensajeSalir.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> opcion = mensajeSalir.showAndWait();

        if (opcion.get() == btnSi) {
            System.exit(0);
        } else {
            mensajeSalir.close();
        }


    }

}
