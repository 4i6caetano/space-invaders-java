package io.github.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FirstScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private int opcaoSelecionada = 0;
    private String mensagemMenu = "";

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        font.draw(batch, "Space Invaders", 100, 300);

        if (opcaoSelecionada == 0) {
            font.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        } else {
            font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
        font.draw(batch, "New Game", 100, 250);

        if (opcaoSelecionada == 1) {
            font.setColor(com.badlogic.gdx.graphics.Color.YELLOW);
        } else {
            font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        }
        font.draw(batch, "Exit", 100, 210);

        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        font.draw(batch, mensagemMenu, 100, 200);

        batch.end();

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
        batch.dispose();
        font.dispose();
    }
}
