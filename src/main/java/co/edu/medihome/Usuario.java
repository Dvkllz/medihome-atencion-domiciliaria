package co.edu.medihome;

import java.util.Objects;

public abstract class Usuario implements Notificable {
    private String identificacion;
    private String nombre;
    private String correo;

    protected Usuario(String identificacion, String nombre, String correo) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setCorreo(correo);
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = validarTexto(identificacion, "La identificación");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "El nombre");
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        String valor = validarTexto(correo, "El correo");
        if (!valor.contains("@")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido");
        }
        this.correo = valor;
    }

    protected static String validarTexto(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " es obligatorio");
        if (valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return valor.trim();
    }

    @Override
    public void notificar(String mensaje) {
        System.out.printf("Notificación para %s <%s>: %s%n", nombre, correo,
                validarTexto(mensaje, "El mensaje"));
    }
}

