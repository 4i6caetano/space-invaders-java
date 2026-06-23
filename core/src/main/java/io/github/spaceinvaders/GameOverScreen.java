package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final Main game;
    private Label lbGameOver, lbPoints, lbPressEnter;

    public GameOverScreen(Main game) {
        this.game = game;

        lbGameOver = new Label("GAME OVER", game.skin.get("default-big", LabelStyle.class));
        lbGameOver.setPosition((game.viewport.getWorldWidth() - lbGameOver.getWidth()) / 2, game.viewport.getWorldHeight() - 100);

        lbPoints = new Label("Points: " + game.totalPoints, game.skin.get("default", LabelStyle.class));
        lbPoints.setPosition((game.viewport.getWorldWidth() - lbPoints.getWidth()) / 2, game.viewport.getWorldHeight() / 2);

        lbPressEnter = new Label("Press ENTER to continue...", game.skin.get("default", LabelStyle.class));
        lbPressEnter.setPosition((game.viewport.getWorldWidth() - lbPressEnter.getWidth()) / 2, 80);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.spriteBatch.begin();
        
        lbGameOver.draw(game.spriteBatch, 1);
        lbPoints.draw(game.spriteBatch, 1);
        lbPressEnter.draw(game.spriteBatch, 1);

        game.spriteBatch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            // Reseta o estado do jogo antes de voltar
            game.currentLevel = 1;
            game.totalPoints = 0;
            game.livesLeft = 3;

            game.setScreen(new MenuScreen(game));
        }
    }

    @Override public void dispose() { 

    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
