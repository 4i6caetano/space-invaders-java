package io.github.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    SpriteBatch batch;
    Sprite entitySprite;

    public Entity(SpriteBatch batch, Texture texture) {
        this(batch, texture, 0, 0, 60, 40);
    }

    public Entity(SpriteBatch batch, Texture texture, float width, float height) {
        this(batch, texture, 0, 0, width, height);
    }

    public Entity(SpriteBatch batch, Texture texture, float x, float y, float width, float height) {
        this.batch = batch;
        
        this.entitySprite = new Sprite(texture);
        this.entitySprite.setSize(width, height);
        this.entitySprite.setPosition(x, y);
    }

    public float getX() {
        return this.entitySprite.getX();
    }

    public float getY() {
        return this.entitySprite.getY();
    }

    public float getWidth() {
        return this.entitySprite.getWidth();
    }

    public float getHeight() {
        return this.entitySprite.getHeight();
    }

    public void move(float x, float y) {
        this.entitySprite.setPosition(x, y);
    }

    public void translate(float x, float y) {
        this.entitySprite.translate(x, y);
    }

    public void draw() {
        this.entitySprite.draw(this.batch);
    }
}
