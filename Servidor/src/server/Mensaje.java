package server;

import java.io.Serializable;

public class Mensaje implements Serializable {

    String mensaje;

    String apodo;

    String nombre;

    public Mensaje(String mensaje, String nombre) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        apodo = "";
    }

    public Mensaje(String mensaje, String apodo, String nombre) {
        this.mensaje = mensaje;
        this.apodo = apodo;
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getApodo() {
        return apodo;
    }

    public String getNombre() {
        return nombre;
    }
}
