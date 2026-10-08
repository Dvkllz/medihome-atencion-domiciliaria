package co.edu.medihome;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class ServicioDomiciliario {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String codigo;
    private LocalDateTime fechaProgramada;
    private final String direccionAtencion;
    private final String motivo;
    private EstadoServicio estado;
    private final Paciente paciente;
    private ProfesionalSalud profesional;
    private AtencionMedica atencion;

    ServicioDomiciliario(String codigo, LocalDateTime fechaProgramada,
                         String direccionAtencion, String motivo, Paciente paciente) {
        this.codigo = Usuario.validarTexto(codigo, "El código del servicio");
        this.fechaProgramada = Objects.requireNonNull(fechaProgramada,
                "La fecha programada es obligatoria");
        this.direccionAtencion = Usuario.validarTexto(direccionAtencion,
                "La dirección de atención");
        this.motivo = Usuario.validarTexto(motivo, "El motivo");
        this.paciente = Objects.requireNonNull(paciente, "El paciente es obligatorio");
        this.estado = EstadoServicio.SOLICITADO;
    }

    public String getCodigo() { return codigo; }
    public LocalDateTime getFechaProgramada() { return fechaProgramada; }
    public String getDireccionAtencion() { return direccionAtencion; }
    public String getMotivo() { return motivo; }
    public EstadoServicio getEstado() { return estado; }
    public Paciente getPaciente() { return paciente; }
    public ProfesionalSalud getProfesional() { return profesional; }
    public AtencionMedica getAtencion() { return atencion; }

    public void programar(LocalDateTime fecha, ProfesionalSalud profesional) {
        exigirEstado(EstadoServicio.SOLICITADO);
        Objects.requireNonNull(fecha, "La fecha es obligatoria");
        Objects.requireNonNull(profesional, "El profesional es obligatorio");
        if (!profesional.estaDisponible(fecha)) {
            throw new IllegalStateException("El profesional no está disponible en esa fecha");
        }
        this.fechaProgramada = fecha;
        this.profesional = profesional;
        this.estado = EstadoServicio.PROGRAMADO;
        profesional.asignarServicio(this);
        paciente.notificar("El servicio " + codigo + " fue programado para " + fecha.format(FORMATO));
        profesional.notificar("Se le asignó el servicio " + codigo + " para " + fecha.format(FORMATO));
    }

    public AtencionMedica iniciarAtencion(LocalDateTime fechaHoraInicio) {
        exigirEstado(EstadoServicio.PROGRAMADO);
        this.atencion = new AtencionMedica(this, fechaHoraInicio);
        this.estado = EstadoServicio.EN_ATENCION;
        paciente.notificar("La atención del servicio " + codigo + " ha comenzado.");
        return atencion;
    }

    public void finalizar(LocalDateTime fechaHoraFin, String observaciones, String recomendaciones) {
        exigirEstado(EstadoServicio.EN_ATENCION);
        atencion.finalizar(fechaHoraFin, observaciones, recomendaciones);
        estado = EstadoServicio.FINALIZADO;
        paciente.notificar("El servicio " + codigo + " fue finalizado.");
    }

    public void cancelar() {
        if (estado == EstadoServicio.EN_ATENCION || estado == EstadoServicio.FINALIZADO) {
            throw new IllegalStateException("No se puede cancelar un servicio iniciado o finalizado");
        }
        estado = EstadoServicio.CANCELADO;
        paciente.notificar("El servicio " + codigo + " fue cancelado.");
        if (profesional != null) {
            profesional.notificar("El servicio " + codigo + " fue cancelado.");
        }
    }

    private void exigirEstado(EstadoServicio esperado) {
        if (estado != esperado) {
            throw new IllegalStateException("La operación requiere estado " + esperado
                    + ", pero el servicio está " + estado);
        }
    }

    public String generarReporte() {
        if (estado != EstadoServicio.FINALIZADO || atencion == null) {
            throw new IllegalStateException("El reporte solo está disponible al finalizar la atención");
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n============================================================\n")
                .append("        REPORTE DE ATENCIÓN DOMICILIARIA - MEDIHOME\n")
                .append("============================================================\n")
                .append("Servicio: ").append(codigo).append('\n')
                .append("Estado: ").append(estado).append('\n')
                .append("Paciente: ").append(paciente.getNombre())
                .append(" (ID ").append(paciente.getIdentificacion()).append(")\n")
                .append("Profesional: ").append(profesional.getNombre())
                .append(" - ").append(profesional.getEspecialidad()).append('\n')
                .append("Registro profesional: ").append(profesional.getNumeroRegistroProfesional()).append('\n')
                .append("Dirección: ").append(direccionAtencion).append('\n')
                .append("Motivo: ").append(motivo).append('\n')
                .append("Inicio: ").append(atencion.getFechaHoraInicio().format(FORMATO)).append('\n')
                .append("Fin: ").append(atencion.getFechaHoraFin().format(FORMATO)).append('\n')
                .append("Observaciones: ").append(atencion.getObservaciones()).append('\n')
                .append("Recomendaciones: ").append(atencion.getRecomendaciones()).append('\n')
                .append("\nSignos vitales registrados: ").append(atencion.getMediciones().size()).append('\n');

        for (int i = 0; i < atencion.getMediciones().size(); i++) {
            MedicionSignosVitales m = atencion.getMediciones().get(i);
            reporte.append(String.format(
                    "  %d. %s | Temp: %.1f °C | FC: %d lpm | PA: %d/%d mmHg | SpO2: %.1f%%%n",
                    i + 1, m.getFechaHora().format(FORMATO), m.getTemperatura(),
                    m.getFrecuenciaCardiaca(), m.getPresionSistolica(),
                    m.getPresionDiastolica(), m.getSaturacionOxigeno()));
        }
        reporte.append("============================================================\n");
        return reporte.toString();
    }
}

