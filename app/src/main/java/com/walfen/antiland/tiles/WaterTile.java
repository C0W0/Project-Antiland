package com.walfen.antiland.tiles;


import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class WaterTile extends Tile {

    public WaterTile(int id) {
        super(new Animation(0.4f, Assets.water), id);
    }

    @Override
    public boolean isBarrier() {
        return true;
    }
}
