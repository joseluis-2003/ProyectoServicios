module com.example.proyectoservicios2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectoservicios2 to javafx.fxml;
    exports com.example.proyectoservicios2;
}