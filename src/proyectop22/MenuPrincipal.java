/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private Usuario usuario;

    public MenuPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Pokémon Sokoban - Menú Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de fondo con imagen estilo Pokémon
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
        panelBotones.setOpaque(false); // Transparente para ver el fondo
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        // Etiqueta bienvenida
        JLabel lbl = new JLabel("¡Bienvenido " + usuario.getNombreCompleto() + "!");
        lbl.setFont(new Font("Verdana", Font.BOLD, 28));
        lbl.setForeground(Color.YELLOW);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.CENTER);

        // Botones estilo Pokémon
        JButton btnJugar = crearBoton("Seleccionar Nivel", new Color(0, 128, 255));
        JButton btnEstadisticas = crearBoton("Estadísticas", new Color(50, 205, 50));
        JButton btnOpciones = crearBoton("Opciones", new Color(255, 140, 0));
        JButton btnSalir = crearBoton("Salir", new Color(220, 20, 60));

        // Acciones
        btnJugar.addActionListener(e -> {
            dispose();
            new MapaNiveles(usuario).setVisible(true);
        });

        btnEstadisticas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Aquí se mostrarán las estadísticas del jugador.", "Estadísticas", JOptionPane.INFORMATION_MESSAGE);
        });

        btnOpciones.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Opciones del juego (volumen, controles, idioma).", "Opciones", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar componentes al panel de botones
        panelBotones.add(lbl);
        panelBotones.add(btnJugar);
        panelBotones.add(btnEstadisticas);
        panelBotones.add(btnOpciones);
        panelBotones.add(btnSalir);

        fondoPanel.add(panelBotones, new GridBagConstraints());
    }

    // Método auxiliar para crear botones con estilo
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return boton;
    }

    // Para probar el menú directamente
    public static void main(String[] args) {
        Usuario test = new Usuario("ash123", "pikachu", "Ash Ketchum");
        new MenuPrincipal(test).setVisible(true);
    }
}
