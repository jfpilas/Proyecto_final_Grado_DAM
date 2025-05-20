package ies.torredelrey.jfma.appgestionparking.modelo;

import java.time.LocalDate;

public class Cliente {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String dni;
    private LocalDate fecha;
    private String direccion;
    private int idCliente;



    public Cliente(int idCliente ,String direccion, String nombre, String apellido, String dni, LocalDate fecha, String email, String telefono) {
        this.idCliente = idCliente;
        this.direccion = direccion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha = fecha;
        this.email = email;
        this.telefono = telefono;


    }

    public Cliente(String direccion, String nombre, String apellido, String dni, LocalDate fecha, String email, String telefono) {
        this.idCliente = getIdCliente();
        this.direccion = direccion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fecha = fecha;
        this.email = email;
        this.telefono = telefono;

    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cliente{");
        sb.append("nombre='").append(nombre).append('\'');
        sb.append(", apellido='").append(apellido).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", dni='").append(dni).append('\'');
        sb.append(", fecha=").append(fecha);
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append(", idCliente=").append(idCliente);
        sb.append('}');
        return sb.toString();
    }
}
