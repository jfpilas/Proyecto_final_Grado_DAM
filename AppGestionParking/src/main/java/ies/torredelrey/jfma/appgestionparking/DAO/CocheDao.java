package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Coche;
import ies.torredelrey.jfma.appgestionparking.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CocheDao {
    public static ObservableList<Coche> listarCochesCliente(int idCliente) {

        ObservableList<Coche> listaCoches = FXCollections.observableArrayList();
        Connection con = Conexion.conectar();
        if (Conexion.conectar() != null) {

            //Hago la consulta
            String consulta = "SELECT ID_Coche,Matricula,Marca,Modelo,Color,Tipo FROM coche WHERE ID_Cliente = ? ";

            try (PreparedStatement coche = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                coche.setInt(1, idCliente);


                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = coche.executeQuery();
                while (resultado.next()) {
                    Coche nuevoCoche = new Coche(resultado.getInt("ID_Coche"),
                            idCliente,
                            resultado.getString("Matricula"),
                            resultado.getString("Marca"),
                            resultado.getString("Modelo"),
                            resultado.getString("Color"),
                            resultado.getString("Tipo"));

                    listaCoches.add(nuevoCoche);
                }
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }
        }

        return listaCoches;
    }

    public static String obtenerMatriculaCochePorId(int idCoche) {
        String consulta = "SELECT Matricula FROM coche WHERE ID_Coche = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(consulta)) {
            stmt.setInt(1, idCoche);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Matricula");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean verificarMatricula(String matricula){
        String consulta = "SELECT COUNT(*) FROM coche WHERE Matricula = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(consulta)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int numero = rs.getInt(1); // obtiene el valor de COUNT(*)
                return numero > 0; // true si existe al menos un coche con esa matrícula
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static boolean modificarCoche(Coche coche){
        String query = "UPDATE coche SET matricula = ?, marca = ?, modelo = ?, color = ?, tipo = ? WHERE ID_Coche = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, coche.getMatricula());
                stmt.setString(2, coche.getMarca());
                stmt.setString(3, coche.getModelo());
                stmt.setString(4, coche.getColor());
                stmt.setString(5, coche.getTipo());
                stmt.setInt(6, coche.getIdCoche());
                stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean eliminarCoche(Coche coche){
        String query = "DELETE FROM coche WHERE ID_Coche = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, coche.getIdCoche());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean guardarCoche(Coche coche){

        Connection con = Conexion.conectar();
        if(Conexion.conectar() != null){

            String insertar = "INSERT INTO coche (ID_Cliente,Matricula,Marca,Modelo,Color,Tipo) VALUES (?,?,?,?,?,?)";

            try (PreparedStatement stmt = con.prepareStatement(insertar)) {

                // Establecer los parámetros de la consulta
                stmt.setInt(1, coche.getIdCliente());
                stmt.setString(2, coche.getMatricula());
                stmt.setString(3, coche.getMarca());
                stmt.setString(4, coche.getModelo());
                stmt.setString(5, coche.getColor());
                stmt.setString(6, coche.getTipo());

                // Ejecutar la inserción
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;  // Si se insertaron filas correctamente
            } catch (SQLException e) {
                System.out.println("Error al guardar el coche: " + e.getMessage());
                return false;
            }
        }

        return true;

    }
}
