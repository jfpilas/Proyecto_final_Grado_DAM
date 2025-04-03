package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public static Usuario verificacion_usuario(String nombreUsuario,String password){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            //Hago la consulta
            String consulta = "SELECT Nombre, Apellido, Email, Telefono, Contraseña, Rol, Nombre_usuario " +
                    "FROM usuario " +
                    "WHERE Nombre_usuario = ? AND Contraseña= ?";

            try (PreparedStatement user = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                user.setString(1, nombreUsuario);
                user.setString(2, password);


                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = user.executeQuery();
                if(resultado.next()){
                    return new Usuario(resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Email"),
                            resultado.getString("Telefono"),
                            resultado.getString("Contraseña"),
                            resultado.getString("Rol"),
                            resultado.getString("Nombre_usuario"));
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return null;
    }

    public boolean guardarUsuario(Usuario usu){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            String insertar = "INSERT INTO usuario (Nombre,Apellido,Email,Telefono,Contraseña,Rol,Nombre_usuario) VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setString(1, usu.getNombre());
                stmt.setString(2, usu.getApellido());
                stmt.setString(3, usu.getEmail());
                stmt.setString(4, usu.getTelefono());
                stmt.setString(5, usu.getPassword());
                stmt.setString(6, usu.getRol());
                stmt.setString(7, usu.getNombre_usuario());

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
