package ec.edu.taller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Ventana hecha con el diseñador de IntelliJ (Ventana.form).
// Los componentes los crea IntelliJ a partir del .form; aquí solo va la lógica.
public class Ventana implements ActionListener {

    private static final int CAPACIDAD = 10;

    private GestorIncidentes gestor = new GestorIncidentes(CAPACIDAD);

    private JPanel panelPrincipal;

    // Pestaña INGRESO DE DATOS
    private JTextField txtCodigo;
    private JComboBox<String> cmbTipo;
    private JTextArea txtDescripcion;
    private JSpinner spnPrioridad;
    private JButton btnRegistrar;

    // Pestaña GESTIONAR INCIDENTES
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

    public Ventana() {
        // Valor inicial 1, mínimo 1, máximo 5, avanza de 1 en 1
        spnPrioridad.setModel(new SpinnerNumberModel(1, 1, 5, 1));

        btnRegistrar.addActionListener(this);
        btnSiguiente.addActionListener(this);
        btnAtender.addActionListener(this);
        btnListar.addActionListener(this);

        actualizarContadores();
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
            JOptionPane.showMessageDialog(panelPrincipal, "Ingrese el código del incidente.");
            return;
        }
        if (gestor.existeIncidente(codigo)) {
            JOptionPane.showMessageDialog(panelPrincipal, "Ya existe un incidente con el código " + codigo + ".");
            return;
        }
        if (gestor.consultarEspaciosDisponibles() == 0) {
            JOptionPane.showMessageDialog(panelPrincipal, "La cola está llena. Atienda un incidente antes de registrar otro.");
            return;
        }

        IncidenteSeguridad incidente = new IncidenteSeguridad(codigo, tipo, descripcion, prioridad);
        if (gestor.registrarIncidente(incidente)) {
            actualizarContadores();
            JOptionPane.showMessageDialog(panelPrincipal, "Incidente " + codigo + " registrado correctamente.");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(panelPrincipal, "No se pudo registrar el incidente.");
        }
    }

    private void siguiente() {
        IncidenteSeguridad incidente = gestor.consultarSiguienteIncidente();
        if (incidente == null) {
            limpiarDetalle();
            JOptionPane.showMessageDialog(panelPrincipal, "No hay incidentes pendientes.");
        } else {
            mostrarDetalle(incidente);
        }
    }

    private void atender() {
        IncidenteSeguridad incidente = gestor.atenderSiguienteIncidente();
        if (incidente == null) {
            limpiarDetalle();
            JOptionPane.showMessageDialog(panelPrincipal, "No hay incidentes pendientes para atender.");
        } else {
            mostrarDetalle(incidente); // aquí el estado ya sale como ATENDIDO
            actualizarContadores();
            listar();
            JOptionPane.showMessageDialog(panelPrincipal, "Incidente " + incidente.getCodigo() + " atendido.");
        }
    }

    private void listar() {
        List<IncidenteSeguridad> lista = gestor.listarIncidentes();
        txtLista.setText("");
        if (lista.isEmpty()) {
            txtLista.append("No hay incidentes pendientes.\n");
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
        JFrame frame = new JFrame("Gestor de Incidentes de Seguridad (Ventana.form)");
        frame.setContentPane(new Ventana().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
