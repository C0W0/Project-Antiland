package com.walfen.antiland.tiles;


import com.walfen.antiland.gfx.Assets;

public class DirtRockTile extends Tile {

    public DirtRockTile(int id) {
        super(Assets.dirtStone, id);
    }

    @Override
    public boolean isBarrier() {
        return true;
    }
}
