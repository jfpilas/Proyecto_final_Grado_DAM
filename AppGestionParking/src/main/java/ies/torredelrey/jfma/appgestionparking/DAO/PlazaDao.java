package ies.torredelrey.jfma.appgestionparking.DAO;

import ies.torredelrey.jfma.appgestionparking.conexionBBDD.Conexion;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;

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
}
