package ec.edu.taller;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazIncidentes extends JFrame implements ActionListener {

    private static final int CAPACIDAD = 10;

    private GestorIncidentes gestor = new GestorIncidentes(CAPACIDAD);

    private JTabbedPane tabbedPane;


    private JTextField txtCodigo;
    private JComboBox<String> cmbTipo;
    private JTextArea txtDescripcion;
    private JSpinner spnPrioridad;
    private JButton btnRegistrar;

    private JLabel lblPendientes;
    private JLabel lblDisponibles;
    private JLabel lblCapacidad;
    private JLabel lblCodigoDetalle;
    private JLabel lblTipoDetalle;
    private JLabel lblPrioridadDetalle;
    private JLabel lblEstadoDetalle;
    private JTextArea txtDescripcionDetalle;
    private JButton btnSiguiente;
    private JButton btnAtender;
    private JButton btnListar;
    private JTextArea txtLista;

    public InterfazIncidentes() {
        setTitle("Gestor de Incidentes de Seguridad");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JLabel lblAutor = new JLabel("AUTOR: Zaid_Mahe");
        lblAutor.setBounds(15, 8, 300, 22);
        add(lblAutor);

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(10, 35, 610, 405);
        tabbedPane.addTab("INGRESO DE DATOS", crearPanelIngreso());
        tabbedPane.addTab("GESTIONAR INCIDENTES", crearPanelGestion());
        add(tabbedPane);

        actualizarContadores();
    }

    private JPanel crearPanelIngreso() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("NUEVO INCIDENTE");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(220, 15, 200, 30);
        panel.add(lblTitulo);

        JLabel lblCodigo = new JLabel("CODIGO");
        lblCodigo.setBounds(40, 65, 120, 25);
        panel.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(170, 65, 270, 25);
        panel.add(txtCodigo);

        JLabel lblTipo = new JLabel("TIPO");
        lblTipo.setBounds(40, 105, 120, 25);
        panel.add(lblTipo);

        String[] tipos = {"PHISHING", "MALWARE", "ACCESO_NO_AUTORIZADO", "FUGA_DATOS", "OTRO"};
        cmbTipo = new JComboBox<>(tipos);
        cmbTipo.setBounds(170, 105, 270, 25);
        panel.add(cmbTipo);

        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(40, 145, 120, 25);
        panel.add(lblDescripcion);

        txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setBounds(170, 145, 360, 75);
        panel.add(scrollDescripcion);

        JLabel lblPrioridad = new JLabel("PRIORIDAD");
        lblPrioridad.setBounds(40, 240, 120, 25);
        panel.add(lblPrioridad);


        spnPrioridad = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        spnPrioridad.setBounds(170, 240, 160, 25);
        panel.add(spnPrioridad);

        JLabel lblAyuda = new JLabel("(1 = más alta, 5 = más baja)");
        lblAyuda.setBounds(340, 240, 220, 25);
        panel.add(lblAyuda);

        JLabel lblEstadoInicial = new JLabel("ESTADO INICIAL:");
        lblEstadoInicial.setBounds(40, 300, 120, 25);
        panel.add(lblEstadoInicial);

        JLabel lblPendiente = new JLabel("PENDIENTE");
        lblPendiente.setBounds(170, 300, 150, 25);
        panel.add(lblPendiente);

        btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setBounds(420, 295, 150, 35);
        btnRegistrar.addActionListener(this);
        panel.add(btnRegistrar);

        return panel;
    }

    private JPanel crearPanelGestion() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        lblPendientes = new JLabel();
        lblPendientes.setBounds(40, 20, 170, 25);
        panel.add(lblPendientes);

        lblDisponibles = new JLabel();
        lblDisponibles.setBounds(230, 20, 170, 25);
        panel.add(lblDisponibles);

        lblCapacidad = new JLabel();
        lblCapacidad.setBounds(420, 20, 150, 25);
        panel.add(lblCapacidad);

        lblCodigoDetalle = new JLabel("CODIGO:");
        lblCodigoDetalle.setBounds(40, 60, 240, 22);
        panel.add(lblCodigoDetalle);

        lblTipoDetalle = new JLabel("TIPO:");
        lblTipoDetalle.setBounds(40, 85, 240, 22);
        panel.add(lblTipoDetalle);

        lblPrioridadDetalle = new JLabel("PRIORIDAD:");
        lblPrioridadDetalle.setBounds(40, 110, 240, 22);
        panel.add(lblPrioridadDetalle);

        lblEstadoDetalle = new JLabel("ESTADO:");
        lblEstadoDetalle.setBounds(300, 60, 270, 22);
        panel.add(lblEstadoDetalle);

        JLabel lblDescripcion = new JLabel("DESCRIPCION:");
        lblDescripcion.setBounds(300, 85, 95, 22);
        panel.add(lblDescripcion);

        txtDescripcionDetalle = new JTextArea();
        txtDescripcionDetalle.setEditable(false);
        txtDescripcionDetalle.setLineWrap(true);
        txtDescripcionDetalle.setWrapStyleWord(true);
        txtDescripcionDetalle.setBounds(395, 88, 175, 55);
        panel.add(txtDescripcionDetalle);

        btnSiguiente = new JButton("SIGUIENTE");
        btnSiguiente.setBounds(40, 160, 160, 32);
        btnSiguiente.addActionListener(this);
        panel.add(btnSiguiente);

        btnAtender = new JButton("ATENDER");
        btnAtender.setBounds(222, 160, 160, 32);
        btnAtender.addActionListener(this);
        panel.add(btnAtender);

        btnListar = new JButton("LISTAR");
        btnListar.setBounds(404, 160, 160, 32);
        btnListar.addActionListener(this);
        panel.add(btnListar);

        txtLista = new JTextArea();
        txtLista.setEditable(false);
        txtLista.setLineWrap(true);
        txtLista.setWrapStyleWord(true);
        txtLista.setText("LISTA DE INCIDENTES ORDENADOS POR PRIORIDAD\nMENOR VALOR = MAYOR PRIORIDAD\n");
        JScrollPane scrollLista = new JScrollPane(txtLista);
        scrollLista.setBounds(40, 205, 524, 150);
        panel.add(scrollLista);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            registrar();
        } else if (e.getSource() == btnSiguiente) {
            siguiente();
        } else if (e.getSource() == btnAtender) {
            atender();
        } else if (e.getSource() == btnListar) {
            listar();
        }
    }

    private void registrar() {
        String codigo = txtCodigo.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();
        String descripcion = txtDescripcion.getText().trim();
        int prioridad = (int) spnPrioridad.getValue();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del incidente.");
            return;
        }
        if (gestor.existeIncidente(codigo)) {
            JOptionPane.showMessageDialog(this, "Ya existe un incidente con el código " + codigo + ".");
            return;
        }
        if (gestor.consultarEspaciosDisponibles() == 0) {
            JOptionPane.showMessageDialog(this, "La cola está llena. Atienda un incidente antes de registrar otro.");
            return;
        }

        IncidenteSeguridad incidente = new IncidenteSeguridad(codigo, tipo, descripcion, prioridad);
        if (gestor.registrarIncidente(incidente)) {
            actualizarContadores();
            JOptionPane.showMessageDialog(this, "Incidente " + codigo + " registrado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el incidente.");
        }
    }

    private void siguiente() {
        IncidenteSeguridad incidente = gestor.consultarSiguienteIncidente();
        if (incidente == null) {
            limpiarDetalle();
            JOptionPane.showMessageDialog(this, "No hay incidentes pendientes.");
        } else {
            mostrarDetalle(incidente);
        }
    }

    private void atender() {
        IncidenteSeguridad incidente = gestor.atenderSiguienteIncidente();
        if (incidente == null) {
            limpiarDetalle();
            JOptionPane.showMessageDialog(this, "No hay incidentes pendientes para atender.");
        } else {
            mostrarDetalle(incidente);
            actualizarContadores();
            listar();
            JOptionPane.showMessageDialog(this, "Incidente " + incidente.getCodigo() + " atendido.");
        }
    }

    private void listar() {
        List<IncidenteSeguridad> lista = gestor.listarIncidentes();
        txtLista.setText("LISTA DE INCIDENTES ORDENADOS POR PRIORIDAD\n");
        txtLista.append("MENOR VALOR = MAYOR PRIORIDAD\n\n");
        if (lista.isEmpty()) {
            txtLista.append("No hay incidentes pendientes.");
        }
        for (int i = 0; i < lista.size(); i++) {
            txtLista.append((i + 1) + ". " + lista.get(i).toString() + "\n");
        }
    }

    private void mostrarDetalle(IncidenteSeguridad incidente) {
        lblCodigoDetalle.setText("CODIGO: " + incidente.getCodigo());
        lblTipoDetalle.setText("TIPO: " + incidente.getTipo());
        lblPrioridadDetalle.setText("PRIORIDAD: " + incidente.getPrioridad());
        lblEstadoDetalle.setText("ESTADO: " + incidente.getEstado());
        txtDescripcionDetalle.setText(incidente.getDescripcion());
    }

    private void limpiarDetalle() {
        lblCodigoDetalle.setText("CODIGO:");
        lblTipoDetalle.setText("TIPO:");
        lblPrioridadDetalle.setText("PRIORIDAD:");
        lblEstadoDetalle.setText("ESTADO:");
        txtDescripcionDetalle.setText("");
    }

    private void actualizarContadores() {
        lblPendientes.setText("PENDIENTES: " + gestor.contarIncidentesPendientes());
        lblDisponibles.setText("DISPONIBLES: " + gestor.consultarEspaciosDisponibles());
        lblCapacidad.setText("CAPACIDAD: " + CAPACIDAD);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        cmbTipo.setSelectedIndex(0);
        txtDescripcion.setText("");
        spnPrioridad.setValue(1);
    }

    public static void main(String[] args) {
        InterfazIncidentes ventana = new InterfazIncidentes();
        ventana.setVisible(true);
    }
}
