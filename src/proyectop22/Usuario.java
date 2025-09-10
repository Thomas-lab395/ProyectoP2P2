/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// Clase Usuario
class Usuario {
    private String username;
    private String password;
    private String nombreCompleto;

    public Usuario(String username, String password, String nombreCompleto) {
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNombreCompleto() { return nombreCompleto; }
}

// Recursos simulados
class Recursos {
    public static Image fondoMapa;
    static {
        // Imagen de prueba: reemplaza con tu ruta real "/res/fondo.png"
        fondoMapa = new ImageIcon("src/res/fondo.png").getImage();
    }
}

// LoginFrame con fondo Pokémon
public class LoginFrame extends JFrame {
    private HashMap<String, Usuario> usuarios = new HashMap<>();

    public LoginFrame() {
        setTitle("Pokémon Sokoban - Inicio de Sesión");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de fondo
        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (Recursos.fondoMapa != null) {
                    g.drawImage(Recursos.fondoMapa, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        fondoPanel.setLayout(new GridBagLayout());
        add(fondoPanel);

        // Panel de login transparente
        JPanel panelLogin = new JPanel(new GridLayout(3, 2, 10, 10));
        panelLogin.setOpaque(false);
        panelLogin.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JLabel lblUser = new JLabel("Usuario:");
        lblUser.setForeground(Color.YELLOW);
        JTextField txtUser = new JTextField();

        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setForeground(Color.YELLOW);
        JPasswordField txtPass = new JPasswordField();

        JButton btnLogin = crearBoton("Iniciar Sesión", new Color(0, 128, 255));
        JButton btnRegister = crearBoton("Registrarse", new Color(255, 165, 0));

        panelLogin.add(lblUser);
        panelLogin.add(txtUser);
        panelLogin.add(lblPass);
        panelLogin.add(txtPass);
        panelLogin.add(btnLogin);
        panelLogin.add(btnRegister);

        fondoPanel.add(panelLogin, new GridBagConstraints());

        // Acción de login
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (usuarios.containsKey(user)) {
                Usuario u = usuarios.get(user);
                if (u.getPassword().equals(pass)) {
                    JOptionPane.showMessageDialog(this, "Bienvenido " + u.getNombreCompleto() + "!");
                    dispose();
                    new MenuPrincipal(u).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción de registro
        btnRegister.addActionListener(e -> {
            JTextField txtU = new JTextField();
            JTextField txtN = new JTextField();
            JPasswordField txtP = new JPasswordField();

            Object[] msg = {
                    "Usuario:", txtU,
                    "Nombre Completo:", txtN,
                    "Contraseña:", txtP
            };

            int opt = JOptionPane.showConfirmDialog(this, msg, "Registro", JOptionPane.OK_CANCEL_OPTION);
            if (opt == JOptionPane.OK_OPTION) {
                String u = txtU.getText().trim();
                String n = txtN.getText().trim();
                String p = new String(txtP.getPassword()).trim();

                if (u.isEmpty() || n.isEmpty() || p.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuarios.containsKey(u)) {
                    JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                usuarios.put(u, new Usuario(u, p, n));
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
            }
        });
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

// MenuPrincipal estilo Pokémon
class MenuPrincipal extends JFrame {
    private Usuario usuario;

    public MenuPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Pokémon Sokoban - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fondo
        JPanel fondoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (Recursos.fondoMapa != null) {
                    g.drawImage(Recursos.fondoMapa, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        fondoPanel.setLayout(new GridBagLayout());
        add(fondoPanel, BorderLayout.CENTER);

        // Panel de botones transparente
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 20, 20));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        JLabel lbl = new JLabel("¡Bienvenido " + usuario.getNombreCompleto() + "!");
        lbl.setFont(new Font("Verdana", Font.BOLD, 28));
        lbl.setForeground(Color.YELLOW);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnJugar = crearBoton("Seleccionar Nivel", new Color(0, 128, 255));
        JButton btnEstadisticas = crearBoton("Estadísticas", new Color(50, 205, 50));
        JButton btnOpciones = crearBoton("Opciones", new Color(255, 140, 0));
        JButton btnSalir = crearBoton("Salir", new Color(220, 20, 60));

        btnJugar.addActionListener(e -> {
            dispose();
            JOptionPane.showMessageDialog(this, "Aquí iría la selección de niveles.");
            // new MapaNiveles(usuario).setVisible(true);
        });

        btnEstadisticas.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Estadísticas del jugador.", "Estadísticas", JOptionPane.INFORMATION_MESSAGE));

        btnOpciones.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Opciones del juego.", "Opciones", JOptionPane.INFORMATION_MESSAGE));

        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(lbl);
        panelBotones.add(btnJugar);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnOpciones);
        panelBotones.add(btnSalir);

        fondoPanel.add(panelBotones, new GridBagConstraints());
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return boton;
    }
  
        
    }
}
