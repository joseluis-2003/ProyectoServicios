package com.example.cliente;

import java.net.InetAddress;

/**
 * La clase Cliente representa un cliente en la aplicación.
 * Cada cliente tiene un apodo y una dirección IP asociada.
 */
public class Cliente {

    /**
     * Apodo del cliente.
     */
    private String apodo;

    /**
     * Dirección IP del cliente.
     */
    private InetAddress inetAddress;

    /**
     * Constructor de la clase Cliente que permite establecer el apodo y la dirección IP.
     *
     * @param apodo       El apodo del cliente.
     * @param inetAddress La dirección IP del cliente.
     */
    public Cliente(String apodo, InetAddress inetAddress) {
        this.apodo = apodo;
        this.inetAddress = inetAddress;
    }

    /**
     * Constructor de la clase Cliente que permite establecer la dirección IP.
     * El apodo se establece como nulo.
     *
     * @param inetAddress La dirección IP del cliente.
     */
    public Cliente(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
        apodo = null;
    }

    /**
     * Obtiene el apodo del cliente.
     *
     * @return El apodo del cliente si es válido; de lo contrario, una cadena vacía.
     */
    public String getApodo() {
        if (apodoValidator()) {
            return apodo;
        } else {
            // Lanzar una excepción u otro manejo de error si el apodo no es válido.
            return "";
        }
    }

    /**
     * Establece el apodo del cliente.
     *
     * @param apodo El nuevo apodo del cliente.
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    /**
     * Obtiene la dirección IP del cliente.
     *
     * @return La dirección IP del cliente.
     */
    public InetAddress getInetAddress() {
        return inetAddress;
    }

    /**
     * Establece la dirección IP del cliente.
     *
     * @param inetAddress La nueva dirección IP del cliente.
     */
    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    /**
     * Método privado que valida el apodo del cliente.
     *
     * @return true si el apodo es válido (no nulo); false, de lo contrario.
     */
    private boolean apodoValidator() {
        return apodo != null;
    }
}

