package co.edu.medijom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EquipoAtencion {
    private String codigo;
    private String nombre;
    private String zonaCobertura;
    private final List<ProfesionalSalud> profesionales = new ArrayList<>();

    public EquipoAtencion(String codigo, String nombre, String zonaCobertura) {
        setCodigo(codigo);
        setNombre(nombre);
        setZonaCobertura(zonaCobertura);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = Usuario.validarTexto(codigo, "El código del equipo");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Usuario.validarTexto(nombre, "El nombre del equipo");
    }

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = Usuario.validarTexto(zonaCobertura, "La zona de cobertura");
    }

    public List<ProfesionalSalud> getProfesionales() {
        return Collections.unmodifiableList(profesionales);
    }

    public void agregarProfesional(ProfesionalSalud profesional) {
        if (profesional == null) {
            throw new IllegalArgumentException("El profesional es obligatorio");
        }
        EquipoAtencion equipoAnterior = profesional.getEquipo();
        if (equipoAnterior != null && equipoAnterior != this) {
            equipoAnterior.retirarProfesional(profesional);
        }
        if (!profesionales.contains(profesional)) {
            profesionales.add(profesional);
            profesional.cambiarEquipo(this);
        }
    }

    public void retirarProfesional(ProfesionalSalud profesional) {
        if (profesionales.remove(profesional) && profesional.getEquipo() == this) {
            profesional.cambiarEquipo(null);
        }
    }
}

