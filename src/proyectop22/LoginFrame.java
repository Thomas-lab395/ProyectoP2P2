/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JTextField nombreCompletoField;
    private JButton loginBtn, registerBtn, toggleRegisterBtn;
    private UserManager userManager;
    private boolean modoRegistro = false;

    public LoginFrame(UserManager um) {
        this.userManager = um;

        setTitle("Sokoban");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 550);
        setLocationRelativeTo(null);

        // Panel con fondo escalado
        JPanel background = new JPanel() {
            Image bg = new ImageIcon(getClass().getResource("/res/fondo-login.jpg")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        background.setLayout(new GridBagLayout());
        add(background);

        // ==== PANEL CENTRAL ====
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(25, 25, 25, 220));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // ==== TÍTULO ====
        JLabel title = new JLabel("Bienvenido a la aventura", SwingConstants.CENTER);
        title.setFont(new Font("SanSerif", Font.BOLD, 24));
        title.setForeground(new Color(255, 215, 0)); // dorado suave
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ==== CAMPOS ====
        userField = new JTextField();
        estilizarCampo(userField, "Usuario");

        passField = new JPasswordField();
        estilizarCampo(passField, "Contraseña");

        nombreCompletoField = new JTextField();
        estilizarCampo(nombreCompletoField, "Nombre completo");
        nombreCompletoField.setVisible(false);

        // ==== BOTONES ====
        loginBtn = crearBoton("Iniciar Sesión", Color.WHITE, new Color(230, 230, 230));
        registerBtn = crearBoton("Registrarse", Color.WHITE, new Color(230, 230, 230));
        toggleRegisterBtn = crearBoton("¿Nuevo? Crear cuenta", Color.WHITE, new Color(230, 230, 230));

        // Eventos
        toggleRegisterBtn.addActionListener(e -> {
            modoRegistro = !modoRegistro;
            nombreCompletoField.setVisible(modoRegistro);
            loginBtn.setVisible(!modoRegistro);
            registerBtn.setVisible(modoRegistro);
            toggleRegisterBtn.setText(modoRegistro ? "← Volver a Iniciar Sesión" : "¿Nuevo? Crear cuenta");
            revalidate();
            repaint();
        });

        loginBtn.addActionListener(e -> iniciarSesion());
        registerBtn.addActionListener(e -> registrarUsuario());

    
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(userField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(nombreCompletoField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(loginBtn);
        panel.add(registerBtn);
        panel.add(Box.createVerticalStrut(10));
        panel.add(toggleRegisterBtn);

        registerBtn.setVisible(false);

        background.add(panel);

        setVisible(true);
    }

    // ===== Métodos auxiliares =====
    private void estilizarCampo(JTextField campo, String placeholder) {
        campo.setBorder(BorderFactory.createTitledBorder(placeholder));
        campo.setFont(new Font("SansSerif", Font.BOLD, 20));
        campo.setBackground(new Color(245, 245, 245));
        campo.setForeground(Color.BLACK);
        campo.setMaximumSize(new Dimension(300, 45));
    }

    private JButton crearBoton(String texto, Color baseColor, Color hoverColor) {
    JButton btn = new JButton(texto);
    btn.setFont(new Font("SansSerif", Font.BOLD, 15));
    btn.setBackground(baseColor);
    btn.setForeground(new Color(50, 50, 50)); // gris oscuro para el texto
    btn.setFocusPainted(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btn.setMaximumSize(new Dimension(300, 40));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Hover effect
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hoverColor); }
        public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(baseColor); }
    });

    return btn;
}

    private void iniciarSesion() {
        String usuario = userField.getText();
        String clave = new String(passField.getPassword());
        try {
            Usuario u = userManager.authenticate(usuario, clave);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "¡Bienvenido " + u.getNombreCompleto() + "!");
                dispose();
                mostrarMapa(u);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al iniciar sesión: " + ex.getMessage());
        }
    }

    private void registrarUsuario() {
        String usuario = userField.getText();
        String clave = new String(passField.getPassword());
        String nombreCompleto = nombreCompletoField.getText();

        if (usuario.isEmpty() || clave.isEmpty() || nombreCompleto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa usuario, contraseña y nombre completo.");
            return;
        }

        try {
            Usuario nuevo = userManager.register(usuario, clave, nombreCompleto);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente. Bienvenido " + nuevo.getNombreCompleto());
            dispose();
            mostrarMapa(nuevo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }

    private void mostrarMapa(Usuario u) {
        JFrame frameMapa = new JFrame("Mapa de Niveles");
        frameMapa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMapa.setSize(800, 600);
        frameMapa.setLocationRelativeTo(null);
        frameMapa.add(new MapaNiveles(u, userManager));
        frameMapa.setVisible(true);
    }
}
