package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.spaceinvaders.entities.*;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {

    private final Main game;

    private float alienSpeed;

    private final float worldWidth;
    private final float worldHeight;

    private final float playerWidth;
    private final float playerHeight;

    private final int NUM_COLUMNS = 8;
    private final int NUM_LINES = 3;
    private final int ALIEN_WIDTH = 30;
    private final int ALIENT_HEIGHT = 20;

    private Entity aliens[];

    public GameScreen(Main game)
    {
        this.game = game;
        this.aliens = new Entity[NUM_COLUMNS * NUM_LINES];

        float baseSpeed = 30f;

        this.worldWidth = game.viewport.getWorldWidth();
        this.worldHeight = game.viewport.getWorldHeight();
        this.playerWidth = game.playerSprite.getWidth();
        this.playerHeight = game.playerSprite.getWidth();

        if(game.currentLevel == 1)
        {
            this.alienSpeed = baseSpeed;
        }
        else
        {
            this.alienSpeed = baseSpeed * 2;
        }

        for (int i = 0; i < NUM_LINES; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                Texture currentTexture;
                float y = game.viewport.getWorldHeight();

                if (i % 3 == 0) {
                    currentTexture = new Texture("sprites/alien1Instance1.png");
                    y -= ALIENT_HEIGHT;
                }
                else if (i % 3 == 1) {
                    currentTexture = new Texture("sprites/alien2Instance1.png");
                    y -= ALIENT_HEIGHT * 2;
                }
                else {
                    currentTexture = new Texture("sprites/alien3Instance1.png");
                    y -= ALIENT_HEIGHT * 3;
                }

                int index = i * NUM_COLUMNS + j;

                aliens[index] = new Entity(game.spriteBatch, currentTexture, ALIEN_WIDTH, ALIENT_HEIGHT);
                aliens[index].move(ALIEN_WIDTH * j, y);
            }
        }
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        input(delta);
        draw();
        logic();
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
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            game.playerSprite.translateX(-speed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            game.playerSprite.translateX( speed * delta);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {

        }
        // PLAYER SHOOTS
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(game.backgroundTexture, 0, 0, worldWidth, worldHeight);
        game.playerSprite.draw(game.spriteBatch);

        for (int i = 0; i < NUM_COLUMNS * NUM_LINES; i++) {
            aliens[i].draw();
        }

        game.spriteBatch.end();
    }

    private void logic()
    {
        game.playerSprite.setX(MathUtils.clamp(game.playerSprite.getX(), 0, worldWidth - playerWidth));
    }
}
