/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static final AssetManager manager = new AssetManager();

    // Texturas del juego
    public static final String JUGADOR = "jugador.png";
    public static final String CAJA = "caja.png";
    public static final String META = "meta.png";
    public static final String PARED = "pared.png";
    public static final String PISO = "piso.png";
    public static final String NIVEL_LIBRE = "nivel_libre.png";
    public static final String NIVEL_BLOQUEADO = "nivel_bloqueado.png";

    // Skin para la UI
    public static final String SKIN = "uiskin.json";

    // Música (mp3)
    public static final String MENU_MUSIC   = "audio/menu.mp3";
    public static final String GAME_MUSIC   = "audio/game.mp3";

    // Sonidos (mp3) - también puedes usar .ogg si quieres menor peso
    public static final String MOVE_SOUND   = "audio/move.mp3";
    public static final String PUSH_SOUND   = "audio/pushBox.mp3";
    public static final String GOAL_SOUND   = "audio/goal.mp3";
    public static final String VICTORY_SOUND= "audio/victory.mp3";
    public static final String DEFEAT_SOUND = "audio/defeat.mp3";
    public static final String RESET_SOUND  = "audio/reset.mp3";
    public static final String CLICK_SOUND  = "audio/click.mp3";
    
        // Animaciones de movimiento
    public static final String PLAYER_WALK_UP1 = "walk_up1.png";
    public static final String PLAYER_WALK_UP2 = "walk_up2.png";
    public static final String PLAYER_WALK_DOWN1 = "front_walk1.png";
    public static final String PLAYER_WALK_DOWN2 = "front_walk2.png";
    public static final String PLAYER_WALK_LEFT1 = "walk_left1.png";
    public static final String PLAYER_WALK_LEFT2 = "walk_left2.png";
    public static final String PLAYER_WALK_RIGHT1 = "walk_right1.png";
    public static final String PLAYER_WALK_RIGHT2 = "walk_right2.png";

    // Animaciones de idle
    public static final String PLAYER_IDLE_UP = "back_idle.png";
    public static final String PLAYER_IDLE_DOWN = "player_down.png";
    public static final String PLAYER_IDLE_LEFT = "idle_left.png";
    public static final String PLAYER_IDLE_RIGHT = "idle_right.png";

    // Animaciones de push (ejemplo izquierda, pero hay para todos)
    public static final String PLAYER_PUSH_LEFT1 = "push_left1.png";
    public static final String PLAYER_PUSH_LEFT2 = "push_left2.png";

    public static void load() {
        // Texturas
        manager.load(JUGADOR, Texture.class);
        manager.load(CAJA, Texture.class);
        manager.load(META, Texture.class);
        manager.load(PARED, Texture.class);
        manager.load(PISO, Texture.class);
        manager.load(NIVEL_LIBRE, Texture.class);
        manager.load(NIVEL_BLOQUEADO, Texture.class);
        manager.load(SKIN, Skin.class);
        
        // Animaciones walk
manager.load(PLAYER_WALK_UP1, Texture.class);
manager.load(PLAYER_WALK_UP2, Texture.class);
manager.load(PLAYER_WALK_DOWN1, Texture.class);
manager.load(PLAYER_WALK_DOWN2, Texture.class);
manager.load(PLAYER_WALK_LEFT1, Texture.class);
manager.load(PLAYER_WALK_LEFT2, Texture.class);
manager.load(PLAYER_WALK_RIGHT1, Texture.class);
manager.load(PLAYER_WALK_RIGHT2, Texture.class);

// Animaciones idle
manager.load(PLAYER_IDLE_UP, Texture.class);
manager.load(PLAYER_IDLE_DOWN, Texture.class);
manager.load(PLAYER_IDLE_LEFT, Texture.class);
manager.load(PLAYER_IDLE_RIGHT, Texture.class);

// Animaciones push
manager.load(PLAYER_PUSH_LEFT1, Texture.class);
manager.load(PLAYER_PUSH_LEFT2, Texture.class);
// y así con las demás direcciones cuando tengas los sprites


        // Música
       manager.load(MENU_MUSIC, com.badlogic.gdx.audio.Music.class);
    manager.load(GAME_MUSIC, com.badlogic.gdx.audio.Music.class);

    // Sonidos
    manager.load(CLICK_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(MOVE_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(PUSH_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(GOAL_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(VICTORY_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(DEFEAT_SOUND, com.badlogic.gdx.audio.Sound.class);
    manager.load(RESET_SOUND, com.badlogic.gdx.audio.Sound.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}

