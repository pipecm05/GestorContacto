package co.edu.uniquindio.gestorcontacto.modelo;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Contacto {
    public SimpleStringProperty nombre = new SimpleStringProperty();
    public SimpleStringProperty apellido = new SimpleStringProperty();
    public SimpleStringProperty telefono = new SimpleStringProperty();
    public SimpleStringProperty correo = new SimpleStringProperty();
    public SimpleObjectProperty<LocalDate> cumpleanos = new SimpleObjectProperty<>();
    public SimpleStringProperty foto = new SimpleStringProperty();

    // Constructor vacío necesario para JavaFX y Lombok
    public Contacto() {
    }

    // Property getters para JavaFX
    public SimpleStringProperty nombreProperty() { return nombre; }
    public SimpleStringProperty apellidoProperty() { return apellido; }
    public SimpleStringProperty telefonoProperty() { return telefono; }
    public SimpleStringProperty correoProperty() { return correo; }
    public SimpleObjectProperty<LocalDate> cumpleanosProperty() { return cumpleanos; }
    public SimpleStringProperty fotoProperty() { return foto; }

    // Getters y setters normales (generados por Lombok)
    public String getNombre() { return nombre.get(); }
    public String getApellido() { return apellido.get(); }
    public String getTelefono() { return telefono.get(); }
    public String getCorreo() { return correo.get(); }
    public LocalDate getCumpleanos() { return cumpleanos.get(); }
    public String getFoto() { return foto.get(); }

    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public void setApellido(String apellido) { this.apellido.set(apellido); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public void setCorreo(String correo) { this.correo.set(correo); }
    public void setCumpleanos(LocalDate cumpleanos) { this.cumpleanos.set(cumpleanos); }
    public void setFoto(String foto) { this.foto.set(foto); }
}