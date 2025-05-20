package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Factura;
import ies.torredelrey.jfma.appgestionparking.modelo.Pago;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;

import java.sql.*;

public class PagoDao {

    public static boolean buscaPagoConMismoIdFactura(int idFactura) throws SQLException {
        Connection con = Conexion.conectar();
        if (con != null) {
            String buscar = "SELECT * FROM pago where ID_Factura = ?";

            try (PreparedStatement pago = con.prepareStatement(buscar)) {
                pago.setInt(1, idFactura);
                try (ResultSet rs = pago.executeQuery()) {
                    // Si encontramos una fila con ese ID_Factura, significa que ya existe el pago
                    if (rs.next()) {
                        return true; // Pago encontrado
                    }
                }
            } catch (SQLException e) {
                // Si ocurre una excepción en la consulta, imprimir error y devolver false
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }

    public static boolean insertarDatosPago(Pago pago){
        Connection con = Conexion.conectar();
        if(con != null){

            String insertar = "INSERT INTO pago (ID_Factura,Monto_Pagado,Fecha_Pago,Metodo_Pago) VALUES (?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setInt(1, pago.getId_factura());
                stmt.setFloat(2, pago.getMontoPagado());
                stmt.setTimestamp(3, Timestamp.valueOf(pago.getFecha_pago()));
                stmt.setString(4, pago.getMetodoPago());

                // Ejecutar la inserción
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;  // Si se insertaron filas correctamente
            } catch (SQLException e) {
                return false;
            }
        }

        return true;
    }

    public static Pago buscaPagoPorIdFactura(int idFactura) throws SQLException {
        Pago pago = null;
        Connection con = Conexion.conectar();
        if (con != null) {
            String buscar = "SELECT * FROM pago where ID_Factura = ?";

            try (PreparedStatement nuevo = con.prepareStatement(buscar)) {
                nuevo.setInt(1, idFactura);

                    //Guardo en resultado lo que me devuelve la base de datos

                    ResultSet resultado = nuevo.executeQuery();
                    while (resultado.next()) {
                        pago = new Pago(
                                resultado.getInt("ID_Factura"),
                                resultado.getInt("Monto_Pagado"),
                                resultado.getTimestamp("Fecha_Pago").toLocalDateTime(),
                                resultado.getString("Metodo_Pago"));


                    }
            } catch (SQLException e) {
                // Si ocurre una excepción en la consulta, imprimir error y devolver false
                e.printStackTrace();
            }

        }
        return pago;
    }
}
