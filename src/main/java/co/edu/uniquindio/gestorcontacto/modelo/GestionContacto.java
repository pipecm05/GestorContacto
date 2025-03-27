package co.edu.uniquindio.gestorcontacto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionContacto {
    private final List<Contacto> contactos = new ArrayList<>();

    public void agregarContacto(Contacto contacto) throws Exception {
        validarContacto(contacto);

        if (existeContacto(contacto)) {
            throw new Exception("Ya existe un contacto con ese nombre y apellido");
        }

        contactos.add(contacto);
    }

    public void actualizarContacto(Contacto viejo, Contacto nuevo) throws Exception {
        validarContacto(nuevo);

        int index = contactos.indexOf(viejo);
        if (index != -1) {
            contactos.set(index, nuevo);
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

    public Contacto buscarPorNombreExacto(String nombre) {
        return contactos.stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
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

        if (contacto.getTelefono() == null || contacto.getTelefono().trim().isEmpty()) {
            throw new Exception("El teléfono es obligatorio");
        }

        if (!contacto.getTelefono().matches("^[+]?[0-9]{10,13}$")) {
            throw new Exception("El teléfono debe tener entre 10 y 13 dígitos");
        }

        if (contacto.getCorreo() == null || contacto.getCorreo().trim().isEmpty()) {
            throw new Exception("El correo es obligatorio");
        }

        if (!contacto.getCorreo().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new Exception("Formato de correo inválido");
        }
    }
}