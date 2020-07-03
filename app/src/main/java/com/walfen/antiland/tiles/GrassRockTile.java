package com.walfen.antiland.tiles;


import com.walfen.antiland.gfx.Assets;

public class GrassRockTile extends Tile {

    public GrassRockTile(int id) {
        super(Assets.grassStone, id);
    }

    @Override
    public boolean isBarrier() {
        return true;
    }
}
