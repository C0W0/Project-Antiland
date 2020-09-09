package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
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
    public void onDeath() {}

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return Assets.NULL;
    }

    @Override
    public String getName() {
        return null;
    }
}
