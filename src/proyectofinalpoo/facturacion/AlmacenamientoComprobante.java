package proyectofinalpoo.facturacion;

public class AlmacenamientoComprobante {
    private static final int CAPACIDAD = 100;

    private Comprobante[] comprobantes;
    private int cantidad;

    public AlmacenamientoComprobante() {
        comprobantes = new Comprobante[CAPACIDAD];
        cantidad = 0;
    }

    public void create(Comprobante comprobante) {
        if (comprobante == null || cantidad == comprobantes.length) {
            return;
        }
        comprobantes[cantidad++] = comprobante;
    }

    public Comprobante findOne(int numero) {
        for (int i = 0; i < cantidad; i++) {
            if (comprobantes[i].getNumero() == numero) {
                return comprobantes[i];
            }
        }
        return null;
    }

    public Comprobante[] find() {
        Comprobante[] resultado = new Comprobante[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = comprobantes[i];
        }
        return resultado;
    }

    public int siguienteNumero() {
        return cantidad + 1;
    }
}
