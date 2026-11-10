package proyectofinalpoo.canchas;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoCancha implements IAlmacenamiento<Cancha> {
    private static final int CAPACIDAD = 100;

    private Cancha[] canchas;
    private int canchasNro;

    public AlmacenamientoCancha() {
        this.canchas = new Cancha[CAPACIDAD];
        this.canchasNro = 0;
    }

    @Override
    public void create(Cancha cancha) {
        if (cancha == null || canchasNro == canchas.length) {
            return;
        }
        canchas[canchasNro++] = cancha;
    }

    @Override
    public Cancha findOne(int id) {
        for (int i = 0; i < canchasNro; i++) {
            if (canchas[i].getCod() == id) {
                return canchas[i];
            }
        }
        return null;
    }

    @Override
    public Cancha[] find() {
        Cancha[] resultado = new Cancha[canchasNro];
        for (int i = 0; i < canchasNro; i++) {
            resultado[i] = canchas[i];
        }
        return resultado;
    }

    @Override
    public void update(int id, Cancha cancha) {
        if (cancha == null) {
            return;
        }
        for (int i = 0; i < canchasNro; i++) {
            if (canchas[i].getCod() == id) {
                canchas[i] = cancha;
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < canchasNro; i++) {
            if (canchas[i].getCod() == id) {
                for (int j = i; j < canchasNro - 1; j++) {
                    canchas[j] = canchas[j + 1];
                }
                canchasNro--;
                canchas[canchasNro] = null;
                return;
            }
        }
    }
}
