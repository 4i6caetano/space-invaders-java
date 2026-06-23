package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelCompleteScreen implements Screen {
    private final Main game;

    private Music leveCompleteMusic;

    private Label lbLevelComplete, lbPressEnter;

    public LevelCompleteScreen(Main game) {
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