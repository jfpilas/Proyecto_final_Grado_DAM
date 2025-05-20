package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class plazaController {
    @FXML
    protected TextField txtCliente;

    @FXML
    protected TextField txtFechaE;

    @FXML
    protected TextField txtFechaS;

    @FXML
    protected TextField txtMatricula;


    private String nombreCliente;
    private String matriculaCoche;
    private LocalDateTime fechaE;
    private LocalDateTime fechaS;



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
        txtFechaE.setText(String.valueOf(fechaE));
        txtFechaE.setEditable(false);
    }

    public void setFechaS(LocalDateTime fecha){
        this.fechaS = fecha;
        txtFechaS.setText(String.valueOf(fechaS));
        txtFechaS.setEditable(false);
    }
}
