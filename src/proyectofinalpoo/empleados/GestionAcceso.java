package proyectofinalpoo.empleados;

public class GestionAcceso {
    private final AlmacenamientoEmpleado almacenamiento;

    public GestionAcceso(AlmacenamientoEmpleado almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public Empleado autenticar(String usuario, String contrasena) {
        Empleado empleado = almacenamiento.findOneByUsuario(usuario);
        if (empleado != null && empleado.validarContrasena(contrasena)) {
            return empleado;
        }
        return null;
    }

    public boolean tienePermiso(Empleado empleado, String funcion) {
        if (empleado == null || funcion == null) {
            return false;
        }
        RolEmpleado rol = empleado.getRol();
        if (rol == RolEmpleado.ADMINISTRADOR) {
            return true;
        }
        if (rol == RolEmpleado.RECEPCIONISTA) {
            return funcion.equals("JUGADORES") || funcion.equals("RESERVAS");
        }
        if (rol == RolEmpleado.ENCARGADO) {
            return funcion.equals("INICIO_PARTIDO") || funcion.equals("CIERRE_PARTIDO")
                    || funcion.equals("INCIDENCIAS");
        }
        return false;
    }
}
