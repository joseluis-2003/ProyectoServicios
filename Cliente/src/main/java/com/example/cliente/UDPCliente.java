package com.example.cliente;// UDPClient.java
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCliente extends Application {
    private static final int PUERTO_SERVIDOR = 5010;
    private static final int PUERTO_CLIENTE = 6010;
    private static final int TAMANO_BUFFER = 1024;

    private TextArea areaChat;
    private TextField campoMensaje;

    @Override
    public void start(Stage primaryStage) {
        areaChat = new TextArea();
        campoMensaje = new TextField();
        Button botonEnviar = new Button("Enviar");
        botonEnviar.setOnAction(e -> enviarMensaje());

        VBox contenedorPrincipal = new VBox(areaChat, campoMensaje, botonEnviar);
        Scene escena = new Scene(contenedorPrincipal, 400, 300);

        primaryStage.setTitle("Cliente UDP");
        primaryStage.setScene(escena);
        primaryStage.show();

        new Thread(this::iniciarCliente).start();
    }

    private void enviarMensaje() {
        try (DatagramSocket socketCliente = new DatagramSocket()) {
            String mensaje = campoMensaje.getText();
            byte[] datosEnviar = mensaje.getBytes();

            DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length, InetAddress.getLocalHost(), PUERTO_SERVIDOR);
            socketCliente.send(paqueteEnviar);

            campoMensaje.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarCliente() {
        try (DatagramSocket socketCliente = new DatagramSocket(PUERTO_CLIENTE)) {
            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                socketCliente.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                areaChat.appendText("Recibido: " + mensaje + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
