package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.FacturaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.PlazaDao;
import ies.torredelrey.jfma.appgestionparking.DAO.ReservaDao;
import ies.torredelrey.jfma.appgestionparking.Gmail.CorreoElectronico;
import ies.torredelrey.jfma.appgestionparking.modelo.*;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class facturaController {

    ObservableList<String> listaModosPago = FXCollections.observableArrayList("Tarjeta","Efectivo","Transferencia");
    ObservableList<Cliente> clientesReserva= FXCollections.observableArrayList();
    ObservableList<Cliente> clienteEncontrado= FXCollections.observableArrayList();
    private int idclienteSeleccionado ;
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnCorreo;

    @FXML
    private Button btnImprimir;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnRefrescar;

    @FXML
    private TextField txtEstado;

    @FXML
    private ComboBox<String> cbxPago;

    @FXML
    private TableView<Cliente> tClientes;
    @FXML
    private TableColumn<Cliente, String> colApellidos;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TextField txtBuscar;

    @FXML
    private ImageView imgCorreo;

    @FXML
    private ImageView imgImprimir;

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
    void OnClickEmail(ActionEvent event) {

        Cliente clienteSeleccionado = tClientes.getSelectionModel().getSelectedItem();
        if(clienteSeleccionado == null){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Debes seleccionar un cliente.");
            return;
        }
        System.out.println(clienteSeleccionado);
        String emailClienteSelecionado = clienteSeleccionado.getEmail();
        String asunto = "Recibo de Factura (Hotel Alcázar)";
        String descripcion = "TENGO QUE ENVIAR UNA FACTURA";

        if(CorreoElectronico.enviarConGMail(emailClienteSelecionado,asunto,descripcion)){
            FuncionesReutilizables.mostrarAlertaInformacion("Éxito","¡Correo enviado!");
        }else{
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No se ha podido enviar el correo.");
        }
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
            ObservableList<Cliente> clientes = ClienteDao.listarClientesPorDni(id);
            for(Reserva r : reservas){
                if(id == r.getIdCliente()){
                    clienteEncontrado.add(new Cliente(r.getIdCliente(),
                            clienteBuscado.getDireccion(),
                            clienteBuscado.getNombre(),
                            clienteBuscado.getApellido(),
                            clienteBuscado.getDni(),
                            clienteBuscado.getFecha(),
                            clienteBuscado.getEmail(),
                            clienteBuscado.getTelefono()));
                }
            }

            if(!clienteEncontrado.isEmpty()){
                this.colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                this.colApellidos.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
                this.colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
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
        for (Reserva r : reservas){
            for(Cliente c : clientes){
                if(r.getIdCliente() == c.getIdCliente()){
                    clientesReserva.add(new Cliente(
                            r.getIdCliente(),
                            c.getDireccion(),
                            c.getNombre(),
                            c.getApellido(),
                            c.getDni(),
                            c.getFecha(),
                            c.getEmail(),
                            c.getTelefono()));
                }
            }

        }

        if(!clientesReserva.isEmpty()){
            this.colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            this.colApellidos.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
            this.colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
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

    private void mostrarFacturaSeleccionada(Cliente cliente) {
        idclienteSeleccionado = cliente.getIdCliente();
        //PREGUNTAR AL PROFE, NOSE COMO GESTIONAR ESTO EN CASO DE QUE HAYA MAS DE UNA PLAZA OCUPADA POR EL MISMO CLIENTE
        LocalDateTime fecha = LocalDateTime.now();
        ObservableList<Reserva> listaReserva = ReservaDao.obtenerReservasPorIdCliente(cliente.getIdCliente());
        if(listaReserva.isEmpty()){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No existen reservas del cliente seleccionado.");
            return;
        }
        System.out.println(listaReserva);

        for (Reserva r : listaReserva){
            txtNumReserva.setText(String.valueOf(r.getIdReserva()));
            txtEstado.setText(r.getEstado());
            Plaza plaza = PlazaDao.ObtenerPlazaPorId(r.getIdPlaza());
            float tarifaPlaza = plaza.getTarifa();
            LocalDateTime entrada = r.getFechaEntrada();
            LocalDateTime salida = r.getFechaSalida();
            long dias = ChronoUnit.DAYS.between(entrada, salida);
            float total = tarifaPlaza* dias;
            txtTotal.setText(String.valueOf(total));
        }


        int indice = tClientes.getItems().indexOf(cliente);
        System.out.println(indice);
        txtCliente.setText(cliente.getNombre()+ " "+ cliente.getApellido());
        txtFechaEmision.setText(String.valueOf(fecha));


    }


    @FXML
    void OnClickImprimir(ActionEvent event) {

    }


    public void setImagenCorreo(String ruta){
       FuncionesReutilizables.ajustarImagenes(ruta,imgCorreo, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
    }

    public void setImagenImprimir(String ruta){
        FuncionesReutilizables.ajustarImagenes(ruta,imgImprimir, TamanoImagenes.ANCHURAIMAGENBOTONES,TamanoImagenes.ALTURAIMAGENBOTONES);
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
