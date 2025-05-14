package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.*;
import ies.torredelrey.jfma.appgestionparking.modelo.Factura;
import ies.torredelrey.jfma.appgestionparking.modelo.FacturaPago;
import ies.torredelrey.jfma.appgestionparking.modelo.Pago;
import ies.torredelrey.jfma.appgestionparking.modelo.Reserva;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.vista.GestorParking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class pagosController {

    ObservableList<String> listaModosEstado = FXCollections.observableArrayList("Pagada","Pendiente");
    ObservableList<String> listaMetodosPago = FXCollections.observableArrayList("Tarjeta","Efectivo","Transferencia");
    ObservableList<FacturaPago> listaFacturaPago = null;

    private int posicionFacturaPagoTabla;
    int idreserva= -1;
    int idPlaza= -1;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnPagar;

    @FXML
    private ComboBox<String> cbxMetodoPago;

    @FXML
    private TextField txtFecha;

    @FXML
    private TextField txtIdFactura;

    @FXML
    private TextField txtTotal;

    @FXML
    private ComboBox<String> cbxEstado;

    @FXML
    private Pane panelPagarFactura;

    @FXML
    private TableColumn<FacturaPago, Integer> colIdFactura;

    @FXML
    private TableColumn<FacturaPago, String> colCliente;

    @FXML
    private TableColumn<FacturaPago, String> colEstado;

    @FXML
    private TableColumn<FacturaPago, Date> colFecha;

    @FXML
    private TableColumn<FacturaPago, String> colReserva;

    @FXML
    private TableColumn<FacturaPago, Float> colTotal;

    @FXML
    private TableView<FacturaPago> tFacturas;


    @FXML
    void listarModosEstado() {
        GestorParking.llenarCombo(cbxEstado,listaModosEstado);
    }

    @FXML
    void listaMetodosPago() {
        GestorParking.llenarCombo(cbxMetodoPago,listaMetodosPago);
    }


    @FXML
    void OnClickPagar(ActionEvent event) throws SQLException {

        final String estado = "Pagada";


        if (txtIdFactura == null || txtIdFactura.getText() == null || txtIdFactura.getText().trim().isEmpty() ||
                txtTotal == null || txtTotal.getText() == null || txtTotal.getText().trim().isEmpty() ||
                txtFecha == null || txtFecha.getText() == null || txtFecha.getText().trim().isEmpty()) {
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "Debes pinchar en una de las facturas para rellenar los campos.");
            return;
        }

        if (cbxMetodoPago == null || cbxMetodoPago.getValue() == null || cbxMetodoPago.getValue().trim().isEmpty()) {
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "Debes rellenar el campo Método de pago por favor");
            return;
        }

        // Obtener los datos del pago
        assert txtIdFactura != null;
        int idfactura = parseInt(txtIdFactura.getText());
        float total = parseFloat(txtTotal.getText());
        String fecha = txtFecha.getText();
        LocalDateTime fechaFormateada = LocalDateTime.parse(fecha);
        String metodoPago = cbxMetodoPago.getValue();

        // Crear el objeto Pago
        Pago datosPago = new Pago(idfactura, total, fechaFormateada, metodoPago);

        boolean verificando = PagoDao.buscaPagoConMismoIdFactura(idfactura);
        System.out.println(verificando);
        if(PagoDao.buscaPagoConMismoIdFactura(idfactura)){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No puedes volver a pagar una factura ya pagada.");
            return;
        }

        boolean verificarDatos = PagoDao.insertarDatosPago(datosPago);
        if (verificarDatos) {
            FuncionesReutilizables.mostrarAlertaInformacion("Éxito", "Se ha guardado el pago correctamente.");
        } else {
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "No se ha podido guardar el pago.");
            return;
        }

        // Consultar las facturas pendientes (las que no han sido pagadas)
        ObservableList<Factura> listaFacturas = FacturaDao.listarFacturas(cbxEstado.getValue());


        // Verificar que haya facturas pendientes
        listaFacturaPago = FXCollections.observableArrayList();
        if (!listaFacturas.isEmpty()) {
            for (Factura f : listaFacturas) {
                int id = f.getidCliente();
                String nombre = ClienteDao.obtenerNombreClientePorId(id);
                idreserva = f.getidReserva();
                System.out.println("ID RESERVA: " + idreserva);
                Reserva reserva = ReservaDao.ObtenerReservaPorId(idreserva);

                if (reserva != null) {
                    idPlaza = reserva.getIdPlaza();
                    System.out.println("ID PLAZA: " + idPlaza);
                }
                float totalf = f.getTotal();
                LocalDateTime fechaf = f.getFecha_emision();
                String estadof = f.getEstado();
                int idfacturaf = f.getIdfactura();
                listaFacturaPago.add(new FacturaPago(nombre, idPlaza, totalf, fechaf, estadof, idfacturaf));

                // Actualizar el estado de la plaza y reserva
                PlazaDao.cambiarEstadoPlaza("Libre", idPlaza);
                ReservaDao.cambiarEstadoReserva("Finalizada", idreserva);

            }
        }

        // Cambiar el estado de la factura a "Pagada" después de guardar el pago
        FacturaDao.cambiaEstado(estado, idfactura);

        // Actualizar la vista con las facturas pendientes
        tFacturas.setItems(listaFacturaPago);
        limpiarCampos();

    }


    @FXML
    void onClickBuscar(ActionEvent event) {

        String comboEstados = cbxEstado.getValue();
        if(comboEstados == null){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Elige como quieres buscar las facturas con el seleccionador, gracias.");
            return;
        }

        ObservableList<Factura> listaFacturas = FacturaDao.listarFacturas(comboEstados);
        listaFacturaPago=FXCollections.observableArrayList();

        for(Factura f : listaFacturas){
            int idCliente = f.getidCliente();
            String nombre = ClienteDao.obtenerNombreClientePorId(idCliente);
            int idreserva = f.getidReserva();
            Reserva reserva = ReservaDao.ObtenerReservaPorId(idreserva);
            int idplaza= -1;
            if(reserva != null){
                idplaza = reserva.getIdPlaza();
            }

            float total = f.getTotal();
            LocalDateTime fecha= f.getFecha_emision();
            String estado = f.getEstado();
            int idfactura= f.getIdfactura();
            listaFacturaPago.add(new FacturaPago(nombre,idplaza,total,fecha,estado,idfactura));

        }

        if(!listaFacturaPago.isEmpty()){
            this.colIdFactura.setCellValueFactory(new PropertyValueFactory<>("idfactura"));
            this.colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
            this.colReserva.setCellValueFactory(new PropertyValueFactory<>("reserva"));
            this.colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            this.colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            this.colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            this.tFacturas.setItems(listaFacturaPago);

            panelPagarFactura.setVisible(!Objects.equals(cbxEstado.getValue(), "Pagada"));

        }else{
            panelPagarFactura.setVisible(false);
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No existen Facturas con ese estado " + cbxEstado.getValue());
            tFacturas.getItems().clear(); //limpio los valores de la tabla
        }


    }

    protected void configurarSeleccionTabla() {
        tFacturas.getSelectionModel().selectedItemProperty().addListener((observable, viejo, nuevo) -> {
            if (nuevo != null) {
                mostrarFacturaSeleccionada(nuevo);
            }
        });
    }

    private void mostrarFacturaSeleccionada(FacturaPago facturaPago) {
        posicionFacturaPagoTabla = listaFacturaPago.indexOf(facturaPago);
        txtIdFactura.setText(String.valueOf(facturaPago.getIdfactura()));
        txtFecha.setText(String.valueOf(facturaPago.getFecha()));
        txtTotal.setText(String.valueOf(facturaPago.getTotal()));

    }

    protected TableColumn<FacturaPago,Integer> getColumnaFactura(){
        return colIdFactura;
    }

    protected Pane devuelvePanelPago(){
        return panelPagarFactura;
    }

    protected void setTxtIdFactura(){
        txtIdFactura.setEditable(false);
    }

    protected void setTxtFecha(){
        txtFecha.setEditable(false);
    }

    protected void setTxtTotal(){
        txtTotal.setEditable(false);
    }

    public void limpiarCampos(){
        txtIdFactura.setText("");
        txtFecha.setText("");
        txtTotal.setText("");
        cbxEstado.setValue("");
    }
}
