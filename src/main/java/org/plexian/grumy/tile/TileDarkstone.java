package org.plexian.grumy.tile;

public class TileDarkstone extends Tile {

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public boolean isTransparent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAnimated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public float[] getTextureCoordinates() {
        return new float[]{0, 0};
    }

}
