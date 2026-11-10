/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.canchas;

/**
 *
 * @author N04520
 */
public class CanchaFutbol5 extends Cancha {

    public CanchaFutbol5(int cod, String nombre, int capacidadMaxima, float tarifaBase) {
        super(cod, nombre, capacidadMaxima, tarifaBase);
    }

    @Override
    protected float getRecargo() {
        return 0.1f;
    }
}
