package io.github.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {
    private final Main game;

    private BitmapFont font;
    private int opcaoSelecionada = 0;

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
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.spriteBatch.begin();

        logo.draw(game.spriteBatch);

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

        game.spriteBatch.end();

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