/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.canchas;

/**
 *
 * @author N04520
 */
public class CanchaFutbol7 extends Cancha {

    public CanchaFutbol7(int cod, String nombre, int capacidadMaxima, float tarifaBase) {
        super(cod, nombre, capacidadMaxima, tarifaBase);
    }

    @Override
    protected float getRecargo() {
        return 0.2f;
    }
}
