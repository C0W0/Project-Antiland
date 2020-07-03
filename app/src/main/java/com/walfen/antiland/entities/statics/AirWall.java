package com.walfen.antiland.entities.statics;


import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.tiles.Tile;

public class AirWall extends StaticEntity{

    public AirWall() {
        super(Tile.TILEWIDTH, Tile.TILEHEIGHT, 1);

        bounds.left = 0;
        bounds.top = Tile.TILEHEIGHT-1;
        bounds.right = bounds.left+Tile.TILEWIDTH;
        bounds.bottom = bounds.top+Tile.TILEHEIGHT;
        id = 1;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void die() {}

    @Override
    public void receiveDamage(int num) {}
}
