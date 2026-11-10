package proyectofinalpoo.reservas;

import java.time.LocalDateTime;
import java.time.LocalTime;
import proyectofinalpoo.IGestion;
import proyectofinalpoo.empleados.Empleado;
import proyectofinalpoo.facturacion.AlmacenamientoComprobante;
import proyectofinalpoo.facturacion.GestionFacturacion;
import proyectofinalpoo.facturacion.TipoComprobante;
import proyectofinalpoo.canchas.AlmacenamientoCancha;
import proyectofinalpoo.canchas.Cancha;
import proyectofinalpoo.canchas.EstadoCancha;
import proyectofinalpoo.jugadores.AlmacenamientoJugador;
import proyectofinalpoo.jugadores.Jugador;
import proyectofinalpoo.servicios.AlmacenamientoServicio;
import proyectofinalpoo.servicios.PaquetePromocional;
import proyectofinalpoo.servicios.ServicioAdicional;

public class GestionReserva implements IGestion<Reserva> {
    private final AlmacenamientoReserva reservas;
    private final AlmacenamientoCancha canchas;
    private final AlmacenamientoJugador jugadores;
    private final AlmacenamientoServicio servicios;
    private final AlmacenamientoComprobante comprobantes;
    private final GestionFacturacion facturacion;

    public GestionReserva(AlmacenamientoReserva reservas, AlmacenamientoCancha canchas,
            AlmacenamientoJugador jugadores, AlmacenamientoServicio servicios) {
        this.reservas = reservas;
        this.canchas = canchas;
        this.jugadores = jugadores;
        this.servicios = servicios;
        this.comprobantes = new AlmacenamientoComprobante();
        this.facturacion = new GestionFacturacion(comprobantes);
    }

    public AlmacenamientoComprobante getComprobantes() {
        return comprobantes;
    }

    @Override
    public void registrar(Reserva reserva) {
        registrar(reserva, null);
    }

    public void registrar(Reserva reserva, Empleado recepcionista) {
        if (reserva == null || reserva.getTitular() == null || reserva.getCancha() == null
                || reserva.getFecha() == null || reserva.getHoraInicio() == null) {
            System.out.println("Complete los datos obligatorios de la reserva");
            return;
        }
        if (jugadores.findOne(reserva.getTitular().getDni()) == null) {
            System.out.println("El jugador titular no está registrado");
            return;
        }
        Cancha cancha = canchas.findOne(reserva.getCancha().getCod());
        if (cancha == null || cancha.getEstado() != EstadoCancha.DISPONIBLE) {
            System.out.println("La cancha no está disponible");
            return;
        }
        if (!horarioValido(reserva)) {
            System.out.println("La reserva debe estar dentro del horario 08:00 a 23:00 y durar de 1 a 3 horas");
            return;
        }
        if (reserva.getParticipantesNro() > cancha.getCapacidadMaxima()) {
            System.out.println("La cantidad de participantes supera la capacidad de la cancha");
            return;
        }
        if (hayCruce(reserva)) {
            System.out.println("La cancha ya tiene una reserva en ese horario");
            return;
        }
        ServicioPedido[] pedidos = reserva.getServicios();
        for (int i = 0; i < pedidos.length; i++) {
            if (servicios.findOne(pedidos[i].getServicio().getCodigo()) == null) {
                System.out.println("El servicio solicitado no está registrado");
                return;
            }
        }
        PaquetePromocional[] paquetes = reserva.getPaquetes();
        for (int i = 0; i < paquetes.length; i++) {
            ServicioAdicional[] incluidos = paquetes[i].getServicios();
            for (int j = 0; j < incluidos.length; j++) {
                if (servicios.findOne(incluidos[j].getCodigo()) == null) {
                    System.out.println("El servicio del paquete no está registrado");
                    return;
                }
            }
        }
        ServicioAdicional[] serviciosDisponibles = servicios.find();
        for (int i = 0; i < serviciosDisponibles.length; i++) {
            int cantidadSolicitada = cantidadSolicitada(reserva, serviciosDisponibles[i].getCodigo());
            if (cantidadSolicitada > 0 && !serviciosDisponibles[i].tieneStock(cantidadSolicitada)) {
                System.out.println("No hay stock suficiente del servicio solicitado");
                return;
            }
        }
        for (int i = 0; i < serviciosDisponibles.length; i++) {
            int cantidadSolicitada = cantidadSolicitada(reserva, serviciosDisponibles[i].getCodigo());
            serviciosDisponibles[i].descontarStock(cantidadSolicitada);
        }
        reserva.setRecepcionista(recepcionista);
        reservas.create(reserva);
    }

    private boolean horarioValido(Reserva reserva) {
        if (reserva.getDuracionHoras() < 1 || reserva.getDuracionHoras() > 3) {
            return false;
        }
        LocalTime inicio = reserva.getHoraInicio();
        LocalTime fin = reserva.getHoraFin();
        return !inicio.isBefore(LocalTime.of(8, 0)) && !fin.isAfter(LocalTime.of(23, 0));
    }

    private boolean hayCruce(Reserva nueva) {
        Reserva[] existentes = reservas.find();
        for (int i = 0; i < existentes.length; i++) {
            Reserva actual = existentes[i];
            if (actual.getCancha().getCod() == nueva.getCancha().getCod()
                    && actual.getFecha().equals(nueva.getFecha())
                    && actual.getEstado() != EstadoReserva.CANCELADA
                    && nueva.getHoraInicio().isBefore(actual.getHoraFin())
                    && actual.getHoraInicio().isBefore(nueva.getHoraFin())) {
                return true;
            }
        }
        return false;
    }

    private int cantidadSolicitada(Reserva reserva, int codigoServicio) {
        int cantidad = 0;
        ServicioPedido[] pedidos = reserva.getServicios();
        for (int i = 0; i < pedidos.length; i++) {
            if (pedidos[i].getServicio().getCodigo() == codigoServicio) {
                cantidad += pedidos[i].getCantidad();
            }
        }
        PaquetePromocional[] paquetes = reserva.getPaquetes();
        for (int i = 0; i < paquetes.length; i++) {
            ServicioAdicional[] incluidos = paquetes[i].getServicios();
            for (int j = 0; j < incluidos.length; j++) {
                if (incluidos[j].getCodigo() == codigoServicio) cantidad++;
            }
        }
        return cantidad;
    }

    private float calcularAlquiler(Reserva reserva) {
        float alquiler = 0;
        for (int i = 0; i < reserva.getDuracionHoras(); i++) {
            alquiler += reserva.getCancha().getTarifa(reserva.getHoraInicio().plusHours(i));
        }
        return alquiler;
    }

    public void confirmar(int numero, float adelanto) {
        Reserva reserva = reservas.findOne(numero);
        if (reserva == null || reserva.getEstado() != EstadoReserva.PENDIENTE) {
            return;
        }
        float alquiler = calcularAlquiler(reserva);
        if (adelanto < alquiler * 0.5f) {
            System.out.println("El adelanto debe cubrir al menos el 50% del alquiler");
            return;
        }
        reserva.registrarAdelanto(adelanto);
        reserva.cambiarEstado(EstadoReserva.CONFIRMADA, "Reserva confirmada con adelanto");
    }

    public void iniciar(int numero) {
        iniciar(numero, null);
    }

    public void iniciar(int numero, Empleado encargado) {
        Reserva reserva = reservas.findOne(numero);
        if (reserva == null || reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            return;
        }
        reserva.setEncargado(encargado);
        reserva.cambiarEstado(EstadoReserva.EN_JUEGO, "Partido iniciado");
        reserva.getCancha().reservar();
    }

    public void finalizar(int numero) {
        finalizar(numero, TipoComprobante.BOLETA, null, null);
    }

    public void finalizar(int numero, TipoComprobante tipo, String ruc, String razonSocial) {
        Reserva reserva = reservas.findOne(numero);
        if (reserva == null || reserva.getEstado() != EstadoReserva.EN_JUEGO) {
            return;
        }
        reserva.cambiarEstado(EstadoReserva.FINALIZADA, "Partido finalizado");
        reserva.getCancha().disponible();
        int dniTitular = reserva.getTitular().getDni();
        jugadores.registrarReservaFinalizada(dniTitular);
        Jugador titularActualizado = jugadores.findOne(dniTitular);
        if (titularActualizado != null) {
            reserva.actualizarTitular(titularActualizado);
        }
        facturacion.emitir(reserva, tipo, ruc, razonSocial);
    }

    public void cancelar(int numero) {
        Reserva reserva = reservas.findOne(numero);
        if (reserva == null || reserva.getEstado() == EstadoReserva.FINALIZADA
                || reserva.getEstado() == EstadoReserva.CANCELADA) {
            return;
        }
        if (reserva.getEstado() == EstadoReserva.EN_JUEGO) {
            return;
        }
        LocalDateTime inicioReserva = LocalDateTime.of(reserva.getFecha(), reserva.getHoraInicio());
        boolean devuelveAdelanto = !LocalDateTime.now().plusHours(24).isAfter(inicioReserva);
        if (devuelveAdelanto) {
            reserva.devolverAdelanto();
            reserva.cambiarEstado(EstadoReserva.CANCELADA, "Cancelada: adelanto devuelto");
        } else {
            reserva.cambiarEstado(EstadoReserva.CANCELADA, "Cancelada: adelanto retenido");
        }
        ServicioAdicional[] serviciosDisponibles = servicios.find();
        for (int i = 0; i < serviciosDisponibles.length; i++) {
            int cantidadSolicitada = cantidadSolicitada(reserva, serviciosDisponibles[i].getCodigo());
            serviciosDisponibles[i].devolverStock(cantidadSolicitada);
        }
    }

    @Override
    public void modificar(int id, Reserva data) {
        System.out.println("Una reserva confirmada se modifica mediante cancelación y una nueva reserva");
    }

    @Override
    public void eliminar(int id) {
        System.out.println("Las reservas conservan su historial; use cancelar");
    }
}
