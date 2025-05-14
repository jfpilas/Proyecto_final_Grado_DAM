package ies.torredelrey.jfma.appgestionparking.controlador;

import ies.torredelrey.jfma.appgestionparking.DAO.ClienteDao;
import ies.torredelrey.jfma.appgestionparking.DAO.CocheDao;
import ies.torredelrey.jfma.appgestionparking.modelo.Cliente;
import ies.torredelrey.jfma.appgestionparking.modelo.Coche;
import ies.torredelrey.jfma.appgestionparking.util.FuncionesReutilizables;
import ies.torredelrey.jfma.appgestionparking.util.Validaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cocheController {
    protected int idCocheSeleccionado;

    @FXML
    private Button btnModificar;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtMatricula;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtTipo;


    @FXML
    private TextField txtColorc;

    @FXML
    private TextField txtDnic;

    @FXML
    private TextField txtMarcac;

    @FXML
    private TextField txtMatriculac;

    @FXML
    private TextField txtModeloc;

    @FXML
    private TextField txtTipoc;

    @FXML
    void OnClickGuardar(ActionEvent event) {
        String dni = txtDnic.getText();
        String matricula = txtMatriculac.getText();
        String marca = txtMarcac.getText();
        String color = txtColorc.getText();
        String modelo = txtModeloc.getText();
        String tipo = txtTipoc.getText();


        if(!Validaciones.validarDNI(dni)){
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "El dni no es correcto.");
            return;
        }


        if(!Validaciones.validarMatricula(matricula)){
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "La matrícula ingresada no es correcta.");
            return;
        }

        if(txtMarcac.getText().isEmpty() || txtModeloc.getText().isEmpty() || txtColorc.getText().isEmpty()|| txtMarcac.getText().isBlank() || txtModeloc.getText().isBlank() || txtColorc.getText().isBlank()){
            FuncionesReutilizables.mostrarAlertaInformacion("Error", "No pueden haber campos vacíos, rellénelos porfavor.");
            return;
        }

        if(!tipo.equals("Normal") && !tipo.equals("VIP") && !tipo.equals("Carga eléctrica")){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","El tipo es incorrecto, debe ser uno de estos tres Normal,VIP o Carga eléctrica." );
            return;
        }

        if(ClienteDao.buscarDniEnBBDD(dni) != null){
            Cliente cliente = ClienteDao.buscarDniEnBBDD(dni);
            int dniCliente = cliente.getIdCliente();
            Coche coche = new Coche(dniCliente,matricula,marca,modelo,color,tipo);

            if(CocheDao.guardarCoche(coche)){
                FuncionesReutilizables.mostrarAlertaInformacion("Éxito", " Coche guardado correctamente");
                limpiarCampos();
            }
        }else{
            FuncionesReutilizables.mostrarAlertaInformacion("Error","No hay registrado ningun cliente con ese dni.");
        }


    }

    @FXML
    void OnClickModificar(ActionEvent event) {
        if(!Validaciones.validarMatricula(txtMatricula.getText())){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Matrícula no válida");
            return;
        }
        if(!Validaciones.validarNombre(txtColor.getText())){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Color no válido");
            return;
        }

        if (!(txtTipo.getText().equals("Normal") || txtTipo.getText().equals("VIP") || txtTipo.getText().equals("Carga eléctrica"))) {
            FuncionesReutilizables.mostrarAlertaInformacion("Error","El tipo debe ser 'Normal', 'VIP' o 'Carga eléctrica'");
            return;
        }

        if(txtMatricula.getText().isBlank() ||
                txtMarca.getText().isBlank() ||
                txtModelo.getText().isBlank() ||
                txtColor.getText().isBlank() ||
                txtTipo.getText().isBlank()){
            FuncionesReutilizables.mostrarAlertaInformacion("Error","Alguno de los campos están vacíos. Por favor revíselos.");

        } else{
            Coche seleccionado = new Coche (
                                            txtMatricula.getText(),
                                            txtMarca.getText(),
                                            txtModelo.getText(),
                                            txtColor.getText(),
                                            txtTipo.getText(),
                                            idCocheSeleccionado
                                            );
            if(CocheDao.modificarCoche(seleccionado)){
                FuncionesReutilizables.mostrarAlertaInformacion("Éxito","Has hecho la modificación correctamente");
            }
            Stage stage = (Stage) btnModificar.getScene().getWindow();
            stage.close();
        }
        System.out.println(idCocheSeleccionado);
    }

    public void setCoche(Coche coche) {
        if (coche != null) {
            txtMatricula.setText(coche.getMatricula());
            txtMarca.setText(coche.getMarca());
            txtModelo.setText(coche.getModelo());
            txtColor.setText(coche.getColor());
            txtTipo.setText(coche.getTipo());
        }
        assert coche != null;
        idCocheSeleccionado = coche.getIdCoche();
    }

    public void limpiarCampos(){
        txtMatriculac.setText("");
        txtMarcac.setText("");
        txtModeloc.setText("");
        txtColorc.setText("");
        txtTipoc.setText("");
        txtDnic.setText("");
    }

}

