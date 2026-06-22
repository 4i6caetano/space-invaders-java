package io.github.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

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
        setScreen(new FirstScreen());

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());;

        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("background/backgroundTexture.png");

        Texture alien1Texture1 = new Texture("sprites/alien1Instance1.png");
        alien1Instance1 = new Sprite(alien1Texture1);

        Texture alien1Texture2 = new Texture("sprites/alien1Instance2.png");
        alien1Instance2 = new Sprite(alien1Texture2);

        Texture alien2Texture1 = new Texture("sprites/alien2Instance1.png");
        alien2Instance1 = new Sprite(alien2Texture1);

        Texture alien2Texture2 = new Texture("sprites/alien2Instance2.png");
        alien2Instance2 = new Sprite(alien2Texture2);

        Texture alien3Texture1 = new Texture("sprites/alien3Instance1.png");
        alien3Instance1 = new Sprite(alien3Texture1);

        Texture alien3Texture2 = new Texture("sprites/alien3Instance2.png");
        alien3Instance2 = new Sprite(alien3Texture2);

        Texture playerTexture = new Texture("sprites/playerSprite.png");
        playerSprite = new Sprite(playerTexture);

        // Resizes and adjustments on sprites

        playerSprite.setSize(70, 50);
        playerSprite.setPosition(1, 1);
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void render()
    {
        input();
        draw();
    }

    public void resume()
    {

    }

    public void pause()
    {

    }

    public void dispose()
    {

    }

    // these live inside the 'render' method.

    private void input()
    {
        float speed = 70f;
        float delta = Gdx.graphics.getDeltaTime();

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            playerSprite.translateX(-speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            playerSprite.translateX( speed * delta);
        }
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        playerSprite.draw(spriteBatch);

        spriteBatch.end();
    }
}
