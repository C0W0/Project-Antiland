package com.walfen.antiland.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.gfx.Animation;

public class ComponentTile extends Tile{

    private TileAddonComponent component;

    public ComponentTile(Bitmap texture, int id, boolean barrier, TileAddonComponent component) {
        super(texture, id, barrier);
        this.component = component;
    }

    public ComponentTile(Animation dynamicTexture, int id, boolean barrier, TileAddonComponent component) {
        super(dynamicTexture, id, barrier);
        this.component = component;
    }

    @Override
    public void update() {
        super.update();
        component.update();
    }

    @Override
    public void draw(Canvas canvas, Rect distRect) {
        super.draw(canvas, distRect);
        component.draw(canvas, distRect);
    }
}
