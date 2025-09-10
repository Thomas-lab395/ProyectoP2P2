/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// MapaNiveles.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapaNiveles extends JPanel {
    private final Usuario usuario;
    private final UserManager userManager;
    private final int niveles = 7;
    private final boolean[] desbloqueados;

    private final Point[] positions;

    public MapaNiveles(Usuario usuario, UserManager userManager) {
        this.usuario = usuario;
        this.userManager = userManager;
        setPreferredSize(new Dimension(800, 600));

        this.desbloqueados = new boolean[niveles];
        // desbloqueo progresivo: 0 siempre desbloqueado, luego comprobar usuario
        desbloqueados[0] = true;
        boolean[] comp = usuario.getNivelesCompletados();
        for (int i = 1; i < niveles; i++) {
            // desbloquea si nivel anterior completado
            desbloqueados[i] = comp[i - 1];
        }

        // positions estilo "candy" en filas
        positions = new Point[niveles];
        int startX = 100, startY = 160, gapX = 100, gapY = 120;
        for (int i = 0; i < niveles; i++) {
            int col = i % 4;
            int row = i / 4;
            positions[i] = new Point(startX + col * gapX, startY + row * gapY);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < niveles; i++) {
                    Rectangle r = new Rectangle(positions[i].x - 32, positions[i].y - 32, 64, 64);
                    if (r.contains(e.getPoint())) {
                        if (desbloqueados[i]) {
                            abrirNivel(i);
                        } else {
                            JOptionPane.showMessageDialog(MapaNiveles.this, "Nivel bloqueado. Completa el anterior primero.");
                        }
                    }
                }
            }
        });
    }

    private void abrirNivel(int nivelIndex) {
        int[][] mapa;
        switch (nivelIndex) {
            case 0 -> mapa = Nivel.getTutorial();
            case 1 -> mapa = Nivel.getNivel1();
            case 2 -> mapa = Nivel.getNivel2();
            default -> mapa = Nivel.getNivel1(); // por defecto si faltan niveles
        }
        LevelFrame lf = new LevelFrame(usuario, userManager, nivelIndex, mapa);
        lf.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;

        // Fondo
        if (Recursos.fondoMapa != null) {
            g.drawImage(Recursos.fondoMapa, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(200, 230, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dibuja burbujas / iconos de nivel (tipo candy)
        for (int i = 0; i < posicionesLenght(); i++) {
            int px = positions[i].x - 32;
            int py = positions[i].y - 32;
            if (desbloqueados[i]) {
                if (Recursos.nivelLibre != null) g.drawImage(Recursos.nivelLibre, px, py, 64, 64, this);
                else {
                    g.setColor(new Color(255, 180, 180));
                    g.fillRoundRect(px, py, 64, 64, 16, 16);
                }
            } else {
                if (Recursos.nivelBloqueado != null) g.drawImage(Recursos.nivelBloqueado, px, py, 64, 64, this);
                else {
                    g.setColor(new Color(160, 160, 160, 220));
                    g.fillRoundRect(px, py, 64, 64, 16, 16);
                    g.setColor(Color.BLACK);
                    g.drawString("Bloq", px + 10, py + 36);
                }
            }
            // número
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString(String.valueOf(i + 1), px + 26, py + 38);
        }

        // info usuario
        g.setColor(new Color(0,0,0,150));
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Usuario: " + usuario.getNombreCompleto(), 20, 30);
        g.drawString("Puntos: " + usuario.getScore(), 20, 50);
    }

    private int posicionesLenght() {
        return Math.min(niveles, positions.length);
    }
}
