package de.gymwkb.civ;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Unit implements HexMap.Cell.Layer {
  private UnitType type;

  @Override
  public TextureRegion getTexture() {
    return type.getTexture();
  }
}
