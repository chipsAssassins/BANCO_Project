package OWNNBANC;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Clase principal de la interfaz gráfica de OWNBANC.
 * Extiende JFrame para crear la ventana principal de la aplicación.
 * Maneja la navegación entre pantallas: Login, Crear Cuenta, Menú, Datos y Transferencia.
 */
public class Main_interfaz extends JFrame {

    // ── Paleta de colores de la aplicación ──
    private static final Color BG_DARK    = new Color(18, 18, 31);   // Fondo general oscuro
    private static final Color BG_CARD    = new Color(30, 30, 46);   // Fondo de tarjetas/paneles
    private static final Color GREEN      = new Color(46, 204, 113); // Color de acento principal
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_GRAY  = new Color(170, 170, 170);
    private static final Color RED        = new Color(231, 76, 60);  // Color de errores
    private static final Color INPUT_BG   = new Color(42, 42, 62);   // Fondo de campos de texto
    private static final Color INPUT_BDR  = new Color(58, 58, 94);   // Borde de campos de texto

    /** Usuario autenticado actualmente en la sesión */
    private Usuarios usuarioActual;

    /**
     * Constructor: inicializa la ventana y muestra la pantalla de login.
     */
    public Main_interfaz() {
        setTitle("OWNBANC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        mostrarLogin();
    }

   
    //  PANTALLA: Mostrar Login
  
    /**
     * Construye y muestra la pantalla de inicio de sesión.
     * Valida campos vacíos antes de llamar a csv1_Manager.login().
     * Lanza UsuarioNoEncontradoException si las credenciales son incorrectas.
     */
    private void mostrarLogin() {
        /*
        btnLogin.addActionListener(e -> {
    String correo = txtCorreo.getText().trim();
    String pass   = new String(txtPass.getPassword()).trim();

    if (correo.isEmpty() || pass.isEmpty()) {
        lblError.setText("Por favor llena todos los campos.");
        return;
    }

    try {
        // Valida el formato del correo antes de buscar en la BD
        excepcioness.validarCorreo(correo);

        Usuarios usuario = csv1_Manager.login(correo, pass);
        if (usuario == null) {
            throw new excepcioness.UsuarioNoEncontradoException(
                "Correo o contraseña incorrectos."
            );
        }
        usuarioActual = usuario;
        mostrarMenu();

    } catch (excepcioness.CorreoInvalidoException ex) {
        lblError.setForeground(RED);
        lblError.setText(ex.getMessage());

    } catch (excepcioness.UsuarioNoEncontradoException ex) {
        lblError.setForeground(RED);
        lblError.setText(ex.getMessage());
        txtPass.setText("");
    }
});
         */
        
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(35, 40, 35, 40));
        card.setPreferredSize(new Dimension(360, 420));

        // Encabezado de la tarjeta
        JLabel titulo = label("OWNBANC", 28, Font.BOLD, GREEN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = label("Inicia sesión en tu cuenta", 13, Font.PLAIN, TEXT_GRAY);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campos de entrada
        JLabel lblCorreo = label("Correo electrónico", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCorreo = inputField("ejemplo@correo.com");
        JLabel lblPass = label("Contraseña", 12, Font.PLAIN, TEXT_GRAY);
        JPasswordField txtPass = passField();

        // Etiqueta para mostrar mensajes de error al usuario
        JLabel lblError = label("", 12, Font.PLAIN, RED);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogin = btnPrimario("Iniciar sesión");
        JButton btnCrear = btnSecundario("¿No tienes cuenta? Crear una");

        /**
         * Listener del botón "Iniciar sesión":
         *  1. Valida que los campos no estén vacíos.
         *  2. Llama a csv1_Manager.login() para autenticar.
         *  3. Si el usuario no existe, captura UsuarioNoEncontradoException
         *     y muestra el mensaje de error sin lanzar la excepción hacia arriba.
         */
        btnLogin.addActionListener(e -> {
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtPass.getPassword()).trim();

            // Validación local: campos obligatorios
            if (correo.isEmpty() || pass.isEmpty()) {
                lblError.setText("Por favor llena todos los campos.");
                return;
            }

            try {
                // Intentamos autenticar; si falla lanza UsuarioNoEncontradoException
                Usuarios usuario = csv1_Manager.login(correo, pass);

                if (usuario == null) {
                    // Si login() devuelve null en vez de lanzar excepción,
                    // la lanzamos nosotros explícitamente
                    throw new excepcioness.UsuarioNoEncontradoException(
                        "Correo o contraseña incorrectos."
                    );
                }

                // Login exitoso: guardamos el usuario y avanzamos al menú
                usuarioActual = usuario;
                mostrarMenu();

            } catch (excepcioness.UsuarioNoEncontradoException ex) {
                // Capturamos el error de autenticación y lo mostramos en la UI
                lblError.setText(ex.getMessage());
                txtPass.setText(""); // Limpiamos la contraseña por seguridad
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

    
    //  PANTALLA: CREAR CUENTA
    
    /**
     * Construye y muestra la pantalla de registro de nuevo usuario.
     * No lanza excepciones propias; la validación es local (campos vacíos).
     */
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

        // Campos del formulario de registro
        JLabel lblNombre = label("Nombre completo", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtNombre = inputField("Tu nombre");
        JLabel lblCorreo = label("Correo electrónico", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCorreo = inputField("ejemplo@correo.com");
        JLabel lblPass = label("Contraseña", 12, Font.PLAIN, TEXT_GRAY);
        JPasswordField txtPass = passField();

        // Etiqueta para mensajes (éxito en verde, error en rojo)
        JLabel lblMensaje = label("", 12, Font.PLAIN, GREEN);
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRegistrar = btnPrimario("Crear cuenta");
        JButton btnVolver    = btnSecundario("← Volver al login");

        /**
         * Listener del botón "Crear cuenta":
         *  1. Valida que todos los campos estén llenos.
         *  2. Genera los datos automáticos (tarjeta, número de cuenta, saldo inicial).
         *  3. Persiste el nuevo usuario mediante csv1_Manager.guardarUsuario().
         */
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String pass   = new String(txtPass.getPassword()).trim();

            // Validación: ningún campo puede estar vacío
            if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()) {
                lblMensaje.setForeground(RED);
                lblMensaje.setText("Por favor llena todos los campos.");
                return;
            }

            // Creamos el usuario con datos generados automáticamente
            Usuarios nuevo = new Usuarios(
                nombre, correo, pass,
                Tarjeta.generarNumero(),    // Genera número de tarjeta único
                Banco.generarCuenta(),      // Genera número de cuenta bancaria
                dinero.generarDinero()      // Asigna saldo inicial aleatorio
            );

            // Guardamos el usuario en el archivo CSV
            csv1_Manager.guardarUsuario(nuevo);

            lblMensaje.setForeground(GREEN);
            lblMensaje.setText("¡Cuenta creada! Ya puedes iniciar sesión.");
        });

        // Regresamos a la pantalla de login sin perder cambios en el frame
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

    
    //  HELPER: FILA DE DATOS (etiqueta izquierda, valor derecha)
    
    /**
     * Crea un panel horizontal con dos etiquetas: una descriptiva (gris) a la
     * izquierda y el valor (blanco/negrita) a la derecha.
     *
     * @param etiqueta Texto descriptivo del campo (ej. "Nombre")
     * @param valor    Valor a mostrar (ej. "Juan Pérez")
     * @return JPanel listo para insertar en el layout vertical
     */
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

    
    //  PANTALLA: MENÚ PRINCIPAL
    
    /**
     * Muestra el menú principal del usuario autenticado.
     * Presenta el saldo actual y da acceso a las demás pantallas.
     * Solo es accesible después de un login exitoso (usuarioActual != null).
     */
    private void mostrarMenu() {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(40, 50, 40, 50));
        card.setPreferredSize(new Dimension(340, 360));

        // Saludo personalizado con el nombre del usuario autenticado
        JLabel bienvenida = label("Hola, " + usuarioActual.getName() + "!", 20, Font.BOLD, GREEN);
        bienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Saldo actualizado dinámicamente después de cada transferencia
        JLabel saldo = label(
            String.format("Saldo: $%.2f", usuarioActual.getDineroEnCuenta()),
            15, Font.PLAIN, TEXT_WHITE
        );
        saldo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnDatos      = btnPrimario("Ver mis datos");
        JButton btnTransferir = btnPrimario("Realizar Transferencia");
        JButton btnCerrar     = btnSecundario("Cerrar sesión");

        // Navega a la pantalla de datos del usuario
        btnDatos.addActionListener(e -> mostrarDatos());

        // Pasa la etiqueta de saldo para actualizarla tras una transferencia exitosa
        btnTransferir.addActionListener(e -> mostrarTransferencia(saldo));

        // Cierra la sesión limpiando usuarioActual y regresando al login
        btnCerrar.addActionListener(e -> {
            usuarioActual = null; // Limpiamos la sesión activa
            mostrarLogin();
        });

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

    // ──────────────────────────────────────────────────────────────
    //  PANTALLA: MIS DATOS
    // ──────────────────────────────────────────────────────────────
    /**
     * Muestra la información completa del usuario autenticado:
     * ID, nombre, correo, número de cuenta, número de tarjeta y saldo.
     */
    private void mostrarDatos() {
        JPanel root = panelOscuro();
        root.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(35, 40, 35, 40));
        card.setPreferredSize(new Dimension(360, 420));

        JLabel titulo = label("Mis datos", 22, Font.BOLD, GREEN);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Línea separadora visual entre el título y los datos
        JSeparator sep = new JSeparator();
        sep.setForeground(INPUT_BDR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        // Panel que contiene cada fila de información del usuario
        JPanel filas = new JPanel();
        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));
        filas.setBackground(BG_CARD);
        filas.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Cada fila() crea un par etiqueta-valor alineado horizontalmente
        filas.add(fila("ID",          String.valueOf(usuarioActual.getId())));
        filas.add(Box.createVerticalStrut(10));
        filas.add(fila("Nombre",      usuarioActual.getName()));
        filas.add(Box.createVerticalStrut(10));
        filas.add(fila("Correo",      usuarioActual.getEmail()));
        filas.add(Box.createVerticalStrut(10));
        filas.add(fila("No. Cuenta",  usuarioActual.getNumCuenta()));
        filas.add(Box.createVerticalStrut(10));
        filas.add(fila("No. Tarjeta", usuarioActual.getNumTarjeta()));
        filas.add(Box.createVerticalStrut(10));
        filas.add(fila("Saldo",       String.format("$%.2f", usuarioActual.getDineroEnCuenta())));

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

  
    //  transferenciaa
    
    /**
     * Muestra la pantalla para realizar transferencias bancarias.
     * Implementa el manejo completo de excepciones del paquete excepcioness:
     *
     *   - SaldoInsuficienteException  → cuando el monto supera el saldo disponible
     *   - UsuarioNoEncontradoException → cuando la cuenta destino no existe
     *   - TransferenciaFallidaException → para cualquier otro fallo en la operación
     *
     * @param saldoMenuLabel Referencia a la etiqueta de saldo en el menú principal,
     *                       para actualizarla si la transferencia es exitosa.
     */
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

        // Muestra el saldo actual antes de operar
        JLabel saldoActual = label(
            String.format("Saldo disponible: $%.2f", usuarioActual.getDineroEnCuenta()),
            13, Font.PLAIN, TEXT_GRAY
        );
        saldoActual.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Campo: número de cuenta del destinatario
        JLabel lblCuenta = label("Número de cuenta destino", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtCuenta = inputField("1-52-XXXXXXXXXXXXXXXX");

        // Campo: monto a transferir
        JLabel lblMonto = label("Monto a transferir", 12, Font.PLAIN, TEXT_GRAY);
        JTextField txtMonto = inputField("0.00");

        // Etiqueta de resultado: verde para éxito, rojo para errores
        JLabel lblResultado = label("", 12, Font.BOLD, GREEN);
        lblResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnEnviar = btnPrimario("Enviar transferencia");
        JButton btnVolver = btnSecundario("← Volver");

        /**
         * Listener del botón "Enviar transferencia":
         *  Bloque try-catch con tres capas de excepción específica:
         *
         *  1. NumberFormatException   → el campo monto no contiene un número válido
         *  2. SaldoInsuficienteException  → saldo menor al monto solicitado
         *  3. UsuarioNoEncontradoException → la cuenta destino no existe en el sistema
         *  4. TransferenciaFallidaException → error genérico durante la operación
         */
        btnEnviar.addActionListener(e -> {
            try {
                // Parseamos el monto; lanza NumberFormatException si no es numérico
                double monto = Double.parseDouble(txtMonto.getText().trim());

                // Validación anticipada de saldo antes de llamar a la lógica de negocio
                if (monto > usuarioActual.getDineroEnCuenta()) {
                    throw new excepcioness.SaldoInsuficienteException(
                        "Saldo insuficiente para realizar la transferencia."
                    );
                }

                // Intentamos la transferencia; puede lanzar excepciones del paquete
                boolean ok = Transferencias.transferir(
                    usuarioActual,
                    txtCuenta.getText().trim(),
                    monto
                );

                if (!ok) {
                    // Si la operación devuelve false sin lanzar excepción,
                    // la convertimos en TransferenciaFallidaException
                    throw new excepcioness.TransferenciaFallidaException(
                        "No se pudo completar la transferencia."
                    );
                }

                // ── Transferencia exitosa ──
                lblResultado.setForeground(GREEN);
                lblResultado.setText("Transferencia exitosa");

                // Actualizamos el saldo en esta pantalla y en el menú principal
                saldoActual.setText(
                    String.format("Saldo disponible: $%.2f", usuarioActual.getDineroEnCuenta())
                );
                saldoMenuLabel.setText(
                    String.format("Saldo: $%.2f", usuarioActual.getDineroEnCuenta())
                );

            } catch (NumberFormatException ex) {
                // El usuario escribió texto en el campo de monto
                lblResultado.setForeground(RED);
                lblResultado.setText("Ingresa un monto válido (ej. 100.00).");

            } catch (excepcioness.SaldoInsuficienteException ex) {
                // Saldo en cuenta menor al monto solicitado
                lblResultado.setForeground(RED);
                lblResultado.setText(ex.getMessage());

            } catch (excepcioness.UsuarioNoEncontradoException ex) {
                // La cuenta de destino no existe en el sistema
                lblResultado.setForeground(RED);
                lblResultado.setText(ex.getMessage());

            } catch (excepcioness.TransferenciaFallidaException ex) {
                // Error genérico durante la transferencia
                lblResultado.setForeground(RED);
                lblResultado.setText(ex.getMessage());
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

    // ──────────────────────────────────────────────────────────────
    //  HELPERS DE UI  (fábrica de componentes reutilizables)
    // ──────────────────────────────────────────────────────────────

    /** Crea un JPanel con el color de fondo oscuro global. */
    private JPanel panelOscuro() {
        JPanel p = new JPanel();
        p.setBackground(BG_DARK);
        return p;
    }

    /**
     * Fábrica de JLabel configurado con fuente, estilo y color.
     * @param texto  Contenido textual
     * @param size   Tamaño de fuente en puntos
     * @param style  Font.BOLD / Font.PLAIN / Font.ITALIC
     * @param color  Color del texto
     */
    private JLabel label(String texto, int size, int style, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", style, size));
        l.setForeground(color);
        return l;
    }

    /**
     * Crea un JTextField estilizado con placeholder.
     * El placeholder desaparece al ganar el foco y reaparece si se deja vacío.
     * @param placeholder Texto de ayuda mostrado cuando el campo está vacío
     */
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

        // FocusListener para comportamiento de placeholder
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) {
                    f.setText("");
                    f.setForeground(TEXT_WHITE);
                }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setForeground(TEXT_GRAY);
                    f.setText(placeholder);
                }
            }
        });
        return f;
    }

    /** Crea un JPasswordField (campo de contraseña) con el estilo de la aplicación. */
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

    /** Botón de acción principal (fondo verde, texto negro). */
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

    /** Botón de acción secundaria (borde verde, texto verde, fondo oscuro). */
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

    /**
     * Agrega una lista de componentes a un JPanel.
     * Acepta Object... para permitir mezclar Component y Box.Filler (struts/glue)
     * en la misma llamada sin necesidad de castear manualmente.
     *
     * @param panel       Panel destino
     * @param componentes Componentes a agregar en orden
     */
    private void agregarComponentes(JPanel panel, Object... componentes) {
        for (Object c : componentes) {
            if (c instanceof Component) panel.add((Component) c);
        }
    }

    /** Punto de entrada de la aplicación. Crea la ventana en el hilo de Swing. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main_interfaz());
    }
}