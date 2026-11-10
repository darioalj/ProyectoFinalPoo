package proyectofinalpoo.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import proyectofinalpoo.canchas.Cancha;
import proyectofinalpoo.jugadores.Jugador;
import proyectofinalpoo.empleados.Empleado;
import proyectofinalpoo.servicios.PaquetePromocional;

public class Reserva {
    private int numero;
    private Jugador titular;
    private Cancha cancha;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracionHoras;
    private Jugador[] participantes;
    private int participantesNro;
    private Jugador[] ingresantes;
    private int ingresantesNro;
    private ServicioPedido[] servicios;
    private int serviciosNro;
    private PaquetePromocional[] paquetes;
    private int paquetesNro;
    private EstadoReserva estado;
    private String[] historial;
    private int historialNro;
    private float adelanto;
    private float adelantoDevuelto;
    private float cargosIncidencias;
    private Empleado recepcionista;
    private Empleado encargado;

    public Reserva(Jugador titular, Cancha cancha, LocalDate fecha,
            LocalTime horaInicio, int duracionHoras, int capacidadParticipantes,
            int capacidadServicios, int capacidadPaquetes) {
        this.numero = 0;
        this.titular = titular;
        this.cancha = cancha;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionHoras = duracionHoras;
        this.participantes = new Jugador[capacidadParticipantes];
        this.ingresantes = new Jugador[capacidadParticipantes];
        this.servicios = new ServicioPedido[capacidadServicios];
        this.paquetes = new PaquetePromocional[capacidadPaquetes];
        this.historial = new String[10];
        this.estado = EstadoReserva.PENDIENTE;
        this.historialNro = 0;
        this.adelanto = 0;
        this.adelantoDevuelto = 0;
        this.cargosIncidencias = 0;
        if (titular != null && participantesNro < participantes.length) {
            participantes[participantesNro] = titular;
            participantesNro++;
        }
        registrarCambio("Reserva creada: PENDIENTE");
    }

    public int getNumero() {
        return numero;
    }

    void setNumero(int numero) {
        this.numero = numero;
    }

    public Jugador getTitular() {
        return titular;
    }

    public void actualizarTitular(Jugador titular) {
        this.titular = titular;
        for (int i = 0; i < participantesNro; i++) {
            if (participantes[i].getDni() == titular.getDni()) {
                participantes[i] = titular;
            }
        }
    }

    public Cancha getCancha() {
        return cancha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaInicio.plusHours(duracionHoras);
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public float getAdelantoDevuelto() {
        return adelantoDevuelto;
    }

    public float getCargosIncidencias() {
        return cargosIncidencias;
    }

    public Empleado getRecepcionista() {
        return recepcionista;
    }

    public Empleado getEncargado() {
        return encargado;
    }

    public int getParticipantesNro() {
        return participantesNro;
    }

    public Jugador[] getParticipantes() {
        return copiarParticipantes();
    }

    public Jugador[] getIngresantes() {
        Jugador[] copia = new Jugador[ingresantesNro];
        for (int i = 0; i < ingresantesNro; i++) {
            copia[i] = ingresantes[i];
        }
        return copia;
    }

    public boolean esParticipante(int dni) {
        for (int i = 0; i < participantesNro; i++) {
            if (participantes[i].getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public boolean registrarIngreso(int dni) {
        if (!esParticipante(dni)) {
            return false;
        }
        for (int i = 0; i < ingresantesNro; i++) {
            if (ingresantes[i].getDni() == dni) {
                return true;
            }
        }
        ingresantes[ingresantesNro] = buscarParticipante(dni);
        ingresantesNro++;
        return true;
    }

    private Jugador buscarParticipante(int dni) {
        for (int i = 0; i < participantesNro; i++) {
            if (participantes[i].getDni() == dni) {
                return participantes[i];
            }
        }
        return null;
    }

    public ServicioPedido[] getServicios() {
        return copiarServicios();
    }

    public PaquetePromocional[] getPaquetes() {
        return copiarPaquetes();
    }

    public String[] getHistorial() {
        return copiarHistorial();
    }

    public boolean agregarParticipante(Jugador jugador) {
        if (jugador == null || participantesNro == participantes.length) {
            return false;
        }
        participantes[participantesNro++] = jugador;
        return true;
    }

    public boolean agregarServicio(ServicioPedido pedido) {
        if (pedido == null || serviciosNro == servicios.length) {
            return false;
        }
        servicios[serviciosNro++] = pedido;
        return true;
    }

    public boolean agregarPaquete(PaquetePromocional paquete) {
        if (paquete == null || paquetesNro == paquetes.length) {
            return false;
        }
        paquetes[paquetesNro++] = paquete;
        return true;
    }

    public void registrarAdelanto(float monto) {
        if (estado == EstadoReserva.PENDIENTE && monto > 0) adelanto += monto;
    }

    public void devolverAdelanto() {
        adelantoDevuelto = adelanto;
    }

    public void agregarCargoIncidencia(float monto) {
        if (monto > 0 && estado == EstadoReserva.EN_JUEGO) cargosIncidencias += monto;
    }

    public void setRecepcionista(Empleado recepcionista) {
        this.recepcionista = recepcionista;
    }

    public void setEncargado(Empleado encargado) {
        this.encargado = encargado;
    }

    public void cambiarEstado(EstadoReserva nuevoEstado, String detalle) {
        if (estado == EstadoReserva.FINALIZADA || estado == EstadoReserva.CANCELADA || nuevoEstado == null) {
            return;
        }
        estado = nuevoEstado;
        registrarCambio(detalle);
    }

    private void registrarCambio(String detalle) {
        if (historialNro == historial.length) {
            String[] ampliado = new String[historial.length * 2];
            for (int i = 0; i < historialNro; i++) ampliado[i] = historial[i];
            historial = ampliado;
        }
        historial[historialNro++] = detalle;
    }

    private Jugador[] copiarParticipantes() {
        Jugador[] copia = new Jugador[participantesNro];
        for (int i = 0; i < participantesNro; i++) copia[i] = participantes[i];
        return copia;
    }

    private ServicioPedido[] copiarServicios() {
        ServicioPedido[] copia = new ServicioPedido[serviciosNro];
        for (int i = 0; i < serviciosNro; i++) copia[i] = servicios[i];
        return copia;
    }

    private PaquetePromocional[] copiarPaquetes() {
        PaquetePromocional[] copia = new PaquetePromocional[paquetesNro];
        for (int i = 0; i < paquetesNro; i++) copia[i] = paquetes[i];
        return copia;
    }

    private String[] copiarHistorial() {
        String[] copia = new String[historialNro];
        for (int i = 0; i < historialNro; i++) copia[i] = historial[i];
        return copia;
    }
}
