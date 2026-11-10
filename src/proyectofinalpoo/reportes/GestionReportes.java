package proyectofinalpoo.reportes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import proyectofinalpoo.empleados.Empleado;
import proyectofinalpoo.facturacion.AlmacenamientoComprobante;
import proyectofinalpoo.facturacion.Comprobante;
import proyectofinalpoo.jugadores.AlmacenamientoJugador;
import proyectofinalpoo.jugadores.Jugador;
import proyectofinalpoo.reservas.AlmacenamientoReserva;
import proyectofinalpoo.reservas.EstadoReserva;
import proyectofinalpoo.reservas.Reserva;

public class GestionReportes {
    private final AlmacenamientoReserva reservas;
    private final AlmacenamientoJugador jugadores;
    private final AlmacenamientoComprobante comprobantes;

    public GestionReportes(AlmacenamientoReserva reservas, AlmacenamientoJugador jugadores,
            AlmacenamientoComprobante comprobantes) {
        this.reservas = reservas;
        this.jugadores = jugadores;
        this.comprobantes = comprobantes;
    }

    public float porcentajeOcupacion(int codigoCancha, LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null || hasta.isBefore(desde)) {
            return 0;
        }
        int horasReservadas = horasReservadas(codigoCancha, desde, hasta);
        long dias = ChronoUnit.DAYS.between(desde, hasta) + 1;
        return horasReservadas * 100.0f / (dias * 15);
    }

    public int horasReservadas(int codigoCancha, LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null || hasta.isBefore(desde)) {
            return 0;
        }
        Reserva[] lista = reservas.find();
        int horas = 0;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].getCancha().getCod() == codigoCancha
                    && !lista[i].getFecha().isBefore(desde) && !lista[i].getFecha().isAfter(hasta)
                    && lista[i].getEstado() != EstadoReserva.CANCELADA) {
                horas += lista[i].getDuracionHoras();
            }
        }
        return horas;
    }

    public int[] productividad(Empleado empleado) {
        int[] resultado = new int[2];
        Reserva[] lista = reservas.find();
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].getRecepcionista() != null
                    && lista[i].getRecepcionista().getDni() == empleado.getDni()) resultado[0]++;
            if (lista[i].getEncargado() != null
                    && lista[i].getEncargado().getDni() == empleado.getDni()
                    && lista[i].getEstado() == EstadoReserva.FINALIZADA) resultado[1]++;
        }
        return resultado;
    }

    public Jugador[] jugadoresConMasReservasFinalizadas() {
        Jugador[] resultado = jugadores.find();
        for (int i = 0; i < resultado.length; i++) {
            for (int j = i + 1; j < resultado.length; j++) {
                if (contarFinalizadas(resultado[j]) > contarFinalizadas(resultado[i])) {
                    Jugador temporal = resultado[i];
                    resultado[i] = resultado[j];
                    resultado[j] = temporal;
                }
            }
        }
        return resultado;
    }

    public float[] ingresos(LocalDate desde, LocalDate hasta) {
        float[] resultado = new float[3];
        if (desde == null || hasta == null || hasta.isBefore(desde)) {
            return resultado;
        }
        Comprobante[] lista = comprobantes.find();
        for (int i = 0; i < lista.length; i++) {
            LocalDate fecha = lista[i].getReserva().getFecha();
            if (!fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                resultado[0] += lista[i].getAlquiler() - lista[i].getDescuento();
                resultado[1] += lista[i].getServicios();
                resultado[2] += lista[i].getPaquetes();
            }
        }
        return resultado;
    }

    private int contarFinalizadas(Jugador jugador) {
        Reserva[] lista = reservas.find();
        int cantidad = 0;
        for (int i = 0; i < lista.length; i++) {
            if (lista[i].getTitular().getDni() == jugador.getDni()
                    && lista[i].getEstado() == EstadoReserva.FINALIZADA) cantidad++;
        }
        return cantidad;
    }
}
