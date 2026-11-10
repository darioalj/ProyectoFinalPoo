package proyectofinalpoo.servicios;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoPaquete implements IAlmacenamiento<PaquetePromocional> {
    private static final int CAPACIDAD = 100;

    private PaquetePromocional[] paquetes;
    private int cantidad;

    public AlmacenamientoPaquete() {
        paquetes = new PaquetePromocional[CAPACIDAD];
        cantidad = 0;
    }

    @Override
    public void create(PaquetePromocional paquete) {
        if (paquete == null || cantidad == paquetes.length
                || findOne(paquete.getCodigo()) != null) {
            return;
        }
        paquetes[cantidad++] = paquete;
    }

    @Override
    public PaquetePromocional findOne(int codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (paquetes[i].getCodigo() == codigo) {
                return paquetes[i];
            }
        }
        return null;
    }

    @Override
    public PaquetePromocional[] find() {
        PaquetePromocional[] resultado = new PaquetePromocional[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = paquetes[i];
        }
        return resultado;
    }

    @Override
    public void update(int codigo, PaquetePromocional paquete) {
        if (paquete == null) {
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            if (paquetes[i].getCodigo() == codigo) {
                paquetes[i] = paquete;
                return;
            }
        }
    }

    @Override
    public void delete(int codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (paquetes[i].getCodigo() == codigo) {
                for (int j = i; j < cantidad - 1; j++) {
                    paquetes[j] = paquetes[j + 1];
                }
                cantidad--;
                paquetes[cantidad] = null;
                return;
            }
        }
    }
}
