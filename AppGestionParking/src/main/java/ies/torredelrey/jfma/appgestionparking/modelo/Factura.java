package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Factura {
    private int idfactura;
    private int idcliente;
    private int idreserva;
    private float total;
    private LocalDateTime fecha_emision;
    private String estado;

    public Factura(int idcliente, int idreserva, float total, LocalDateTime fecha_emision, String estado) {
        this.idcliente = idcliente;
        this.idreserva = idreserva;
        this.total = total;
        this.fecha_emision = fecha_emision;
        this.estado = estado;
    }

    public Factura(int idcliente, int idreserva, float total, LocalDateTime fecha_emision, String estado,int idfactura) {
        this.idcliente = idcliente;
        this.idreserva = idreserva;
        this.total = total;
        this.fecha_emision = fecha_emision;
        this.estado = estado;
        this.idfactura=idfactura;
    }

    public int getidCliente() {
        return idcliente;
    }

    public void setidCliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public int getidReserva() {
        return idreserva;
    }

    public void setidReserva(int idreserva) {
        this.idreserva = idreserva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public LocalDateTime getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(LocalDateTime fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Factura{");
        sb.append("idfactura=").append(idfactura);
        sb.append(", id_cliente=").append(idcliente);
        sb.append(", id_reserva=").append(idreserva);
        sb.append(", total=").append(total);
        sb.append(", fecha_emision=").append(fecha_emision);
        sb.append(", estado='").append(estado).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
