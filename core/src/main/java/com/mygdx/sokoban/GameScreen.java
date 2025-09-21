/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class GameScreen implements Screen {
    private final SokobanGame game;
    private final Usuario usuario;
    private final int nivelIndex;
    private final int[][] mapaOriginal;
    private int[][] mapa;
    private int playerX, playerY;
    private final int TILE_SIZE = 64;

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    
    private final Stage hudStage;
    private final Label timerLabel;
    private final Instant startTime;
    private final Thread timerThread;
    private volatile boolean gameRunning = true;

    public GameScreen(SokobanGame game, Usuario usuario, int nivelIndex) {
        this.game = game;
        this.usuario = usuario;
        this.nivelIndex = nivelIndex;
        
        mapaOriginal = Nivel.getNivelByIndex(nivelIndex);
        this.mapa = new int[mapaOriginal.length][];
        for (int i = 0; i < mapaOriginal.length; i++) {
            this.mapa[i] = mapaOriginal[i].clone();
        }

        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                if (mapa[y][x] == 9) {
                    playerX = x;
                    playerY = y;
                    mapa[y][x] = 0;
                }
            }
        }
        
        camera = new OrthographicCamera();
        viewport = new FitViewport((float)mapa[0].length * TILE_SIZE, (float)mapa.length * TILE_SIZE, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        
        hudStage = new Stage(new FitViewport(800, 600));
        Skin skin = Assets.manager.get(Assets.SKIN, Skin.class);
        Table hudTable = new Table();
        hudTable.top().left();
        hudTable.setFillParent(true);
        timerLabel = new Label("Tiempo: 0s", skin);
        hudTable.add(timerLabel).pad(10);
        hudStage.addActor(hudTable);
        
        startTime = Instant.now();
        timerThread = new Thread(() -> {
            while (gameRunning) {
                long seconds = Duration.between(startTime, Instant.now()).getSeconds();
                Gdx.app.postRunnable(() -> timerLabel.setText("Tiempo: " + seconds + "s"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    @Override
    public void render(float delta) {
        handleInput();
        
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        camera.update();
        
        // --- CORRECCIÓN 1: Usando getters ---
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        
        Texture piso = Assets.manager.get(Assets.PISO, Texture.class);
        Texture pared = Assets.manager.get(Assets.PARED, Texture.class);
        Texture caja = Assets.manager.get(Assets.CAJA, Texture.class);
        Texture meta = Assets.manager.get(Assets.META, Texture.class);
        Texture jugador = Assets.manager.get(Assets.JUGADOR, Texture.class);

        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                int screenY = mapa.length - 1 - y;
                game.getBatch().draw(piso, x * TILE_SIZE, screenY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                
                if (mapaOriginal[y][x] == 3) {
                    game.getBatch().draw(meta, x * TILE_SIZE, screenY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
                
                Texture toDraw = null;
                switch(mapa[y][x]) {
                    case 1: toDraw = pared; break;
                    case 2: toDraw = caja; break;
                }
                if (toDraw != null) {
                    game.getBatch().draw(toDraw, x * TILE_SIZE, screenY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        game.getBatch().draw(jugador, playerX * TILE_SIZE, (mapa.length - 1 - playerY) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        game.getBatch().end();
        
        hudStage.getViewport().apply();
        hudStage.act(delta);
        hudStage.draw();
    }
    
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) move(0, -1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) move(0, 1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) move(-1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) move(1, 0);
    }
    
    private void move(int dx, int dy) {
        if (!gameRunning) return;
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newY < 0 || newY >= mapa.length || newX < 0 || newX >= mapa[0].length || mapa[newY][newX] == 1) return;
        
        if (mapa[newY][newX] == 2) {
            int boxNewX = newX + dx;
            int boxNewY = newY + dy;
            if (boxNewY < 0 || boxNewY >= mapa.length || boxNewX < 0 || boxNewX >= mapa[0].length || mapa[boxNewY][boxNewX] == 1 || mapa[boxNewY][boxNewX] == 2) return;
            
            mapa[boxNewY][boxNewX] = 2;
            mapa[newY][newX] = 0;
        }
        playerX = newX;
        playerY = newY;
        
        if (checkWin()) onWin();
    }
    
    private boolean checkWin() {
       for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                if (mapaOriginal[y][x] == 3 && mapa[y][x] != 2) {
                    return false;
                }
            }
        }
        return true;
    }

    private void onWin() {
        if (!gameRunning) return;
        gameRunning = false;
        timerThread.interrupt();
        long seconds = Duration.between(startTime, Instant.now()).getSeconds();
        usuario.addSecondsPlayed(seconds);
        usuario.addScore(1000 - Math.min(900, (int) seconds));
        usuario.marcarNivelCompletado(nivelIndex);
        
        try {
            // --- CORRECCIÓN 1: Usando getters ---
            game.getUserManager().saveUser(usuario);
        } catch (IOException e) {
            // --- CORRECCIÓN 2: Mejor manejo de errores ---
            Skin skin = Assets.manager.get(Assets.SKIN, Skin.class);
            new Dialog("Error", skin)
                .text("No se pudo guardar el progreso.")
                .button("OK")
                .show(hudStage);
        }
        
        Gdx.app.postRunnable(() -> {
            // --- CORRECCIÓN 3: Eliminado el dispose() ---
            game.setScreen(new LevelSelectScreen(game, usuario));
        });
    }

    @Override
    public void dispose() {
        gameRunning = false;
        if (timerThread != null) timerThread.interrupt();
        hudStage.dispose();
    }
    
    @Override public void show() { Gdx.input.setInputProcessor(Gdx.input.getInputProcessor()); }
    @Override public void resize(int width, int height) { viewport.update(width, height, true); hudStage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}