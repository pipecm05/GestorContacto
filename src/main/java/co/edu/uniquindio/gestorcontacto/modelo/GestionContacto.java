package co.edu.uniquindio.gestorcontacto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestionContacto {
    private List<Contacto> contactos = new ArrayList<>();

    public void agregarContacto(Contacto contacto) throws Exception {
        validarContacto(contacto);

        if (existeContacto(contacto)) {
            throw new Exception("Ya existe un contacto con ese nombre y apellido");
        }

        contactos.add(contacto);
    }

    public void actualizarContacto(Contacto contactoActual, Contacto contactoNuevo) throws Exception {
        validarContacto(contactoNuevo);

        int index = contactos.indexOf(contactoActual);
        if (index != -1) {
            contactos.set(index, contactoNuevo);
        }
    }

    public void eliminarContacto(Contacto contacto) {
        contactos.remove(contacto);
    }

    public List<Contacto> buscarPorNombre(String nombre) {
        return contactos.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Contacto> buscarPorTelefono(String telefono) {
        return contactos.stream()
                .filter(c -> c.getTelefono().contains(telefono))
                .collect(Collectors.toList());
    }

    public List<Contacto> listarContactos() {
        return new ArrayList<>(contactos);
    }

    private boolean existeContacto(Contacto contacto) {
        return contactos.stream()
                .anyMatch(c -> c.getNombre().equalsIgnoreCase(contacto.getNombre())
                        && c.getApellido().equalsIgnoreCase(contacto.getApellido()));
    }

    private void validarContacto(Contacto contacto) throws Exception {
        if (contacto.getNombre() == null || contacto.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre es obligatorio");
        }

        if (contacto.getApellido() == null || contacto.getApellido().trim().isEmpty()) {
            throw new Exception("El apellido es obligatorio");
        }

        if (contacto.getTelefono() == null || contacto.getTelefono().trim().isEmpty()) {
            throw new Exception("El teléfono es obligatorio");
        }

        if (!Pattern.matches("^[+]?[0-9]{10,13}$", contacto.getTelefono())) {
            throw new Exception("El teléfono no tiene un formato válido");
        }

        if (contacto.getCorreo() == null || contacto.getCorreo().trim().isEmpty()) {
            throw new Exception("El correo es obligatorio");
        }

        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", contacto.getCorreo())) {
            throw new Exception("El correo no tiene un formato válido");
        }

        if (contacto.getCumpleanos() == null) {
            throw new Exception("La fecha de cumpleaños es obligatoria");
        }
    }
}
