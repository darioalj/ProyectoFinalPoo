package proyectofinalpoo.jugadores;

import proyectofinalpoo.IGestion;

public class GestionJugador implements IGestion<Jugador> {
    private final AlmacenamientoJugador almacenamiento;

    public GestionJugador(AlmacenamientoJugador almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    @Override
    public void registrar(Jugador jugador) {
        if (jugador == null || jugador.getDni() <= 0 || jugador.getTelefono() <= 0
                || jugador.getNombres() == null || jugador.getNombres().trim().isEmpty()
                || jugador.getApellidos() == null || jugador.getApellidos().trim().isEmpty()
                || jugador.getCorreo() == null || jugador.getCorreo().trim().isEmpty()) {
            System.out.println("Complete los datos obligatorios del jugador");
            return;
        }
        if (almacenamiento.findOne(jugador.getDni()) != null) {
            System.out.println("Ya existe un jugador con el DNI ingresado");
            return;
        }
        almacenamiento.create(jugador);
    }

    @Override
    public void modificar(int dni, Jugador jugador) {
        if (almacenamiento.findOne(dni) == null) {
            System.out.println("No existe un jugador con el DNI ingresado");
            return;
        }
        if (jugador == null || jugador.getDni() <= 0 || jugador.getTelefono() <= 0
                || jugador.getNombres() == null || jugador.getNombres().trim().isEmpty()
                || jugador.getApellidos() == null || jugador.getApellidos().trim().isEmpty()
                || jugador.getCorreo() == null || jugador.getCorreo().trim().isEmpty()) {
            System.out.println("Complete los datos obligatorios del jugador");
            return;
        }
        almacenamiento.update(dni, jugador);
    }

    @Override
    public void eliminar(int dni) {
        if (almacenamiento.findOne(dni) == null) {
            System.out.println("No existe un jugador con el DNI ingresado");
            return;
        }
        almacenamiento.delete(dni);
    }
}
