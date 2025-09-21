package com.mygdx.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SokobanGame extends Game {
    public SpriteBatch batch;
    public UserManager userManager;

    @Override
    public void create() {
        // Inicializar objetos globales
        batch = new SpriteBatch();
        userManager = new UserManager();

        // Cargar todos los assets antes de mostrar pantalla
        Assets.load();
        Assets.manager.finishLoading();

        // Pantalla inicial
        setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render(); // delega el render a la pantalla activa
    }

    @Override
    public void dispose() {
        // Liberar recursos en orden
        if (getScreen() != null) {
            getScreen().dispose();
        }
        batch.dispose();
        Assets.dispose();
    }
}
