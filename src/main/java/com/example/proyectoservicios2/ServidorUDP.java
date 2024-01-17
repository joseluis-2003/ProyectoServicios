package com.example.proyectoservicios2;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorUDP {
    private static final int PUERTO_SERVIDOR = 5010;
    private static final int PUERTO_CLIENTE = 6010;
    private static final int TAMANO_BUFFER = 1024;

    private List<InetAddress> direccionesClientes;
    private Map<InetAddress, String> apodosClientes;

    public ServidorUDP() {
        direccionesClientes = new ArrayList<>();
        apodosClientes = new HashMap<>();
    }

    public void iniciarServidor() {
        try (DatagramSocket socketServidor = new DatagramSocket(PUERTO_SERVIDOR)) {
            System.out.println("El servidor está en ejecución. Esperando clientes...");

            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                socketServidor.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                InetAddress direccionCliente = paqueteRecibido.getAddress();

                if (mensaje.equals("STOP")) {
                    System.out.println("Servidor detenido.");
                    break;
                } else if (mensaje.startsWith("NICK:")) {
                    // Manejar el registro de apodo del cliente
                    manejarRegistroApodo(mensaje, direccionCliente);
                } else {
                    // Manejar mensajes regulares
                    System.out.println("Recibido de " + direccionCliente + ": " + mensaje);

                    // Reenviar el mensaje a todos los clientes
                    reenviarMensajeAClientes(mensaje, direccionCliente);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarRegistroApodo(String mensaje, InetAddress direccionCliente) {
        String apodo = mensaje.substring(5); // Extraer el apodo del mensaje
        if (!apodosClientes.containsValue(apodo)) {
            apodosClientes.put(direccionCliente, apodo);
            System.out.println("Cliente " + direccionCliente + " registrado con apodo: " + apodo);
        } else {
            // Informar al cliente que el apodo elegido ya está en uso
            System.out.println("Apodo ya en uso. Por favor, elige otro.");
        }
    }

    private void reenviarMensajeAClientes(String mensaje, InetAddress direccionRemitente) {
        try (DatagramSocket socketCliente = new DatagramSocket()) {
            byte[] datosEnviar = mensaje.getBytes();

            for (InetAddress direccionCliente : direccionesClientes) {
                if (!direccionCliente.equals(direccionRemitente)) {
                    DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length, direccionCliente, PUERTO_CLIENTE);
                    socketCliente.send(paqueteEnviar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorUDP().iniciarServidor();
    }
}

