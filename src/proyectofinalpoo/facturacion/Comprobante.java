package proyectofinalpoo.facturacion;

import proyectofinalpoo.reservas.Reserva;
import proyectofinalpoo.reservas.ServicioPedido;
import proyectofinalpoo.servicios.PaquetePromocional;

public class Comprobante {
    private static final float TASA_IGV = 0.18f;

    private int numero;
    private Reserva reserva;
    private TipoComprobante tipo;
    private String ruc;
    private String razonSocial;
    private float alquiler;
    private float descuento;
    private float servicios;
    private float paquetes;
    private float cargosIncidencias;
    private float subtotal;
    private float igv;
    private float total;
    private float adelanto;
    private float saldo;
    private String[] conceptos;
    private float[] montosConceptos;
    private int conceptosNro;

    public Comprobante(int numero, Reserva reserva, TipoComprobante tipo, String ruc, String razonSocial) {
        this.numero = numero;
        this.reserva = reserva;
        this.tipo = tipo;
        this.ruc = ruc;
        this.razonSocial = razonSocial;
        ServicioPedido[] pedidos = reserva.getServicios();
        PaquetePromocional[] listaPaquetes = reserva.getPaquetes();
        conceptos = new String[pedidos.length + listaPaquetes.length + 3];
        montosConceptos = new float[conceptos.length];
        calcular();
    }

    private void calcular() {
        for (int i = 0; i < reserva.getDuracionHoras(); i++) {
            alquiler += reserva.getCancha().getTarifa(reserva.getHoraInicio().plusHours(i));
        }
        agregarConcepto("Alquiler de cancha (" + reserva.getDuracionHoras() + " horas)", alquiler);
        descuento = alquiler * reserva.getTitular().getDescuento();
        if (descuento > 0) {
            agregarConcepto("Descuento", -descuento);
        }
        ServicioPedido[] pedidos = reserva.getServicios();
        for (int i = 0; i < pedidos.length; i++) {
            float monto = pedidos[i].getServicio().getPrecioUnitario() * pedidos[i].getCantidad();
            servicios += monto;
            agregarConcepto("Servicio: " + pedidos[i].getServicio().getNombre()
                    + " x " + pedidos[i].getCantidad(), monto);
        }
        PaquetePromocional[] listaPaquetes = reserva.getPaquetes();
        for (int i = 0; i < listaPaquetes.length; i++) {
            paquetes += listaPaquetes[i].getPrecioPromocional();
            agregarConcepto("Paquete: " + listaPaquetes[i].getNombre(),
                    listaPaquetes[i].getPrecioPromocional());
        }
        cargosIncidencias = reserva.getCargosIncidencias();
        if (cargosIncidencias > 0) {
            agregarConcepto("Cargos por incidencias", cargosIncidencias);
        }
        subtotal = alquiler - descuento + servicios + paquetes + cargosIncidencias;
        igv = subtotal * TASA_IGV;
        total = subtotal + igv;
        adelanto = reserva.getAdelanto();
        saldo = total - adelanto;
    }

    private void agregarConcepto(String concepto, float monto) {
        conceptos[conceptosNro] = concepto;
        montosConceptos[conceptosNro] = monto;
        conceptosNro++;
    }

    public String[] getConceptos() {
        String[] copia = new String[conceptosNro];
        for (int i = 0; i < conceptosNro; i++) {
            copia[i] = conceptos[i];
        }
        return copia;
    }

    public float[] getMontosConceptos() {
        float[] copia = new float[conceptosNro];
        for (int i = 0; i < conceptosNro; i++) {
            copia[i] = montosConceptos[i];
        }
        return copia;
    }

    public int getNumero() {
        return numero;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public TipoComprobante getTipo() {
        return tipo;
    }

    public String getRuc() {
        return ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public float getAlquiler() {
        return alquiler;
    }

    public float getDescuento() {
        return descuento;
    }

    public float getServicios() {
        return servicios;
    }

    public float getPaquetes() {
        return paquetes;
    }

    public float getCargosIncidencias() {
        return cargosIncidencias;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public float getIgv() {
        return igv;
    }

    public float getTotal() {
        return total;
    }

    public float getAdelanto() {
        return adelanto;
    }

    public float getSaldo() {
        return saldo;
    }
}
