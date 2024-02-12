package com.example.cliente;

import com.example.cliente.Interfaces.ConstantsInterface;

import java.io.*;
import java.net.*;

public class ApodoValidator extends Thread implements ConstantsInterface {

    InetAddress inetAddress;

    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(5011)){

            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                serverSocket.receive(paqueteRecibido);
                inetAddress = paqueteRecibido.getAddress();
                ByteArrayInputStream bais = new ByteArrayInputStream(datosRecibidos);
                ObjectInputStream ois = new ObjectInputStream(bais);
                String respuesta = ois.readUTF();
                procesarMensaje(respuesta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void procesarMensaje(String mensaje) {
        Boolean validator;
        if (mensaje != null && mensaje.startsWith("Apodo: ")) {
            String apodo = mensaje.substring("Apodo: ".length());
            validator = UDPServidor.verificarApodo(apodo);

            try (DatagramSocket socketCliente = new DatagramSocket()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                if (validator) {
                    oos.writeInt(1);
                } else{
                    oos.writeInt(0);
                }
                byte[] datosEnviar = baos.toByteArray();

                DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length,
                        inetAddress, 6011);
                socketCliente.send(paqueteEnviar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
