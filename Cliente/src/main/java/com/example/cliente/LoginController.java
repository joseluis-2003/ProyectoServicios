package com.example.cliente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    private TextField textFieldIp;
    @FXML
    private TextField textFieldLocalPort;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Label labelError;
    @FXML
    private Label accessError;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void onActionButtonLogin(ActionEvent event) {
        UDPCliente.PUERTO_CLIENTE = Integer.parseInt(textFieldLocalPort.getText());

        if (autenticar()) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Hello-view.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la vista Hello-view.fxml si es necesario
                // HelloController helloController = loader.getController();

                // Configurar la nueva escena y mostrarla
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Cliente UDP - Hello View");
                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Manejo de error de autenticación
            // ...
        }
    }

    // Métodos asociados a los eventos onAction de los TextField
    @FXML
    private void onActionTextFieldLocalPort(ActionEvent event) {
        // Lógica asociada al evento del TextField LocalPort
    }

    @FXML
    private void onActionTextFieldusername(ActionEvent event) {
        // Lógica asociada al evento del TextField Username
    }

    @FXML
    private void onActionTextFieldIp(ActionEvent event) {
        // Lógica asociada al evento del TextField IP
    }

    // Método de autenticación (deberías implementar tu propia lógica)
    private boolean autenticar() {
        // Implementa tu lógica de autenticación aquí
        return true;
    }
}
