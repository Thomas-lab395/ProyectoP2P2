package com.mygdx.sokoban;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SokobanGame extends Game {
    public SpriteBatch batch;
    public UserManager userManager;

    // --- MÉTODOS GETTER AÑADIDOS ---
    public SpriteBatch getBatch() {
        return batch;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        userManager = new UserManager();
        Assets.load();
        Assets.manager.finishLoading();
        this.setScreen(new LoginScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.dispose();
        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}