package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.*;
import ies.torredelrey.jfma.appgestionparking.Gmail.CorreoElectronico;
import ies.torredelrey.jfma.appgestionparking.PDF.FacturaPDF;
import ies.torredelrey.jfma.appgestionparking.modelo.*;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Rutas;
import ies.torredelrey.jfma.appgestionparking.util.TamanoImagenes;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class facturaController {

    ObservableList<String> listaModosPago = FXCollections.observableArrayList("Tarjeta","Efectivo","Transferencia");
    ObservableList<ReservaCliente> clientesReserva= FXCollections.observableArrayList();
    ObservableList<ReservaCliente> clienteEncontrado= FXCollections.observableArrayList();
    private int idclienteSeleccionado ;
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnRefrescar;

    @FXML
    private TextField txtEstado;

    @FXML
    private ComboBox<String> cbxPago;

    @FXML
    private TableView<ReservaCliente> tClientes;

    @FXML
    private TableColumn<ReservaCliente, String> colApellidos;

    @FXML
    private TableColumn<ReservaCliente, String> colEmail;

    @FXML
    private TableColumn<ReservaCliente, String> colNombre;

    @FXML
    private TableColumn<ReservaCliente,String> colIdCoche;

    @FXML
    private TableColumn<ReservaCliente,Integer> colIdReserva;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ImageView imgGuardar;

    @FXML
    private ImageView imgBuscar;

    @FXML
    private ImageView imgRefrescar;


    @FXML
    private TextField txtCliente;

    @FXML
    private TextField txtFechaEmision;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtNumReserva;

    @FXML
    private void listarModosDePago(){
        GestorParking.llenarCombo(cbxPago,listaModosPago);
    }


    @FXML
    void onClickBuscar(ActionEvent event) {
        String dni = txtBuscar.getText();

        if(dni.isEmpty()){
            FuncionesReutilizables.mostrarAlertaInformacion("Información","Rellena el campo dni, no puedes dejarlo vacío");
            return;
        }

        Cliente clienteBuscado = ClienteDao.buscarDniEnBBDD(dni);
        if(clienteBuscado!=null){

            int id = clienteBuscado.getIdCliente();
            ObservableList<Reserva> reservas = ReservaDao.listarReservas();
            ObservableList<Cliente> clientes = ClienteDao.listarClientes();
            ObservableList<Coche> coches = CocheDao.listarCoches();

            Map<Integer, Cliente> clienteMap = new HashMap<>();
            for (Cliente c : clientes) {
                clienteMap.put(c.getIdCliente(), c);
            }

            Map<Integer, Coche> cocheMap = new HashMap<>();
            for (Coche co : coches) {
                cocheMap.put(co.getIdCoche(), co);
            }
            clienteEncontrado.clear();
            for (Reserva r : reservas) {
                if (r.getIdCliente() == id) {
                    Cliente cliente = clienteMap.get(r.getIdCliente());
                    Coche coche = cocheMap.get(r.getIdCoche());

                    if (cliente != null && coche != null) {
                        clienteEncontrado.add(new ReservaCliente(r, cliente, coche));
                    }
                }
            }

            if(!clienteEncontrado.isEmpty()){
                this.colNombre.setCellValueFactory(new PropertyValueFactory<>("NombreCliente"));
                this.colApellidos.setCellValueFactory(new PropertyValueFactory<>("ApellidosCliente"));
                this.colEmail.setCellValueFactory(new PropertyValueFactory<>("EmailCliente"));
                this.colIdReserva.setCellValueFactory(new PropertyValueFactory<>("IdReserva") );
                this.colIdCoche.setCellValueFactory(new PropertyValueFactory<>("MatriculaCoche") );
                this.tClientes.setItems(clienteEncontrado);
                txtBuscar.clear();

            }else{
                FuncionesReutilizables.mostrarAlertaInformacion("Error","No tiene ninguna reserva el cliente con el siguiente dni "+dni);
                tClientes.getItems().clear(); //limpio los valores de la tabla
            }
        }
    }

    @FXML
    void onClickResfrescar(ActionEvent event) {
        clientesReserva.clear();
        ObservableList<Reserva> reservas = ReservaDao.listarReservas();
        ObservableList<Cliente> clientes = ClienteDao.listarClientes();
        ObservableList<Coche> coches = CocheDao.listarCoches();

        Map<Integer, Cliente> clienteMap = new HashMap<>();
        for (Cliente c : clientes) {
            clienteMap.put(c.getIdCliente(), c);
        }

        Map<Integer, Coche> cocheMap = new HashMap<>();
        for (Coche co : coches) {
            cocheMap.put(co.getIdCoche(), co);
        }

        for (Reserva r : reservas) {
            Cliente cliente = clienteMap.get(r.getIdCliente());
            Coche coche = cocheMap.get(r.getIdCoche());

            if (cliente != null && coche != null) {
                clientesReserva.add(new ReservaCliente(r, cliente, coche));
            }
        }
        if(!clientesReserva.isEmpty()){
            this.colNombre.setCellValueFactory(new PropertyValueFactory<>("NombreCliente"));
            this.colApellidos.setCellValueFactory(new PropertyValueFactory<>("ApellidosCliente"));
            this.colEmail.setCellValueFactory(new PropertyValueFactory<>("EmailCliente"));
            this.colIdReserva.setCellValueFactory(new PropertyValueFactory<>("IdReserva") );
            this.colIdCoche.setCellValueFactory(new PropertyValueFactory<>("MatriculaCoche") );
            this.tClientes.setItems(clientesReserva);


        }else{
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No hay Reservas para hacer factura");
            tClientes.getItems().clear(); //limpio los valores de la tabla
        }

    }

    @FXML
    void OnClickGuardar(ActionEvent event) {
        //TERMINAR ESTO , DEBE ESTAR FUERA DEL METODO GUARDAR PARA QUE CUANDO CLIQUE ME MUESTRE LOS DATOS
        int idReserva = Integer.parseInt(txtNumReserva.getText());
        String estado = "Pendiente";
        Factura nuevaFactura = new Factura(idclienteSeleccionado,idReserva,Float.parseFloat(txtTotal.getText()),LocalDateTime.parse(txtFechaEmision.getText()),estado);

        System.out.println(nuevaFactura);
        if(FacturaDao.guardarFactura(nuevaFactura)){
            FuncionesReutilizables.mostrarAlertaInformacion("Éxito", "Factura creada y guardada correctamente");

        }else{
            Reserva reserva = ReservaDao.ObtenerReservaPorId(nuevaFactura.getidReserva());

            if(reserva != null){
                FuncionesReutilizables.mostrarAlertaInformacion("Error", "Factura ya registrada para el pago.");
            }else{
                FuncionesReutilizables.mostrarAlertaInformacion("Error", "Factura no se ha  guardado correctamente");
            }

        }

    }

    protected void configurarSeleccionTabla() {
        tClientes.getSelectionModel().selectedItemProperty().addListener((observable, viejo, nuevo) -> {
            if (nuevo != null) {
                mostrarFacturaSeleccionada(nuevo);
            }
        });
    }

    private void mostrarFacturaSeleccionada(ReservaCliente reservacliente) {
        Cliente cliente = reservacliente.cliente;
        Reserva reserva = reservacliente.reserva;

        // Llenas los campos con los datos de la reserva
        txtNumReserva.setText(String.valueOf(reserva.getIdReserva()));
        txtEstado.setText(reserva.getEstado());

        Plaza plaza = PlazaDao.ObtenerPlazaPorId(reserva.getIdPlaza());
        float tarifaPlaza = plaza.getTarifa();
        LocalDateTime entrada = reserva.getFechaEntrada();
        LocalDateTime salida = reserva.getFechaSalida();
        long dias = ChronoUnit.DAYS.between(entrada, salida);
        float total = tarifaPlaza * dias;
        txtTotal.setText(String.valueOf(total));

        txtCliente.setText(cliente.getNombre() + " " + cliente.getApellido());
        txtFechaEmision.setText(String.valueOf(LocalDateTime.now()));
        idclienteSeleccionado = cliente.getIdCliente();

    }


    public void setImagenGuardar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgGuardar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenBuscar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgBuscar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenRefrescar(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgRefrescar, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }


}
