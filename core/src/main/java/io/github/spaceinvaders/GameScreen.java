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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import io.github.spaceinvaders.entities.*;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen implements Screen {
    private final Main game;

    private Music backgroundMusic;
    private Sound shootingSFX;
    private Sound explosionSFX;
    
    private Stage stage;
    private Label lbPoints;
    private Label lbLevel;
    private Entity lives[];

    private final float WORLD_WIDTH;
    private final float WORLD_HEIGHT;

    private final int NUM_COLUMNS = 6;
    private final int NUM_LINES = 4;
    private final int TOTAL_NUMBER_OF_ALIENS = NUM_COLUMNS * NUM_LINES;

    private Entity player;
    private float playerSpeed;

    private PlayerBullet playerBullet;
    private final Texture bulletTexture;

    private Alien explosion;
    private final Texture explosionTexture;
    private final Texture explosionTexture2;

    private Alien aliens[];
    private int direction = 1;
    private final float DROP_AMOUNT;
    private final float LIMIT_Y;
    private float alienSpeed;

    private final com.badlogic.gdx.utils.Array<PlayerBullet> alienBullets = new com.badlogic.gdx.utils.Array<>();
    private float alienShootTimer = 0f;
    private final float ALIEN_SHOOT_INTERVAL = 1.5f;

    private int frameCount;

    private boolean playerLose = false;

    public GameScreen(Main game)
    {
        this.game = game;
        this.frameCount = 0;

        this.WORLD_WIDTH = game.viewport.getWorldWidth();
        this.WORLD_HEIGHT = game.viewport.getWorldHeight();

        this.bulletTexture = new Texture("sprites/playerBullet.png");
        this.explosionTexture = new Texture("sprites/explosion.png");
        this.explosionTexture2 = new Texture("sprites/explosion2.png");

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

        this.aliens = new Alien[TOTAL_NUMBER_OF_ALIENS];

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
        this.player = new Entity(game.spriteBatch, playerTexture, WORLD_WIDTH / 2, ENTITY_HEIGHT, ENTITY_WIDTH, ENTITY_HEIGHT);
        playerSpeed = ENTITY_WIDTH * 2;

        lives = new Entity[game.livesLeft];
        for (int i = 0; i < game.livesLeft; i++) {
            lives[i] = new Entity(game.spriteBatch, playerTexture, WORLD_WIDTH - (ENTITY_WIDTH / 2 * (game.livesLeft - i)), 0, ENTITY_WIDTH / 2, ENTITY_HEIGHT / 2);
        }
    }

    //
    // Custom functions
    //

    private void updateAliens(float delta) {
        boolean hitTheBorder = false;
        boolean allAliensGone = true;

        float deltaX = alienSpeed * direction * delta;

        for (Entity alien : aliens) {
            if (alien == null) continue;

            allAliensGone = false;

            alien.translate(deltaX, 0);

            if ((direction == 1 && alien.getX() + alien.getWidth() >= WORLD_WIDTH) || (direction == -1 && alien.getX() <= 0)) {
                hitTheBorder = true;
            }
        }

        if (allAliensGone) {
            this.dispose(); // Limpa música e sfx da fase atual

            if (game.currentLevel == 1) {
                game.setScreen(new LevelCompleteScreen(game));
            } else {
                game.setScreen(new GameComplete(game));
            }
        }

        if (hitTheBorder) {
            direction *= -1;

            for (Entity alien : aliens) {
                if (alien == null) continue;

                if (alien.getY() - DROP_AMOUNT > LIMIT_Y) {
                    alien.translate(0, -DROP_AMOUNT);
                }
                else {
                    playerLose = true;
                }
            }
        }
    }

    private void bulletCollisionlogic(float delta) {
        float currentX = player.getX();
        float clampedX = MathUtils.clamp(currentX, 0, WORLD_WIDTH - player.getWidth());
        if (currentX != clampedX) {
            player.move(clampedX, player.getY());
        }

        if(playerBullet != null)
        {
            playerBullet.update(delta);

            for(int i = 0; i < TOTAL_NUMBER_OF_ALIENS; i++)
            {
                if(aliens[i] != null){
                    float alienRightBorder = aliens[i].getX() + aliens[i].getWidth();
                    float alienHeightBorder = aliens[i].getY() + aliens[i].getHeight();

                    float bulletRightBorder = playerBullet.getX() + playerBullet.getWidth();
                    float bulletHeightBorder = playerBullet.getY() + playerBullet.getHeight();

                    // Quando a bala acerta um alien
                    if(bulletRightBorder > aliens[i].getX() && playerBullet.getX() < alienRightBorder && playerBullet.getY() < alienHeightBorder && bulletHeightBorder > aliens[i].getY())
                    {
                        this.explosion = new Alien(game.spriteBatch, explosionTexture, explosionTexture2, aliens[i].getX() + aliens[i].getHeight() / 2, aliens[i].getY(), aliens[i].getHeight(), aliens[i].getHeight());
                        aliens[i] = null;
                        explosionSFX.play();

                        playerBullet = null;

                        game.totalPoints += 100;
                        this.alienSpeed += 20;

                        break;
                    }
                }
            }

            if(playerBullet != null && playerBullet.isOutOfBounds(WORLD_HEIGHT))
            {
                playerBullet = null;
            }
        }

        for (int i = alienBullets.size - 1; i >= 0; i--) {
            PlayerBullet b = alienBullets.get(i);
            
            // Move a bala para baixo (inverso do playerBullet)
            b.translate(0, -300f * delta); 
            
            // Se sair da tela por baixo, remove
            if (b.getY() + b.getHeight() < 0) {
                alienBullets.removeIndex(i);
                continue;
            }
            
            // Checa colisão com o Player
            float playerRight = player.getX() + player.getWidth();
            float playerTop = player.getY() + player.getHeight();
            float bRight = b.getX() + b.getWidth();
            float bTop = b.getY() + b.getHeight();
            
            if (bRight > player.getX() && b.getX() < playerRight && b.getY() < playerTop && bTop > player.getY()) {
                // Remove a bala que atingiu
                alienBullets.removeIndex(i);
                
                // Cria explosão no local do Player
                this.explosion = new Alien(game.spriteBatch, explosionTexture, explosionTexture2, player.getX(), player.getY(), player.getWidth(), player.getHeight());

                if (explosionSFX != null) explosionSFX.play();
                
                game.livesLeft--;

                lives[game.livesLeft] = null;
            }
        }

        if (game.livesLeft == 0) {
            playerLose = true;
        }
    }

    private void alienShoot() {
        // Cria uma lista temporária de índices de aliens que ainda estão vivos
        com.badlogic.gdx.utils.IntArray aliveAliens = new com.badlogic.gdx.utils.IntArray();
        for (int i = 0; i < TOTAL_NUMBER_OF_ALIENS; i++) {
            if (aliens[i] != null) {
                aliveAliens.add(i);
            }
        }

        // Se houver aliens vivos, escolhe um sortido para atirar
        if (aliveAliens.size > 0) {
            int randomIdx = aliveAliens.get(MathUtils.random(0, aliveAliens.size - 1));
            Alien shooter = aliens[randomIdx];
            
            float bulletX = shooter.getX() + (shooter.getWidth() / 2f) - (bulletTexture.getWidth() / 2f);
            float bulletY = shooter.getY();
            
            // Reutiliza o PlayerBullet, mas vamos inverter a velocidade dele na lógica
            PlayerBullet alienBullet = new PlayerBullet(game.spriteBatch, bulletTexture, bulletX, bulletY);
            alienBullets.add(alienBullet);
            
            if (shootingSFX != null) shootingSFX.play();
        }
    }

    //
    // Screen interface functions
    //

    @Override
    public void show() {
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Spacing around.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();

        shootingSFX = Gdx.audio.newSound(Gdx.files.internal("effects/SfxLazer.ogg"));
        explosionSFX = Gdx.audio.newSound(Gdx.files.internal("effects/SfxExplosion.ogg"));

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        lbLevel = new Label("Level: " + game.currentLevel, game.skin);
        lbPoints = new Label("Points" + game.totalPoints, game.skin);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        table.add(lbLevel).left().pad(10);
        table.add(lbPoints).left().pad(10);

        stage.addActor(table);
    }

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(game.backgroundTexture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        player.draw();

        if(playerBullet != null)
        {
            playerBullet.draw();
        }

        for (Alien alien : aliens) {
            if (alien != null) {
                alien.draw();
            }
        }

        for (PlayerBullet bullet : alienBullets) {
            bullet.draw();
        }

        for (Entity life : lives) {
            if (life != null) {
                life.draw();
            }
        }

        if (explosion != null) {
            explosion.draw();
        }

        lbPoints.setText("Points: " + game.totalPoints);

        stage.draw();

        game.spriteBatch.end();
    }

    @Override
    public void render(float delta) {
        input(delta);

        if (this.frameCount % 10 == 0 && this.explosion != null) {
            this.explosion.changeTexture();
        }
        else if (this.frameCount % 15 == 0) {
            updateAliens(delta);
            this.explosion = null;
            this.frameCount = 0;
        }

        alienShootTimer += delta;
        if (alienShootTimer >= ALIEN_SHOOT_INTERVAL) {
            alienShootTimer = 0f;
            alienShoot();
        }

        draw();
        bulletCollisionlogic(delta);
        stage.act(delta);

        if (playerLose) {
            this.dispose();

            game.setScreen(new GameOverScreen(game));
        }

        this.frameCount++;
    }

    private void input(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.translate(-playerSpeed * delta, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.translate(playerSpeed * delta, 0);
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
        {
            if(playerBullet == null)
            {
                float bulletX = player.getX() + (player.getWidth() / 2f) - (bulletTexture.getWidth() / 2f);
                float bulletY = player.getY() + player.getHeight();
                playerBullet = new PlayerBullet(game.spriteBatch, bulletTexture, bulletX, bulletY);

                shootingSFX.play();
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
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
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

        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        game.viewport.update(width, height, true);

    }
}
