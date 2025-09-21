/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static final AssetManager manager = new AssetManager();

    // Texturas del juego
    public static final String JUGADOR = "jugador.png"; // Cambia a tus nombres de archivo
    public static final String CAJA = "caja.png";
    public static final String META = "meta.png";
    public static final String PARED = "pared.png";
    public static final String PISO = "piso.png";
    public static final String NIVEL_LIBRE = "nivel_libre.png";
    public static final String NIVEL_BLOQUEADO = "nivel_bloqueado.png";

    // Skin para la UI
    public static final String SKIN = "uiskin.json"; // Asegúrate de tener estos archivos en 'assets'

    public static void load() {
        manager.load(JUGADOR, Texture.class);
        manager.load(CAJA, Texture.class);
        manager.load(META, Texture.class);
        manager.load(PARED, Texture.class);
        manager.load(PISO, Texture.class);
        manager.load(NIVEL_LIBRE, Texture.class);
        manager.load(NIVEL_BLOQUEADO, Texture.class);
        manager.load(SKIN, Skin.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
