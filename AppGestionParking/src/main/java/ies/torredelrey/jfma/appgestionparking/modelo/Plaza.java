package ies.torredelrey.jfma.appgestionparking.modelo;

public class Plaza {
    private int numPlaza;
    private String tipo;
    private String estado;
    private String tarifa;

    public Plaza(int numPlaza, String tipo, String estado, String tarifa) {
        this.numPlaza = numPlaza;
        this.tipo = tipo;
        this.estado = estado;
        this.tarifa = tarifa;
    }

    public int getNumPlaza() {
        return numPlaza;
    }

    public void setNumPlaza(int numPlaza) {
        this.numPlaza = numPlaza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTarifa() {
        return tarifa;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }
}
