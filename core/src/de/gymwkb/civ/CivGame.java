package de.gymwkb.civ;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import de.gymwkb.civ.game.GameController;
import de.gymwkb.civ.game.HumanPlayerController;
import de.gymwkb.civ.game.ai.DummyPlayerController;

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
        createGame();
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
    
    private void createGame() {
        GameController game = new GameController();
        HumanPlayerController humanPlayer = new HumanPlayerController(game, 0);
        DummyPlayerController dummyPlayer = new DummyPlayerController(game, 1);
        setScreen(new GameScreen(humanPlayer));
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }
    
}
