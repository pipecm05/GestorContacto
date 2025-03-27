module co.edu.uniquindio.gestorcontacto {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    // Abre el paquete de controladores a JavaFX FXML
    opens co.edu.uniquindio.gestorcontacto.Controladores to javafx.fxml;

    // Abre el paquete de vista (donde está Contacto.fxml) a JavaFX FXML
    opens co.edu.uniquindio.gestorcontacto to javafx.fxml;

    // Exporta el paquete principal para que otros módulos puedan usarlo
    exports co.edu.uniquindio.gestorcontacto;
    exports co.edu.uniquindio.gestorcontacto.modelo;
    exports co.edu.uniquindio.gestorcontacto.Controladores;
}