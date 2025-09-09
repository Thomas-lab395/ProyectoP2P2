/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop22;

// LevelFrame.java
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class LevelFrame extends JFrame {
    private final Usuario usuario;
    private final UserManager userManager;
    private final int nivelIndex;
    private Instant started;
    private volatile boolean finished = false;

    public LevelFrame(Usuario usuario, UserManager userManager, int nivelIndex, int[][] mapa) {
        super("Sokoban - Nivel " + (nivelIndex + 1));
        this.usuario = usuario;
        this.userManager = userManager;
        this.nivelIndex = nivelIndex;

        SokobanPanel panel = new SokobanPanel(mapa, this::onWin);
        add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // si no terminó el juego pero cerró ventana, guardamos tiempo jugado hasta ese momento
                stopAndSave();
            }
        });

        started = Instant.now();
        // thread opcional para actualizar UI (por ejemplo, cronómetro) puede añadirse aquí
    }

    private void stopAndSave() {
        if (finished) return;
        finished = true;
        Instant now = Instant.now();
        long seconds = Duration.between(started, now).getSeconds();
        usuario.addSecondsPlayed(seconds);
        try {
            userManager.saveUser(usuario);
        } catch (IOException ex) {
            System.err.println("Error guardando usuario: " + ex.getMessage());
        }
    }

    private void onWin() {
        // marcar nivel, sumar score, guardar tiempo y usuario
        usuario.marcarNivelCompletado(nivelIndex);
        long seconds = Duration.between(started, Instant.now()).getSeconds();
        usuario.addSecondsPlayed(seconds);
        usuario.addScore(1000 - Math.min(900, (int) seconds)); // ejemplo simple de score
        try {
            userManager.saveUser(usuario);
        } catch (IOException e) {
            System.err.println("Error guardando usuario al ganar: " + e.getMessage());
        }
        // pequeña espera y cerrar
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "¡Guardado progreso!");
            dispose();
        });
    }
}

