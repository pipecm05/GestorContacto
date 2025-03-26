package co.edu.uniquindio.gestorcontacto.Controladores;

import co.edu.uniquindio.gestorcontacto.modelo.Contacto;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import co.edu.uniquindio.gestorcontacto.modelo.GestionContacto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import java.net.URL;
import java.util.ResourceBundle;


public class InicioControlador implements Initializable  {
    @FXML
    private TextField txtTitulo;

    @FXML
    private ComboBox<String> txtCategoria;

    @FXML
    private TextArea txtNota;

    private final GestionContacto gestionContacto;

    @FXML
    private TableView<Contacto> tablaNotas;

    @FXML
    private TableColumn<Contacto, String> colTitulo;

    @FXML
    private TableColumn<Contacto, String> colCategoria;

    @FXML
    private TableColumn<Contacto, String> colTexto;

    @FXML
    private TableColumn<Contacto, String> colFecha;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoria()));
        colTexto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaCreacion().toString()));

        txtCategoria.setItems( FXCollections.observableArrayList(gestionContacto.listarCategorias()) );

    }


    public InicioControlador() {
        gestionContacto = new GestionContacto();
    }

    public void crearNota(ActionEvent e){
        try {
            gestionContacto.agregarNota(txtTitulo.getText(), txtNota.getText(), txtCategoria.getValue());


            mostrarAlerta("Nota creada correctamente", Alert.AlertType.INFORMATION);


            txtTitulo.clear();
            txtNota.clear();
            txtCategoria.setValue(null);

        }catch (Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }

        tablaNotas.setItems( FXCollections.observableArrayList(gestionContacto.listarNotas()) );


    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){


        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }




}
