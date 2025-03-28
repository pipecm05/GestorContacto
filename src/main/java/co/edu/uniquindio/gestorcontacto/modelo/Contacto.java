package co.edu.uniquindio.gestorcontacto.modelo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Contacto {
    private SimpleStringProperty nombre = new SimpleStringProperty();
    private SimpleStringProperty apellido = new SimpleStringProperty();
    private SimpleStringProperty telefono = new SimpleStringProperty();
    private SimpleStringProperty correo = new SimpleStringProperty();
    private SimpleObjectProperty<LocalDate> cumpleanos = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Image> foto = new SimpleObjectProperty<>();

    // Property getters para JavaFX
    public SimpleStringProperty nombreProperty() { return nombre; }
    public SimpleStringProperty apellidoProperty() { return apellido; }
    public SimpleStringProperty telefonoProperty() { return telefono; }
    public SimpleStringProperty correoProperty() { return correo; }
    public SimpleObjectProperty<LocalDate> cumpleanosProperty() { return cumpleanos; }
    public SimpleObjectProperty<Image> fotoProperty() { return foto; }

    // Getters y setters normales (generados por Lombok)
    public String getNombre() { return nombre.get(); }
    public String getApellido() { return apellido.get(); }
    public String getTelefono() { return telefono.get(); }
    public String getCorreo() { return correo.get(); }
    public LocalDate getCumpleanos() { return cumpleanos.get(); }
    public Image getFoto() { return foto.get(); }

    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setApellido(String apellido) { this.apellido.set(apellido); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public void setCorreo(String correo) { this.correo.set(correo); }
    public void setCumpleanos(LocalDate cumpleanos) { this.cumpleanos.set(cumpleanos); }
    public void setFoto(Image foto) { this.foto.set(foto); }
}