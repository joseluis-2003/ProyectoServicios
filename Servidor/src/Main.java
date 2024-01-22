import server.UDPServidor;

/**
 * La clase Main contiene el método principal que inicia el servidor UDP.
 */
public class Main {

    /**
     * El método principal de la aplicación que inicia el servidor UDP.
     *
     * @param args Los argumentos de la línea de comandos (no se utilizan en este caso).
     */
    public static void main(String[] args) {
        // Crear una instancia de UDPServidor y iniciar el servidor.
        UDPServidor server = new UDPServidor();
        server.iniciarServidor();
    }
}

