package co.edu.uniquindio.gestorcontacto.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GestionContacto {

    List<Contacto> contactos;

    public GestionContacto(){
        this.contactos=new ArrayList<>();
    }

    public void agregarNota(String titulo, String descripcion, String categoria) throws Exception{

        if (titulo == null || titulo.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty() ||
                categoria == null || categoria.trim().isEmpty()) {

            throw new Exception("Todos los campos son obligatorios");
        }

        Contacto contacto = new Contacto(titulo, descripcion, categoria, LocalDate.now(), null);
        contactos.add(contacto);
    }

    public ArrayList<Contacto> listarNotas(){
        return new ArrayList<>(contactos);
    }

    public ArrayList<String> listarCategorias(){
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Trabajo");
        categorias.add("Estudio");
        categorias.add("Personal");
        return categorias;
    }



    public Boolean actualizarNota(String titulo, String nuevoTitulo, String nuevaDescripcion, String nuevaCategoria){
        for(Contacto contacto : contactos){
            if(contacto.getTitulo().equals(titulo)){
                if(!listarCategorias().contains(nuevaCategoria)){
                    return false;
                }
                contacto.setTitulo(nuevoTitulo);
                contacto.setDescripcion(nuevaDescripcion);
                contacto.setCategoria(nuevaCategoria);
                return true;
            }
        }
        return false;
    }
}
