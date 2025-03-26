module co.edu.uniquindio.gestorcontacto {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens co.edu.uniquindio.recordatoriosuq.controladores to javafx.fxml;
    exports co.edu.uniquindio.gestorcontacto;
}