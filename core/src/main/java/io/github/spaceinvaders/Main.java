package io.github.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    int currentLevel = 1;
    int totalPoints = 0;
    int livesLeft = 3;

    FitViewport viewport;

    SpriteBatch spriteBatch;

    Texture backgroundTexture;

    @Override
    public void create()
    {

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());;

        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("background/backgroundTexture.png");

        setScreen(new MenuScreen(this));
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
