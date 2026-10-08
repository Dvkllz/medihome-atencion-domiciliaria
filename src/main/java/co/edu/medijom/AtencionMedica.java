package co.edu.medijom;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class AtencionMedica {
    private final ServicioDomiciliario servicio;
    private final LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String observaciones;
    private String recomendaciones;
    private final List<MedicionSignosVitales> mediciones = new ArrayList<>();

    AtencionMedica(ServicioDomiciliario servicio, LocalDateTime fechaHoraInicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio es obligatorio");
        this.fechaHoraInicio = Objects.requireNonNull(fechaHoraInicio,
                "La fecha y hora de inicio son obligatorias");
    }

    public ServicioDomiciliario getServicio() { return servicio; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public String getObservaciones() { return observaciones; }
    public String getRecomendaciones() { return recomendaciones; }

    public List<MedicionSignosVitales> getMediciones() {
        return Collections.unmodifiableList(mediciones);
    }

    public MedicionSignosVitales registrarMedicion(LocalDateTime fechaHora,
                                                    double temperatura,
                                                    int frecuenciaCardiaca,
                                                    int presionSistolica,
                                                    int presionDiastolica,
                                                    double saturacionOxigeno) {
        if (fechaHora.isBefore(fechaHoraInicio)) {
            throw new IllegalArgumentException("La medición no puede ser anterior al inicio");
        }
        MedicionSignosVitales medicion = new MedicionSignosVitales(fechaHora, temperatura,
                frecuenciaCardiaca, presionSistolica, presionDiastolica, saturacionOxigeno);
        mediciones.add(medicion);
        return medicion;
    }

    void finalizar(LocalDateTime fechaHoraFin, String observaciones, String recomendaciones) {
        Objects.requireNonNull(fechaHoraFin, "La fecha y hora de fin son obligatorias");
        if (fechaHoraFin.isBefore(fechaHoraInicio)) {
            throw new IllegalArgumentException("El fin no puede ser anterior al inicio");
        }
        this.fechaHoraFin = fechaHoraFin;
        this.observaciones = Usuario.validarTexto(observaciones, "Las observaciones");
        this.recomendaciones = Usuario.validarTexto(recomendaciones, "Las recomendaciones");
    }
}

