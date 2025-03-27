package co.edu.uniquindio.gestorcontacto.Controladores;

import co.edu.uniquindio.gestorcontacto.modelo.Contacto;
import co.edu.uniquindio.gestorcontacto.modelo.GestionContacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContactosControlador implements Initializable {

    // Componentes de la tabla
    @FXML private TableView<Contacto> tablaContactos;
    @FXML private TableColumn<Contacto, String> colNombre;
    @FXML private TableColumn<Contacto, String> colApellido;
    @FXML private TableColumn<Contacto, String> colTelefono;
    @FXML private TableColumn<Contacto, String> colCorreo;
    @FXML private TableColumn<Contacto, LocalDate> colCumpleanos;

    // Componentes de búsqueda
    @FXML private ComboBox<String> cbFiltro;
    @FXML private TextField txtBusqueda;

    // Componentes del formulario
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private DatePicker dpCumpleanos;
    @FXML private ImageView imgFoto;

    private final GestionContacto gestionContacto = new GestionContacto();
    private Contacto contactoSeleccionado;
    private final ObservableList<Contacto> listaCompletaContactos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColumnasTabla();
        configurarBusqueda();
        actualizarTabla();
    }

    // 1. Configuración inicial
    private void configurarColumnasTabla() {
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colApellido.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
        colCorreo.setCellValueFactory(cellData -> cellData.getValue().correoProperty());
        colCumpleanos.setCellValueFactory(cellData -> cellData.getValue().cumpleanosProperty());
    }

    private void configurarBusqueda() {
        cbFiltro.setItems(FXCollections.observableArrayList(
                "Todos",
                "Nombre",
                "Teléfono",
                "Correo"
        ));
        cbFiltro.getSelectionModel().selectFirst();

        txtBusqueda.setOnAction(event -> buscarContacto(null));

        txtBusqueda.textProperty().addListener((observable, oldValue, newValue) -> {
            aplicarFiltro();
        });

        cbFiltro.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            aplicarFiltro();
        });
    }

    // 2. Métodos de búsqueda
    @FXML
    private void buscarContacto(ActionEvent event) {
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        String criterio = cbFiltro.getValue();
        String textoBusqueda = txtBusqueda.getText().trim().toLowerCase();

        if (textoBusqueda.isEmpty() || criterio == null || criterio.equals("Todos")) {
            tablaContactos.setItems(listaCompletaContactos);
            return;
        }

        FilteredList<Contacto> datosFiltrados = new FilteredList<>(listaCompletaContactos);

        datosFiltrados.setPredicate(contacto -> {
            if (contacto == null) return false;

            switch (criterio) {
                case "Nombre":
                    return (contacto.getNombre() != null && contacto.getNombre().toLowerCase().contains(textoBusqueda)) ||
                            (contacto.getApellido() != null && contacto.getApellido().toLowerCase().contains(textoBusqueda));
                case "Teléfono":
                    return contacto.getTelefono() != null && contacto.getTelefono().contains(textoBusqueda);
                case "Correo":
                    return contacto.getCorreo() != null && contacto.getCorreo().toLowerCase().contains(textoBusqueda);
                default:
                    return true;
            }
        });

        tablaContactos.setItems(datosFiltrados);
    }

    // 3. Métodos CRUD
    @FXML
    private void guardarContacto(ActionEvent event) {
        try {
            if (!validarCampos()) return;

            Contacto contacto = new Contacto();
            contacto.setNombre(txtNombre.getText());
            contacto.setApellido(txtApellido.getText());
            contacto.setTelefono(txtTelefono.getText());
            contacto.setCorreo(txtCorreo.getText());
            contacto.setCumpleanos(dpCumpleanos.getValue());

            if (imgFoto.getImage() != null) {
                contacto.setFoto(imgFoto.getImage().getUrl());
            }

            if (contactoSeleccionado != null) {
                gestionContacto.actualizarContacto(contactoSeleccionado, contacto);
                mostrarAlerta("Éxito", "Contacto actualizado", Alert.AlertType.INFORMATION);
            } else {
                gestionContacto.agregarContacto(contacto);
                mostrarAlerta("Éxito", "Contacto agregado", Alert.AlertType.INFORMATION);
            }

            limpiarFormulario();
            actualizarTabla();
            contactoSeleccionado = null;

        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void mostrarFormularioAgregar(ActionEvent event) {
        limpiarFormulario();
        contactoSeleccionado = null;
    }

    @FXML
    private void mostrarFormularioEditar(ActionEvent event) {
        contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (contactoSeleccionado != null) {
            cargarDatosContacto(contactoSeleccionado);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un contacto para editar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void eliminarContacto(ActionEvent event) {
        Contacto seleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            Optional<ButtonType> resultado = mostrarConfirmacion("¿Eliminar contacto?", "Esta acción no se puede deshacer");
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                gestionContacto.eliminarContacto(seleccionado);
                actualizarTabla();
                mostrarAlerta("Éxito", "Contacto eliminado", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Error", "Seleccione un contacto", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        limpiarFormulario();
        contactoSeleccionado = null;
    }

    @FXML
    private void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );
        File archivo = fileChooser.showOpenDialog(null);
        if (archivo != null) {
            imgFoto.setImage(new Image(archivo.toURI().toString()));
        }
    }

    // 4. Métodos auxiliares
    private void actualizarTabla() {
        listaCompletaContactos.setAll(gestionContacto.listarContactos());
        tablaContactos.setItems(listaCompletaContactos);
    }

    private void cargarDatosContacto(Contacto contacto) {
        txtNombre.setText(contacto.getNombre());
        txtApellido.setText(contacto.getApellido());
        txtTelefono.setText(contacto.getTelefono());
        txtCorreo.setText(contacto.getCorreo());
        dpCumpleanos.setValue(contacto.getCumpleanos());

        if (contacto.getFoto() != null && !contacto.getFoto().isEmpty()) {
            imgFoto.setImage(new Image(contacto.getFoto()));
        }
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        dpCumpleanos.setValue(null);
        imgFoto.setImage(null);
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                dpCumpleanos.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        new Alert(tipo, mensaje, ButtonType.OK).showAndWait();
    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }
}