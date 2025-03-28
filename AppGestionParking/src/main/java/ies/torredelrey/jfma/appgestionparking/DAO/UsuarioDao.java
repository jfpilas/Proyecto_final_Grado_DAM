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
}
