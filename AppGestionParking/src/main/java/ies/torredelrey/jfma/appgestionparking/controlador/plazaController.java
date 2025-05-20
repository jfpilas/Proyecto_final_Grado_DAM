package ies.torredelrey.jfma.appgestionparking.controlador;


import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.ReservaDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Plaza;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class plazaController {
    @FXML
    protected TextField txtCliente;

    @FXML
    protected TextField txtFechaE;

    @FXML
    protected TextField txtFechaS;

    @FXML
    protected TextField txtMatricula;

    @FXML
    private Button btnCancelar;




    private String nombreCliente;
    private String matriculaCoche;
    private LocalDateTime fechaE;
    private LocalDateTime fechaS;
    private int idPlaza;

    @FXML
    void OnClickCancelar(ActionEvent event) throws IOException {
        Plaza seleccionada = PlazaDao.ObtenerPlazaPorId(idPlaza);
        System.out.println(seleccionada);

        if (FuncionesReutilizables.mostrarAlertaConfirmacionCancelar("Cancelar Reserva", "¿Estas seguro de que quieres cancelar la reserva ?")) {
            if (seleccionada != null) {
                if (seleccionada.getEstado().equals("Ocupada")|| seleccionada.getEstado().equals("Reservada") ) {
                    PlazaDao.cambiarEstadoPlaza("Libre", idPlaza);
                    Reserva elegida = ReservaDao.obtenerReservaPorIdPlaza(idPlaza);
                    if (elegida != null) {
                        ReservaDao.cambiarEstadoReserva("Cancelada", elegida.getIdReserva());
                    } else {
                        FuncionesReutilizables.mostrarAlertaInformacion("Error", "No se ha podido cancelar la reserva");
                    }
                } else {
                    FuncionesReutilizables.mostrarAlertaInformacion("Error", "Esta plaza no está ocupada.");
                }
                Stage stage = (Stage) btnCancelar.getScene().getWindow();
                stage.close();
            } else {
                FuncionesReutilizables.mostrarAlertaInformacion("Error", "No existe ninguna plaza seleccionada.");
            }
        }
    }

    public void setNombreCliente(String nombre){
        this.nombreCliente = nombre;
        txtCliente.setText(nombreCliente);
        txtCliente.setEditable(false);
    }

    public void setMatriculaCoche(String matricula){
        this.matriculaCoche = matricula;
        txtMatricula.setText(matriculaCoche);
        txtMatricula.setEditable(false);
    }

    public void setFechaE(LocalDateTime fecha){
        this.fechaE = fecha;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaformateada = fechaE.format(formato);
        txtFechaE.setText(fechaformateada);
        txtFechaE.setEditable(false);
    }

    public void setFechaS(LocalDateTime fecha){
        this.fechaS = fecha;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String fechaformateada = fechaS.format(formato);
        txtFechaS.setText(fechaformateada);
        txtFechaS.setEditable(false);
    }

    public void setPlazaSeleccionada(int id){
        this.idPlaza = id;
    }

    public void setBtnCancelar(boolean visible) {
        if (btnCancelar != null) {
            btnCancelar.setVisible(visible);
        }
    }

}
