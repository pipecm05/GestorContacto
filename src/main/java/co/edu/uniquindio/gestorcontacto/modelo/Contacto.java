package co.edu.uniquindio.gestorcontacto.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class Contacto {

    public String titulo, descripcion, categoria;
    LocalDate fechaCreacion, recordatorio;
}
