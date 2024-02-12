package com.example.cliente;

import java.io.Serializable;

public class Mensaje implements Serializable {

    String mensaje;

    String apodo;

    public Mensaje(String mensaje, String apodo) {
        this.mensaje = mensaje;
        this.apodo = apodo;
    }


    public String getMensaje() {
        return mensaje;
    }

    public String getApodo() {
        return apodo;
    }
}
