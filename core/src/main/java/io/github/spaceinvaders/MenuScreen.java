package io.github.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen {
    private final Main game;

    private BitmapFont font;
    private int opcaoSelecionada = 0;
<<<<<<< Updated upstream:core/src/main/java/io/github/spaceinvaders/MenuScreen.java

    private Sprite logo;
    private Texture logoTexture;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        font = game.skin.getFont("default");

        logoTexture = new Texture(Gdx.files.internal("sprites/SpaceInvaders.png"));
        logo = new Sprite(logoTexture);
        logo.setPosition((game.viewport.getWorldWidth() - logo.getWidth()) / 2, game.viewport.getWorldHeight() - logo.getHeight());
=======
    private String mensagemMenu = "";
    //para vram
    private Texture naveTexture;
    private float naveX = 100;
    private float naveY = 100;
    private float velocidadeX = 200;
    private float velocidadeY = 150;
    //estrelinhas passando
    private com.badlogic.gdx.utils.FloatArray estrelasX;
    private com.badlogic.gdx.utils.FloatArray estrelasY;
    private final int QTD_ESTRELAS = 50;

    @Override
    public void show() {
        //batch vai renderizar as imagens e o font escrever
        batch = new SpriteBatch();
        font = new BitmapFont();
        naveTexture = new Texture(Gdx.files.internal("sprites/NewPiskel.png"));
        //gemini deu a ideia de rastrear as bolinhas individualmete, ou seja, cada bolinha vai
        // vai ter uma posição unica, isso tudo guardado nesse floatArray
        estrelasX = new com.badlogic.gdx.utils.FloatArray();
        estrelasY = new com.badlogic.gdx.utils.FloatArray();

        for (int i = 0; i < QTD_ESTRELAS; i++) {
            estrelasX.add(com.badlogic.gdx.math.MathUtils.random(0, Gdx.graphics.getWidth()));
            estrelasY.add(com.badlogic.gdx.math.MathUtils.random(0, Gdx.graphics.getHeight()));
        }
>>>>>>> Stashed changes:core/src/main/java/io/github/spaceinvaders/FirstScreen.java
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        //aqui, a cada frame vai alterando a velocidade, e conforme for alterando a vl,
        //vai alterando a poisção e etc
        naveX += velocidadeX * delta;
        naveY += velocidadeY * delta;

        if (naveX < 0 || naveX > Gdx.graphics.getWidth() - 132) {
            velocidadeX = -velocidadeX;
        }

        if (naveY < 0 || naveY > Gdx.graphics.getHeight() - 132) {
            velocidadeY = -velocidadeY;
        }

        game.spriteBatch.begin();

<<<<<<< Updated upstream:core/src/main/java/io/github/spaceinvaders/MenuScreen.java
        logo.draw(game.spriteBatch);
=======
        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        for (int i = 0; i < QTD_ESTRELAS; i++) {
            //isso que vai fazer a magica das volinhas passarem, pois ele vai fornecer a altura e veloidade corretas

            float y = estrelasY.get(i) - (300 * delta);
            //o efeito de "passar" é por causa dessa subtração ai
            if (y < 0) {
                y = Gdx.graphics.getHeight();
                //a posição y e x vão ser aleatórias para cada bolinha
                estrelasX.set(i, com.badlogic.gdx.math.MathUtils.random(0, Gdx.graphics.getWidth()));
            }
            estrelasY.set(i, y);
            //desenhando a bolinha usando as posições aleatórias
            font.draw(batch, ".", estrelasX.get(i), estrelasY.get(i));
        }



        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        font.draw(batch, "Space Invaders", 100, 300);
>>>>>>> Stashed changes:core/src/main/java/io/github/spaceinvaders/FirstScreen.java

        if (opcaoSelecionada == 0) {
            font.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        } else {
            font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
        font.draw(game.spriteBatch, "New Game", 100, 250);

        if (opcaoSelecionada == 1) {
            font.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        } else {
            font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
        font.draw(game.spriteBatch, "Exit", 100, 210);

<<<<<<< Updated upstream:core/src/main/java/io/github/spaceinvaders/MenuScreen.java
        game.spriteBatch.end();
=======
        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        font.draw(batch, mensagemMenu, 100, 200);
        batch.draw(naveTexture, naveX, naveY, 132, 132);
        batch.end();
>>>>>>> Stashed changes:core/src/main/java/io/github/spaceinvaders/FirstScreen.java

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            opcaoSelecionada++;
            if (opcaoSelecionada > 1) {
                opcaoSelecionada = 0;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            opcaoSelecionada--;
            if (opcaoSelecionada < 0) {
                opcaoSelecionada = 1;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (opcaoSelecionada) {
                case 0:
                    this.game.setScreen(new GameScreen(game));
                    break;

                case 1:
                    Gdx.app.exit();
                    break;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        game.spriteBatch.dispose();
        font.dispose();
    }
}