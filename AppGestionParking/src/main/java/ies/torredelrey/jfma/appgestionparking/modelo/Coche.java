package ies.torredelrey.jfma.appgestionparking.modelo;

public class Coche {
    private String matricula;
    private String Marca;
    private String Modelo;
    private String Color;
    private String Tipo;
    private int idCliente;

    public Coche( int idCliente, String matricula, String marca, String modelo, String color, String tipo) {
        this.idCliente = idCliente;
        this.matricula = matricula;
        Marca = marca;
        Modelo = modelo;
        Color = color;
        Tipo = tipo;

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coche{");
        sb.append("matricula='").append(matricula).append('\'');
        sb.append(", Marca='").append(Marca).append('\'');
        sb.append(", Modelo='").append(Modelo).append('\'');
        sb.append(", Color='").append(Color).append('\'');
        sb.append(", Tipo='").append(Tipo).append('\'');
        sb.append(", idCliente=").append(idCliente);
        sb.append('}');
        return sb.toString();
    }
}
