package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClienteDao {

    public boolean guardarCliente(Cliente cliente){
        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            String insertar = "INSERT INTO cliente (Nombre,Apellido,Dni,Fecha_Nacimiento,Direccion,Email,Telefono) VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setString(1, cliente.getNombre());
                stmt.setString(2, cliente.getApellido());
                stmt.setString(3, cliente.getDni());
                stmt.setDate(4, java.sql.Date.valueOf(cliente.getFecha()));  // Convertir LocalDate a Date
                stmt.setString(5, cliente.getDireccion());
                stmt.setString(6, cliente.getEmail());
                stmt.setString(7, cliente.getTelefono());

                // Ejecutar la inserción
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;  // Si se insertaron filas correctamente
            } catch (SQLException e) {
                System.out.println("Error al guardar el cliente: " + e.getMessage());
                return false;
            }
        }

        return true;
    }
}
