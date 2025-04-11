package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class parkingController {

    private final int  NumTotalPlazas = 12;

    @FXML
    private Label numPlazasOcupadas;

    @FXML
    private ImageView imagenColorParking;

    @FXML
    public void initialize() {
        mostrarPlazas();  // Llamar al método para mostrar las plazas ocupadas al inicio

    }

    private void mostrarPlazas(){
        int numeroPlazas = PlazaDao.contarPlazasOcupadas();
        numPlazasOcupadas.setText(numeroPlazas + " de "+NumTotalPlazas);
        if(numeroPlazas==12){
            String ruta = "/imagenes/fondoRojo.png";
            Image fondoRojo = new Image(Objects.requireNonNull(getClass().getResource(ruta)).toString());

            imagenColorParking.setImage(fondoRojo);
        }else{

            String ruta = "/imagenes/fondoVerde.png";
            Image fondoVerde = new Image(Objects.requireNonNull(getClass().getResource(ruta)).toString());

            imagenColorParking.setImage(fondoVerde);
        }
    }

}
