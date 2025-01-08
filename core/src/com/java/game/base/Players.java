package com.java.game.base;

import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Players{
    protected float originalVelocity;
    protected float velocity;
    protected float life;
    protected float maxLife;
    protected boolean fullPath = false;
    protected int dropedCoins;
    protected float distanceWalked;
    protected float[] badEffectTime = { 0.f, 0.f, 0.f, 0.f }; // ROSA, VERDE, VERMELHA, AZUL

}
