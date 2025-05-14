package ies.torredelrey.jfma.appgestionparking.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarDNI(String dni) {
        // Validamos que el DNI tenga entre 8 y 20 caracteres
        if (dni.length() < 8 || dni.length() > 20) {
            return false;
        }

        // Validamos que el DNI contenga solo caracteres alfanuméricos (letras y números)
        if (!dni.matches("[a-zA-Z0-9]+")) {
            return false;
        }

        return true;
    }

    public static boolean validarNombre(String nombre) {
        // Verifica que el nombre no esté vacío y contenga solo letras y espacios
        return nombre != null && !nombre.isEmpty() && nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
    }

    // Método para validar Teléfono (solo números, mínimo 7 y máximo 15 dígitos)

    public static boolean validarTelefono(String telefono) {

        return telefono != null && telefono.matches("\\d{7,15}");
    }

    // Método para validar Fecha de Nacimiento (no debe ser una fecha futura)

    public static boolean validarFechaNacimiento(LocalDate fecha) {
        if (fecha == null) {
            return false; // La fecha no debe ser nula
        }

        // Comprobamos que la fecha no esté en el futuro
        if (fecha.isAfter(LocalDate.now())) {
            return false;
        }

        // verificar si el usuario tiene una edad mínima (por ejemplo, 18 años)
        LocalDate fechaMinima = LocalDate.now().minusYears(18);
        if (fecha.isAfter(fechaMinima)) {
            return false;
        }

        return true;
    }

    // Método para validar Email con una expresión regular
    public static boolean validarEmail(String email) {
        // Expresión regular básica para validar un email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);

    }

    public static boolean validarContrasena(String contrasena) {
        // Expresión regular para validar la contraseña
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);

        // Comparar la contraseña con la expresión regular
        Matcher matcher = pattern.matcher(contrasena);

        // Retorna true si la contraseña es válida
        return matcher.matches();
    }

    public static boolean validarMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) return false;  // Añadido control de espacios vacíos

        matricula = matricula.trim();


        String matriculaMayus = matricula.toUpperCase();


        matriculaMayus = matriculaMayus.replaceAll("[ÁÁÉÉÍÍÓÓÚÚáéíóú]", "");


        String formatoModerno = "^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$";  // 4 dígitos + 3 letras
        String formatoAntiguo = "^[A-Z]{1,2}[ -]?[0-9]{4}[ -]?[A-Z]{0,2}$"; // 1-2 letras + 4 dígitos + 0-2 letras


        return matriculaMayus.matches(formatoModerno)
                || matriculaMayus.matches(formatoAntiguo);

    }
}
