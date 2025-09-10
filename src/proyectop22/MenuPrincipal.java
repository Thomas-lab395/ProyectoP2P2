/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// MenuPrincipal.java
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    private final Usuario usuario;
    private final UserManager userManager;

    public MenuPrincipal(Usuario usuario, UserManager userManager) {
        this.usuario = usuario;
        this.userManager = userManager;

        setTitle("Sokoban - Menú Principal");
        setSize(420, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(240, 248, 255));

        JLabel lbl = new JLabel("Bienvenido " + usuario.getNombreCompleto(), SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnJugar = new JButton("Seleccionar Nivel");
        JButton btnPerfil = new JButton("Mi Perfil");
        JButton btnSalir = new JButton("Salir");

        btnJugar.addActionListener(e -> {
            JFrame f = new JFrame("Mapa de Niveles - " + usuario.getNombreCompleto());
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.add(new MapaNiveles(usuario, userManager));
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            this.dispose();
        });

        btnPerfil.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Usuario: " + usuario.getUsername() + "\n" +
                    "Nombre: " + usuario.getNombreCompleto() + "\n" +
                    "Registrado: " + usuario.getFechaRegistro() + "\n" +
                    "Tiempo jugado (s): " + usuario.getTotalSecondsPlayed() + "\n" +
                    "Niveles completados: " + countCompleted(usuario.getNivelesCompletados())
            );
        });

        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(lbl);
        panel.add(btnJugar);
        panel.add(btnPerfil);
        panel.add(btnSalir);

        add(panel);
    }

    private int countCompleted(boolean[] arr) {
        int c = 0;
        for (boolean b : arr) if (b) c++;
        return c;
    }
}


