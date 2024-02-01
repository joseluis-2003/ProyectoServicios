package com.example.cliente.Interfaces;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Esta interfaz proporciona constantes utilizadas en la aplicación.
 * Incluye valores como el puerto del servidor, el puerto del cliente
 * y el tamaño del búfer utilizado para la transferencia de datos.
 */
public interface ConstantsInterface {

    /**
     * Puerto utilizado por el servidor para la comunicación.
     */
    int PUERTO_SERVIDOR = 5010;

    /**
     * Puerto utilizado por el cliente para la comunicación.
     */
    int PUERTO_CLIENTE = 6010;

    /**
     * Tamaño del búfer utilizado para la transferencia de datos
     * entre el servidor y el cliente.
     */
    int TAMANO_BUFFER = 1024;

    String ipServidor = "192.168.56.1";

}
