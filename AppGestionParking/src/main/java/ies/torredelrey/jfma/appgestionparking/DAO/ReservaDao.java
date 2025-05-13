package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ReservaDao {

    public static boolean guardarReserva(Reserva reserva){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            String insertar = "INSERT INTO reserva (ID_Cliente,ID_Coche,ID_Plaza,Fecha_Entrada,Fecha_Salida,Estado) VALUES (?,?,?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setInt(1, reserva.getIdCliente());
                stmt.setInt(2, reserva.getIdCoche());
                stmt.setInt(3, reserva.getIdPlaza());
                stmt.setDate(4, java.sql.Date.valueOf(reserva.getFechaEntrada()));
                stmt.setDate(5, java.sql.Date.valueOf(reserva.getFechaSalida()));
                stmt.setString(6, reserva.getEstado());

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

    public static Reserva verificacionPlazaReservada(int id){
        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            //Hago la consulta
            String consulta = "SELECT ID_Cliente,ID_Coche,ID_Plaza,Fecha_Entrada,Fecha_Salida,Estado FROM reserva WHERE ID_Plaza = ? ";

            try (PreparedStatement reserva = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                reserva.setInt(1, id);



                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = reserva.executeQuery();
                if(resultado.next()){
                    return new Reserva(resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Coche"),
                            resultado.getInt("ID_Plaza"),
                            resultado.getDate("Fecha_Entrada").toLocalDate(),
                            resultado.getDate("Fecha_Salida").toLocalDate(),
                            resultado.getString("Estado")
                    );
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return null;
    }

    public static HashMap<String, Integer> obtenerIdClienteYCoche(String dni, String matricula) {
        Connection con = Conexion.conectar();

        if (con != null) {
            String consulta ="SELECT c.ID_Cliente,co.ID_Coche FROM cliente c INNER JOIN coche co ON c.ID_Cliente = co.ID_Cliente WHERE c.Dni = ? and  co.matricula = ?";

            try (PreparedStatement stmt = con.prepareStatement(consulta)) {
                stmt.setString(1, dni);
                stmt.setString(2, matricula);

                ResultSet resultado = stmt.executeQuery();
                if (resultado.next()) {
                    HashMap<String, Integer> ids = new HashMap<>();
                    ids.put("idCliente", resultado.getInt("Id_Cliente"));
                    ids.put("idCoche", resultado.getInt("Id_Coche"));
                    return ids;
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener IDs: " + e.getMessage());
            }
        }

        return null;
    }

}
