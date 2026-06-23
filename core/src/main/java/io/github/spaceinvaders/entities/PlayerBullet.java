package io.github.spaceinvaders.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerBullet extends Entity {

    private final float speed = 100f;

    public PlayerBullet(SpriteBatch batch, Texture texture, float x, float y)
    {
        super(batch, texture, x, y, 5, 15);
    }

    public void update(float delta)
    {
        translate(0, speed * delta);
    }

    public boolean isOutOfBounds(float worldHeight)
    {
        return getY() > worldHeight;
    }
}
