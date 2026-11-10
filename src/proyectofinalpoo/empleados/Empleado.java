/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalpoo.empleados;

/**
 *
 * @author N04520
 */
public class Empleado {
    private int dni;
    private String nombres;
    private String apellidos;
    private String usuario;
    private String contraseña;
    private RolEmpleado rol;

    public Empleado(int dni, String nombres, String apellidos, String usuario, String contraseña, RolEmpleado rol) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public boolean validarContrasena(String contrasenaIngresada) {
        return contraseña.equals(contrasenaIngresada);
    }

    public boolean tieneContrasena() {
        return contraseña != null && !contraseña.trim().isEmpty();
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }
}
