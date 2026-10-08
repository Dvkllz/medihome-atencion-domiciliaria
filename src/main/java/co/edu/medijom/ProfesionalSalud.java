package co.edu.medijom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ProfesionalSalud extends Usuario {
    private String numeroRegistroProfesional;
    private String especialidad;
    private EquipoAtencion equipo;
    private final List<ServicioDomiciliario> serviciosAsignados = new ArrayList<>();

    public ProfesionalSalud(String identificacion, String nombre, String correo,
                            String numeroRegistroProfesional, String especialidad) {
        super(identificacion, nombre, correo);
        setNumeroRegistroProfesional(numeroRegistroProfesional);
        setEspecialidad(especialidad);
    }

    public String getNumeroRegistroProfesional() {
        return numeroRegistroProfesional;
    }

    public void setNumeroRegistroProfesional(String numeroRegistroProfesional) {
        this.numeroRegistroProfesional = validarTexto(numeroRegistroProfesional,
                "El número de registro profesional");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = validarTexto(especialidad, "La especialidad");
    }

    public EquipoAtencion getEquipo() {
        return equipo;
    }

    void cambiarEquipo(EquipoAtencion nuevoEquipo) {
        this.equipo = nuevoEquipo;
    }

    public List<ServicioDomiciliario> getServiciosAsignados() {
        return Collections.unmodifiableList(serviciosAsignados);
    }

    void asignarServicio(ServicioDomiciliario servicio) {
        if (!serviciosAsignados.contains(servicio)) {
            serviciosAsignados.add(servicio);
        }
    }

    public boolean estaDisponible(LocalDateTime fecha) {
        return serviciosAsignados.stream()
                .filter(servicio -> servicio.getEstado() != EstadoServicio.CANCELADO)
                .filter(servicio -> servicio.getEstado() != EstadoServicio.FINALIZADO)
                .noneMatch(servicio -> servicio.getFechaProgramada().equals(fecha));
    }
}

