/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.sokoban;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    public int x, y; // posición en la grilla
    private int tileSize;

    // Estados
    private enum State { IDLE, WALK, PUSH }
    private State currentState = State.IDLE;

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDir = Direction.DOWN;

    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private Animation<TextureRegion> idleUp, idleDown, idleLeft, idleRight;
    private Animation<TextureRegion> pushUp, pushDown, pushLeft, pushRight;

    private Animation<TextureRegion> currentAnimation;
    private float stateTime;

    public Player(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.tileSize = tileSize;
        this.stateTime = 0f;

        // === WALK ===
        walkUp = makeAnim("back_walk1.png", "back_walk2.png");
        walkDown = makeAnim("front_walk1.png", "front_walk2.png");
        walkLeft = makeAnim("walk_left1.png", "walk_left2.png");
        walkRight = makeAnim("walk_right1.png", "walk_right2.png");

        // === IDLE ===
        idleUp = makeAnim("back_idle.png");
        idleDown = makeAnim("player_down.png");
        idleLeft = makeAnim("idle_left.png");
        idleRight = makeAnim("idle_right.png");

        // === PUSH ===
        pushUp = makeAnim("push_up1.png", "push_up2.png");
        pushDown = makeAnim("push_down1.png", "push_down2.png");
        pushLeft = makeAnim("push_left1.png", "push_left2.png");
        pushRight = makeAnim("push_right1.png", "push_right2.png");

        // Animación inicial
        currentAnimation = idleDown;
    }

    private Animation<TextureRegion> makeAnim(String... files) {
        TextureRegion[] frames = new TextureRegion[files.length];
        for (int i = 0; i < files.length; i++) {
            Texture tex = Assets.manager.get("png/" + files[i], Texture.class); // carpeta png/
            frames[i] = new TextureRegion(tex);
        }
        Animation<TextureRegion> anim = new Animation<>(0.15f, frames);
        if (files.length > 1) anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        return anim;
    }

    public void setStateWalk(Direction dir) {
        currentDir = dir;

