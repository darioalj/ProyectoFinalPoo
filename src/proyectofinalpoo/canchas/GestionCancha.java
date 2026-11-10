/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.canchas;

import proyectofinalpoo.IGestion;
import proyectofinalpoo.IAlmacenamiento;

/**
 *
 * @author N04520
 */
public class GestionCancha implements IGestion<Cancha> {
    private final IAlmacenamiento<Cancha> alm;

    public GestionCancha(IAlmacenamiento<Cancha> alm) {
        this.alm = alm;
    }

    @Override
    public void registrar(Cancha data) {
        if (data == null) {
            return;
        }
        if (alm.findOne(data.getCod()) != null) {
            System.out.println("Ya existe una cancha con el código ingresado");
            return;
        }
        alm.create(data);
    }

    @Override
    public void modificar(int id, Cancha data) {
        if (data == null || alm.findOne(id) == null) {
            return;
        }
        Cancha canchaConCodigo = alm.findOne(data.getCod());
        if (canchaConCodigo != null && data.getCod() != id) {
            System.out.println("Ya existe una cancha con el código ingresado");
            return;
        }
        alm.update(id, data);
    }

    @Override
    public void eliminar(int id) {
        alm.delete(id);
    }
}
