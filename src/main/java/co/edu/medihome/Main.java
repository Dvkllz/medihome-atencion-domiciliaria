package co.edu.medihome;

import java.time.LocalDateTime;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Paciente paciente = new Paciente(
                "1020304050",
                "Laura Gómez",
                "laura.gomez@example.com",
                "3001234567",
                "Calle 20 # 15-30, Medellín");

        ProfesionalSalud profesional = new ProfesionalSalud(
                "80123456",
                "Dr. Carlos Ramírez",
                "carlos.ramirez@medihome.com",
                "RM-45821",
                "Medicina general");

        EquipoAtencion equipo = new EquipoAtencion(
                "EQ-NORTE-01", "Equipo Norte", "Zona norte de Medellín");
        equipo.agregarProfesional(profesional);

        LocalDateTime fechaProgramada = LocalDateTime.of(2026, 10, 8, 9, 0);
        ServicioDomiciliario servicio = paciente.solicitarServicio(
                "SD-2026-001",
                fechaProgramada,
                paciente.getDireccionPrincipal(),
                "Fiebre y malestar general");

        servicio.programar(fechaProgramada, profesional);

        AtencionMedica atencion = servicio.iniciarAtencion(
                LocalDateTime.of(2026, 10, 8, 9, 5));

        MedicionSignosVitales medicion = atencion.registrarMedicion(
                LocalDateTime.of(2026, 10, 8, 9, 10),
                38.2, 92, 118, 76, 97.0);

        servicio.finalizar(
                LocalDateTime.of(2026, 10, 8, 9, 40),
                "Cuadro viral sin signos de alarma. Paciente estable.",
                "Hidratación, reposo y control de temperatura cada seis horas.");

        // Se conserva la referencia para evidenciar la instancia solicitada.
        if (medicion.getTemperatura() > 38) {
            System.out.println("Observación automática: temperatura elevada.");
        }

        System.out.println(servicio.generarReporte());
    }
}
