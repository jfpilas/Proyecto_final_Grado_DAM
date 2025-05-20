package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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
                stmt.setTimestamp(4, Timestamp.valueOf(reserva.getFechaEntrada()));
                stmt.setTimestamp(5, Timestamp.valueOf(reserva.getFechaSalida()));
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
            String consulta = "SELECT ID_Reserva, ID_Cliente,ID_Coche,ID_Plaza,Fecha_Entrada,Fecha_Salida,Estado FROM reserva WHERE ID_Plaza = ? AND Estado = 'Activa' ";

            try (PreparedStatement reserva = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                reserva.setInt(1, id);




                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = reserva.executeQuery();
                if(resultado.next()){
                    return new Reserva( resultado.getInt("ID_Reserva"),
                            resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Coche"),
                            resultado.getInt("ID_Plaza"),
                            resultado.getTimestamp("Fecha_Entrada").toLocalDateTime(),
                            resultado.getTimestamp("Fecha_Salida").toLocalDateTime(),
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

    public static ObservableList<Reserva> obtenerReservasPorIdCliente(int idCliente) {
        ObservableList<Reserva> reservasCliente = FXCollections.observableArrayList();
        ObservableList<Reserva> todasReservas = listarReservas(); // método que ya tienes

        for (Reserva r : todasReservas) {
            if (r.getIdCliente() == idCliente) {
                reservasCliente.add(r);
            }
        }
        return reservasCliente;
    }

    public static Reserva ObtenerReservaPorId(int id) {

        Connection con = Conexion.conectar();
        Reserva reserva = null;
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT * FROM reserva where ID_Reserva = ? ";

            try (PreparedStatement factura = con.prepareStatement(consulta)) {
                factura.setInt(1,id);

                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = factura.executeQuery();
                while (resultado.next()) {
                    reserva = new Reserva(
                            resultado.getInt("ID_Plaza"),
                            resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Coche"),
                            resultado.getInt("ID_Plaza"),
                            resultado.getTimestamp("Fecha_Entrada").toLocalDateTime(),
                            resultado.getTimestamp("Fecha_Salida").toLocalDateTime(),
                            resultado.getString("Estado"));

                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return reserva;
    }

    public static ObservableList<Reserva> listarReservas() {

        ObservableList<Reserva> listaReservas = FXCollections.observableArrayList();
        Connection con = Conexion.conectar();
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Reserva,ID_Cliente,ID_Coche,ID_Plaza,Fecha_Entrada,Fecha_Salida,Estado FROM reserva WHERE Estado = ?";

            try (PreparedStatement reserva = con.prepareStatement(consulta)) {
                reserva.setString(1,"Activa");

                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = reserva.executeQuery();
                while (resultado.next()) {
                    Reserva nuevaReserva = new Reserva(resultado.getInt("ID_Reserva"),
                            resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Coche"),
                            resultado.getInt("ID_Plaza"),
                            resultado.getTimestamp("Fecha_Entrada").toLocalDateTime(),
                            resultado.getTimestamp("Fecha_Salida").toLocalDateTime(),
                            resultado.getString("Estado"));

                    listaReservas.add(nuevaReserva);
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return listaReservas;
    }

    public static boolean cambiarEstadoReserva(String nuevoEstado,int id){
        Connection con = Conexion.conectar();

        if (con != null) {
            String modificacion = "UPDATE reserva SET Estado = ? WHERE ID_Reserva = ?";

            try (PreparedStatement reserva = con.prepareStatement(modificacion)) {
                reserva.setString(1, nuevoEstado);
                reserva.setInt(2, id);

                int filasAfectadas = reserva.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return false;
    }

}
