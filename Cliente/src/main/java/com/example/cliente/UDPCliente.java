package com.example.cliente;// UDPClient.java
import com.example.cliente.Interfaces.ConstantsInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * La clase UDPCliente representa la aplicación cliente UDP con interfaz gráfica.
 * Permite enviar mensajes al servidor y mostrar los mensajes recibidos en un área de chat.
 */
public class UDPCliente extends Application implements ConstantsInterface {

    /**
     * Área de texto para mostrar los mensajes del chat.
     */
    private TextArea areaChat;

    /**
     * Campo de texto para ingresar mensajes a enviar.
     */
    private TextField campoMensaje;

    private Inet4Address inetAdressServer;


    /**
     * Método principal de la aplicación JavaFX que inicia la interfaz gráfica del cliente UDP.
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        setServerIp();
        areaChat = new TextArea();
        campoMensaje = new TextField();
        Button botonEnviar = new Button("Enviar");
        botonEnviar.setOnAction(e -> enviarMensaje());

        VBox contenedorPrincipal = new VBox(areaChat, campoMensaje, botonEnviar);
        Scene escena = new Scene(contenedorPrincipal, 400, 300);

        primaryStage.setTitle("Cliente UDP");
        primaryStage.setScene(escena);
        primaryStage.show();

        // Iniciar el cliente en un hilo separado.
        new Thread(this::iniciarCliente).start();
    }

    /**
     * Método que se llama cuando se presiona el botón "Enviar".
     * Envía el mensaje ingresado al servidor a través de DatagramSocket.
     */
    private void enviarMensaje() {
        try (DatagramSocket socketCliente = new DatagramSocket()) {
            String texto = campoMensaje.getText();
            String nombre = "Ricardo";
            //if(apodo != null)
            Mensaje mensaje = new Mensaje(texto, nombre);

            // Convertir el objeto a bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mensaje);
            byte[] datosEnviar = baos.toByteArray();

            DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length,
                    inetAdressServer, PUERTO_SERVIDOR);
            socketCliente.send(paqueteEnviar);

            campoMensaje.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que inicia el cliente y escucha continuamente los mensajes recibidos del servidor.
     * Los mensajes recibidos se muestran en el área de chat.
     */
    private void iniciarCliente() {
        try (DatagramSocket socketCliente = new DatagramSocket(PUERTO_CLIENTE)) {
            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                socketCliente.receive(paqueteRecibido);

                ByteArrayInputStream bais = new ByteArrayInputStream(datosRecibidos);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Mensaje mensaje = (Mensaje) ois.readObject();

                areaChat.appendText("Recibido de "+mensaje.getNombre()+": " + mensaje.getMensaje() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setServerIp (){
        try {
            inetAdressServer = (Inet4Address) InetAddress.getByName(ipServidor);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método principal que lanza la aplicación JavaFX.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
