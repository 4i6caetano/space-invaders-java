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

public class GameComplete implements Screen {
    private final Main game;
    private BitmapFont font;

    private Music levelCompleteMusic;

    private Label lbLevelComplete, lbPressEnter, lbPoints;

    private com.badlogic.gdx.utils.FloatArray estrelasX;
    private com.badlogic.gdx.utils.FloatArray estrelasY;
    private final int QTD_ESTRELAS = 50;

    public GameComplete(Main game) {
        this.game = game;
        this.font = game.skin.getFont("default");

        lbLevelComplete = new Label("GAME COMPLETE", game.skin.get("default-big", LabelStyle.class));
        lbLevelComplete.setPosition((game.viewport.getWorldWidth() - lbLevelComplete.getWidth()) / 2, game.viewport.getWorldHeight() - 100);

        lbPoints = new Label("Points: " + game.totalPoints, game.skin.get("default", LabelStyle.class));
        lbPoints.setPosition((game.viewport.getWorldWidth() - lbPoints.getWidth()) / 2, game.viewport.getWorldHeight() / 2);

        lbPressEnter = new Label("Press ENTER to go back...", game.skin.get("default", LabelStyle.class));
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
        lbPoints.draw(game.spriteBatch, 1);

        game.spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Reseta o estado do jogo antes de voltar
            game.currentLevel = 1;
            game.totalPoints = 0;
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override public void show() {
        levelCompleteMusic = Gdx.audio.newMusic(Gdx.files.internal("effects/LevelComplete.mp3"));
        levelCompleteMusic.setLooping(false);
        levelCompleteMusic.setVolume(0.5f);
        levelCompleteMusic.play();
    }

    @Override public void dispose() { 
        levelCompleteMusic.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
