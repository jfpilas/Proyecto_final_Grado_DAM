package ies.torredelrey.jfma.appgestionparking.conexionBBDD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {
    // Datos de conexión

    private static final String URL = "jdbc:mysql://localhost:3306/gestionparking";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "root";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);

            return conn;
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        conectar(); // Probar la conexión
    }
}
