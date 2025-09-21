package com.mygdx.sokoban.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.sokoban.SokobanGame;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Sokoban");
    config.setWindowedMode(800, 600);
    config.useVsync(true);
    config.setForegroundFPS(60);

    new Lwjgl3Application(new SokobanGame(), config);
}
}