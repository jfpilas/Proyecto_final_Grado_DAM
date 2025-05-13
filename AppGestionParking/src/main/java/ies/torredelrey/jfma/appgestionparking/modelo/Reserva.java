package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {

    private int idCliente;
    private int idCoche;
    private int idPlaza;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;

    public Reserva(int idCliente, int idCoche, int idPlaza, LocalDate fechaEntrada, LocalDate fechaSalida, String estado) {
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.idPlaza = idPlaza;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    public Reserva(int idCliente, int idCoche, int idPlaza, LocalDate fechaEntrada, LocalDate fechaSalida) {
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.idPlaza = idPlaza;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = "Activa";
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCoche() {
        return idCoche;
    }

    public void setIdCoche(int idCoche) {
        this.idCoche = idCoche;
    }

    public int getIdPlaza() {
        return idPlaza;
    }

    public void setIdPlaza(int idPlaza) {
        this.idPlaza = idPlaza;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
