package proyectofinalpoo.servicios;

public class PaquetePromocional {
    private int codigo;
    private String nombre;
    private ServicioAdicional[] servicios;
    private int serviciosNro;
    private float precioPromocional;

    public PaquetePromocional(int codigo, String nombre, int capacidadServicios, float precioPromocional) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.servicios = new ServicioAdicional[capacidadServicios];
        this.serviciosNro = 0;
        this.precioPromocional = precioPromocional;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecioPromocional() {
        return precioPromocional;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioPromocional(float precioPromocional) {
        this.precioPromocional = precioPromocional;
    }

    public boolean agregarServicio(ServicioAdicional servicio) {
        if (servicio == null || serviciosNro == servicios.length) {
            return false;
        }
        for (int i = 0; i < serviciosNro; i++) {
            if (servicios[i].getCodigo() == servicio.getCodigo()) {
                return false;
            }
        }
        servicios[serviciosNro] = servicio;
        serviciosNro++;
        return true;
    }

    public void quitarServicio(int codigo) {
        for (int i = 0; i < serviciosNro; i++) {
            if (servicios[i].getCodigo() == codigo) {
                for (int j = i; j < serviciosNro - 1; j++) {
                    servicios[j] = servicios[j + 1];
                }
                serviciosNro--;
                servicios[serviciosNro] = null;
                return;
            }
        }
    }

    public ServicioAdicional[] getServicios() {
        ServicioAdicional[] resultado = new ServicioAdicional[serviciosNro];
        for (int i = 0; i < serviciosNro; i++) {
            resultado[i] = servicios[i];
        }
        return resultado;
    }
}
