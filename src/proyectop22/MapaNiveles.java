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
       
        desbloqueados[0] = true;
        boolean[] comp = usuario.getNivelesCompletados();
        for (int i = 1; i < niveles; i++) {
            // desbloquea si nivel anterior completado
            desbloqueados[i] = comp[i - 1];
        }


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

    // ==== Dibujar los cuadros de niveles (más grandes) ====
    int size = 90; // tamaño nuevo
    for (int i = 0; i < posicionesLenght(); i++) {
        int px = positions[i].x - size / 2;
        int py = positions[i].y - size / 2;

        if (desbloqueados[i]) {
            if (Recursos.nivelLibre != null) 
                g.drawImage(Recursos.nivelLibre, px, py, size, size, this);
            else {
                g.setColor(new Color(255, 180, 180));
                g.fillRoundRect(px, py, size, size, 20, 20);
            }
        } else {
            if (Recursos.nivelBloqueado != null) 
                g.drawImage(Recursos.nivelBloqueado, px, py, size, size, this);
            else {
                g.setColor(new Color(160, 160, 160, 220));
                g.fillRoundRect(px, py, size, size, 20, 20);
                g.setColor(Color.BLACK);
                g.drawString("X", px + size/2 - 5, py + size/2 + 5);
            }
        }

        // número de nivel en el centro
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String num = String.valueOf(i + 1);
        int tx = px + (size - fm.stringWidth(num)) / 2;
        int ty = py + (size + fm.getAscent()) / 2 - 8;
        g.drawString(num, tx, ty);
    }

    // ==== Caja superior de usuario y puntos ====
    int boxWidth = 250;
    int boxHeight = 60;
    g.setColor(new Color(0, 0, 0, 180)); // fondo negro semitransparente
    g.fillRoundRect(15, 15, boxWidth, boxHeight, 20, 20);

    g.setColor(Color.WHITE);
    g.setFont(new Font("SansSerif", Font.BOLD, 20));
    g.drawString("Usuario: " + usuario.getNombreCompleto(), 30, 40);
    g.drawString("Puntos: " + usuario.getScore(), 30, 65);
}

    private int posicionesLenght() {
        return Math.min(niveles, positions.length);
    }
}
