package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.ReservaDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Plaza;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class parkingController {

    private final int  NumTotalPlazas = 12;

    @FXML
    private Label numPlazasOcupadas;

    @FXML
    private ImageView imagenColorParking;

    @FXML
    private Label plaza1;

    @FXML
    private Label plaza10;

    @FXML
    private Label plaza11;

    @FXML
    private Label plaza12;

    @FXML
    private Label plaza2;

    @FXML
    private Label plaza3;

    @FXML
    private Label plaza4;

    @FXML
    private Label plaza5;

    @FXML
    private Label plaza6;

    @FXML
    private Label plaza7;

    @FXML
    private Label plaza8;

    @FXML
    private Label plaza9;

    @FXML
    void OnClickPlaza1(ActionEvent event) throws IOException {
        seleccionVentanaSegunReserva(plaza1);
    }


    @FXML
    void OnClickPlaza2(ActionEvent event) throws IOException {
        seleccionVentanaSegunReserva(plaza2);
    }

    @FXML
    void OnClickPlaza3(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza3);
    }

    @FXML
    void OnClickPlaza4(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza4);
    }

    @FXML
    void OnClickPlaza5(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza5);
    }

    @FXML
    void OnClickPlaza6(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza6);
    }

    @FXML
    void OnClickPlaza7(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza7);
    }

    @FXML
    void OnClickPlaza8(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza8);
    }

    @FXML
    void OnClickPlaza9(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza9);
    }

    @FXML
    void OnClickPlaza10(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza10);
    }

    @FXML
    void OnClickPlaza11(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza11);
    }

    @FXML
    void OnClickPlaza12(ActionEvent event) throws IOException {
        abrirVentanaReserva("Reserva",plaza12);
    }


    public void mostrarPlazas(){
        int numeroPlazas = PlazaDao.contarPlazasOcupadas();
        if (numPlazasOcupadas == null) {
            System.out.println("numPlazasOcupadas es null");
        } else {
            numPlazasOcupadas.setText(numeroPlazas + " de "+NumTotalPlazas);
        }

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

    private void abrirVentanaPlaza(String titulo,Label plaza) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.PLAZA));
        AnchorPane root = loader.load();
        plazaController controller = loader.getController();

        if(PlazaDao.devuelvePlaza(plaza.getText()) != null){
            Plaza resultado = PlazaDao.devuelvePlaza(plaza.getText());

            if(ReservaDao.verificacionPlazaReservada(resultado.getIdPlaza()) != null){
                Reserva datos = ReservaDao.verificacionPlazaReservada(resultado.getIdPlaza());
                //CARGO LOS DATOS EN MI VENTANA PLAZA CON EL CLIENTE Y COCHE QUE LA OCUPA
                String nombre = ClienteDao.obtenerNombreClientePorId(datos.getIdCliente());
                String matricula = CocheDao.obtenerMatriculaCochePorId(datos.getIdCoche());
                System.out.println(nombre);
                System.out.println(matricula);
                controller.setNombreCliente(nombre);
                controller.setMatriculaCoche(matricula);
                controller.setFechaE(datos.getFechaEntrada());
                controller.setFechaS(datos.getFechaSalida());
            }else{
                System.out.println("El resultado de devolver Reserva es nulo");
            }

        }else{
            System.out.println("El resultado de devolver plaza es nulo");
        }

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();

    }

    private void abrirVentanaReserva(String titulo,Label plaza) throws IOException {
        FXMLLoader loader = new FXMLLoader(GestorParking.class.getResource(Rutas.RESERVA));
        AnchorPane root = loader.load();

        //Para poder cargar el numero de plaza en mi pestaña reserva instancio a reservaController
        reservaController controller = loader.getController();

        String numeroPlaza = plaza.getText();
        LocalDate actual = LocalDate.now();
        Plaza nuevaPlaza = PlazaDao.devuelvePlaza(numeroPlaza);
        int idPlaza = nuevaPlaza.getIdPlaza();
        controller.setNumeroPlaza(numeroPlaza);
        controller.setIdPlaza(idPlaza);
        controller.setFechaEntrada(actual);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();

    }

    private Reserva seleccionVentanaSegunReserva(Label plaza) throws IOException {
        Reserva verificar = null;
        if(PlazaDao.devuelvePlaza(plaza.getText()) != null){
            Plaza plazaclicada = PlazaDao.devuelvePlaza(plaza.getText());
            int id_plaza = plazaclicada.getIdPlaza();

            // Verificar si la plaza ya está reservada
             verificar = ReservaDao.verificacionPlazaReservada(id_plaza);

            // Si verificar es null, significa que la plaza no está reservada
            if (verificar == null) {
                // Si la plaza no está reservada, abre la ventana de reserva
                abrirVentanaReserva("Reserva", plaza);
            } else {
                // Si la plaza está reservada, muestra una ventana indicando que la plaza está ocupada
                abrirVentanaPlaza("PLAZA OCUPADA",plaza);

            }
        }else{
            System.out.println("Devuelve nulo");
        }
        return verificar;
    }




}
