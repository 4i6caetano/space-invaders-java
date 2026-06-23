package io.github.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    int currentLevel = 1;

    FitViewport viewport;

    SpriteBatch spriteBatch;

    Texture backgroundTexture;

    Sprite alien1Instance1;
    Sprite alien1Instance2;
    Sprite alien2Instance1;
    Sprite alien2Instance2;
    Sprite alien3Instance1;
    Sprite alien3Instance2;

    Sprite playerSprite;

    @Override
    public void create()
    {

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());;

        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("background/backgroundTexture.png");

        Texture playerTexture = new Texture("sprites/playerSprite.png");
        playerSprite = new Sprite(playerTexture);

        // Resizes and adjustments on sprites

        playerSprite.setSize(60, 40);
        playerSprite.setPosition(1, 1);

        setScreen(new GameScreen(this));
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void dispose()
    {
        spriteBatch.dispose();
        backgroundTexture.dispose();
    }
}
