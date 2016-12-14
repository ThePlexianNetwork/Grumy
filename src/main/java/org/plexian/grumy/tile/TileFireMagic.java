package org.plexian.grumy.tile;

public class TileFireMagic extends Tile {
    @Override
    public int getId() {
        return 2;
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
        return new float[]{2,1};
    }
}
