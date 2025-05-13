package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Plaza;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlazaDao {

    public static int contarPlazasOcupadas() {
        Connection con = Conexion.conectar();

        // Si la conexión es válida
        if (con != null) {

            // Consulta SQL para contar las plazas cuyo estado sea 'Ocupada'
            String contarPlazas = "SELECT COUNT(*) FROM plaza WHERE Estado = 'Ocupada'";

            try (PreparedStatement stmt = con.prepareStatement(contarPlazas)) {

                // Ejecutar la consulta y obtener el resultado
                ResultSet resultado = stmt.executeQuery();

                // Si la consulta devuelve algún resultado, obtener el conteo de plazas ocupadas
                if (resultado.next()) {
                    return resultado.getInt(1);  // El conteo de las plazas ocupadas
                }

            } catch (SQLException e) {
                // En caso de error, mostrar el mensaje de error
                System.out.println("Error al contar las plazas ocupadas: " + e.getMessage());
            }
        }

        return 0;  // En caso de no encontrar resultados o error, devolver 0
    }

    public static Plaza devuelvePlaza(String numeroPlaza){
        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            //Hago la consulta
            String consulta = "SELECT ID_Plaza,Numero_Plaza, Tipo,Estado,Tarifa FROM plaza WHERE Numero_Plaza = ? ";

            try (PreparedStatement plaza = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                plaza.setString(1, numeroPlaza);



                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = plaza.executeQuery();
                if(resultado.next()){
                    return new Plaza(resultado.getInt("ID_Plaza"),
                            resultado.getString("Numero_Plaza"),
                            resultado.getString("Tipo"),
                            resultado.getString("Estado"),
                            resultado.getString("Tarifa")
                            );
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return null;
    }

    public static boolean cambiarEstadoPlaza(String nuevoEstado,int id){
        Connection con = Conexion.conectar();

        if (con != null) {
            String modificacion = "UPDATE plaza SET Estado = ? WHERE ID_Plaza = ?";

            try (PreparedStatement plaza = con.prepareStatement(modificacion)) {
                plaza.setString(1, nuevoEstado);
                plaza.setInt(2, id);

                int filasAfectadas = plaza.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return false;
    }
}

