/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private final SokobanGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;

    private int[][] mapa;        // mapa actual
    private int[][] mapaInicial; // copia del mapa original para reinicios

    private Texture caja, meta, pared, piso;

    private final int tileSize = 64;

    private boolean victoria = false;
    private BitmapFont font;

    // posición inicial del jugador
    private int startX, startY;

    public GameScreen(SokobanGame game, int nivel) {
        this.game = game;
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // cargar texturas
        caja  = Assets.manager.get(Assets.CAJA, Texture.class);
        meta  = Assets.manager.get(Assets.META, Texture.class);
        pared = Assets.manager.get(Assets.PARED, Texture.class);
        piso  = Assets.manager.get(Assets.PISO, Texture.class);

        font = new BitmapFont();
        font.getData().setScale(2f);

        // cargar mapa desde archivo
        mapaInicial = loadLevel("levels/level" + nivel + ".txt");

        reiniciarMapa();
    }

    private void reiniciarMapa() {
        // clonar mapa inicial
        mapa = new int[mapaInicial.length][];
        for (int y = 0; y < mapaInicial.length; y++) {
            mapa[y] = mapaInicial[y].clone();
        }

        // buscar jugador
        startX = 0;
        startY = 0;
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                if (mapa[y][x] == 9) {
                    startX = x;
                    startY = y;
                    mapa[y][x] = 0; // limpiar
                }
            }
        }
        player = new Player(startX, startY, tileSize);
        victoria = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // dibujar mapa
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                int px = x * tileSize;
                int py = (mapa.length - 1 - y) * tileSize;

                batch.draw(piso, px, py, tileSize, tileSize);

                switch (mapa[y][x]) {
                    case 1 -> batch.draw(pared, px, py, tileSize, tileSize);
                    case 2 -> batch.draw(caja, px, py, tileSize, tileSize);
                    case 3 -> batch.draw(meta, px, py, tileSize, tileSize);
                }
            }
        }

        // jugador animado
        player.update(delta);
        player.draw(batch, mapa.length);

        // mensaje de victoria
        if (victoria) {
            font.draw(batch, "¡Nivel completado!", 280, 300);
            font.draw(batch, "Presiona ENTER para volver al menu", 180, 260);
        }

        batch.end();

        // controles
        if (!victoria) {
            handleInput();
            if (isWin()) {
                victoria = true;
                Assets.manager.get(Assets.VICTORY_SOUND, Sound.class).play();
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                game.setScreen(new MenuScreen(game, null)); // TODO: pasa el Usuario real
                dispose();
            }
        }

        // 🔁 reinicio con tecla R
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Assets.manager.get(Assets.RESET_SOUND, Sound.class).play();
            reiniciarMapa();
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) mover(0, -1, Player.Direction.UP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) mover(0, 1, Player.Direction.DOWN);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) mover(-1, 0, Player.Direction.LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) mover(1, 0, Player.Direction.RIGHT);
    }

    private int[][] loadLevel(String path) {
        String text = Gdx.files.internal(path).readString();
        String[] lines = text.split("\\r?\\n");
        int rows = lines.length;
        int cols = lines[0].trim().split("\\s+").length;

        int[][] map = new int[rows][cols];

        for (int y = 0; y < rows; y++) {
            String[] tokens = lines[y].trim().split("\\s+");
            for (int x = 0; x < cols; x++) {
                map[y][x] = Integer.parseInt(tokens[x]);
            }
        }

        return map;
    }

    private void mover(int dx, int dy, Player.Direction dir) {
        int newX = player.x + dx;
        int newY = player.y + dy;

        // ✅ chequeo de límites
        if (newX < 0 || newX >= mapa[0].length || newY < 0 || newY >= mapa.length) return;

        if (mapa[newY][newX] == 1) return; // pared

        if (mapa[newY][newX] == 2) { // caja
            int boxNewX = newX + dx;
            int boxNewY = newY + dy;

            // ✅ chequeo de límites también para la caja
            if (boxNewX < 0 || boxNewX >= mapa[0].length || boxNewY < 0 || boxNewY >= mapa.length) return;

            if (mapa[boxNewY][boxNewX] == 0 || mapa[boxNewY][boxNewX] == 3) {
                mapa[boxNewY][boxNewX] = 2;
                mapa[newY][newX] = 0;
                player.x = newX;
                player.y = newY;
                player.setStatePush(dir);
                Assets.manager.get(Assets.PUSH_SOUND, Sound.class).play();
                return;
            }
        }

        // mover normal
        player.x = newX;
        player.y = newY;
        player.setStateWalk(dir);
        Assets.manager.get(Assets.MOVE_SOUND, Sound.class).play();
    }

    private boolean isWin() {
        for (int y = 0; y < mapa.length; y++) {
            for (int x = 0; x < mapa[y].length; x++) {
                if (mapa[y][x] == 2) return false;
            }
        }
        return true;
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { font.dispose(); }
}

