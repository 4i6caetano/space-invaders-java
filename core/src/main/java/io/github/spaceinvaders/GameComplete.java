package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameComplete implements Screen {
    private final Main game;

    private Music levelCompleteMusic;

    private Label lbLevelComplete, lbPressEnter;

    public GameComplete(Main game) {
        this.game = game;

        lbLevelComplete = new Label("LEVEL COMPLETE", game.skin.get("default-big", LabelStyle.class));
        lbLevelComplete.setPosition((game.viewport.getWorldWidth() - lbLevelComplete.getWidth()) / 2, game.viewport.getWorldHeight() - 100);

        lbPressEnter = new Label("Press ENTER to continue...", game.skin.get("default", LabelStyle.class));
        lbPressEnter.setPosition((game.viewport.getWorldWidth() - lbPressEnter.getWidth()) / 2, 80);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.spriteBatch.begin();
        
        lbLevelComplete.draw(game.spriteBatch, 1);
        lbPressEnter.draw(game.spriteBatch, 1);

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
