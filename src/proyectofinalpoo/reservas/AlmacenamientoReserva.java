package proyectofinalpoo.reservas;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoReserva implements IAlmacenamiento<Reserva> {
    private static final int CAPACIDAD = 100;

    private Reserva[] reservas;
    private int cantidad;
    private int siguienteNumero = 1;

    public AlmacenamientoReserva() {
        reservas = new Reserva[CAPACIDAD];
        cantidad = 0;
    }

    public int obtenerSiguienteNumero() {
        return siguienteNumero;
    }

    @Override
    public void create(Reserva reserva) {
        if (reserva == null || cantidad == reservas.length) {
            return;
        }
        reserva.setNumero(siguienteNumero);
        reservas[cantidad++] = reserva;
        siguienteNumero++;
    }

    @Override
    public Reserva findOne(int numero) {
        for (int i = 0; i < cantidad; i++) {
            if (reservas[i].getNumero() == numero) {
                return reservas[i];
            }
        }
        return null;
    }

    @Override
    public Reserva[] find() {
        Reserva[] resultado = new Reserva[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = reservas[i];
        }
        return resultado;
    }

    @Override
    public void update(int numero, Reserva reserva) {
        if (reserva == null) {
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            if (reservas[i].getNumero() == numero) {
                reservas[i] = reserva;
                return;
            }
        }
    }

    @Override
    public void delete(int numero) {
        for (int i = 0; i < cantidad; i++) {
            if (reservas[i].getNumero() == numero) {
                for (int j = i; j < cantidad - 1; j++) {
                    reservas[j] = reservas[j + 1];
                }
                cantidad--;
                reservas[cantidad] = null;
                return;
            }
        }
    }
}
