package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FacturaPago {
    private String cliente;
    private int idfactura;
    private int Reserva;
    private float total;
    private LocalDateTime fecha;
    private String estado;
    private String matricula;

    public FacturaPago(String cliente, int reserva, float total, LocalDateTime fecha, String estado,int idfactura,String matricula) {
        this.cliente = cliente;
        Reserva = reserva;
        this.total = total;
        this.fecha = fecha;
        this.estado = estado;
        this.idfactura = idfactura;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FacturaPago{");
        sb.append("cliente='").append(cliente).append('\'');
        sb.append(", Reserva=").append(Reserva);
        sb.append(", total=").append(total);
        sb.append(", fecha=").append(fecha);
        sb.append(", estado='").append(estado).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getReserva() {
        return Reserva;
    }

    public void setReserva(int reserva) {
        Reserva = reserva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(int idfactura) {
        this.idfactura = idfactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
