package de.gymwkb.civ.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLayer implements HexMap.Cell.Layer {
    private TextureRegion texture;
    
    public TextureLayer(TextureRegion texture) {
        this.texture = texture;
    }

    @Override
    public TextureRegion getTexture() {
        return texture;
    }
}
