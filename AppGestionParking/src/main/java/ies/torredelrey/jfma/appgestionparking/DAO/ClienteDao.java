package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ClienteDao {

    public boolean guardarCliente(String nombre , String apellido , String dni , LocalDate fecha , String direccion , String email , String telefono){
        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            String insertar = "INSERT INTO cliente (Nombre,Apellido,Dni,Fecha_Nacimiento,Email,Telefono) VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setString(3, dni);
                stmt.setDate(4, java.sql.Date.valueOf(fecha));  // Convertir LocalDate a Date
                stmt.setString(5, direccion);
                stmt.setString(6, email);
                stmt.setString(7, telefono);

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
