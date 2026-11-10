package proyectofinalpoo.facturacion;

import proyectofinalpoo.reservas.EstadoReserva;
import proyectofinalpoo.reservas.Reserva;

public class GestionFacturacion {
    private final AlmacenamientoComprobante almacenamiento;

    public GestionFacturacion(AlmacenamientoComprobante almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public void emitir(Reserva reserva, TipoComprobante tipo, String ruc, String razonSocial) {
        if (reserva == null || reserva.getEstado() != EstadoReserva.FINALIZADA) {
            System.out.println("Solo se puede facturar una reserva finalizada");
            return;
        }
        if (tipo == TipoComprobante.FACTURA && (ruc == null || razonSocial == null)) {
            System.out.println("La factura requiere RUC y razón social");
            return;
        }
        Comprobante comprobante = new Comprobante(almacenamiento.siguienteNumero(), reserva, tipo, ruc, razonSocial);
        almacenamiento.create(comprobante);
    }
}
