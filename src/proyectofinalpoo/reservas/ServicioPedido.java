package proyectofinalpoo.reservas;

import proyectofinalpoo.servicios.ServicioAdicional;

public class ServicioPedido {
    private ServicioAdicional servicio;
    private int cantidad;

    public ServicioPedido(ServicioAdicional servicio, int cantidad) {
        this.servicio = servicio;
        this.cantidad = cantidad;
    }

    public ServicioAdicional getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }
}
