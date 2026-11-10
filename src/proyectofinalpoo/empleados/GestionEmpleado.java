/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.empleados;

import proyectofinalpoo.IGestion;

/**
 *
 * @author N04520
 */
public class GestionEmpleado implements IGestion<Empleado> {
    private final AlmacenamientoEmpleado alm;
    
    public GestionEmpleado(AlmacenamientoEmpleado alm) {
        this.alm = alm;
    }
    
    @Override
    public void registrar(Empleado empleado) {
        if (!datosObligatorios(empleado)) {
            System.out.println("Complete los datos obligatorios del empleado");
            return;
        }
        Empleado dniEmpleado = alm.findOne(empleado.getDni());
        Empleado usuarioEmpleado = alm.findOneByUsuario(empleado.getUsuario());
        
        if (dniEmpleado != null) {
            System.out.println("Ya existe un usuario con el dni ingresado");
            return;
        } else if (usuarioEmpleado != null) {
            System.out.println("Ya existe un usuario con el nombre de usuario ingresado");
            return;
        }
        
        alm.create(empleado);
    }
    
    @Override
    public void modificar(int dni, Empleado empleado) {
        if (empleado == null || alm.findOne(dni) == null) {
            System.out.println("No existe un empleado o los datos son incorrectos");
            return;
        }
        if (!datosObligatorios(empleado)) {
            System.out.println("Complete los datos obligatorios del empleado");
            return;
        }
        Empleado empleadoConDni = alm.findOne(empleado.getDni());
        if (empleadoConDni != null && empleadoConDni.getDni() != dni) {
            System.out.println("Ya existe un empleado con el DNI ingresado");
            return;
        }
        Empleado usuarioEmpleado = alm.findOneByUsuario(empleado.getUsuario());
        if (usuarioEmpleado != null && usuarioEmpleado.getDni() != dni) {
            System.out.println("Ya existe un usuario con el nombre de usuario ingresado");
            return;
        }
        
        alm.update(dni, empleado);
    }
    
    @Override
    public void eliminar(int dni) {
        Empleado dniEmpleado = alm.findOne(dni);
        if (dniEmpleado == null) {
            System.out.println("No existe un usuario con el dni ingresado");
            return;
        }
        
        alm.delete(dni);
    }

    private boolean datosObligatorios(Empleado empleado) {
        if (empleado == null || empleado.getDni() <= 0) {
            return false;
        }
        if (empleado.getNombres() == null || empleado.getNombres().trim().isEmpty()) {
            return false;
        }
        if (empleado.getApellidos() == null || empleado.getApellidos().trim().isEmpty()) {
            return false;
        }
        if (empleado.getUsuario() == null || empleado.getUsuario().trim().isEmpty()) {
            return false;
        }
        if (!empleado.tieneContrasena()) {
            return false;
        }
        return empleado.getRol() != null;
    }
}
