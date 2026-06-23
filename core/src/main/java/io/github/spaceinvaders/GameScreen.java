package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.spaceinvaders.entities.*;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private final Main game;

    private Music backgroundMusic;
    private Sound shootingSFX;
    private Sound explosionSFX;

    private final float WORLD_WIDTH;
    private final float WORLD_HEIGHT;

    private final Texture bulletTexture;
    private SpriteBatch spriteBatch;

    private final int NUM_COLUMNS = 8;
    private final int NUM_LINES = 6;

    private Entity player;
    private float playerSpeed;

    private Alien aliens[];
    private int direction = 1;
    private final float DROP_AMOUNT;
    private final float LIMIT_Y;
    private float alienSpeed;

    private int frameCount;

    private Entity playerBullet;

    public GameScreen(Main game)
    {
        this.game = game;
        this.frameCount = 0;

        this.WORLD_WIDTH = game.viewport.getWorldWidth();
        this.WORLD_HEIGHT = game.viewport.getWorldHeight();

        this.bulletTexture = game.playerBulletTexture;
        this.spriteBatch = game.spriteBatch;

        float ENTITY_WIDTH = WORLD_WIDTH / (NUM_COLUMNS + 2);
        float ENTITY_HEIGHT = ENTITY_WIDTH / 2;

        DROP_AMOUNT = ENTITY_HEIGHT / 2;
        LIMIT_Y = ENTITY_HEIGHT * 3;

        if(game.currentLevel == 1) {
            this.alienSpeed = ENTITY_WIDTH * 4;
        }
        else {
            this.alienSpeed = ENTITY_WIDTH * 8;
        }


        Texture alien1 = new Texture("sprites/alien1Instance1.png");
        Texture alien1Texture2 = new Texture("sprites/alien1Instance2.png");

        Texture alien2 = new Texture("sprites/alien2Instance1.png");
        Texture alien2Texture2 = new Texture("sprites/alien2Instance2.png");

        Texture alien3 = new Texture("sprites/alien3Instance1.png");
        Texture alien3Texture2 = new Texture("sprites/alien3Instance2.png");

        this.aliens = new Alien[NUM_COLUMNS * NUM_LINES];

        int linesPerAlienType = MathUtils.floor(NUM_LINES / 3);
        for (int i = 0; i < NUM_LINES; i++) {

            Texture currentTexture;
            Texture alternateTexture;

            if (i < linesPerAlienType) {
                currentTexture = alien3;
                alternateTexture = alien3Texture2;
            }
            else if (i >= linesPerAlienType && i < linesPerAlienType * 2) {
                currentTexture = alien2;
                alternateTexture = alien2Texture2;
            }
            else {
                currentTexture = alien1;
                alternateTexture = alien1Texture2;
            }

            float y = WORLD_HEIGHT - (ENTITY_HEIGHT * (i + 1) + ENTITY_HEIGHT / 3 * i);

            for (int j = 0; j < NUM_COLUMNS; j++) {
                int index = i * NUM_COLUMNS + j;

                aliens[index] = new Alien(game.spriteBatch, currentTexture, alternateTexture, ENTITY_WIDTH * j, y, ENTITY_WIDTH, ENTITY_HEIGHT);
            }
        }

        Texture playerTexture = new Texture("sprites/playerSprite.png");
        this.player = new Entity(game.spriteBatch, playerTexture, WORLD_WIDTH / 2, 0, ENTITY_WIDTH, ENTITY_HEIGHT);
        playerSpeed = ENTITY_WIDTH;
    }

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Spacing around.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        shootingSFX = Gdx.audio.newSound(Gdx.files.internal("effects/SfxLazer.ogg"));
        explosionSFX = Gdx.audio.newSound(Gdx.files.internal("effects/SfxExplosion.ogg"));
    }

    @Override
    public void render(float delta) {
        input(delta);
        if (this.frameCount % 20 == 0) {
            updateAliens(delta);
            this.frameCount = 0;
        }
        draw();
        logic();

        this.frameCount++;
    }

    private void updateAliens(float delta) {
        boolean hitTheBorder = false;

        float deltaX = alienSpeed * direction * delta;

        for (Entity alien : aliens) {
            if (alien == null) continue;

            alien.move(deltaX, 0);

            if ((direction == 1 && alien.getX() + alien.getWidth() >= WORLD_WIDTH) || (direction == -1 && alien.getX() <= 0)) {
                hitTheBorder = true;
            }
        }

        if (hitTheBorder) {
            direction *= -1;

            for (Entity alien : aliens) {
                if (alien == null) continue;

                if (alien.getY() - DROP_AMOUNT > LIMIT_Y) {
                    alien.move(0, -DROP_AMOUNT);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        game.viewport.update(width, height, true);
    }

    private void input(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            game.player.translate(-playerSpeed * delta, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            game.player.translate(playerSpeed * delta, 0);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if(playerBullet == null)
            {
                float bulletX = game.player.getX() + (game.player.getWidth() / 2f) - (bulletTexture.getWidth() / 2f);
                float bulletY = game.player.getY() + game.player.getHeight();
                playerBullet = new PlayerBullet(spriteBatch, bulletTexture, bulletX, bulletY);
            }
        }
    }

    @Override
    public void resume() {
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    @Override
    public void hide() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }

        if (shootingSFX != null) {
            shootingSFX.dispose();
        }

        if (explosionSFX != null) {
            explosionSFX.dispose();
        }
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(game.backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        game.player.draw();

        for (int i = 0; i < NUM_COLUMNS * NUM_LINES; i++) {
            if (aliens[i] != null) { // Evita erro se o alien morrer
                aliens[i].draw();
            }
        }

        game.spriteBatch.end();
    }

    private void logic()
    {
        game.player.move(MathUtils.clamp(game.player.getX(), 0, WORLD_WIDTH - player.getWidth()), 0);
    }
}
