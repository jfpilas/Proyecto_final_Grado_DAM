package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Factura;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaDao {


    public static Factura buscarFacturaPorFacturaID(int factura) {

        Factura nueva = null;
        Connection con = Conexion.conectar();
        if (con != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Factura,ID_Cliente,ID_Reserva,Monto_Total,Fecha_Emision,Estado FROM factura where ID_Factura = ? ";

            try (PreparedStatement factur = con.prepareStatement(consulta)) {
                factur.setInt(1,factura);

                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = factur.executeQuery();
                while (resultado.next()) {
                    nueva = new Factura(
                            resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Reserva"),
                            resultado.getFloat("Monto_Total"),
                            resultado.getTimestamp("Fecha_Emision").toLocalDateTime(),
                            resultado.getString("Estado"),
                            resultado.getInt("ID_Factura"));


                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return nueva;
    }

    public static ObservableList<Factura> listarFacturas(String estado) {

        ObservableList<Factura> listaFacturas = FXCollections.observableArrayList();
        Connection con = Conexion.conectar();
        if (con != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Factura,ID_Cliente,ID_Reserva,Monto_Total,Fecha_Emision,Estado FROM factura where Estado = ? ";

            try (PreparedStatement factura = con.prepareStatement(consulta)) {
                factura.setString(1,estado);

                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = factura.executeQuery();
                while (resultado.next()) {
                    Factura nuevaFactura = new Factura(
                            resultado.getInt("ID_Cliente"),
                            resultado.getInt("ID_Reserva"),
                            resultado.getFloat("Monto_Total"),
                            resultado.getTimestamp("Fecha_Emision").toLocalDateTime(),
                            resultado.getString("Estado"),
                            resultado.getInt("ID_Factura"));

                    listaFacturas.add(nuevaFactura);
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return listaFacturas;
    }

    public static void cambiaEstado(String estado,int idFactura) {
        Connection con = Conexion.conectar();
        if (con != null) {

            String modifica = "UPDATE factura SET Estado = ? WHERE ID_Factura = ?";

            try (PreparedStatement factura = con.prepareStatement(modifica)) {
                factura.setString(1, estado);
                factura.setInt(2, idFactura);

                factura.executeUpdate();

            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }
    }

        public static boolean guardarFactura(Factura factura) {
            Connection con = Conexion.conectar();
            if (con == null) {
                System.out.println("Conexión fallida.");
                return false;
            }

            String insert = "INSERT INTO factura (ID_Cliente, ID_Reserva, Monto_Total, Fecha_Emision, Estado) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setInt(1, factura.getidCliente());
                ps.setInt(2, factura.getidReserva());
                ps.setFloat(3, factura.getTotal());
                ps.setTimestamp(4, java.sql.Timestamp.valueOf(factura.getFecha_emision()));
                ps.setString(5, factura.getEstado());

                int filasAfectadas = ps.executeUpdate();
                return filasAfectadas > 0;

            } catch (SQLException e) {
                System.out.println("Error al guardar factura: " + e.getMessage());
                return false;
            }
        }

}
