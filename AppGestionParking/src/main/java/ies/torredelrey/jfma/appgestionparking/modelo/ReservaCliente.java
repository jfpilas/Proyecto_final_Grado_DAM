package ies.torredelrey.jfma.appgestionparking.modelo;

import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ReservaCliente {
    public Reserva reserva;
    public Cliente cliente;
    public Coche coche;

    public ReservaCliente(Reserva reserva, Cliente cliente,Coche coche) {
        this.reserva = reserva;
        this.cliente = cliente;
        this.coche= coche;
    }

    public String getMatriculaCoche(){return coche.getMatricula();}

    public String getNombreCliente() {
        return cliente.getNombre();
    }

    public String getApellidosCliente() {
        return cliente.getApellido();
    }

    public String getEmailCliente() {
        return cliente.getEmail();
    }

    public int getIdReserva() {
        return reserva.getIdReserva();
    }

    public int getIdCoche(){
        return reserva.getIdCoche();
    }


    public String getEstadoReserva() {
        return reserva.getEstado();
    }

    public LocalDateTime getFechaEntrada() {
        return reserva.getFechaEntrada();
    }

    public LocalDateTime getFechaSalida() {
        return reserva.getFechaSalida();
    }

    public float getTarifaPlaza() {
        Plaza plaza = PlazaDao.ObtenerPlazaPorId(reserva.getIdPlaza());
        return plaza.getTarifa();
    }

    public float getTotalReserva() {
        long dias = ChronoUnit.DAYS.between(reserva.getFechaEntrada(), reserva.getFechaSalida());
        return getTarifaPlaza() * dias;
    }
}
