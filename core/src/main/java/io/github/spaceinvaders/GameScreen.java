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

    private Entity aliens[];

    // VARIÁVEIS DE MOVIMENTO DOS ALIENS
    private float alienSpeed = 40f;      // Velocidade horizontal
    private int direction = 1;           // 1 = Direita, -1 = Esquerda
    private final float DROP_AMOUNT = 15f; // Quanto eles descem ao bater na borda
    private final float LIMIT_Y = 100f;   // Limite inferior (altura mínima que podem chegar)

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
        
        int ALIEN_WIDTH = (int) (game.viewport.getWorldWidth() / (NUM_COLUMNS + 2));
        int ALIENT_HEIGHT = ALIEN_WIDTH / 2;

        // Otimização: Carregar texturas fora do loop para evitar memory leak
        Texture alien1 = new Texture("sprites/alien1Instance1.png");
        Texture alien2 = new Texture("sprites/alien2Instance1.png");
        Texture alien3 = new Texture("sprites/alien3Instance1.png");

        for (int i = 0; i < NUM_LINES; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                Texture currentTexture;
                float y = game.viewport.getWorldHeight();

                if (i % 3 == 0) {
                    currentTexture = alien1;
                    y -= ALIENT_HEIGHT;
                }
                else if (i % 3 == 1) {
                    currentTexture = alien2;
                    y -= ALIENT_HEIGHT * 2;
                }
                else {
                    currentTexture = alien3;
                    y -= ALIENT_HEIGHT * 3;
                }

                int index = i * NUM_COLUMNS + j;

                aliens[index] = new Entity(game.spriteBatch, currentTexture, ALIEN_WIDTH, ALIENT_HEIGHT);
                aliens[index].move(ALIEN_WIDTH * (j + 1), y);
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        input(delta);
        updateAliens(delta); // <- Atualiza a lógica dos aliens
        draw();
        logic();
    }

    private void updateAliens(float delta) {
        boolean bateramNaBorda = false;
        float worldWidth = game.viewport.getWorldWidth();

        // 1. Calcula quanto eles devem andar NESSE frame
        float deslocamentoX = alienSpeed * direction * delta;

        // 2. Aplica o translate horizontal e checa as bordas
        for (Entity alien : aliens) {
            if (alien == null) continue;

            // Move apenas o delta desse frame
            alien.move(deslocamentoX, 0); 

            // Checa a borda usando o getX() atualizado do seu alien
            if ((direction == 1 && alien.getX() + alien.getWidth() >= worldWidth) || (direction == -1 && alien.getX() <= 0)) {
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Lembre-se de dar dispose nas texturas aqui se necessário!
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
            if (aliens[i] != null) { // Evita erro se o alien morrer
                aliens[i].draw();
            }
        }

        game.spriteBatch.end();
    }

    private void logic()
    {
        game.playerSprite.setX(MathUtils.clamp(game.playerSprite.getX(), 0, worldWidth - playerWidth));
    }
}
