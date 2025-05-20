package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Pago {
    private int id_factura;
    private float montoPagado;
    private LocalDateTime fecha_pago;
    private String metodoPago;

    public Pago(int id_factura, float montoPagado, LocalDateTime fecha_pago, String metodoPago) {
        this.id_factura = id_factura;
        this.montoPagado = montoPagado;
        this.fecha_pago = fecha_pago;
        this.metodoPago = metodoPago;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public float getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(float montoPagado) {
        this.montoPagado = montoPagado;
    }

    public LocalDateTime getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDateTime fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pago{");
        sb.append("id_factura=").append(id_factura);
        sb.append(", montoPagado=").append(montoPagado);
        sb.append(", fecha_pago=").append(fecha_pago);
        sb.append(", metodoPago='").append(metodoPago).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
