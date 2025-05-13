package ies.torredelrey.jfma.appgestionparking.modelo;

public class Plaza {
    private int idPlaza;
    private String numPlaza;
    private String tipo;
    private String estado;
    private String tarifa;

    public Plaza(int idPlaza,String numPlaza, String tipo, String estado, String tarifa) {
        this.idPlaza = idPlaza;
        this.numPlaza = numPlaza;
        this.tipo = tipo;
        this.estado = estado;
        this.tarifa = tarifa;
    }

    public Plaza(String numPlaza, String tipo, String estado, String tarifa) {
        this.idPlaza = getIdPlaza();
        this.numPlaza = numPlaza;
        this.tipo = tipo;
        this.estado = estado;
        this.tarifa = tarifa;
    }

    public int getIdPlaza() {
        return idPlaza;
    }

    public void setIdPlaza(int idPlaza) {
        this.idPlaza = idPlaza;
    }

    public String getNumPlaza() {
        return numPlaza;
    }

    public void setNumPlaza(String numPlaza) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Plaza{");
        sb.append("numPlaza='").append(numPlaza).append('\'');
        sb.append(", tipo='").append(tipo).append('\'');
        sb.append(", estado='").append(estado).append('\'');
        sb.append(", tarifa='").append(tarifa).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
