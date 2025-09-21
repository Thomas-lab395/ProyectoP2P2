/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
    private final SokobanGame game;
    private final Stage stage;
    private final Skin skin;

    private Music menuMusic;
    private Sound clickSound;
    private final Usuario usuario;

    public MenuScreen(final SokobanGame game, Usuario usuario) {
        this.game = game;
        this.usuario = usuario;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = Assets.manager.get(Assets.SKIN, Skin.class);

        // Música
        menuMusic = Assets.manager.get(Assets.MENU_MUSIC, Music.class);
        menuMusic.setLooping(true);
        menuMusic.play();

        // Sonido click
        clickSound = Assets.manager.get(Assets.CLICK_SOUND, Sound.class);

        // UI con Scene2D
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        String nombre = (usuario != null) ? usuario.getNombreCompleto() : "Jugador";
        Label lblBienvenida = new Label("Bienvenido " + nombre, skin);

        TextButton btnJugar = new TextButton("Seleccionar Nivel", skin);
        TextButton btnPerfil = new TextButton("Mi Perfil", skin);
        TextButton btnSalir = new TextButton("Salir", skin);

        table.add(lblBienvenida).colspan(2).pad(20).row();
        table.add(btnJugar).width(200).pad(10).row();
        table.add(btnPerfil).width(200).pad(10).row();
        table.add(btnSalir).width(200).pad(10).row();

        stage.addActor(table);

        // === EVENTOS ===
        btnJugar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(1f);
                Gdx.app.postRunnable(() -> {
                    stopMusic();
                    game.setScreen(new LevelSelectScreen(game, usuario));
                });
            }
        });

        btnPerfil.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(1f);
                if (usuario != null) {
                    Dialog perfil = new Dialog("Perfil", skin);
                    perfil.text(
                            "Usuario: " + usuario.getUsername() + "\n" +
                            "Nombre: " + usuario.getNombreCompleto() + "\n" +
                            "Niveles completados: " + countCompleted(usuario.getNivelesCompletados())
                    );
                    perfil.button("OK");
                    perfil.show(stage);
                } else {
                    Dialog perfil = new Dialog("Perfil", skin);
                    perfil.text("Modo invitado");
                    perfil.button("OK");
                    perfil.show(stage);
                }
            }
        });

        btnSalir.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clickSound.play(1f);
                stopMusic();
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (menuMusic != null && !menuMusic.isPlaying()) {
            menuMusic.setLooping(true);
            menuMusic.play();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {
        stopMusic();
    }

    @Override
    public void dispose() {
        stage.dispose();
        // No liberar music/sounds aquí, Assets.dispose() lo hará
    }

    private void stopMusic() {
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
        }
    }

    private int countCompleted(boolean[] niveles) {
        int c = 0;
        for (boolean b : niveles) if (b) c++;
        return c;
    }
}

