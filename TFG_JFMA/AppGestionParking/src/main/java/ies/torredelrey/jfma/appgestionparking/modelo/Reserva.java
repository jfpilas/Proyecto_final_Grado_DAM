package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {

    private int idReserva;
    private int idCliente;
    private int idCoche;
    private int idPlaza;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private String estado;

    public Reserva(int idReserva, int idCliente, int idCoche, int idPlaza, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, String estado) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.idCoche = idCoche;
        this.idPlaza = idPlaza;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }

    public Reserva(int idCliente, int idCoche, int idPlaza, LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
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

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdReserva(){return idReserva;}

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

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Reserva{");
        sb.append("idReserva=").append(idReserva);
        sb.append(", idCliente=").append(idCliente);
        sb.append(", idCoche=").append(idCoche);
        sb.append(", idPlaza=").append(idPlaza);
        sb.append(", fechaEntrada=").append(fechaEntrada);
        sb.append(", fechaSalida=").append(fechaSalida);
        sb.append(", estado='").append(estado).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
