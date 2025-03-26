module co.edu.uniquindio.gestorcontacto {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.gestorcontacto to javafx.fxml;
    exports co.edu.uniquindio.gestorcontacto;
}