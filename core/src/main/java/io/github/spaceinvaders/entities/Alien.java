package io.github.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien extends Entity {
    Texture entityTexture, alternateTexture;

    public Alien(SpriteBatch batch, Texture texture1, Texture texture2, float x, float y, float width, float height) {
        super(batch, texture1, x, y, width, height);

        entityTexture = texture1;
        alternateTexture = texture2;
    }

    public void changeTexture() {
        if (this.entitySprite.getTexture().equals(this.entityTexture)) {
            this.entitySprite.setTexture(this.alternateTexture);
        }
        else {
            this.entitySprite.setTexture(this.entityTexture);
        }
    }

    @Override
    public void move(float xAmount, float yAmount) {
        super.move(xAmount, yAmount);
        changeTexture();
    }

    @Override
    public void translate(float x, float y) {
        super.translate(x, y);
        changeTexture();
    }
}
