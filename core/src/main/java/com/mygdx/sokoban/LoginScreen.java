package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

    private Music loginMusic;

    public LoginScreen(final SokobanGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = Assets.manager.get(Assets.SKIN, Skin.class);

        // 🎶 cargar música del login
        loginMusic = Assets.manager.get(Assets.MENU_MUSIC, Music.class);
        loginMusic.setLooping(true);
        loginMusic.play();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        final TextField usernameField = new TextField("", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        final TextField nombreCompletoField = new TextField("", skin);

        final TextButton loginButton = new TextButton("Iniciar Sesión", skin);
        final TextButton registerButton = new TextButton("Registrarse", skin);
        final Label infoLabel = new Label("", skin);

        // Layout
        table.add(new Label("Usuario:", skin)).pad(10);
        table.add(usernameField).width(200).pad(10).row();
        table.add(new Label("Contraseña:", skin)).pad(10);
        table.add(passwordField).width(200).pad(10).row();
        table.add(new Label("Nombre Completo (Registro):", skin)).pad(10);
        table.add(nombreCompletoField).width(200).pad(10).row();
        table.add(loginButton).colspan(2).pad(10).row();
        table.add(registerButton).colspan(2).pad(10).row();
        table.add(infoLabel).colspan(2).pad(10);

        stage.addActor(table);

        // === EVENTOS ===
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.manager.get(Assets.CLICK_SOUND, com.badlogic.gdx.audio.Sound.class).play();

                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    infoLabel.setText("Por favor, completa usuario y contraseña.");
                    return;
                }

                try {
                    final Usuario u = game.userManager.authenticate(usernameField.getText(), passwordField.getText());
                    if (u != null) {
                        infoLabel.setText("¡Bienvenido " + u.getNombreCompleto() + "!");
                        Gdx.app.postRunnable(() -> {
                            stopMusic();
                            game.setScreen(new LevelSelectScreen(game, u));
                        });
                    } else {
                        infoLabel.setText("Usuario o contraseña incorrectos.");
                        Assets.manager.get(Assets.DEFEAT_SOUND, com.badlogic.gdx.audio.Sound.class).play();
                    }
                } catch (IOException e) {
                    infoLabel.setText("Error al iniciar sesión.");
                }
            }
        });

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Assets.manager.get(Assets.CLICK_SOUND, com.badlogic.gdx.audio.Sound.class).play();

                if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nombreCompletoField.getText().isEmpty()) {
                    infoLabel.setText("Completa usuario, contraseña y nombre.");
                    return;
                }

                try {
                    final Usuario u = game.userManager.register(
                            usernameField.getText(),
                            passwordField.getText(),
                            nombreCompletoField.getText()
                    );
                    infoLabel.setText("Usuario creado: " + u.getNombreCompleto());
                    Gdx.app.postRunnable(() -> {
                        stopMusic();
                        game.setScreen(new LevelSelectScreen(game, u));
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
        if (loginMusic != null && !loginMusic.isPlaying()) {
            loginMusic.setLooping(true);
            loginMusic.play();
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
        stopMusic(); // 👈 solo parar, no liberar (lo hace Assets)
    }

    private void stopMusic() {
        if (loginMusic != null && loginMusic.isPlaying()) {
            loginMusic.stop();
        }
    }
}
