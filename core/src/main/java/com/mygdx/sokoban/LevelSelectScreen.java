/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LevelSelectScreen implements Screen {
    private final SokobanGame game;
    private final Usuario usuario;
    private final Stage stage;
    private final Skin skin;

    private Music levelMusic;

    public LevelSelectScreen(final SokobanGame game, final Usuario usuario) {
        this.game = game;
        this.usuario = usuario;
        this.stage = new Stage(new ScreenViewport());
        this.skin = Assets.manager.get(Assets.SKIN, Skin.class);
        Gdx.input.setInputProcessor(stage);

        // 🎶 Música para selección de niveles
        levelMusic = Assets.manager.get(Assets.MENU_MUSIC, Music.class);
        levelMusic.setLooping(true);
        levelMusic.play();

        // Tabla principal
        Table table = new Table();
        table.setFillParent(true);
        table.top().pad(20);

        // Mostrar niveles en grilla 3xN
        Table levelsTable = new Table();
        boolean[] completados = (usuario != null) ? usuario.getNivelesCompletados() : new boolean[5];
        int totalNiveles = completados.length;

        for (int i = 0; i < totalNiveles; i++) {
            boolean desbloqueado = (i == 0) || completados[i - 1]; // primero libre, luego depende del anterior
            String icono = desbloqueado ? Assets.NIVEL_LIBRE : Assets.NIVEL_BLOQUEADO;

            ImageButton btnNivel = new ImageButton(
                    new TextureRegionDrawable(Assets.manager.get(icono, com.badlogic.gdx.graphics.Texture.class))
            );

            if (desbloqueado) {
                final int nivel = i;
                btnNivel.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        stopMusic();
                        // 👇 Pasamos el número de nivel (+1 porque los archivos se llaman level1.txt, level2.txt…)
                        game.setScreen(new GameScreen(game, nivel + 1));
                    }
                });
            } else {
                btnNivel.setDisabled(true);
            }

            levelsTable.add(btnNivel).size(100).pad(10);

            if ((i + 1) % 3 == 0) { // salto de fila cada 3 niveles
                levelsTable.row();
            }
        }

        // Botón volver al menú
        TextButton btnVolver = new TextButton("Volver", skin);
        btnVolver.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stopMusic();
                game.setScreen(new MenuScreen(game, usuario));
            }
        });

        table.add(levelsTable).row();
        table.add(btnVolver).padTop(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (levelMusic != null && !levelMusic.isPlaying()) {
            levelMusic.setLooping(true);
            levelMusic.play();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() { stopMusic(); }

    @Override
    public void dispose() {
        stage.dispose();
        stopMusic(); // 👈 solo parar, no liberar (lo maneja Assets)
    }

    private void stopMusic() {
        if (levelMusic != null && levelMusic.isPlaying()) {
            levelMusic.stop();
        }
    }
}
