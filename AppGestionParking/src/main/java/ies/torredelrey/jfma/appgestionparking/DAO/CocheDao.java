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
            String consulta = "SELECT Matricula,Marca,Modelo,Color,Tipo FROM coche WHERE ID_Cliente = ? ";

            try (PreparedStatement coche = con.prepareStatement(consulta)) {

                // paso los parametros a la consulta que hize arriba
                coche.setInt(1, idCliente);


                //Guardo en resultado lo que me devuelve la base de datos

                ResultSet resultado = coche.executeQuery();
                while (resultado.next()) {
                    Coche nuevoCoche = new Coche(idCliente,
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
}
