package proyectofinalpoo.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import proyectofinalpoo.canchas.AlmacenamientoCancha;
import proyectofinalpoo.canchas.Cancha;
import proyectofinalpoo.canchas.CanchaFutbol11;
import proyectofinalpoo.canchas.CanchaFutbol5;
import proyectofinalpoo.canchas.CanchaFutbol7;
import proyectofinalpoo.canchas.EstadoCancha;
import proyectofinalpoo.canchas.GestionCancha;
import proyectofinalpoo.empleados.AlmacenamientoEmpleado;
import proyectofinalpoo.empleados.Empleado;
import proyectofinalpoo.empleados.GestionAcceso;
import proyectofinalpoo.empleados.GestionEmpleado;
import proyectofinalpoo.empleados.RolEmpleado;
import proyectofinalpoo.facturacion.Comprobante;
import proyectofinalpoo.facturacion.TipoComprobante;
import proyectofinalpoo.jugadores.AlmacenamientoJugador;
import proyectofinalpoo.jugadores.GestionJugador;
import proyectofinalpoo.jugadores.Jugador;
import proyectofinalpoo.jugadores.JugadorRegular;
import proyectofinalpoo.reportes.GestionReportes;
import proyectofinalpoo.reservas.AlmacenamientoReserva;
import proyectofinalpoo.reservas.EstadoReserva;
import proyectofinalpoo.reservas.GestionReserva;
import proyectofinalpoo.reservas.Reserva;
import proyectofinalpoo.reservas.ServicioPedido;
import proyectofinalpoo.servicios.AlmacenamientoPaquete;
import proyectofinalpoo.servicios.AlmacenamientoServicio;
import proyectofinalpoo.servicios.GestionPaquete;
import proyectofinalpoo.servicios.GestionServicio;
import proyectofinalpoo.servicios.PaquetePromocional;
import proyectofinalpoo.servicios.ServicioAdicional;

public class VentanaPrincipal extends JFrame {

    private final AlmacenamientoEmpleado empleados;
    private final AlmacenamientoCancha canchas;
    private final AlmacenamientoJugador jugadores;
    private final AlmacenamientoServicio servicios;
    private final AlmacenamientoPaquete paquetes;
    private final AlmacenamientoReserva reservas;
    private final GestionEmpleado gestionEmpleado;
    private final GestionCancha gestionCancha;
    private final GestionJugador gestionJugador;
    private final GestionServicio gestionServicio;
    private final GestionPaquete gestionPaquete;
    private final GestionReserva gestionReserva;
    private final GestionReportes gestionReportes;
    private final GestionAcceso gestionAcceso;

    private Empleado empleadoActual;
    private JComboBox<Integer> selectorServiciosPaquete;

    public VentanaPrincipal(AlmacenamientoEmpleado empleados, AlmacenamientoCancha canchas,
            AlmacenamientoJugador jugadores, AlmacenamientoServicio servicios,
            AlmacenamientoPaquete paquetes, AlmacenamientoReserva reservas,
            GestionEmpleado gestionEmpleado, GestionCancha gestionCancha,
            GestionJugador gestionJugador, GestionServicio gestionServicio,
            GestionPaquete gestionPaquete, GestionReserva gestionReserva,
            GestionReportes gestionReportes, GestionAcceso gestionAcceso) {
        this.empleados = empleados;
        this.canchas = canchas;
        this.jugadores = jugadores;
        this.servicios = servicios;
        this.paquetes = paquetes;
        this.reservas = reservas;
        this.gestionEmpleado = gestionEmpleado;
        this.gestionCancha = gestionCancha;
        this.gestionJugador = gestionJugador;
        this.gestionServicio = gestionServicio;
        this.gestionPaquete = gestionPaquete;
        this.gestionReserva = gestionReserva;
        this.gestionReportes = gestionReportes;
        this.gestionAcceso = gestionAcceso;

        setTitle("Gestión del Complejo Deportivo");
        setSize(1050, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mostrarAcceso();
    }

    private void mostrarAcceso() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField usuario = new JTextField();
        JPasswordField contrasena = new JPasswordField();
        JButton ingresar = new JButton("Ingresar");
        JButton crearAdministrador = new JButton("Crear administrador inicial");

        panel.add(new JLabel("Usuario"));
        panel.add(usuario);
        panel.add(new JLabel("Contraseña"));
        panel.add(contrasena);
        panel.add(ingresar);
        panel.add(crearAdministrador);

        crearAdministrador.setEnabled(empleados.find().length == 0);
        crearAdministrador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                crearAdministradorInicial();
            }
        });
        ingresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                String clave = new String(contrasena.getPassword());
                empleadoActual = gestionAcceso.autenticar(usuario.getText().trim(), clave);
                if (empleadoActual == null) {
                    mostrarMensaje("Usuario o contraseña incorrectos");
                    return;
                }
                mostrarMenu();
            }
        });

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void crearAdministradorInicial() {
        JTextField dni = new JTextField();
        JTextField nombres = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField usuario = new JTextField();
        JPasswordField contrasena = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("DNI"));
        panel.add(dni);
        panel.add(new JLabel("Nombres"));
        panel.add(nombres);
        panel.add(new JLabel("Apellidos"));
        panel.add(apellidos);
        panel.add(new JLabel("Usuario"));
        panel.add(usuario);
        panel.add(new JLabel("Contraseña"));
        panel.add(contrasena);

        int opcion = JOptionPane.showConfirmDialog(this, panel,
                "Crear primer administrador", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }
        int numeroDni = leerEntero(dni.getText());
        if (numeroDni < 0) {
            mostrarMensaje("Ingrese un DNI válido");
            return;
        }
        Empleado administrador = new Empleado(numeroDni, nombres.getText().trim(),
                apellidos.getText().trim(), usuario.getText().trim(),
                new String(contrasena.getPassword()), RolEmpleado.ADMINISTRADOR);
        gestionEmpleado.registrar(administrador);
        if (empleados.find().length > 0) {
            mostrarMensaje("Administrador registrado. Ya puede ingresar.");
        } else {
            mostrarMensaje("No se registró. Complete correctamente los datos del administrador.");
        }
    }

    private void mostrarMenu() {
        JTabbedPane pestanas = new JTabbedPane();
        if (puede("EMPLEADOS")) {
            pestanas.addTab("Empleados", panelEmpleados());
        }
        if (puede("CANCHAS")) {
            pestanas.addTab("Canchas", panelCanchas());
        }
        if (puede("JUGADORES")) {
            pestanas.addTab("Jugadores", panelJugadores());
        }
        if (puede("SERVICIOS")) {
            pestanas.addTab("Servicios y paquetes", panelServicios());
        }
        if (puede("RESERVAS") || puede("INICIO_PARTIDO") || puede("CIERRE_PARTIDO")) {
            pestanas.addTab("Reservas", panelReservas());
        }
        pestanas.addTab("Agenda diaria", panelAgenda());
        if (puede("REPORTES")) {
            pestanas.addTab("Reportes", panelReportes());
        }

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.add(new JLabel("Sesión: " + empleadoActual.getNombres()
                + " " + empleadoActual.getApellidos() + " - " + empleadoActual.getRol()),
                BorderLayout.NORTH);
        contenido.add(pestanas, BorderLayout.CENTER);
        JButton cerrarSesion = new JButton("Cerrar sesión");
        cerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                empleadoActual = null;
                mostrarAcceso();
            }
        });
        contenido.add(cerrarSesion, BorderLayout.SOUTH);
        setContentPane(contenido);
        revalidate();
        repaint();
    }

    private boolean puede(String funcion) {
        return gestionAcceso.tienePermiso(empleadoActual, funcion);
    }

    private JPanel crearPanelTabla(JPanel formulario, JTable tabla, JPanel acciones) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(formulario, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private JTable crearTabla(String... columnas) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        return new JTable(modelo);
    }

    private DefaultTableModel modelo(JTable tabla) {
        return (DefaultTableModel) tabla.getModel();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private int leerEntero(String valor) {
        String texto = valor.trim();
        if (texto.isEmpty()) {
            return -1;
        }
        int resultado = 0;
        for (int i = 0; i < texto.length(); i++) {
            char digito = texto.charAt(i);
            if (digito < '0' || digito > '9') {
                return -1;
            }
            resultado = resultado * 10 + (digito - '0');
        }
        return resultado;
    }

    private float leerDecimal(String valor) {
        String texto = valor.trim();
        int punto = texto.indexOf('.');
        if (punto == -1) {
            punto = texto.indexOf(',');
        }
        if (punto == 0 || punto == texto.length() - 1) {
            return -1;
        }
        String parteEntera = texto;
        String parteDecimal = "";
        if (punto >= 0) {
            parteEntera = texto.substring(0, punto);
            parteDecimal = texto.substring(punto + 1);
        }
        int entero = leerEntero(parteEntera);
        int decimal = 0;
        if (punto >= 0) {
            decimal = leerEntero(parteDecimal);
        }
        if (entero < 0 || (punto >= 0 && decimal < 0)) {
            return -1;
        }
        float divisor = 1;
        for (int i = 0; i < parteDecimal.length(); i++) {
            divisor *= 10;
        }
        return entero + decimal / divisor;
    }

    private int idSeleccionado(JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return -1;
        }
        return leerEntero(tabla.getValueAt(fila, 0).toString());
    }

    private JPanel panelEmpleados() {
        JTextField dni = new JTextField();
        JTextField nombres = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField usuario = new JTextField();
        JPasswordField contrasena = new JPasswordField();
        JComboBox<RolEmpleado> rol = new JComboBox<>(RolEmpleado.values());
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        formulario.add(new JLabel("DNI"));
        formulario.add(dni);
        formulario.add(new JLabel("Nombres"));
        formulario.add(nombres);
        formulario.add(new JLabel("Apellidos"));
        formulario.add(apellidos);
        formulario.add(new JLabel("Usuario"));
        formulario.add(usuario);
        formulario.add(new JLabel("Contraseña"));
        formulario.add(contrasena);
        formulario.add(new JLabel("Rol"));
        formulario.add(rol);

        JTable tabla = crearTabla("DNI", "Nombres", "Apellidos", "Usuario", "Rol");
        JPanel acciones = new JPanel();
        JButton registrar = new JButton("Registrar");
        JButton modificar = new JButton("Modificar seleccionado");
        JButton eliminar = new JButton("Eliminar seleccionado");
        acciones.add(registrar);
        acciones.add(modificar);
        acciones.add(eliminar);

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                Empleado empleado = new Empleado(leerEntero(dni.getText()), nombres.getText().trim(),
                apellidos.getText().trim(), usuario.getText().trim(),
                new String(contrasena.getPassword()), (RolEmpleado) rol.getSelectedItem());
                int cantidadAnterior = empleados.find().length;
                gestionEmpleado.registrar(empleado);
                actualizarEmpleados(tabla);
                if (empleados.find().length > cantidadAnterior) {
                    mostrarMensaje("Empleado registrado");
                } else {
                    mostrarMensaje("No se registró. Revise los datos y que DNI/usuario no estén repetidos.");
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int dniAnterior = idSeleccionado(tabla);
                if (dniAnterior < 0) {
                    mostrarMensaje("Seleccione un empleado de la tabla");
                    return;
                }
                Empleado empleado = new Empleado(leerEntero(dni.getText()), nombres.getText().trim(),
                apellidos.getText().trim(), usuario.getText().trim(),
                new String(contrasena.getPassword()), (RolEmpleado) rol.getSelectedItem());
                gestionEmpleado.modificar(dniAnterior, empleado);
                actualizarEmpleados(tabla);
                if (empleados.findOne(empleado.getDni()) == empleado) {
                    mostrarMensaje("Empleado modificado");
                } else {
                    mostrarMensaje("No se modificó. Revise DNI, usuario y campos obligatorios.");
                }
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int dniEliminar = idSeleccionado(tabla);
                if (dniEliminar < 0) {
                    mostrarMensaje("Seleccione un empleado de la tabla");
                    return;
                }
                gestionEmpleado.eliminar(dniEliminar);
                actualizarEmpleados(tabla);
            }
        });

        actualizarEmpleados(tabla);
        return crearPanelTabla(formulario, tabla, acciones);
    }

    private void actualizarEmpleados(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        Empleado[] lista = empleados.find();
        for (int i = 0; i < lista.length; i++) {
            modelo.addRow(new Object[]{lista[i].getDni(), lista[i].getNombres(),
                lista[i].getApellidos(), lista[i].getUsuario(), lista[i].getRol()});
        }
    }

    private JPanel panelJugadores() {
        JTextField dni = new JTextField();
        JTextField nombres = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField telefono = new JTextField();
        JTextField correo = new JTextField();
        JPanel formulario = new JPanel(new GridLayout(5, 2, 5, 5));
        formulario.add(new JLabel("DNI"));
        formulario.add(dni);
        formulario.add(new JLabel("Nombres"));
        formulario.add(nombres);
        formulario.add(new JLabel("Apellidos"));
        formulario.add(apellidos);
        formulario.add(new JLabel("Teléfono"));
        formulario.add(telefono);
        formulario.add(new JLabel("Correo"));
        formulario.add(correo);

        JTable tabla = crearTabla("DNI", "Nombres", "Apellidos", "Teléfono", "Correo", "Tipo", "Reservas finalizadas");
        JPanel acciones = new JPanel();
        JButton registrar = new JButton("Registrar");
        JButton modificar = new JButton("Modificar seleccionado");
        JButton eliminar = new JButton("Eliminar seleccionado");
        acciones.add(registrar);
        acciones.add(modificar);
        acciones.add(eliminar);

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                Jugador jugador = crearJugador(dni, nombres, apellidos, telefono, correo);
                int cantidadAnterior = jugadores.find().length;
                gestionJugador.registrar(jugador);
                actualizarJugadores(tabla);
                if (jugadores.find().length > cantidadAnterior) {
                    mostrarMensaje("Jugador registrado");
                } else {
                    mostrarMensaje("No se registró. Revise los datos obligatorios y el DNI.");
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int dniAnterior = idSeleccionado(tabla);
                if (dniAnterior < 0) {
                    mostrarMensaje("Seleccione un jugador de la tabla");
                    return;
                }
                Jugador jugador = crearJugador(dni, nombres, apellidos, telefono, correo);
                gestionJugador.modificar(dniAnterior, jugador);
                actualizarJugadores(tabla);
                mostrarMensaje("Se procesó la modificación del jugador");
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int dniEliminar = idSeleccionado(tabla);
                if (dniEliminar < 0) {
                    mostrarMensaje("Seleccione un jugador de la tabla");
                    return;
                }
                gestionJugador.eliminar(dniEliminar);
                actualizarJugadores(tabla);
            }
        });

        actualizarJugadores(tabla);
        return crearPanelTabla(formulario, tabla, acciones);
    }

    private Jugador crearJugador(JTextField dni, JTextField nombres, JTextField apellidos,
            JTextField telefono, JTextField correo) {
        return new JugadorRegular(leerEntero(dni.getText()), nombres.getText().trim(),
                apellidos.getText().trim(), leerEntero(telefono.getText()), correo.getText().trim());
    }

    private void actualizarJugadores(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        Jugador[] lista = jugadores.find();
        for (int i = 0; i < lista.length; i++) {
            String tipo = "Regular";
            if (lista[i] instanceof proyectofinalpoo.jugadores.JugadorFrecuente) {
                tipo = "Frecuente";
            }
            modelo.addRow(new Object[]{lista[i].getDni(), lista[i].getNombres(),
                lista[i].getApellidos(), lista[i].getTelefono(), lista[i].getCorreo(),
                tipo, lista[i].getReservasFinalizadas()});
        }
    }

    private JPanel panelCanchas() {
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Fútbol 5", "Fútbol 7", "Fútbol 11"});
        JTextField capacidad = new JTextField();
        JTextField tarifa = new JTextField();
        JComboBox<EstadoCancha> estado = new JComboBox<>(EstadoCancha.values());
        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        formulario.add(new JLabel("Código"));
        formulario.add(codigo);
        formulario.add(new JLabel("Nombre"));
        formulario.add(nombre);
        formulario.add(new JLabel("Tipo"));
        formulario.add(tipo);
        formulario.add(new JLabel("Capacidad máxima"));
        formulario.add(capacidad);
        formulario.add(new JLabel("Tarifa base por hora"));
        formulario.add(tarifa);
        formulario.add(new JLabel("Estado"));
        formulario.add(estado);

        JTable tabla = crearTabla("Código", "Nombre", "Tipo", "Capacidad", "Tarifa", "Estado");
        JPanel acciones = new JPanel();
        JButton registrar = new JButton("Registrar");
        JButton modificar = new JButton("Modificar seleccionado");
        JButton eliminar = new JButton("Eliminar seleccionado");
        acciones.add(registrar);
        acciones.add(modificar);
        acciones.add(eliminar);

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                Cancha cancha = crearCancha(codigo, nombre, tipo, capacidad, tarifa, estado);
                int cantidadAnterior = canchas.find().length;
                gestionCancha.registrar(cancha);
                actualizarCanchas(tabla);
                if (canchas.find().length > cantidadAnterior) {
                    mostrarMensaje("Cancha registrada");
                } else {
                    mostrarMensaje("No se registró. Revise el código y los datos de la cancha.");
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoAnterior = idSeleccionado(tabla);
                if (codigoAnterior < 0) {
                    mostrarMensaje("Seleccione una cancha de la tabla");
                    return;
                }
                Cancha cancha = crearCancha(codigo, nombre, tipo, capacidad, tarifa, estado);
                if (cancha == null) {
                    return;
                }
                gestionCancha.modificar(codigoAnterior, cancha);
                actualizarCanchas(tabla);
                mostrarMensaje("Se procesó la modificación de la cancha");
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoEliminar = idSeleccionado(tabla);
                if (codigoEliminar < 0) {
                    mostrarMensaje("Seleccione una cancha de la tabla");
                    return;
                }
                gestionCancha.eliminar(codigoEliminar);
                actualizarCanchas(tabla);
            }
        });

        actualizarCanchas(tabla);
        return crearPanelTabla(formulario, tabla, acciones);
    }

    private Cancha crearCancha(JTextField codigo, JTextField nombre, JComboBox<String> tipo,
            JTextField capacidad, JTextField tarifa, JComboBox<EstadoCancha> estado) {
        int numeroCodigo = leerEntero(codigo.getText());
        int maximo = leerEntero(capacidad.getText());
        float precio = leerDecimal(tarifa.getText());
        if (numeroCodigo < 0 || maximo <= 0 || precio < 0 || nombre.getText().trim().isEmpty()) {
            mostrarMensaje("Complete los datos de la cancha con valores válidos");
            return null;
        }
        String modalidad = (String) tipo.getSelectedItem();
        Cancha cancha;
        if (modalidad.equals("Fútbol 5")) {
            cancha = new CanchaFutbol5(numeroCodigo, nombre.getText().trim(), maximo, precio);
        } else if (modalidad.equals("Fútbol 7")) {
            cancha = new CanchaFutbol7(numeroCodigo, nombre.getText().trim(), maximo, precio);
        } else {
            cancha = new CanchaFutbol11(numeroCodigo, nombre.getText().trim(), maximo, precio);
        }
        cancha.setEstado((EstadoCancha) estado.getSelectedItem());
        return cancha;
    }

    private void actualizarCanchas(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        Cancha[] lista = canchas.find();
        for (int i = 0; i < lista.length; i++) {
            String tipo = "Fútbol 11";
            if (lista[i] instanceof CanchaFutbol5) {
                tipo = "Fútbol 5";
            } else if (lista[i] instanceof CanchaFutbol7) {
                tipo = "Fútbol 7";
            }
            modelo.addRow(new Object[]{lista[i].getCod(), lista[i].getNombre(), tipo,
                lista[i].getCapacidadMaxima(), lista[i].getTarifaBase(), lista[i].getEstado()});
        }
    }

    private JTabbedPane panelServicios() {
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();
        JPanel formularioServicio = new JPanel(new GridLayout(4, 2, 5, 5));
        formularioServicio.add(new JLabel("Código del servicio"));
        formularioServicio.add(codigo);
        formularioServicio.add(new JLabel("Nombre"));
        formularioServicio.add(nombre);
        formularioServicio.add(new JLabel("Precio unitario"));
        formularioServicio.add(precio);
        formularioServicio.add(new JLabel("Stock"));
        formularioServicio.add(stock);

        JTable tablaServicios = crearTabla("Código", "Nombre", "Precio", "Stock");
        JPanel accionesServicio = new JPanel();
        JButton registrarServicio = new JButton("Registrar servicio");
        JButton modificarServicio = new JButton("Modificar seleccionado");
        JButton eliminarServicio = new JButton("Eliminar seleccionado");
        accionesServicio.add(registrarServicio);
        accionesServicio.add(modificarServicio);
        accionesServicio.add(eliminarServicio);

        registrarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                ServicioAdicional servicio = crearServicio(codigo, nombre, precio, stock);
                int cantidadAnterior = servicios.find().length;
                gestionServicio.registrar(servicio);
                actualizarServicios(tablaServicios);
                if (servicios.find().length > cantidadAnterior) {
                    mostrarMensaje("Servicio registrado");
                }
            }
        });
        modificarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoAnterior = idSeleccionado(tablaServicios);
                if (codigoAnterior < 0) {
                    mostrarMensaje("Seleccione un servicio");
                    return;
                }
                gestionServicio.modificar(codigoAnterior, crearServicio(codigo, nombre, precio, stock));
                actualizarServicios(tablaServicios);
            }
        });
        eliminarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoEliminar = idSeleccionado(tablaServicios);
                if (codigoEliminar < 0) {
                    mostrarMensaje("Seleccione un servicio");
                    return;
                }
                gestionServicio.eliminar(codigoEliminar);
                actualizarServicios(tablaServicios);
            }
        });
        actualizarServicios(tablaServicios);

        JPanel panelServicio = crearPanelTabla(formularioServicio, tablaServicios, accionesServicio);
        JPanel panelPaquete = panelPaquetes();
        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Servicios adicionales", panelServicio);
        pestanas.addTab("Paquetes promocionales", panelPaquete);
        return pestanas;
    }

    private ServicioAdicional crearServicio(JTextField codigo, JTextField nombre,
            JTextField precio, JTextField stock) {
        int numeroCodigo = leerEntero(codigo.getText());
        float precioUnitario = leerDecimal(precio.getText());
        int unidades = leerEntero(stock.getText());
        if (numeroCodigo < 0 || precioUnitario < 0 || unidades < 0
                || nombre.getText().trim().isEmpty()) {
            mostrarMensaje("Complete los datos del servicio con valores válidos");
            return null;
        }
        return new ServicioAdicional(numeroCodigo, nombre.getText().trim(), precioUnitario, unidades);
    }

    private void actualizarServicios(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        ServicioAdicional[] lista = servicios.find();
        for (int i = 0; i < lista.length; i++) {
            modelo.addRow(new Object[]{lista[i].getCodigo(), lista[i].getNombre(),
                lista[i].getPrecioUnitario(), lista[i].getStockDisponible()});
        }
        actualizarSelectorServicios();
    }

    private JPanel panelPaquetes() {
        JTextField codigo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField precio = new JTextField();
        selectorServiciosPaquete = new JComboBox<>();
        JPanel formulario = new JPanel(new GridLayout(4, 2, 5, 5));
        formulario.add(new JLabel("Código del paquete"));
        formulario.add(codigo);
        formulario.add(new JLabel("Nombre"));
        formulario.add(nombre);
        formulario.add(new JLabel("Precio promocional"));
        formulario.add(precio);
        formulario.add(new JLabel("Servicio para agregar (-1 si ninguno)"));
        formulario.add(selectorServiciosPaquete);

        JTable tabla = crearTabla("Código", "Nombre", "Servicios", "Precio promocional");
        JPanel acciones = new JPanel();
        JButton registrar = new JButton("Registrar paquete");
        JButton modificar = new JButton("Modificar seleccionado");
        JButton agregarServicio = new JButton("Agregar servicio seleccionado");
        JButton quitarServicio = new JButton("Quitar servicio seleccionado");
        JButton eliminar = new JButton("Eliminar seleccionado");
        acciones.add(registrar);
        acciones.add(modificar);
        acciones.add(agregarServicio);
        acciones.add(quitarServicio);
        acciones.add(eliminar);

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoPaquete = leerEntero(codigo.getText());
                float precioPaquete = leerDecimal(precio.getText());
                if (codigoPaquete < 0 || precioPaquete < 0 || nombre.getText().trim().isEmpty()) {
                    mostrarMensaje("Complete el código, nombre y precio del paquete");
                    return;
                }
                PaquetePromocional paquete = new PaquetePromocional(
                codigoPaquete, nombre.getText().trim(), 20, precioPaquete);
                int codigoServicio = codigoServicioSeleccionado();
                if (codigoServicio >= 0) {
                    paquete.agregarServicio(servicios.findOne(codigoServicio));
                }
                int cantidadAnterior = paquetes.find().length;
                gestionPaquete.registrar(paquete);
                actualizarPaquetes(tabla);
                if (paquetes.find().length > cantidadAnterior) {
                    mostrarMensaje("Paquete registrado");
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoAnterior = idSeleccionado(tabla);
                PaquetePromocional paquete = paquetes.findOne(codigoAnterior);
                float precioPaquete = leerDecimal(precio.getText());
                if (paquete == null || precioPaquete < 0 || nombre.getText().trim().isEmpty()) {
                    mostrarMensaje("Seleccione un paquete y complete sus datos");
                    return;
                }
                paquete.setNombre(nombre.getText().trim());
                paquete.setPrecioPromocional(precioPaquete);
                gestionPaquete.modificar(codigoAnterior, paquete);
                actualizarPaquetes(tabla);
            }
        });

        agregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoPaquete = idSeleccionado(tabla);
                int codigoServicio = codigoServicioSeleccionado();
                PaquetePromocional paquete = paquetes.findOne(codigoPaquete);
                ServicioAdicional servicio = servicios.findOne(codigoServicio);
                if (paquete == null || servicio == null) {
                    mostrarMensaje("Seleccione un paquete y un servicio");
                    return;
                }
                if (paquete.agregarServicio(servicio)) {
                    actualizarPaquetes(tabla);
                } else {
                    mostrarMensaje("El servicio no se pudo agregar al paquete");
                }
            }
        });

        quitarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoPaquete = idSeleccionado(tabla);
                int codigoServicio = codigoServicioSeleccionado();
                PaquetePromocional paquete = paquetes.findOne(codigoPaquete);
                if (paquete == null || codigoServicio < 0) {
                    mostrarMensaje("Seleccione un paquete y un servicio");
                    return;
                }
                paquete.quitarServicio(codigoServicio);
                actualizarPaquetes(tabla);
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int codigoEliminar = idSeleccionado(tabla);
                if (codigoEliminar < 0) {
                    mostrarMensaje("Seleccione un paquete");
                    return;
                }
                gestionPaquete.eliminar(codigoEliminar);
                actualizarPaquetes(tabla);
            }
        });

        actualizarSelectorServicios();
        actualizarPaquetes(tabla);
        return crearPanelTabla(formulario, tabla, acciones);
    }

    private int codigoServicioSeleccionado() {
        if (selectorServiciosPaquete == null || selectorServiciosPaquete.getSelectedItem() == null) {
            return -1;
        }
        return (Integer) selectorServiciosPaquete.getSelectedItem();
    }

    private void actualizarSelectorServicios() {
        if (selectorServiciosPaquete == null) {
            return;
        }
        selectorServiciosPaquete.removeAllItems();
        selectorServiciosPaquete.addItem(-1);
        ServicioAdicional[] lista = servicios.find();
        for (int i = 0; i < lista.length; i++) {
            selectorServiciosPaquete.addItem(lista[i].getCodigo());
        }
    }

    private void actualizarPaquetes(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        PaquetePromocional[] lista = paquetes.find();
        for (int i = 0; i < lista.length; i++) {
            ServicioAdicional[] incluidos = lista[i].getServicios();
            String nombresServicios = "";
            for (int j = 0; j < incluidos.length; j++) {
                if (j > 0) {
                    nombresServicios += ", ";
                }
                nombresServicios += incluidos[j].getNombre();
            }
            modelo.addRow(new Object[]{lista[i].getCodigo(), lista[i].getNombre(),
                nombresServicios, lista[i].getPrecioPromocional()});
        }
    }

    private JPanel panelReservas() {
        JTextField dniTitular = new JTextField();
        JTextField codigoCancha = new JTextField();
        JTextField fecha = new JTextField(LocalDate.now().toString());
        JTextField hora = new JTextField("08:00");
        JTextField duracion = new JTextField("1");
        JTextField dnisParticipantes = new JTextField();
        JTextField serviciosPedidos = new JTextField();
        JTextField codigosPaquetes = new JTextField();
        JPanel formulario = new JPanel(new GridLayout(8, 2, 5, 5));
        formulario.add(new JLabel("DNI del jugador titular"));
        formulario.add(dniTitular);
        formulario.add(new JLabel("Código de cancha"));
        formulario.add(codigoCancha);
        formulario.add(new JLabel("Fecha (AAAA-MM-DD)"));
        formulario.add(fecha);
        formulario.add(new JLabel("Hora de inicio (HH:mm)"));
        formulario.add(hora);
        formulario.add(new JLabel("Duración en horas (1 a 3)"));
        formulario.add(duracion);
        formulario.add(new JLabel("DNI participantes separados por coma"));
        formulario.add(dnisParticipantes);
        formulario.add(new JLabel("Servicios: código:cantidad separados por coma"));
        formulario.add(serviciosPedidos);
        formulario.add(new JLabel("Códigos de paquetes separados por coma"));
        formulario.add(codigosPaquetes);

        JTable tabla = crearTabla("Nro.", "Fecha", "Hora", "Cancha", "Titular", "Participantes", "Ingresaron", "Estado", "Adelanto");
        JPanel acciones = new JPanel();
        JButton registrar = new JButton("Crear reserva");
        JButton confirmar = new JButton("Confirmar y registrar adelanto");
        JButton iniciar = new JButton("Iniciar partido");
        JButton finalizar = new JButton("Finalizar y emitir comprobante");
        JButton cancelar = new JButton("Cancelar");
        JTextField cargoIncidencia = new JTextField(6);
        JButton agregarIncidencia = new JButton("Agregar cargo de incidencia");
        acciones.add(registrar);
        acciones.add(confirmar);
        acciones.add(iniciar);
        acciones.add(finalizar);
        acciones.add(cancelar);
        acciones.add(cargoIncidencia);
        acciones.add(agregarIncidencia);
        registrar.setEnabled(puede("RESERVAS"));
        confirmar.setEnabled(puede("RESERVAS"));
        cancelar.setEnabled(puede("RESERVAS"));
        iniciar.setEnabled(puede("INICIO_PARTIDO"));
        finalizar.setEnabled(puede("CIERRE_PARTIDO"));
        agregarIncidencia.setEnabled(puede("INCIDENCIAS"));

        registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                Reserva reserva = crearReserva(dniTitular.getText(), codigoCancha.getText(),
                fecha.getText(), hora.getText(), duracion.getText(),
                dnisParticipantes.getText(), serviciosPedidos.getText(), codigosPaquetes.getText());
                if (reserva == null) {
                    return;
                }
                int cantidadAnterior = reservas.find().length;
                gestionReserva.registrar(reserva, empleadoActual);
                actualizarReservas(tabla);
                if (reservas.find().length > cantidadAnterior) {
                    mostrarMensaje("Reserva registrada con estado Pendiente");
                } else {
                    mostrarMensaje("No se registró. Revise disponibilidad, horario, capacidad y stock.");
                }
            }
        });

        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int numero = idSeleccionado(tabla);
                if (numero < 0) {
                    mostrarMensaje("Seleccione una reserva");
                    return;
                }
                String valor = JOptionPane.showInputDialog(this, "Monto del adelanto");
                if (valor == null) {
                    return;
                }
                gestionReserva.confirmar(numero, leerDecimal(valor));
                actualizarReservas(tabla);
                Reserva reserva = reservas.findOne(numero);
                if (reserva != null && reserva.getEstado() == EstadoReserva.CONFIRMADA) {
                    mostrarMensaje("Reserva confirmada");
                } else {
                    mostrarMensaje("El adelanto debe cubrir al menos el 50% del alquiler");
                }
            }
        });

        iniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int numero = idSeleccionado(tabla);
                if (numero < 0) {
                    mostrarMensaje("Seleccione una reserva");
                    return;
                }
                Reserva reserva = reservas.findOne(numero);
                if (reserva == null || reserva.getEstado() != EstadoReserva.CONFIRMADA) {
                    mostrarMensaje("Solo se pueden iniciar reservas confirmadas");
                    return;
                }
                String textoDnis = JOptionPane.showInputDialog(VentanaPrincipal.this,
                        "DNI de los jugadores que ingresaron, separados por coma");
                if (textoDnis == null) {
                    return;
                }
                int[] dnis = leerListaEnteros(textoDnis);
                if (dnis.length == 0) {
                    mostrarMensaje("Ingrese al menos un DNI de participante");
                    return;
                }
                for (int i = 0; i < dnis.length; i++) {
                    if (!reserva.esParticipante(dnis[i])) {
                        mostrarMensaje("El DNI " + dnis[i] + " no pertenece a la reserva");
                        return;
                    }
                }
                for (int i = 0; i < dnis.length; i++) {
                    reserva.registrarIngreso(dnis[i]);
                }
                gestionReserva.iniciar(numero, empleadoActual);
                actualizarReservas(tabla);
                if (reserva != null && reserva.getEstado() == EstadoReserva.EN_JUEGO) {
                    mostrarMensaje("Partido iniciado");
                } else {
                    mostrarMensaje("Solo se pueden iniciar reservas confirmadas");
                }
            }
        });

        finalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int numero = idSeleccionado(tabla);
                if (numero < 0) {
                    mostrarMensaje("Seleccione una reserva");
                    return;
                }
                emitirComprobante(numero, tabla);
            }
        });

        cancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int numero = idSeleccionado(tabla);
                if (numero < 0) {
                    mostrarMensaje("Seleccione una reserva");
                    return;
                }
                gestionReserva.cancelar(numero);
                actualizarReservas(tabla);
                Reserva reserva = reservas.findOne(numero);
                if (reserva != null && reserva.getEstado() == EstadoReserva.CANCELADA) {
                    if (reserva.getAdelantoDevuelto() == reserva.getAdelanto()) {
                        mostrarMensaje("Reserva cancelada. Adelanto devuelto: " + reserva.getAdelanto());
                    } else {
                        mostrarMensaje("Reserva cancelada. El adelanto queda retenido.");
                    }
                } else {
                    mostrarMensaje("La reserva no se puede cancelar en su estado actual");
                }
            }
        });

        agregarIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int numero = idSeleccionado(tabla);
                float monto = leerDecimal(cargoIncidencia.getText());
                Reserva reserva = reservas.findOne(numero);
                if (reserva == null || monto <= 0 || reserva.getEstado() != EstadoReserva.EN_JUEGO) {
                    mostrarMensaje("Seleccione un partido en juego e ingrese un monto válido");
                    return;
                }
                reserva.agregarCargoIncidencia(monto);
                mostrarMensaje("Cargo agregado");
            }
        });

        actualizarReservas(tabla);
        return crearPanelTabla(formulario, tabla, acciones);
    }

    private Reserva crearReserva(String textoDni, String textoCancha, String textoFecha,
            String textoHora, String textoDuracion, String textoParticipantes,
            String textoServicios, String textoPaquetes) {
        int dni = leerEntero(textoDni);
        int codigoCancha = leerEntero(textoCancha);
        int horas = leerEntero(textoDuracion);
        LocalDate fecha = leerFecha(textoFecha);
        LocalTime hora = leerHora(textoHora);
        Jugador titular = jugadores.findOne(dni);
        Cancha cancha = canchas.findOne(codigoCancha);
        if (titular == null || cancha == null || fecha == null || hora == null
                || horas < 1 || horas > 3) {
            mostrarMensaje("Complete los datos de la reserva correctamente");
            return null;
        }

        ServicioAdicional[] listaServicios = servicios.find();
        PaquetePromocional[] listaPaquetes = paquetes.find();
        Reserva reserva = new Reserva(titular, cancha, fecha, hora, horas,
                cancha.getCapacidadMaxima(), listaServicios.length + 1, listaPaquetes.length + 1);

        int[] dnis = leerListaEnteros(textoParticipantes);
        for (int i = 0; i < dnis.length; i++) {
            if (dnis[i] == dni) {
                continue;
            }
            Jugador participante = jugadores.findOne(dnis[i]);
            if (participante == null || !reserva.agregarParticipante(participante)) {
                mostrarMensaje("Revise los DNI de los participantes y la capacidad de la cancha");
                return null;
            }
        }

        if (!agregarServiciosPedidos(reserva, textoServicios)) {
            mostrarMensaje("Use el formato código:cantidad para los servicios solicitados");
            return null;
        }
        if (!agregarPaquetes(reserva, textoPaquetes)) {
            mostrarMensaje("Revise los códigos de paquetes solicitados");
            return null;
        }
        return reserva;
    }

    private boolean agregarServiciosPedidos(Reserva reserva, String texto) {
        String pendiente = texto.trim();
        if (pendiente.isEmpty()) {
            return true;
        }
        while (!pendiente.isEmpty()) {
            int coma = pendiente.indexOf(',');
            String item = pendiente;
            if (coma >= 0) {
                item = pendiente.substring(0, coma).trim();
                pendiente = pendiente.substring(coma + 1).trim();
            } else {
                pendiente = "";
            }
            int separador = item.indexOf(':');
            if (separador < 0) {
                return false;
            }
            int codigo = leerEntero(item.substring(0, separador));
            int cantidad = leerEntero(item.substring(separador + 1));
            ServicioAdicional servicio = servicios.findOne(codigo);
            if (servicio == null || cantidad < 1
                    || !reserva.agregarServicio(new ServicioPedido(servicio, cantidad))) {
                return false;
            }
        }
        return true;
    }

    private boolean agregarPaquetes(Reserva reserva, String texto) {
        int[] codigos = leerListaEnteros(texto);
        for (int i = 0; i < codigos.length; i++) {
            PaquetePromocional paquete = paquetes.findOne(codigos[i]);
            if (paquete == null || !reserva.agregarPaquete(paquete)) {
                return false;
            }
        }
        return true;
    }

    private int[] leerListaEnteros(String texto) {
        int[] resultado = new int[texto.length()];
        int cantidad = 0;
        int numero = 0;
        boolean leyendoNumero = false;
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            if (caracter >= '0' && caracter <= '9') {
                numero = numero * 10 + caracter - '0';
                leyendoNumero = true;
            } else if (caracter == ',' || Character.isWhitespace(caracter)) {
                if (leyendoNumero) {
                    resultado[cantidad] = numero;
                    cantidad++;
                    numero = 0;
                    leyendoNumero = false;
                }
            } else {
                return new int[0];
            }
        }
        if (leyendoNumero) {
            resultado[cantidad] = numero;
            cantidad++;
        }
        int[] lista = new int[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = resultado[i];
        }
        return lista;
    }

    private LocalDate leerFecha(String texto) {
        if (texto.length() != 10 || texto.charAt(4) != '-' || texto.charAt(7) != '-') {
            return null;
        }
        int anio = leerEntero(texto.substring(0, 4));
        int mes = leerEntero(texto.substring(5, 7));
        int dia = leerEntero(texto.substring(8, 10));
        if (anio < 1 || mes < 1 || mes > 12 || dia < 1) {
            return null;
        }
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (mes == 2 && esAnioBisiesto(anio)) {
            diasPorMes[1] = 29;
        }
        if (dia > diasPorMes[mes - 1]) {
            return null;
        }
        return LocalDate.of(anio, mes, dia);
    }

    private boolean esAnioBisiesto(int anio) {
        return anio % 400 == 0 || (anio % 4 == 0 && anio % 100 != 0);
    }

    private LocalTime leerHora(String texto) {
        if (texto.length() != 5 || texto.charAt(2) != ':') {
            return null;
        }
        int hora = leerEntero(texto.substring(0, 2));
        int minuto = leerEntero(texto.substring(3, 5));
        if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
            return null;
        }
        return LocalTime.of(hora, minuto);
    }

    private void actualizarReservas(JTable tabla) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        Reserva[] lista = reservas.find();
        for (int i = 0; i < lista.length; i++) {
            Jugador[] ingresantes = lista[i].getIngresantes();
            String dnisIngresantes = "";
            for (int j = 0; j < ingresantes.length; j++) {
                if (j > 0) {
                    dnisIngresantes += ", ";
                }
                dnisIngresantes += ingresantes[j].getDni();
            }
            modelo.addRow(new Object[]{lista[i].getNumero(), lista[i].getFecha(),
                lista[i].getHoraInicio(), lista[i].getCancha().getNombre(),
                lista[i].getTitular().getDni(), lista[i].getParticipantesNro(), dnisIngresantes,
                lista[i].getEstado(), lista[i].getAdelanto()});
        }
    }

    private void emitirComprobante(int numero, JTable tabla) {
        Object[] opciones = {"Boleta", "Factura"};
        int tipo = JOptionPane.showOptionDialog(this, "Tipo de comprobante", "Comprobante",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        if (tipo < 0) {
            return;
        }
        String ruc = null;
        String razonSocial = null;
        if (tipo == 1) {
            ruc = JOptionPane.showInputDialog(this, "RUC");
            razonSocial = JOptionPane.showInputDialog(this, "Razón social");
            if (ruc == null || ruc.trim().isEmpty() || razonSocial == null || razonSocial.trim().isEmpty()) {
                mostrarMensaje("La factura requiere RUC y razón social");
                return;
            }
        }
        int cantidadAnterior = gestionReserva.getComprobantes().find().length;
        TipoComprobante tipoComprobante = TipoComprobante.BOLETA;
        if (tipo == 1) {
            tipoComprobante = TipoComprobante.FACTURA;
        }
        gestionReserva.finalizar(numero, tipoComprobante, ruc, razonSocial);
        actualizarReservas(tabla);
        Comprobante[] lista = gestionReserva.getComprobantes().find();
        if (lista.length > cantidadAnterior) {
            mostrarComprobante(lista[lista.length - 1]);
        }
    }

    private void mostrarComprobante(Comprobante comprobante) {
        String texto = "Comprobante: " + comprobante.getNumero()
                + "\nTipo: " + comprobante.getTipo()
                + "\nRUC: " + comprobante.getRuc()
                + "\nRazón social: " + comprobante.getRazonSocial()
                + "\n\nDetalle:";
        String[] conceptos = comprobante.getConceptos();
        float[] montos = comprobante.getMontosConceptos();
        for (int i = 0; i < conceptos.length; i++) {
            texto += "\n" + conceptos[i] + ": " + montos[i];
        }
        texto += "\n\nSubtotal: " + comprobante.getSubtotal()
                + "\nIGV: " + comprobante.getIgv()
                + "\nTotal: " + comprobante.getTotal()
                + "\nAdelanto: " + comprobante.getAdelanto()
                + "\nSaldo: " + comprobante.getSaldo();
        mostrarMensaje(texto);
    }

    private JPanel panelAgenda() {
        JTextField fecha = new JTextField(LocalDate.now().toString(), 12);
        JButton consultar = new JButton("Consultar fecha");
        JTable tabla = crearTabla("Cancha", "Fecha", "Hora", "Fin", "Jugador titular", "Estado");
        JPanel encabezado = new JPanel();
        encabezado.add(new JLabel("Fecha (AAAA-MM-DD)"));
        encabezado.add(fecha);
        encabezado.add(consultar);
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(encabezado, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                LocalDate fechaConsulta = leerFecha(fecha.getText());
                if (fechaConsulta == null) {
                    mostrarMensaje("Ingrese la fecha con formato AAAA-MM-DD");
                    return;
                }
                actualizarAgenda(tabla, fechaConsulta);
            }
        });
        actualizarAgenda(tabla, LocalDate.now());
        return panel;
    }

    private void actualizarAgenda(JTable tabla, LocalDate fecha) {
        DefaultTableModel modelo = modelo(tabla);
        modelo.setRowCount(0);
        Cancha[] listaCanchas = canchas.find();
        Reserva[] listaReservas = reservas.find();
        for (int i = 0; i < listaCanchas.length; i++) {
            boolean tieneReserva = false;
            for (int j = 0; j < listaReservas.length; j++) {
                Reserva reserva = listaReservas[j];
                if (reserva.getCancha().getCod() == listaCanchas[i].getCod()
                        && reserva.getFecha().equals(fecha)
                        && reserva.getEstado() != EstadoReserva.CANCELADA) {
                    modelo.addRow(new Object[]{listaCanchas[i].getNombre(), fecha,
                        reserva.getHoraInicio(), reserva.getHoraFin(),
                        reserva.getTitular().getDni(), reserva.getEstado()});
                    tieneReserva = true;
                }
            }
            if (!tieneReserva) {
                String estado = "Libre";
                if (listaCanchas[i].getEstado() == EstadoCancha.EN_MANTENIMIENTO) {
                    estado = "En mantenimiento";
                } else if (listaCanchas[i].getEstado() == EstadoCancha.OCUPADA) {
                    estado = "Ocupada";
                }
                modelo.addRow(new Object[]{listaCanchas[i].getNombre(), fecha,
                    "-", "-", "-", estado});
            }
        }
    }

    private JPanel panelReportes() {
        JTextField fechaDesde = new JTextField(LocalDate.now().toString(), 10);
        JTextField fechaHasta = new JTextField(LocalDate.now().toString(), 10);
        JTextField codigoCancha = new JTextField(5);
        JTextField dniEmpleado = new JTextField(8);
        JPanel formulario = new JPanel(new GridLayout(2, 4, 5, 5));
        formulario.add(new JLabel("Desde (AAAA-MM-DD)"));
        formulario.add(fechaDesde);
        formulario.add(new JLabel("Hasta (AAAA-MM-DD)"));
        formulario.add(fechaHasta);
        formulario.add(new JLabel("Código de cancha"));
        formulario.add(codigoCancha);
        formulario.add(new JLabel("DNI de empleado"));
        formulario.add(dniEmpleado);

        JTable tabla = crearTabla("Concepto", "Detalle", "Resultado");
        JPanel acciones = new JPanel();
        JButton ocupacion = new JButton("Reporte de ocupación");
        JButton productividad = new JButton("Reporte de productividad");
        JButton jugadoresFrecuentes = new JButton("Jugadores con más reservas finalizadas");
        JButton ingresos = new JButton("Reporte de ingresos");
        acciones.add(ocupacion);
        acciones.add(productividad);
        acciones.add(jugadoresFrecuentes);
        acciones.add(ingresos);

        ocupacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                LocalDate desde = leerFecha(fechaDesde.getText());
                LocalDate hasta = leerFecha(fechaHasta.getText());
                int codigo = leerEntero(codigoCancha.getText());
                if (!rangoValido(desde, hasta) || codigo < 0) {
                    mostrarMensaje("Ingrese fechas y código de cancha válidos");
                    return;
                }
                float porcentaje = gestionReportes.porcentajeOcupacion(codigo, desde, hasta);
                int horasReservadas = gestionReportes.horasReservadas(codigo, desde, hasta);
                DefaultTableModel modelo = modelo(tabla);
                modelo.setRowCount(0);
                modelo.addRow(new Object[]{"Cancha", codigo});
                modelo.addRow(new Object[]{"Rango", desde + " a " + hasta});
                modelo.addRow(new Object[]{"Horas reservadas", horasReservadas});
                modelo.addRow(new Object[]{"Ocupación", porcentaje + "%"});
            }
        });

        productividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                int dni = leerEntero(dniEmpleado.getText());
                Empleado empleado = empleados.findOne(dni);
                if (empleado == null) {
                    mostrarMensaje("No se encontró el empleado");
                    return;
                }
                int[] datos = gestionReportes.productividad(empleado);
                DefaultTableModel modelo = modelo(tabla);
                modelo.setRowCount(0);
                modelo.addRow(new Object[]{"Empleado", empleado.getNombres() + " " + empleado.getApellidos()});
                modelo.addRow(new Object[]{"Reservas registradas", datos[0]});
                modelo.addRow(new Object[]{"Partidos finalizados", datos[1]});
            }
        });

        jugadoresFrecuentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                Jugador[] lista = gestionReportes.jugadoresConMasReservasFinalizadas();
                DefaultTableModel modelo = modelo(tabla);
                modelo.setRowCount(0);
                for (int i = 0; i < lista.length; i++) {
                    modelo.addRow(new Object[]{lista[i].getDni(), lista[i].getNombres() + " "
                        + lista[i].getApellidos(), lista[i].getReservasFinalizadas()});
                }
            }
        });

        ingresos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evento) {
                LocalDate desde = leerFecha(fechaDesde.getText());
                LocalDate hasta = leerFecha(fechaHasta.getText());
                if (!rangoValido(desde, hasta)) {
                    mostrarMensaje("Ingrese un rango de fechas válido");
                    return;
                }
                float[] datos = gestionReportes.ingresos(desde, hasta);
                DefaultTableModel modelo = modelo(tabla);
                modelo.setRowCount(0);
                modelo.addRow(new Object[]{"Rango", desde + " a " + hasta});
                modelo.addRow(new Object[]{"Alquiler de canchas", datos[0]});
                modelo.addRow(new Object[]{"Servicios adicionales", datos[1]});
                modelo.addRow(new Object[]{"Paquetes promocionales", datos[2]});
                modelo.addRow(new Object[]{"Total", "", datos[0] + datos[1] + datos[2]});
            }
        });

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(formulario, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private boolean rangoValido(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null) {
            return false;
        }
        return !hasta.isBefore(desde);
    }
}

