package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.spaceinvaders.entities.*;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private final Main game;

    private Stage stage;
    private Skin skin;

    private Music backgroundMusic;
    private Sound shootingSFX;
    private Sound explosionSFX;

    private final float WORLD_WIDTH;
    private final float WORLD_HEIGHT;

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

    public GameScreen(Main game)
    {
        this.game = game;
        this.frameCount = 0;

        this.WORLD_WIDTH = game.viewport.getWorldWidth();
        this.WORLD_HEIGHT = game.viewport.getWorldHeight();

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
            if (i == 1) continue;

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

                if (j == 4) continue;

                aliens[index] = new Alien(game.spriteBatch, currentTexture, alternateTexture, ENTITY_WIDTH * j, y, ENTITY_WIDTH, ENTITY_HEIGHT);
            }
        }

        Texture playerTexture = new Texture("sprites/playerSprite.png");
        this.player = new Entity(game.spriteBatch, playerTexture, WORLD_WIDTH / 2, 0, ENTITY_WIDTH, ENTITY_HEIGHT);
        playerSpeed = ENTITY_WIDTH;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Spacing around.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        shootingSFX = Gdx.audio.newSound(Gdx.files.internal("effects/SfxLazer.ogg"));

        // Configuração da Skin e Interface
        skin = new Skin();
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        Label lblLevel = new Label("Level: " + game.getCurrentLevel(), skin);
        Label lblPoints = new Label("Points: 0", skin);
        Label lblLives = new Label("Lives: 3", skin);
        Label sep1 = new Label(" | ", skin);
        Label sep2 = new Label(" | ", skin);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        table.add(lblLevel).pad(10);
        table.add(sep1);
        table.add(lblPoints).pad(10);
        table.add(sep2);
        table.add(lblLives).pad(10);

        stage.addActor(table);
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
        stage.act(delta);

        this.frameCount++;
    }

    private void updateAliens(float delta) {
        boolean bateramNaBorda = false;

        // 1. Calcula quanto eles devem andar NESSE frame
        float deslocamentoX = alienSpeed * direction * delta;

        // 2. Aplica o translate horizontal e checa as bordas
        for (Entity alien : aliens) {
            if (alien == null) continue;

            // Move apenas o delta desse frame
            alien.move(deslocamentoX, 0); 

            // Checa a borda usando o getX() atualizado do seu alien
            if ((direction == 1 && alien.getX() + alien.getWidth() >= WORLD_WIDTH) || (direction == -1 && alien.getX() <= 0)) {
                bateramNaBorda = true;
            }
        }

        // 3. Se bateram na borda, inverte a direção e dá um translate para baixo
        if (bateramNaBorda) {
            direction *= -1; // Inverte o sentido

            for (Entity alien : aliens) {
                if (alien == null) continue;

                // Só desce se não estiver abaixo do limite do jogo
                if (alien.getY() - DROP_AMOUNT > LIMIT_Y) {
                    // translate: anda 0 no X e -DROP_AMOUNT no Y (para baixo)
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

    @Override
    public void pause() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
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
    public void dispose() {
        stage.dispose();
        skin.dispose();

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

    private void input(float delta)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            game.playerSprite.translateX(-playerSpeed * delta);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            game.playerSprite.translateX(playerSpeed * delta);
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
        {

        }
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(game.backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        game.playerSprite.draw(game.spriteBatch);

        for (int i = 0; i < NUM_COLUMNS * NUM_LINES; i++) {
            if (aliens[i] != null) { // Evita erro se o alien morrer
                aliens[i].draw();
            }
        }

        game.spriteBatch.end();
    }

    private void logic()
    {
        game.playerSprite.setX(MathUtils.clamp(game.playerSprite.getX(), 0, WORLD_WIDTH - player.getWidth()));
    }
}
