package de.gymwkb.civ.registry;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class HextureRegistry {
    private AtlasRegion[] textures;

    public HextureRegistry(TextureAtlas atlas) {
        Hexture[] types = Hexture.values();
        textures = new AtlasRegion[types.length];
        for(int i = 0; i < types.length; i++) {
            AtlasRegion reg = atlas.findRegion(types[i].name());
            textures[i] = reg;
        }
    }

    public AtlasRegion getTexture(Hexture hexture) {
        return textures[hexture.ordinal()];
    }
    
    public enum Hexture {
        //FOREGROUND
        SELECTION,
        //TERRAIN
        TERRAIN_DEFAULT
        //UNITS
    }
}
