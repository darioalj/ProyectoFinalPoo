/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.jugadores;

/**
 *
 * @author N04520
 */
public abstract class Jugador {
    private int dni;
    private String nombres;
    private String apellidos;
    private int telefono;
    private String correo;
    private int reservasFinalizadas;

    public Jugador(int dni, String nombres, String apellidos, int telefono, String correo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.reservasFinalizadas = 0;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getReservasFinalizadas() {
        return reservasFinalizadas;
    }

    void setReservasFinalizadas(int reservasFinalizadas) {
        this.reservasFinalizadas = reservasFinalizadas;
    }

    public void registrarReservaFinalizada() {
        reservasFinalizadas++;
    }
    
    public abstract float getDescuento();
}
