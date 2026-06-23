package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen {
    private final Main game;
    private final BitmapFont font;

    public GameOverScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.spriteBatch.begin();
        font.draw(game.spriteBatch, "Você perdeu o jogo...", 100, 450);
        font.draw(game.spriteBatch, "Pontuacao Final: " + game.totalPoints, 100, 350);
        font.draw(game.spriteBatch, "Pressione ENTER para voltar ao menu", 100, 250);
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
        font.dispose();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
