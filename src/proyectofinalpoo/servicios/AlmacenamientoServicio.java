package proyectofinalpoo.servicios;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoServicio implements IAlmacenamiento<ServicioAdicional> {
    private static final int CAPACIDAD = 100;

    private ServicioAdicional[] servicios;
    private int cantidad;

    public AlmacenamientoServicio() {
        servicios = new ServicioAdicional[CAPACIDAD];
        cantidad = 0;
    }

    @Override
    public void create(ServicioAdicional servicio) {
        if (servicio == null || cantidad == servicios.length
                || findOne(servicio.getCodigo()) != null) {
            return;
        }
        servicios[cantidad++] = servicio;
    }

    @Override
    public ServicioAdicional findOne(int codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (servicios[i].getCodigo() == codigo) {
                return servicios[i];
            }
        }
        return null;
    }

    @Override
    public ServicioAdicional[] find() {
        ServicioAdicional[] resultado = new ServicioAdicional[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = servicios[i];
        }
        return resultado;
    }

    @Override
    public void update(int codigo, ServicioAdicional servicio) {
        if (servicio == null) {
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            if (servicios[i].getCodigo() == codigo) {
                servicios[i] = servicio;
                return;
            }
        }
    }

    @Override
    public void delete(int codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (servicios[i].getCodigo() == codigo) {
                for (int j = i; j < cantidad - 1; j++) {
                    servicios[j] = servicios[j + 1];
                }
                cantidad--;
                servicios[cantidad] = null;
                return;
            }
        }
    }
}
