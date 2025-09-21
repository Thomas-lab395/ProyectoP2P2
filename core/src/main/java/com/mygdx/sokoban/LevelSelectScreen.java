/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelSelectScreen implements Screen {
    private final SokobanGame game;
    private final Usuario usuario;
    private final Stage stage;
    private final Skin skin;

    public LevelSelectScreen(SokobanGame game, Usuario usuario) {
        this.game = game;
        this.usuario = usuario;
        this.stage = new Stage(new ScreenViewport());
        skin = Assets.manager.get(Assets.SKIN, Skin.class);

        Table table = new Table();
        table.setFillParent(true);
        table.top().pad(20);

        table.add(new Label("Selecciona un Nivel", skin, "title")).colspan(4).pad(20).row();

        boolean[] completados = usuario.getNivelesCompletados();
        boolean desbloqueadoAnterior = true;

        for (int i = 0; i < 7; i++) {
            boolean desbloqueadoActual = (i == 0) || desbloqueadoAnterior;
            
            Texture tex = desbloqueadoActual ?
                Assets.manager.get(Assets.NIVEL_LIBRE, Texture.class) :
                Assets.manager.get(Assets.NIVEL_BLOQUEADO, Texture.class);
            
            Button levelButton = new Button(skin);
            levelButton.add(new Image(tex)).size(80, 80);
            levelButton.row();
            levelButton.add(new Label(String.valueOf(i + 1), skin));

            final int nivelIndex = i;
            if (desbloqueadoActual) {
                 levelButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        game.setScreen(new GameScreen(game, usuario, nivelIndex));
                        dispose();
                    }
                });
            }

            table.add(levelButton).size(100, 100).pad(15);
            if ((i + 1) % 4 == 0) {
                table.row();
            }
            
            desbloqueadoAnterior = completados[i];
        }
        
        stage.addActor(table);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.9f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    
    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}