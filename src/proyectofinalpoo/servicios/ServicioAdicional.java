package proyectofinalpoo.servicios;

public class ServicioAdicional {
    private int codigo;
    private String nombre;
    private float precioUnitario;
    private int stockDisponible;

    public ServicioAdicional(int codigo, String nombre, float precioUnitario, int stockDisponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.stockDisponible = stockDisponible;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public boolean tieneStock(int cantidad) {
        if (cantidad <= 0) {
            return false;
        }
        return stockDisponible >= cantidad;
    }

    public void descontarStock(int cantidad) {
        if (tieneStock(cantidad)) {
            stockDisponible -= cantidad;
        }
    }

    public void devolverStock(int cantidad) {
        if (cantidad > 0) {
            stockDisponible += cantidad;
        }
    }
}
