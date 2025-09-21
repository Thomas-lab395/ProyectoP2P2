/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.IOException;

public class LoginScreen implements Screen {
    private final SokobanGame game;
    private final Stage stage;
    private final Skin skin;

    public LoginScreen(final SokobanGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = Assets.manager.get(Assets.SKIN, Skin.class);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        final TextField usernameField = new TextField("", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        final TextField nombreCompletoField = new TextField("", skin);

        final TextButton loginButton = new TextButton("Iniciar Sesion", skin);
        final TextButton registerButton = new TextButton("Registrarse", skin);
        final Label infoLabel = new Label("", skin);

        table.add(new Label("Usuario:", skin)).pad(10);
        table.add(usernameField).width(200).pad(10).row();
        table.add(new Label("Contrasena:", skin)).pad(10);
        table.add(passwordField).width(200).pad(10).row();
        table.add(new Label("Nombre Completo (Registro):", skin)).pad(10);
        table.add(nombreCompletoField).width(200).pad(10).row();
        table.add(loginButton).colspan(2).pad(10).row();
        table.add(registerButton).colspan(2).pad(10).row();
        table.add(infoLabel).colspan(2).pad(10);

        stage.addActor(table);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    final Usuario u = game.userManager.authenticate(usernameField.getText(), passwordField.getText());
                    if (u != null) {
                        // --- CORRECCIÓN AQUÍ ---
                        // Programamos el cambio de pantalla para el siguiente fotograma.
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.setScreen(new LevelSelectScreen(game, u));
                            }
                        });
                    } else {
                        infoLabel.setText("Usuario o contrasena incorrectos.");
                    }
                } catch (IOException e) {
                    infoLabel.setText("Error al iniciar sesion.");
                }
            }
        });

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    final Usuario u = game.userManager.register(usernameField.getText(), passwordField.getText(), nombreCompletoField.getText());
                     // --- CORRECCIÓN AQUÍ ---
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setScreen(new LevelSelectScreen(game, u));
                        }
                    });
                } catch (Exception e) {
                    infoLabel.setText("Error al registrar: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        // --- CORRECCIÓN AQUÍ ---
        // Ya no llamamos a dispose() desde aquí.
        // La clase SokobanGame se encargará de esto al cerrar el juego.
    }

    @Override
    public void dispose() {
        // Aquí se liberan los recursos de la pantalla de Login (el Stage)
        stage.dispose();
    }
}