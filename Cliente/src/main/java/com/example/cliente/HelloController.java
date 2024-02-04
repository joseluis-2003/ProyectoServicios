package com.example.cliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextArea areaChat;

    @FXML
    private TextField campoMensaje;

    @FXML
    private Button botonEnviar;

    // Método llamado cuando se presiona el botón "Enviar"
    @FXML
    private void enviarMensaje() {
        String mensaje = campoMensaje.getText();
        if (!mensaje.isEmpty()) {
            // Limpiar el campo de texto después de enviar el mensaje
            campoMensaje.clear();
        }
    }
}
