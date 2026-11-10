/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.jugadores;

/**
 *
 * @author N04520
 */
public class JugadorFrecuente extends Jugador {
    
    public JugadorFrecuente(int dni, String nombres, String apellidos, int telefono, String correo) {
        super(dni, nombres, apellidos, telefono, correo);
    }

    @Override
    public float getDescuento() {
        return 0.3f;
    }
}
