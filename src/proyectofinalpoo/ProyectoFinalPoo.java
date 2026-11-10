package proyectofinalpoo;

import proyectofinalpoo.canchas.AlmacenamientoCancha;
import proyectofinalpoo.canchas.GestionCancha;
import proyectofinalpoo.empleados.AlmacenamientoEmpleado;
import proyectofinalpoo.empleados.GestionAcceso;
import proyectofinalpoo.empleados.GestionEmpleado;
import proyectofinalpoo.jugadores.AlmacenamientoJugador;
import proyectofinalpoo.jugadores.GestionJugador;
import proyectofinalpoo.reportes.GestionReportes;
import proyectofinalpoo.reservas.AlmacenamientoReserva;
import proyectofinalpoo.reservas.GestionReserva;
import proyectofinalpoo.servicios.AlmacenamientoPaquete;
import proyectofinalpoo.servicios.AlmacenamientoServicio;
import proyectofinalpoo.servicios.GestionPaquete;
import proyectofinalpoo.servicios.GestionServicio;
import proyectofinalpoo.gui.VentanaPrincipal;

public class ProyectoFinalPoo {

    public static void main(String[] args) {
        AlmacenamientoEmpleado almacenamientoEmpleado = new AlmacenamientoEmpleado();
        AlmacenamientoCancha almacenamientoCancha = new AlmacenamientoCancha();
        AlmacenamientoJugador almacenamientoJugador = new AlmacenamientoJugador();
        AlmacenamientoServicio almacenamientoServicio = new AlmacenamientoServicio();
        AlmacenamientoPaquete almacenamientoPaquete = new AlmacenamientoPaquete();
        AlmacenamientoReserva almacenamientoReserva = new AlmacenamientoReserva();
        GestionEmpleado gestionEmpleado = new GestionEmpleado(almacenamientoEmpleado);
        GestionCancha gestionCancha = new GestionCancha(almacenamientoCancha);
        GestionJugador gestionJugador = new GestionJugador(almacenamientoJugador);
        GestionServicio gestionServicio = new GestionServicio(almacenamientoServicio);
        GestionPaquete gestionPaquete = new GestionPaquete(almacenamientoPaquete);
        GestionReserva gestionReserva = new GestionReserva(
                almacenamientoReserva, almacenamientoCancha, almacenamientoJugador, almacenamientoServicio);
        GestionReportes gestionReportes = new GestionReportes(
                almacenamientoReserva, almacenamientoJugador, gestionReserva.getComprobantes());
        GestionAcceso gestionAcceso = new GestionAcceso(almacenamientoEmpleado);

        VentanaPrincipal ventana = new VentanaPrincipal(
                almacenamientoEmpleado,
                almacenamientoCancha,
                almacenamientoJugador,
                almacenamientoServicio,
                almacenamientoPaquete,
                almacenamientoReserva,
                gestionEmpleado,
                gestionCancha,
                gestionJugador,
                gestionServicio,
                gestionPaquete,
                gestionReserva,
                gestionReportes,
                gestionAcceso);
        ventana.setVisible(true);
    }
}
