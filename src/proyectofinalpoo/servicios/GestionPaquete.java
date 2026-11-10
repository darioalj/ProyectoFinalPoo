package proyectofinalpoo.servicios;

import proyectofinalpoo.IGestion;

public class GestionPaquete implements IGestion<PaquetePromocional> {
    private final AlmacenamientoPaquete almacenamiento;

    public GestionPaquete(AlmacenamientoPaquete almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    @Override
    public void registrar(PaquetePromocional paquete) {
        almacenamiento.create(paquete);
    }

    @Override
    public void modificar(int codigo, PaquetePromocional paquete) {
        almacenamiento.update(codigo, paquete);
    }

    @Override
    public void eliminar(int codigo) {
        almacenamiento.delete(codigo);
    }
}
