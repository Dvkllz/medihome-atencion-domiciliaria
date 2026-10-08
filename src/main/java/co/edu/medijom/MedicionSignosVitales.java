package co.edu.medijom;

import java.time.LocalDateTime;
import java.util.Objects;

public final class MedicionSignosVitales {
    private final LocalDateTime fechaHora;
    private final double temperatura;
    private final int frecuenciaCardiaca;
    private final int presionSistolica;
    private final int presionDiastolica;
    private final double saturacionOxigeno;

    MedicionSignosVitales(LocalDateTime fechaHora, double temperatura,
                          int frecuenciaCardiaca, int presionSistolica,
                          int presionDiastolica, double saturacionOxigeno) {
        this.fechaHora = Objects.requireNonNull(fechaHora, "La fecha y hora son obligatorias");
        if (temperatura < 30 || temperatura > 45) {
            throw new IllegalArgumentException("La temperatura debe estar entre 30 y 45 °C");
        }
        if (frecuenciaCardiaca <= 0 || presionSistolica <= 0 || presionDiastolica <= 0) {
            throw new IllegalArgumentException("Frecuencia y presiones deben ser positivas");
        }
        if (saturacionOxigeno < 0 || saturacionOxigeno > 100) {
            throw new IllegalArgumentException("La saturación debe estar entre 0 y 100 %");
        }
        this.temperatura = temperatura;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.presionSistolica = presionSistolica;
        this.presionDiastolica = presionDiastolica;
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public double getTemperatura() { return temperatura; }
    public int getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public int getPresionSistolica() { return presionSistolica; }
    public int getPresionDiastolica() { return presionDiastolica; }
    public double getSaturacionOxigeno() { return saturacionOxigeno; }
}

