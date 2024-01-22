package server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import Interfaces.ConstantsInterface;

public class UDPServidor extends Cliente implements ConstantsInterface{

    private List<Cliente> clientes;

    public UDPServidor() {
        clientes = new ArrayList<>();
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
                    manejarRegistroApodo(mensaje, direccionCliente);
                }
                System.out.println("Recibido de " + direccionCliente + ": " + mensaje);
                reenviarMensajeAClientes(mensaje, direccionCliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void manejarRegistroApodo(String mensaje, InetAddress direccionCliente) {
        String apodo = mensaje.substring(5);
        if (verificarApodo(apodo)) {
            Cliente cliente = buscarCliente(direccionCliente);
            if(cliente != null){
                cliente.setApodo(apodo);
            }
            System.out.println("server.Cliente " + direccionCliente + " registrado con apodo: " + apodo);
        } else {
            System.out.println("Apodo ya en uso. Por favor, elige otro.");
        }
    }

    private void reenviarMensajeAClientes(String mensaje, InetAddress direccionRemitente) {
        try (DatagramSocket socketCliente = new DatagramSocket()) {
            byte[] datosEnviar = mensaje.getBytes();

            Iterator<Cliente> iterador = clientes.iterator();

            while (iterador.hasNext()) {
                Cliente cliente = iterador.next();
                if (cliente.getInetAddress()==direccionRemitente) {
                    DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length,
                            cliente.getInetAddress(), PUERTO_CLIENTE);
                    socketCliente.send(paqueteEnviar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verificarApodo(String apodo){
        Iterator<Cliente> iterador = clientes.iterator();

        while (iterador.hasNext()) {
            Cliente cliente = iterador.next();
            if(cliente.getApodo().equals(apodo)){
                return false;
            }
        }

        return true;
    }

    private Cliente buscarCliente(InetAddress inetAddress){
        Iterator<Cliente> iterador = clientes.iterator();

        while (iterador.hasNext()) {
            Cliente cliente = iterador.next();
            if(cliente.getInetAddress() == inetAddress){
                return cliente;
            }
        }

        return null;
    }
}
