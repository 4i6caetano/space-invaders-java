package io.github.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelCompleteScreen implements Screen {
    private final Main game;
    private final BitmapFont font;
    private Music leveCompleteMusic;

    public LevelCompleteScreen(Main game) {
        this.game = game;
        this.font = new BitmapFont(); // Fonte padrão (branca)
        this.font.getData().setScale(2f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.spriteBatch.begin();
        font.draw(game.spriteBatch, "FASE CONCLUIDA!", 100, 400);
        font.draw(game.spriteBatch, "Pressione ENTER para proxima fase", 100, 300);
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
        font.dispose();
        leveCompleteMusic.dispose();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}