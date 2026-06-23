package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {

    private final Main game;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean clickingLeft;
    private boolean clickingRight;

    public GameScreen(Main game)
    {
        this.game = game;

    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        input(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        game.viewport.update(width, height, true);

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }

    private void input(float delta)
    {
        float speed = 70f;
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
        {
            game.playerSprite.translateX(-speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
        {
            game.playerSprite.translateX( speed * delta);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {
a
        }
        // PLAYER SHOOTS
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.spriteBatch.draw(game.backgroundTexture, 0, 0, worldWidth, worldHeight);
        game.playerSprite.draw(game.spriteBatch);

        game.spriteBatch.end();
    }
}
