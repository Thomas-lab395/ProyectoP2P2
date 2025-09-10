/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SokobanPanel extends JPanel implements KeyListener {
    private int playerX, playerY;
    private int[][] mapa;

    private Image jugador, caja, meta, pared, piso;

    public SokobanPanel(int[][] mapaInicial) {
        setFocusable(true);
        addKeyListener(this);

        this.mapa = mapaInicial;

        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                if (mapa[y][x] == 9) {
                    playerX = x;
                    playerY = y;
                    mapa[y][x] = 0;
                }
            }
        }

        // Cargar imágenes desde carpeta Recursos/
        jugador = new ImageIcon("src/sokoban/Recursos/jugador.png").getImage();
        caja = new ImageIcon("src/sokoban/Recursos/caja.png").getImage();
        meta = new ImageIcon("src/sokoban/Recursos/meta.png").getImage();
        pared = new ImageIcon("src/sokoban/Recursos/pared.png").getImage();
        piso = new ImageIcon("src/sokoban/Recursos/piso.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = 50;
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                g.drawImage(piso, x * size, y * size, size, size, null);

                switch (mapa[y][x]) {
                    case 1 -> g.drawImage(pared, x * size, y * size, size, size, null);
                    case 2 -> g.drawImage(caja, x * size, y * size, size, size, null);
                    case 3 -> g.drawImage(meta, x * size, y * size, size, size, null);
                }
            }
        }

        g.drawImage(jugador, playerX * size, playerY * size, size, size, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int newX = playerX, newY = playerY;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> newY--;
            case KeyEvent.VK_DOWN -> newY++;
            case KeyEvent.VK_LEFT -> newX--;
            case KeyEvent.VK_RIGHT -> newX++;
        }

        if (mapa[newY][newX] != 1) {
            if (mapa[newY][newX] == 2) {
                int boxNewX = newX + (newX - playerX);
                int boxNewY = newY + (newY - playerY);
                if (mapa[boxNewY][boxNewX] == 0 || mapa[boxNewY][boxNewX] == 3) {
                    mapa[boxNewY][boxNewX] = 2;
                    mapa[newY][newX] = 0;
                    playerX = newX;
                    playerY = newY;
                }
            } else {
                playerX = newX;
                playerY = newY;
            }
        }

        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}