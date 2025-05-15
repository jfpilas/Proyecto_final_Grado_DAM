package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.ReservaDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Plaza;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class parkingController {

    private Timeline timeline;
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
    private ImageView img1;

    @FXML
    private ImageView img10;

    @FXML
    private ImageView img11;

    @FXML
    private ImageView img12;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img8;

    @FXML
    private ImageView img9;

    @FXML
    private ImageView imgTipo1;

    @FXML
    private ImageView imgTipo10;

    @FXML
    private ImageView imgTipo11;

    @FXML
    private ImageView imgTipo12;

    @FXML
    private ImageView imgTipo2;

    @FXML
    private ImageView imgTipo3;

    @FXML
    private ImageView imgTipo4;

    @FXML
    private ImageView imgTipo5;

    @FXML
    private ImageView imgTipo6;

    @FXML
    private ImageView imgTipo7;

    @FXML
    private ImageView imgTipo8;

    @FXML
    private ImageView imgTipo9;

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
        int numeroPlazas = PlazaDao.contarPlazasOcupadasYReservadas();
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
            assert plazaclicada != null;
            int id_plaza = plazaclicada.getIdPlaza();

            // Verificar si la plaza ya está reservada
             verificar = ReservaDao.verificacionPlazaReservada(id_plaza);

            if (verificar == null) {

                abrirVentanaReserva("Reserva", plaza);
            } else {

                abrirVentanaPlaza("PLAZA OCUPADA",plaza);

            }
        }else{
            System.out.println("Devuelve nulo");
        }
        return verificar;
    }

    private void actualizarEstadoPlazas() {
        String rutaCoche = "/imagenes/coche.jpg";
        String rutaReservada = "/imagenes/reservada.jpg";
        Image coche = new Image(Objects.requireNonNull(getClass().getResource(rutaCoche)).toString());
        Image reservada = new Image(Objects.requireNonNull(getClass().getResource(rutaReservada)).toString());
        ObservableList<Plaza> plazas = PlazaDao.listarTodasLasPlazas();
        System.out.println("Ocupadas: " + PlazaDao.contarPlazasOcupadasYReservadas());
        for (Plaza plaza : plazas) {
            String codigo = plaza.getNumPlaza();
            String estado = plaza.getEstado(); // "Libre", "Ocupada", "Reservada"
            ImageView imagen = consigueImagenPorNumeroPlaza(codigo);
            if (imagen != null) {
                switch (estado) {
                    case "Libre" -> imagen.setImage(null);
                    case "Ocupada" -> imagen.setImage(coche);
                    case "Reservada" -> imagen.setImage(reservada);
                }
            }
        }
    }

    private ImageView consigueImagenPorNumeroPlaza(String numero) {
        return switch (numero) {
            case "P001" -> img1;
            case "P002" -> img2;
            case "P003" -> img3;
            case "P004" -> img4;
            case "P005" -> img5;
            case "P006" -> img6;
            case "P007" -> img7;
            case "P008" -> img8;
            case "P009" -> img9;
            case "P010" -> img10;
            case "P011" -> img11;
            case "P012" -> img12;
            default -> null;
        };
    }

    public void iniciarActualizacionAutomatica() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e ->{
                    actualizarEstadoPlazas();
                    mostrarPlazas();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void detenerActualizacion() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    public void inicializaImagenesTipoPlaza(){
        String rutaTipoVip = "/imagenes/imagenvip.jpg";
        String rutaTipoElectrica = "/imagenes/electrica.png";
        Image vip = new Image(Objects.requireNonNull(getClass().getResource(rutaTipoVip)).toString());
        Image electrica = new Image(Objects.requireNonNull(getClass().getResource(rutaTipoElectrica)).toString());
        ObservableList<Plaza> plazas = PlazaDao.listarTodasLasPlazas();

        for (Plaza plaza : plazas) {
            String codigo = plaza.getNumPlaza();
            String tipo = plaza.getTipo();
            ImageView imagen = consigueImagenPorTipoPlaza(codigo);
            if (imagen != null) {
                switch (tipo) {
                    case "normal" -> imagen.setImage(null);
                    case "VIP" -> imagen.setImage(vip);
                    case "Carga eléctrica" -> imagen.setImage(electrica);
                }
            }
        }
    }

    private ImageView consigueImagenPorTipoPlaza(String numero) {
        return switch (numero) {
            case "P001" -> imgTipo1;
            case "P002" -> imgTipo2;
            case "P003" -> imgTipo3;
            case "P004" -> imgTipo4;
            case "P005" -> imgTipo5;
            case "P006" -> imgTipo6;
            case "P007" -> imgTipo7;
            case "P008" -> imgTipo8;
            case "P009" -> imgTipo9;
            case "P010" -> imgTipo10;
            case "P011" -> imgTipo11;
            case "P012" -> imgTipo12;
            default -> null;
        };
    }

}
