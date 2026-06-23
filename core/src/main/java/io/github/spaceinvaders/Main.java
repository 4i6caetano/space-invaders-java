package io.github.spaceinvaders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    int currentLevel = 1;
    int totalPoints = 0;
    int livesLeft = 3;

    FitViewport viewport;

    SpriteBatch spriteBatch;

    Texture backgroundTexture;
    Skin skin;

    @Override
    public void create()
    {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());;

        spriteBatch = new SpriteBatch();

        backgroundTexture = new Texture("background/backgroundTexture.png");

        // 1. Inicializa a Skin vazia
        skin = new Skin();

        // 2. Carrega e gera a fonte customizada pelo arquivo TTF
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        
        BitmapFont minhaFonte = generator.generateFont(parameter);
        generator.dispose(); // Fecha o gerador para não vazar memória

        // 3. REGISTRA a fonte na Skin
        skin.add("default", minhaFonte);

        // 4. CRIA E REGISTRA o LabelStyle usando a fonte recém-adicionada
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default"); // Pega a fonte que registramos acima
        
        // ESSA LINHA RESOLVE O ERRO: Registra o estilo "default" para os Labels encontrarem
        skin.add("default", labelStyle);

        setScreen(new MenuScreen(this));
    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    public void dispose()
    {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}
