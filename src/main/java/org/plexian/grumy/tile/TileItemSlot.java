package org.plexian.grumy.tile;

import org.plexian.grumy.tile.Tile;

public class TileItemSlot extends Tile {

    @Override
    public int getId() {
        return 903;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isAnimated() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public float[] getTextureCoordinates() {
        return new float[] {3, 0};
    }

}
