package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.UsuarioDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

import static ies.torredelrey.jfma.appgestionparking.controlador.clienteController.*;
import static ies.torredelrey.jfma.appgestionparking.vista.GestorParking.llenarCombo;

public class usuarioController {

    ObservableList<String> rolesList = FXCollections.observableArrayList("Administrador","Cliente","Recepcionista");

    @FXML
    private Button btnCancelarUuario;

    @FXML
    private Button btnGuardarUsuario;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtNombreUsuario;

    @FXML
    private TextField txtPassword;

    @FXML
    private ComboBox<String> cbxRol;

    @FXML
    private TextField txtTelefono;

    @FXML
    void onClickCancelar(ActionEvent event) {
        Stage stage= (Stage) btnCancelarUuario.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onClickGuardar(ActionEvent event) {

        String nombre = txtNombre.getText();
        String nombreUsuario= txtNombreUsuario.getText();
        String apellidos = txtApellidos.getText();
        String rol = cbxRol.getValue();
        String email= txtEmail.getText();
        String telefono = txtTelefono.getText();
        String contrasena = txtPassword.getText();

        if (!Validaciones.validarNombre(nombre)) {
            // Mostrar mensaje de error
            System.out.println("Nombre inválido");
            return;
        }



        if (!Validaciones.validarNombre(apellidos)) {
            // Mostrar mensaje de error
            System.out.println("Nombre inválido");
            return;
        }

        // Validación teléfono
        if (!Validaciones.validarTelefono(telefono)) {
            // Mostrar mensaje de error
            System.out.println("Teléfono inválido");
            return;
        }

        // Validación del Email con una expresión regular
        if (!Validaciones.validarEmail(email)) {
            // Mostrar mensaje de error
            System.out.println("Email inválido");
            return;
        }

        if(rol == null){
            System.out.println("No puedes dejar el rol vacío");
            return;
        }

        if(!Validaciones.validarContrasena(contrasena)){
            System.out.println("La contraseña " +
                    "Debe tener al menos 8 caracteres.\n" +
                    "Debe contener al menos una letra mayúscula.\n" +
                    "Debe contener al menos una letra minúscula.\n" +
                    "Debe contener al menos un número.\n" +
                    "Debe contener al menos un carácter especial (@, #, $, %, &, etc.).");
            return;
        }

        // Si todas las validaciones son correctas, guardar los datos

        Usuario nuevousuario = new Usuario(nombre, apellidos,email, telefono, contrasena, rol, nombreUsuario);
        UsuarioDao usuario = new UsuarioDao();
        boolean exito = usuario.guardarUsuario(nuevousuario);

        if (exito) {
            Alert mensajeExito = new Alert(Alert.AlertType.INFORMATION);

            mensajeExito.setTitle("Éxito");
            mensajeExito.setContentText("Se ha guardado correctamente el Usuario");

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

    public void listarRoles(Event event) {
        llenarCombo(cbxRol,rolesList);
    }
}
