package io.github.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    FitViewport viewport;
    SpriteBatch spriteBatch;

    Texture backgroundImage;

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

        viewport = new FitViewport(viewport.getWorldWidth(), viewport.getWorldHeight());

        spriteBatch = new SpriteBatch();

        backgroundImage = new Texture("sprites/backgroundTexture");

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
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void render()
    {

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
}
