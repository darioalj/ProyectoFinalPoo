/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.canchas;

import java.time.LocalTime;

/**
 *
 * @author N04520
 */
public abstract class Cancha {
    private int cod;
    private String nombre;
    private int capacidadMaxima;
    private float tarifaBase;
    private EstadoCancha estado;

    public Cancha(int cod, String nombre, int capacidadMaxima, float tarifaBase) {
        this.cod = cod;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.tarifaBase = tarifaBase;
        this.estado = EstadoCancha.EN_MANTENIMIENTO;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public float getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(float tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    
    public float getTarifa() {
        return getTarifa(LocalTime.now());
    }

    public float getTarifa(LocalTime horaReserva) {
        LocalTime horaLimite = LocalTime.of(18, 0);
        float recargo = 0;

        if (!horaReserva.isBefore(horaLimite)) {
            recargo = getRecargo();
        }
        
        return tarifaBase + tarifaBase * recargo;
    }
    
    protected abstract float getRecargo();

    public EstadoCancha getEstado() {
        return estado;
    }

    public void setEstado(EstadoCancha estado) {
        this.estado = estado;
    }

    public void reservar() {
        if (estado == EstadoCancha.EN_MANTENIMIENTO) {
            System.out.println("Las canchas en mantenimiento no se pueden reservar");
            return;
        }
        
        this.estado = EstadoCancha.OCUPADA;
    }
    
    public void disponible() {
        this.estado = EstadoCancha.DISPONIBLE;
    }
}
