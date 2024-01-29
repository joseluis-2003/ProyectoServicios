package server;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import Interfaces.ConstantsInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La clase UDPServidor extiende la clase Cliente e implementa la interfaz ConstantsInterface,
 * proporcionando funcionalidad para gestionar un servidor UDP y la comunicación con clientes.
 */
public class UDPServidor extends Cliente implements ConstantsInterface {

    /**
     * Lista que almacena objetos Cliente que representan a los clientes conectados al servidor.
     */
    private List<Cliente> clientes;

    /**
     * Constructor predeterminado de la clase UDPServidor.
     * Inicializa la lista de clientes.
     */
    public UDPServidor() {
        clientes = new ArrayList<>();
    }

    /**
     * Inicia el servidor UDP y espera la conexión de clientes.
     * Este método se ejecuta en un bucle infinito, recibiendo y procesando mensajes de los clientes.
     * El servidor puede ser detenido enviando un mensaje "STOP".
     */
    public void iniciarServidor() {
        try (DatagramSocket socketServidor = new DatagramSocket(PUERTO_SERVIDOR)) {
            System.out.println("El servidor está en ejecución. Esperando clientes...");

            while (true) {
                byte[] datosRecibidos = new byte[TAMANO_BUFFER];
                DatagramPacket paqueteRecibido = new DatagramPacket(datosRecibidos, datosRecibidos.length);
                socketServidor.receive(paqueteRecibido);

                ByteArrayInputStream bais = new ByteArrayInputStream(datosRecibidos);
                ObjectInputStream ois = new ObjectInputStream(bais);
                Mensaje mensaje = (Mensaje) ois.readObject();

                InetAddress direccionCliente = paqueteRecibido.getAddress();

                if (mensaje.getMensaje().equals("STOP")) {
                    System.out.println("Servidor detenido.");
                    break;
                }
                System.out.println("Recibido de " + direccionCliente + ": " + mensaje.getMensaje());
                reenviarMensajeAClientes(mensaje, direccionCliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el registro de apodo de un cliente.
     * Extrae el apodo del mensaje y verifica su disponibilidad antes de asignarlo al cliente.
     *
     * @param mensaje           El mensaje que contiene el apodo a registrar.
     * @param direccionCliente La dirección IP del cliente que envía el mensaje.
     */
    private void manejarRegistroApodo(String mensaje, InetAddress direccionCliente) {
        String apodo = mensaje.substring(5);
        if (verificarApodo(apodo)) {
            Cliente cliente = buscarCliente(direccionCliente);
            if(cliente != null){
                cliente.setApodo(apodo);
                System.out.println("server.Cliente " + direccionCliente + " registrado con apodo: " + apodo);
            }else{
                System.out.println("Cliente no encontrado");
            }
        } else {
            System.out.println("Apodo ya en uso. Por favor, elige otro.");
        }
    }

    /**
     * Reenvía un mensaje a todos los clientes excepto al remitente.
     *
     * @param mensaje            El mensaje a reenviar.
     * @param direccionRemitente La dirección IP del cliente que envió el mensaje original.
     */
    private void reenviarMensajeAClientes(Mensaje mensaje, InetAddress direccionRemitente) {
        try (DatagramSocket socketCliente = new DatagramSocket()) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mensaje);
            byte[] datosEnviar = baos.toByteArray();

            Iterator<Cliente> iterador = clientes.iterator();

            while (iterador.hasNext()) {
                Cliente cliente = iterador.next();
                if (cliente.getInetAddress() != direccionRemitente) {
                    DatagramPacket paqueteEnviar = new DatagramPacket(datosEnviar, datosEnviar.length,
                            cliente.getInetAddress(), PUERTO_CLIENTE);
                    socketCliente.send(paqueteEnviar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si un apodo ya está en uso por otro cliente.
     *
     * @param apodo El apodo a verificar.
     * @return true si el apodo está disponible; false, de lo contrario.
     */
    private boolean verificarApodo(String apodo) {
        Iterator<Cliente> iterador = clientes.iterator();

        while (iterador.hasNext()) {
            Cliente cliente = iterador.next();
            if (cliente.getApodo().equals(apodo)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Busca un cliente en la lista de clientes basándose en su dirección IP.
     *
     * @param inetAddress La dirección IP del cliente a buscar.
     * @return El objeto Cliente correspondiente a la dirección IP dada, o null si no se encuentra.
     */
    private Cliente buscarCliente(InetAddress inetAddress) {
        Iterator<Cliente> iterador = clientes.iterator();

        while (iterador.hasNext()) {
            Cliente cliente = iterador.next();
            if (cliente.getInetAddress() == inetAddress) {
                return cliente;
            }
        }

        return null;
    }
}
