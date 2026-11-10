package proyectofinalpoo.servicios;

import proyectofinalpoo.IGestion;

public class GestionServicio implements IGestion<ServicioAdicional> {
    private final AlmacenamientoServicio almacenamiento;

    public GestionServicio(AlmacenamientoServicio almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    @Override
    public void registrar(ServicioAdicional servicio) {
        almacenamiento.create(servicio);
    }

    @Override
    public void modificar(int codigo, ServicioAdicional servicio) {
        almacenamiento.update(codigo, servicio);
    }

    @Override
    public void eliminar(int codigo) {
        almacenamiento.delete(codigo);
    }
}
