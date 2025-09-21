package com.mygdx.sokoban.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.sokoban.SokobanGame;

/**
 * Punto de entrada de la aplicación desktop (LWJGL3).
 * Aquí se configura la ventana y se arranca el juego.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // Configuración de la ventana
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Sokoban");           // Título de la ventana
        config.setWindowedMode(800, 600);     // Resolución inicial
        config.useVsync(true);                // Activa VSync
        config.setForegroundFPS(60);          // FPS máximo

        // Arranca el juego con la configuración
        new Lwjgl3Application(new SokobanGame(), config);
    }
}
