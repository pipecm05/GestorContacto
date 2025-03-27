package co.edu.uniquindio.gestorcontacto.Controladores;

import co.edu.uniquindio.gestorcontacto.modelo.Contacto;
import co.edu.uniquindio.gestorcontacto.modelo.GestionContacto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ContactosControlador implements Initializable {

    // Componentes de la tabla
    @FXML private TableView<Contacto> tablaContactos;
    @FXML private TableColumn<Contacto, String> colNombre;
    @FXML private TableColumn<Contacto, String> colApellido;
    @FXML private TableColumn<Contacto, String> colTelefono;
    @FXML private TableColumn<Contacto, String> colCorreo;
    @FXML private TableColumn<Contacto, String> colCumpleanos;

    // Componentes de búsqueda
    @FXML private ComboBox<String> cbFiltro;
    @FXML private TextField txtBusqueda;

    // Componentes del formulario
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private DatePicker dpCumpleanos;

    private final GestionContacto gestionContacto = new GestionContacto();
    private Contacto contactoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabla();
        configurarBusqueda();
        cargarDatosIniciales();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCumpleanos.setCellValueFactory(new PropertyValueFactory<>("cumpleanos"));

        tablaContactos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> contactoSeleccionado = newSelection);
    }

    private void configurarBusqueda() {
        cbFiltro.setItems(FXCollections.observableArrayList("Nombre", "Teléfono"));
        cbFiltro.getSelectionModel().selectFirst();
    }

    private void cargarDatosIniciales() {
        actualizarTabla();
    }

    @FXML
    private void agregarContacto(ActionEvent event) {
        if (validarCampos()) {
            Contacto nuevoContacto = crearContactoDesdeFormulario();

            try {
                gestionContacto.agregarContacto(nuevoContacto);
                mostrarAlerta("Éxito", "Contacto agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarFormulario(event);
                actualizarTabla();
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void editarContacto(ActionEvent event) {
        if (contactoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un contacto para editar", Alert.AlertType.WARNING);
            return;
        }

        if (validarCampos()) {
            Contacto contactoEditado = crearContactoDesdeFormulario();

            try {
                gestionContacto.actualizarContacto(contactoSeleccionado, contactoEditado);
                mostrarAlerta("Éxito", "Contacto actualizado correctamente", Alert.AlertType.INFORMATION);
                limpiarFormulario(event);
                actualizarTabla();
                contactoSeleccionado = null;
            } catch (Exception e) {
                mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void eliminarContacto(ActionEvent event) {
        if (contactoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un contacto para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Eliminar contacto?");
        confirmacion.setContentText("Esta acción no se puede deshacer");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            gestionContacto.eliminarContacto(contactoSeleccionado);
            mostrarAlerta("Éxito", "Contacto eliminado correctamente", Alert.AlertType.INFORMATION);
            actualizarTabla();
            limpiarFormulario(event);
        }
    }

    @FXML
    private void buscarContacto(ActionEvent event) {
        String criterio = cbFiltro.getValue();
        String busqueda = txtBusqueda.getText().trim();

        if (busqueda.isEmpty()) {
            mostrarAlerta("Advertencia", "Ingrese un término de búsqueda", Alert.AlertType.WARNING);
            return;
        }

        List<Contacto> resultados;
        if ("Nombre".equals(criterio)) {
            resultados = gestionContacto.buscarPorNombre(busqueda);
        } else {
            resultados = gestionContacto.buscarPorTelefono(busqueda);
        }

        if (resultados.isEmpty()) {
            mostrarAlerta("Información", "No se encontraron contactos", Alert.AlertType.INFORMATION);
        } else {
            tablaContactos.setItems(FXCollections.observableArrayList(resultados));
        }
    }

    @FXML
    private void buscarPorNombreCompleto(ActionEvent event) {
        String nombre = txtBusqueda.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta("Advertencia", "Ingrese un nombre para buscar", Alert.AlertType.WARNING);
            return;
        }

        Contacto contacto = gestionContacto.buscarPorNombreExacto(nombre);

        if (contacto != null) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Información del contacto");
            alerta.setHeaderText("Datos completos de " + contacto.getNombre());
            alerta.setContentText(
                    "Nombre: " + contacto.getNombre() + "\n" +
                            "Apellido: " + contacto.getApellido() + "\n" +
                            "Teléfono: " + contacto.getTelefono() + "\n" +
                            "Correo: " + contacto.getCorreo() + "\n" +
                            "Cumpleaños: " + contacto.getCumpleanos()
            );
            alerta.showAndWait();
        } else {
            mostrarAlerta("Información", "No se encontró el contacto", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        dpCumpleanos.setValue(null);
        contactoSeleccionado = null;
    }

    private Contacto crearContactoDesdeFormulario() {
        Contacto contacto = new Contacto();
        contacto.setNombre(txtNombre.getText());
        contacto.setApellido(txtApellido.getText());
        contacto.setTelefono(txtTelefono.getText());
        contacto.setCorreo(txtCorreo.getText());
        contacto.setCumpleanos(LocalDate.parse(dpCumpleanos.getValue() != null ? dpCumpleanos.getValue().toString() : ""));
        return contacto;
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                dpCumpleanos.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        if (!validarTelefono(txtTelefono.getText())) {
            mostrarAlerta("Error", "Formato de teléfono inválido", Alert.AlertType.ERROR);
            return false;
        }

        if (!validarCorreo(txtCorreo.getText())) {
            mostrarAlerta("Error", "Formato de correo inválido", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private boolean validarTelefono(String telefono) {
        return Pattern.matches("^[+]?[0-9]{10,13}$", telefono);
    }

    private boolean validarCorreo(String correo) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", correo);
    }

    private void actualizarTabla() {
        tablaContactos.setItems(FXCollections.observableArrayList(gestionContacto.listarContactos()));
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}