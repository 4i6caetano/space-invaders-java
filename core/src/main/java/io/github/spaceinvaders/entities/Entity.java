package io.github.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
    SpriteBatch batch;
    Sprite EntitySprite;

    public Entity(SpriteBatch batch, Texture texture) {
        this(batch, texture, 60, 40);
    }

    public Entity(SpriteBatch batch, Texture texture, float width, float height) {
        this.batch = batch;
        
        this.EntitySprite = new Sprite(texture);
        this.EntitySprite.setSize(width, height);
    }

    public void move(float newX, float newY) {
        this.EntitySprite.setPosition(newX, newY);
    }

    public void draw() {
        this.EntitySprite.draw(this.batch);
    }
}
