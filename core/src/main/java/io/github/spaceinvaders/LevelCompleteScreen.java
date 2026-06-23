package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelCompleteScreen implements Screen {
    private final Main game;
    private BitmapFont font;

    private Music leveCompleteMusic;

    private Label lbLevelComplete, lbPressEnter;

    private com.badlogic.gdx.utils.FloatArray estrelasX;
    private com.badlogic.gdx.utils.FloatArray estrelasY;
    private final int QTD_ESTRELAS = 50;

    public LevelCompleteScreen(Main game) {
        this.game = game;
        this.font = game.skin.getFont("default");

        lbLevelComplete = new Label("LEVEL COMPLETE", game.skin.get("default-big", LabelStyle.class));
        lbLevelComplete.setPosition((game.viewport.getWorldWidth() - lbLevelComplete.getWidth()) / 2, game.viewport.getWorldHeight() - 100);

        lbPressEnter = new Label("Press ENTER to continue...", game.skin.get("default", LabelStyle.class));
        lbPressEnter.setPosition((game.viewport.getWorldWidth() - lbPressEnter.getWidth()) / 2, 80);

        estrelasX = new com.badlogic.gdx.utils.FloatArray();
        estrelasY = new com.badlogic.gdx.utils.FloatArray();

        for (int i = 0; i < QTD_ESTRELAS; i++) {
            estrelasX.add(com.badlogic.gdx.math.MathUtils.random(0, Gdx.graphics.getWidth()));
            estrelasY.add(com.badlogic.gdx.math.MathUtils.random(0, Gdx.graphics.getHeight()));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        // desenha as labels na tela
        game.spriteBatch.begin();

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
            font.draw(game.spriteBatch, ".", estrelasX.get(i), estrelasY.get(i));
        }
        
        lbLevelComplete.draw(game.spriteBatch, 1);
        lbPressEnter.draw(game.spriteBatch, 1);

        game.spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.currentLevel++;
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void show() {
        leveCompleteMusic = Gdx.audio.newMusic(Gdx.files.internal("effects/LevelComplete.mp3"));
        leveCompleteMusic.setLooping(false);
        leveCompleteMusic.setVolume(0.5f);
        leveCompleteMusic.play();
    }
    
    @Override public void dispose() {
        leveCompleteMusic.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}