package de.gymwkb.civ;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CivGame extends Game {
    public SpriteBatch batch;
    public AssetManager assets;
    
    public static CivGame instance;

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        assets = new AssetManager();
        loadAssets();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
    
    private void loadAssets() {
        assets.load("hextures/pack.atlas", TextureAtlas.class);
        assets.load("skin/uiskin.json", Skin.class);
        assets.finishLoading();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }
}
