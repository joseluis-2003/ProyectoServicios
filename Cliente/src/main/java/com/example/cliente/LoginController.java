package com.example.cliente;

import com.example.cliente.Interfaces.ConstantsInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.net.*;

public class LoginController implements ConstantsInterface {

    private Inet4Address inetAdressServer;
    public TextField textFieldApodo;
    @FXML
    private TextField textFieldLocalPort;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private Label labelError;
    @FXML
    private Label accessError;

    static Cliente usuario;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setServerIp();
    }

    @FXML
    private void onActionButtonLogin(ActionEvent event) {
        labelError.setText("");

        UDPCliente.PUERTO_CLIENTE = Integer.parseInt(textFieldLocalPort.getText());

        if (textFieldApodo.getText().isEmpty()) {
            usuario = new Cliente(textFieldUsername.getText());
        } else {
            String apodo = textFieldApodo.getText();
            if (autenticar(apodo)) {
                usuario = new Cliente(apodo);
            } else {
                labelError.setText("Apodo ocupado");
                return;
            }
        }

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

    }

/*
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
    */


    // Método de autenticación (deberías implementar tu propia lógica)
    private boolean autenticar(String apodo) {
        try (DatagramSocket socketCliente = new DatagramSocket()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeUTF("Apodo: " + apodo);
            byte[] datosEnviar = baos.toByteArray();

            DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length,
                    inetAdressServer, 5011);
            socketCliente.send(paqueteEnviar);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (DatagramSocket socketCliente = new DatagramSocket(6011)) {
            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                socketCliente.receive(paqueteRecibido);

                ByteArrayInputStream bais = new ByteArrayInputStream(datosRecibidos);
                ObjectInputStream ois = new ObjectInputStream(bais);
                int respuesta = ois.readInt();
                if (respuesta == 1) {
                    return true;
                } else if(respuesta == 0){
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void setServerIp() {
        try {
            inetAdressServer = (Inet4Address) InetAddress.getByName(ipServidor);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}

