package co.edu.medihome;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Paciente extends Usuario {
    private String telefono;
    private String direccionPrincipal;
    private final List<ServicioDomiciliario> servicios = new ArrayList<>();

    public Paciente(String identificacion, String nombre, String correo,
                    String telefono, String direccionPrincipal) {
        super(identificacion, nombre, correo);
        setTelefono(telefono);
        setDireccionPrincipal(direccionPrincipal);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = validarTexto(telefono, "El teléfono");
    }

    public String getDireccionPrincipal() {
        return direccionPrincipal;
    }

    public void setDireccionPrincipal(String direccionPrincipal) {
        this.direccionPrincipal = validarTexto(direccionPrincipal, "La dirección principal");
    }

    public List<ServicioDomiciliario> getServicios() {
        return Collections.unmodifiableList(servicios);
    }

    public ServicioDomiciliario solicitarServicio(String codigo, LocalDateTime fechaProgramada,
                                                   String direccionAtencion, String motivo) {
        ServicioDomiciliario servicio = new ServicioDomiciliario(
                codigo, fechaProgramada, direccionAtencion, motivo, this);
        servicios.add(servicio);
        notificar("Se registró la solicitud del servicio " + codigo + ".");
        return servicio;
    }
}

