package proyectofinalpoo.empleados;

import proyectofinalpoo.IAlmacenamiento;

public class AlmacenamientoEmpleado implements IAlmacenamiento<Empleado> {
    private static final int CAPACIDAD = 100;

    private Empleado[] empleados;
    private int empleadosNro;

    public AlmacenamientoEmpleado() {
        this.empleados = new Empleado[CAPACIDAD];
        this.empleadosNro = 0;
    }

    @Override
    public void create(Empleado empleado) {
        if (empleado == null || empleadosNro == empleados.length) {
            return;
        }
        empleados[empleadosNro++] = empleado;
    }

    @Override
    public Empleado findOne(int dni) {
        for (int i = 0; i < empleadosNro; i++) {
            if (empleados[i].getDni() == dni) {
                return empleados[i];
            }
        }
        return null;
    }

    public Empleado findOneByUsuario(String usuario) {
        for (int i = 0; i < empleadosNro; i++) {
            String usuarioEmpleado = empleados[i].getUsuario();
            if (usuarioEmpleado == null && usuario == null) {
                return empleados[i];
            }
            if (usuarioEmpleado != null && usuarioEmpleado.equals(usuario)) {
                return empleados[i];
            }
        }
        return null;
    }

    @Override
    public Empleado[] find() {
        Empleado[] resultado = new Empleado[empleadosNro];
        for (int i = 0; i < empleadosNro; i++) {
            resultado[i] = empleados[i];
        }
        return resultado;
    }

    @Override
    public void update(int dni, Empleado empleado) {
        if (empleado == null) {
            return;
        }
        for (int i = 0; i < empleadosNro; i++) {
            if (empleados[i].getDni() == dni) {
                empleados[i] = empleado;
                return;
            }
        }
    }

    @Override
    public void delete(int dni) {
        for (int i = 0; i < empleadosNro; i++) {
            if (empleados[i].getDni() == dni) {
                for (int j = i; j < empleadosNro - 1; j++) {
                    empleados[j] = empleados[j + 1];
                }
                empleadosNro--;
                empleados[empleadosNro] = null;
                return;
            }
        }
    }
}
