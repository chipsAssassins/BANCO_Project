package OWNNBANC;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Main_interfaz extends JFrame {

    private static final Color BG_DARK    = new Color(18, 18, 31);
    private static final Color BG_CARD    = new Color(30, 30, 46);
    private static final Color GREEN      = new Color(46, 204, 113);
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_GRAY  = new Color(170, 170, 170);
    private static final Color RED        = new Color(231, 76, 60);
    private static final Color INPUT_BG   = new Color(42, 42, 62);
    private static final Color INPUT_BDR  = new Color(58, 58, 94);

    private Usuarios usuarioActual;

    public Main_interfaz() {
        setTitle("OWNBANC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        mostrarLogin();
    }

    private void mostrarLogin() {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(35, 40, 35, 40));
        card.setPreferredSize(new Dimension(360, 420));

        JLabel titulo = label("OWNBANC", 28, Font.BOLD, GREEN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = label("Inicia sesión en tu cuenta", 13, Font.PLAIN, TEXT_GRAY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCorreo = label("Correo electrónico", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCorreo = inputField("ejemplo@correo.com");
        JLabel lblPass = label("Contraseña", 12, Font.PLAIN, TEXT_GRAY);
        JPasswordField txtPass = passField();

        JLabel lblError = label("", 12, Font.PLAIN, RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogin = btnPrimario("Iniciar sesión");
        JButton btnCrear = btnSecundario("¿No tienes cuenta? Crear una");

        btnLogin.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtPass.getPassword()).trim();
            if (correo.isEmpty() || pass.isEmpty()) {
                lblError.setText("Por favor llena todos los campos.");
                return;
            }
            Usuarios usuario = csv1_Manager.login(correo, pass);
            if (usuario != null) {
                usuarioActual = usuario;
                mostrarMenu();
            } else {
                lblError.setText("Correo o contraseña incorrectos.");
                txtPass.setText("");
            }
        });

        btnCrear.addActionListener(e -> mostrarCrearCuenta());

        agregarComponentes(card,
            titulo, Box.createVerticalStrut(4), sub,
            Box.createVerticalStrut(24),
            lblCorreo, Box.createVerticalStrut(4), txtCorreo,
            Box.createVerticalStrut(12),
            lblPass, Box.createVerticalStrut(4), txtPass,
            Box.createVerticalStrut(8),
            lblError, Box.createVerticalStrut(12),
            btnLogin, Box.createVerticalStrut(8), btnCrear
        );

        root.add(card);
        setContentPane(root);
        setSize(460, 520);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void mostrarCrearCuenta() {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(35, 40, 35, 40));
        card.setPreferredSize(new Dimension(360, 460));

        JLabel titulo = label("Crear cuenta", 24, Font.BOLD, GREEN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblNombre = label("Nombre completo", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtNombre = inputField("Tu nombre");
        JLabel lblCorreo = label("Correo electrónico", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCorreo = inputField("ejemplo@correo.com");
        JLabel lblPass = label("Contraseña", 12, Font.PLAIN, TEXT_GRAY);
        JPasswordField txtPass = passField();

        JLabel lblMensaje = label("", 12, Font.PLAIN, GREEN);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistrar = btnPrimario("Crear cuenta");
        JButton btnVolver    = btnSecundario("← Volver al login");

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtPass.getPassword()).trim();
            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                lblMensaje.setForeground(RED);
                lblMensaje.setText("Por favor llena todos los campos.");
                return;
            }
            Usuarios nuevo = new Usuarios(nombre, correo, pass,
                Tarjeta.generarNumero(), Banco.generarCuenta(), dinero.generarDinero());
            csv1_Manager.guardarUsuario(nuevo);
            lblMensaje.setForeground(GREEN);
            lblMensaje.setText("¡Cuenta creada! Ya puedes iniciar sesión.");
        });

        btnVolver.addActionListener(e -> mostrarLogin());

        agregarComponentes(card,
            titulo, Box.createVerticalStrut(20),
            lblNombre, Box.createVerticalStrut(4), txtNombre,
            Box.createVerticalStrut(12),
            lblCorreo, Box.createVerticalStrut(4), txtCorreo,
            Box.createVerticalStrut(12),
            lblPass, Box.createVerticalStrut(4), txtPass,
            Box.createVerticalStrut(8),
            lblMensaje, Box.createVerticalStrut(12),
            btnRegistrar, Box.createVerticalStrut(8), btnVolver
        );

        root.add(card);
        setContentPane(root);
        setSize(460, 560);
        revalidate();
        repaint();
    }
    private JPanel fila(String etiqueta, String valor) {
    JPanel fila = new JPanel(new BorderLayout());
    fila.setBackground(BG_CARD);
    fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

    JLabel lbl = label(etiqueta, 12, Font.PLAIN, TEXT_GRAY);
    JLabel val = label(valor, 12, Font.BOLD, TEXT_WHITE);

    fila.add(lbl, BorderLayout.WEST);
    fila.add(val, BorderLayout.EAST);
    return fila;
}

    private void mostrarMenu() {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(40, 50, 40, 50));
        card.setPreferredSize(new Dimension(340, 360));

        JLabel bienvenida = label("Hola, " + usuarioActual.getName() + "!", 20, Font.BOLD, GREEN);
        bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel saldo = label(String.format("Saldo: $%.2f", usuarioActual.getDineroEnCuenta()), 15, Font.PLAIN, TEXT_WHITE);
        saldo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnDatos      = btnPrimario("Ver mis datos");
        JButton btnTransferir = btnPrimario("Realizar Transferencia");
        JButton btnCerrar     = btnSecundario("Cerrar sesión");

        btnDatos.addActionListener(e -> mostrarDatos());
            

        btnTransferir.addActionListener(e -> mostrarTransferencia(saldo));
        btnCerrar.addActionListener(e -> mostrarLogin());

        agregarComponentes(card,
            bienvenida, Box.createVerticalStrut(8), saldo,
            Box.createVerticalStrut(28),
            btnDatos, Box.createVerticalStrut(10),
            btnTransferir, Box.createVerticalStrut(10),
            btnCerrar
        );

        root.add(card);
        setContentPane(root);
        setSize(420, 420);
        revalidate();
        repaint();
    }
    private void mostrarDatos() {
    JPanel root = panelOscuro();
    root.setLayout(new GridBagLayout());

    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(BG_CARD);
    card.setBorder(new EmptyBorder(35, 40, 35, 40));
    card.setPreferredSize(new Dimension(360, 420));

    // Título
    JLabel titulo = label("Mis datos", 22, Font.BOLD, GREEN);
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Separador visual
    JSeparator sep = new JSeparator();
    sep.setForeground(INPUT_BDR);
    sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

    // Filas de datos
    JPanel filas = new JPanel();
    filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));
    filas.setBackground(BG_CARD);
    filas.setAlignmentX(Component.CENTER_ALIGNMENT);

    filas.add(fila("ID", String.valueOf(usuarioActual.getId())));
    filas.add(Box.createVerticalStrut(10));
    filas.add(fila("Nombre", usuarioActual.getName()));
    filas.add(Box.createVerticalStrut(10));
    filas.add(fila("Correo", usuarioActual.getEmail()));
    filas.add(Box.createVerticalStrut(10));
    filas.add(fila("No. Cuenta", usuarioActual.getNumCuenta()));
    filas.add(Box.createVerticalStrut(10));
    filas.add(fila("No. Tarjeta", usuarioActual.getNumTarjeta()));
    filas.add(Box.createVerticalStrut(10));
    filas.add(fila("Saldo", String.format("$%.2f", usuarioActual.getDineroEnCuenta())));

    JButton btnVolver = btnSecundario("← Volver");
    btnVolver.addActionListener(e -> mostrarMenu());

    agregarComponentes(card,
        titulo,
        Box.createVerticalStrut(16),
        sep,
        Box.createVerticalStrut(20),
        filas,
        Box.createVerticalStrut(24),
        btnVolver
    );

    root.add(card);
    setContentPane(root);
    setSize(440, 500);
    revalidate();
    repaint();
}

    private void mostrarTransferencia(JLabel saldoMenuLabel) {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(35, 40, 35, 40));
        card.setPreferredSize(new Dimension(360, 420));

        JLabel titulo = label("Transferencia", 22, Font.BOLD, GREEN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel saldoActual = label(String.format("Saldo disponible: $%.2f", usuarioActual.getDineroEnCuenta()), 13, Font.PLAIN, TEXT_GRAY);
        saldoActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCuenta = label("Número de cuenta destino", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCuenta = inputField("1-52-XXXXXXXXXXXXXXXX");
        JLabel lblMonto = label("Monto a transferir", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtMonto = inputField("0.00");

        JLabel lblResultado = label("", 12, Font.BOLD, GREEN);
        lblResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnEnviar = btnPrimario("Enviar transferencia");
        JButton btnVolver = btnSecundario("← Volver");

        btnEnviar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                boolean ok = Transferencias.transferir(usuarioActual, txtCuenta.getText().trim(), monto);
                if (ok) {
                    lblResultado.setForeground(GREEN);
                    lblResultado.setText("Transferencia exitosa");
                    saldoActual.setText(String.format("Saldo disponible: $%.2f", usuarioActual.getDineroEnCuenta()));
                    saldoMenuLabel.setText(String.format("Saldo: $%.2f", usuarioActual.getDineroEnCuenta()));
                } else {
                    lblResultado.setForeground(RED);
                    lblResultado.setText("No se pudo realizar la transferencia.");
                }
            } catch (NumberFormatException ex) {
                lblResultado.setForeground(RED);
                lblResultado.setText("Ingresa un monto válido.");
            }
        });

        btnVolver.addActionListener(e -> mostrarMenu());

        agregarComponentes(card,
            titulo, Box.createVerticalStrut(4), saldoActual,
            Box.createVerticalStrut(20),
            lblCuenta, Box.createVerticalStrut(4), txtCuenta,
            Box.createVerticalStrut(12),
            lblMonto, Box.createVerticalStrut(4), txtMonto,
            Box.createVerticalStrut(8),
            lblResultado, Box.createVerticalStrut(12),
            btnEnviar, Box.createVerticalStrut(8), btnVolver
        );

        root.add(card);
        setContentPane(root);
        setSize(440, 500);
        revalidate();
        repaint();
    }

    // ── Helpers ──
    private JPanel panelOscuro() {
        JPanel p = new JPanel();
        p.setBackground(BG_DARK);
        return p;
    }

    private JLabel label(String texto, int size, int style, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(color);
        return l;
    }

    private JTextField inputField(String placeholder) {
        JTextField f = new JTextField();
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT_GRAY);
        f.setCaretColor(TEXT_WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(INPUT_BDR, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        f.setText(placeholder);
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) { f.setText(""); f.setForeground(TEXT_WHITE); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setForeground(TEXT_GRAY); f.setText(placeholder); }
            }
        });
        return f;
    }

    private JPasswordField passField() {
        JPasswordField f = new JPasswordField();
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT_WHITE);
        f.setCaretColor(TEXT_WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(INPUT_BDR, 1, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        f.setFont(new Font("Arial", Font.PLAIN, 13));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return f;
    }

    private JButton btnPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(GREEN);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Arial", Font.BOLD, 13));
        b.setBorder(new EmptyBorder(10, 20, 10, 20));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        return b;
    }

    private JButton btnSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setBackground(BG_CARD);
        b.setForeground(GREEN);
        b.setFont(new Font("Arial", Font.PLAIN, 13));
        b.setBorder(new LineBorder(GREEN, 1, true));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return b;
    }

    private void agregarComponentes(JPanel panel, Object... componentes) {
        for (Object c : componentes) {
            if (c instanceof Component) panel.add((Component) c);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_interfaz());
    }
}


