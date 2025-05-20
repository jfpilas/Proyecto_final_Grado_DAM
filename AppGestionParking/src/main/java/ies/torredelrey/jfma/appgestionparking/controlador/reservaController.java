package ies.torredelrey.jfma.appgestionparking.controlador;


import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.ReservaDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Coche;
import ies.torredelrey.jfma.appgestionparking.modelo.Plaza;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class reservaController {


    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnReservar;

    @FXML
    private DatePicker dtpFechaE;

    @FXML
    private DatePicker dtpFechaS;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtNumPlaza;


    private String numeroPlaza;
    private int IdPlaza;
    private LocalDate fechaActual;

    public void setNumeroPlaza(String numeroPlaza) {
        this.numeroPlaza = numeroPlaza;
        if (txtNumPlaza != null) {
            txtNumPlaza.setText(numeroPlaza);
            txtNumPlaza.setEditable(false);
        }
    }

    public void setIdPlaza(int idPlaza){
        this.IdPlaza = idPlaza;
    }

    public void setFechaEntrada(LocalDate actual){
        this.fechaActual = actual;
        dtpFechaE.setValue(fechaActual);
    }

    @FXML
    void OnClickCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void OnClickReservar(ActionEvent event) {

        mostrarAlertaConfirmacionGuardado("CONFIRMACIÓN","¿Estás seguro que quieres reservar con esos datos ?");
        Stage stage = (Stage) btnReservar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlertaConfirmacionGuardado(String titulo, String mensaje) {


        Alert mensajeSalir = new Alert(Alert.AlertType.CONFIRMATION);

        mensajeSalir.setTitle(titulo);
        mensajeSalir.setContentText(mensaje);

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        mensajeSalir.getButtonTypes().setAll(btnSi, btnNo);

        Optional<ButtonType> opcion = mensajeSalir.showAndWait();

        if (opcion.get() == btnSi) {
            String dni = txtDni.getText();
            String matricula = txtMatricula.getText();
            LocalDate fechaEntrada = dtpFechaE.getValue();
            LocalDate fechaSalida = dtpFechaS.getValue();

            LocalDateTime fechaEntradaHora =LocalDateTime.of(fechaEntrada, LocalTime.now());
            LocalDateTime fechaSalidaHora =LocalDateTime.of(fechaSalida, LocalTime.now());

            Reserva verificacion = ReservaDao.verificacionPlazaReservada(IdPlaza);

            Plaza nueva = PlazaDao.ObtenerPlazaPorId(IdPlaza);
            String tipoplaza = nueva.getTipo();
            if(verificacion!= null){
                FuncionesReutilizables.mostrarAlertaInformacion("Error","Esta plaza no está disponible para reservar , ya está ocupada");
                return;
            }

            Cliente cliente = ClienteDao.buscarDniEnBBDD(dni);
            if(cliente == null){
                FuncionesReutilizables.mostrarAlertaInformacion("Error", "Este cliente no está registrado en el hotel");
                return;
            }

            if(!Validaciones.validarDNI(dni)){
                FuncionesReutilizables.mostrarAlertaInformacion("Error","Su dni no es correcto,seguro que se ha equivocado.Prueba otra vez.");

                return ;
            }

            if(!CocheDao.verificarMatricula(matricula)){
                FuncionesReutilizables.mostrarAlertaInformacion("Error","Su matrícula no está registrada en el hotel.");
                return;
            }

            if(!Validaciones.validarMatricula(matricula)){
                FuncionesReutilizables.mostrarAlertaInformacion("Error","Su matrícula es incorrecta.");

                return;
            }
            if(fechaEntrada.isBefore(LocalDate.now())|| fechaSalida.isBefore(fechaEntrada)){
                FuncionesReutilizables.mostrarAlertaInformacion("Error","No puedes marcar una fecha anterior a la de hoy para realizar una reserva o marcar una hora de salida anterior a la de entrada.");
                return;
            }

            HashMap<String,Integer> resultadoIds = ReservaDao.obtenerIdClienteYCoche(dni,matricula);

            if(resultadoIds != null){
                Reserva nuevaReserva = new Reserva(resultadoIds.get("idCliente"),
                        resultadoIds.get("idCoche"),
                        IdPlaza, //necesito un metodo para conseguir el id de la plaza
                        fechaEntradaHora,
                        fechaSalidaHora
                );

                int id = resultadoIds.get("idCoche");
                ObservableList<Coche> nuevo = CocheDao.listarCoches();
                Coche seleccionado = null;
                for (Coche c : nuevo){
                    if(c.getIdCoche()== id){
                        seleccionado = new Coche(c.getMatricula(),c.getMarca(),c.getModelo(),c.getColor(),c.getTipo());
                    }
                }
                assert seleccionado != null;
                if(!tipoplaza.equals(seleccionado.getTipo())){
                    FuncionesReutilizables.mostrarAlertaInformacion("Error", "No es compatible el tipo de coche con el tipo de plaza que quieres reservar. Por favor elije otra. Gracias.");
                    return;
                }

                if (ReservaDao.cocheYaReservadoEnFecha(resultadoIds.get("idCoche"), fechaEntradaHora, fechaSalidaHora)) {
                    FuncionesReutilizables.mostrarAlertaInformacion("Error", "Este coche ya tiene una reserva activa en ese periodo.");
                    return;
                }

                int totalReservasConIDcoche= ReservaDao.verificacionPlazaReservadaPorMismoCoche(nuevaReserva.getIdCoche());
                if(totalReservasConIDcoche > 0){
                    FuncionesReutilizables.mostrarAlertaInformacion("Error", "Ya existe una reserva con ese coche, revísalo.");
                    return;
                }
                boolean exito = ReservaDao.guardarReserva(nuevaReserva);


                if (exito) {
                    if(nuevaReserva.getFechaEntrada().isAfter(LocalDateTime.now())){
                        PlazaDao.cambiarEstadoPlaza("Reservada",nuevaReserva.getIdPlaza());
                    }else{
                        PlazaDao.cambiarEstadoPlaza("Ocupada",nuevaReserva.getIdPlaza());
                    }
                    Alert mensajeExito = new Alert(Alert.AlertType.INFORMATION);

                    mensajeExito.setTitle("Éxito");
                    mensajeExito.setContentText("Se ha guardado correctamente la Reserva");

                    ButtonType btnVale = new ButtonType("Vale");


                    mensajeExito.getButtonTypes().setAll(btnVale);

                    // Bloqueo el cierre de ventana con la x

                    Stage stage = (Stage) mensajeExito.getDialogPane().getScene().getWindow();
                    stage.setOnCloseRequest(event -> event.consume());

                    Optional<ButtonType> opcion1 = mensajeExito.showAndWait();

                    if (opcion1.get() == btnVale) {
                        mensajeExito.close();
                        //Si el guardado es exitoso entonces cambio la plaza a ocupada



                    }
                }
            }
        } else {
            mensajeSalir.close();
        }
    }
}
