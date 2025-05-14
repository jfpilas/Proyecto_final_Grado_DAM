package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.*;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static String obtenerNombreClientePorId(int idCliente) {
        String consulta = "SELECT Nombre FROM cliente WHERE ID_Cliente = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(consulta)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Cliente buscarDniEnBBDD(String dni){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            //Hago la consulta
            String consulta = "SELECT ID_Cliente,Nombre, Apellido, Dni, Direccion, Fecha_Nacimiento, Email, Telefono " +
                    "FROM Cliente " +
                    "WHERE Dni = ?";

            try (PreparedStatement cliente = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                cliente.setString(1, dni);



                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = cliente.executeQuery();
                if(resultado.next()){
                    return new Cliente(
                            resultado.getInt("ID_Cliente"),
                            resultado.getString("Direccion"),
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Dni"),
                            resultado.getDate("Fecha_Nacimiento").toLocalDate(),
                            resultado.getString("Email"),
                            resultado.getString("Telefono"));

                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return null;
    }

    public static ObservableList<Cliente> listarClientesPorDni(int idCliente) {

        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        Connection con = Conexion.conectar();
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Cliente,Direccion,Nombre,Apellido,Dni,Fecha_Nacimiento,Email,Telefono FROM cliente WHERE ID_Cliente = ? ";

            try (PreparedStatement cliente = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                cliente.setInt(1, idCliente);


                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = cliente.executeQuery();
                while (resultado.next()) {
                    Cliente nuevoCliente = new Cliente(resultado.getInt("ID_Cliente"),
                            resultado.getString("Direccion"),
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Dni"),
                            resultado.getDate("Fecha_Nacimiento").toLocalDate(),
                            resultado.getString("Email"),
                            resultado.getString("Telefono"));

                    listaClientes.add(nuevoCliente);
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return listaClientes;
    }

    public static Cliente ObtenerClientePorId(int id) {

        Connection con = Conexion.conectar();
        Cliente cliente = null;
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT * FROM cliente where ID_Cliente = ? ";

            try (PreparedStatement factura = con.prepareStatement(consulta)) {
                factura.setInt(1,id);

                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = factura.executeQuery();
                while (resultado.next()) {
                    cliente = new Cliente(
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Direccion"),
                            resultado.getString("Dni"),
                            resultado.getDate("Fecha_Nacimiento").toLocalDate(),
                            resultado.getString("Email"),
                            resultado.getString("Telefono")
                    );

                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return cliente;
    }

    public static ObservableList<Cliente> listarClientes() {

        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        Connection con = Conexion.conectar();
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Cliente,Direccion,Nombre,Apellido,Dni,Fecha_Nacimiento,Email,Telefono FROM cliente ";

            try (PreparedStatement cliente = con.prepareStatement(consulta)) {


                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = cliente.executeQuery();
                while (resultado.next()) {
                    Cliente nuevoCliente = new Cliente(resultado.getInt("ID_Cliente"),
                            resultado.getString("Direccion"),
                            resultado.getString("Nombre"),
                            resultado.getString("Apellido"),
                            resultado.getString("Dni"),
                            resultado.getDate("Fecha_Nacimiento").toLocalDate(),
                            resultado.getString("Email"),
                            resultado.getString("Telefono"));

                    listaClientes.add(nuevoCliente);
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return listaClientes;
    }

    public static boolean buscarEmailYdni(String email,String dni){

        Connection con = Conexion.conectar();
        if (con != null){
            String sql = "SELECT COUNT(*) FROM cliente WHERE dni = ? OR email = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {

                stmt.setString(1, dni);
                stmt.setString(2, email);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // true si ya existe un cliente con ese DNI o email
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // no encontrado o error
    }
}
