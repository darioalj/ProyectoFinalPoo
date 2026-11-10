package proyectofinalpoo.jugadores;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoJugador implements IAlmacenamiento<Jugador> {
    private static final int CAPACIDAD = 100;

    private Jugador[] jugadores;
    private int jugadoresNro;

    public AlmacenamientoJugador() {
        jugadores = new Jugador[CAPACIDAD];
        jugadoresNro = 0;
    }

    @Override
    public void create(Jugador jugador) {
        if (jugador == null || jugadoresNro == jugadores.length
                || findOne(jugador.getDni()) != null) {
            return;
        }
        jugadores[jugadoresNro] = jugador;
        actualizarTipo(jugadoresNro);
        jugadoresNro++;
    }

    @Override
    public Jugador findOne(int dni) {
        for (int i = 0; i < jugadoresNro; i++) {
            if (jugadores[i].getDni() == dni) {
                return jugadores[i];
            }
        }
        return null;
    }

    @Override
    public Jugador[] find() {
        Jugador[] resultado = new Jugador[jugadoresNro];
        for (int i = 0; i < jugadoresNro; i++) {
            resultado[i] = jugadores[i];
        }
        return resultado;
    }

    public void registrarReservaFinalizada(int dni) {
        for (int i = 0; i < jugadoresNro; i++) {
            if (jugadores[i].getDni() == dni) {
                jugadores[i].registrarReservaFinalizada();
                actualizarTipo(i);
                return;
            }
        }
    }

    @Override
    public void update(int dni, Jugador jugador) {
        if (jugador == null) {
            return;
        }
        for (int i = 0; i < jugadoresNro; i++) {
            if (jugadores[i].getDni() == dni) {
                if (jugador.getDni() != dni && findOne(jugador.getDni()) != null) {
                    return;
                }
                jugador.setReservasFinalizadas(jugadores[i].getReservasFinalizadas());
                jugadores[i] = jugador;
                actualizarTipo(i);
                return;
            }
        }
    }

    @Override
    public void delete(int dni) {
        for (int i = 0; i < jugadoresNro; i++) {
            if (jugadores[i].getDni() == dni) {
                for (int j = i; j < jugadoresNro - 1; j++) {
                    jugadores[j] = jugadores[j + 1];
                }
                jugadoresNro--;
                jugadores[jugadoresNro] = null;
                return;
            }
        }
    }

    private void actualizarTipo(int indice) {
        Jugador actual = jugadores[indice];

        if (actual.getReservasFinalizadas() >= 5 && !(actual instanceof JugadorFrecuente)) {
            JugadorFrecuente frecuente = new JugadorFrecuente(actual.getDni(), actual.getNombres(),
                    actual.getApellidos(), actual.getTelefono(), actual.getCorreo());
            frecuente.setReservasFinalizadas(actual.getReservasFinalizadas());
            jugadores[indice] = frecuente;
        }

        if (actual.getReservasFinalizadas() < 5 && !(actual instanceof JugadorRegular)) {
            JugadorRegular regular = new JugadorRegular(actual.getDni(), actual.getNombres(),
                    actual.getApellidos(), actual.getTelefono(), actual.getCorreo());
            regular.setReservasFinalizadas(actual.getReservasFinalizadas());
            jugadores[indice] = regular;
        }
    }
}
