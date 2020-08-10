package com.walfen.antiland.tiles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.GraphicalTerminalElement;
import com.walfen.antiland.gfx.ImageEditor;

public class TileAddonComponent implements GraphicalTerminalElement {


    private Bitmap texture;
    private Animation dynamicTexture;

    public TileAddonComponent(Bitmap texture){
        this.texture = ImageEditor.scaleBitmap(texture, Tile.TILEWIDTH, Tile.TILEHEIGHT);

    }

    public TileAddonComponent(Animation dynamicTexture){
        dynamicTexture.scale(Tile.TILEWIDTH, Tile.TILEHEIGHT);
        this.dynamicTexture = dynamicTexture;
    }

    @Override
    public void update() {
        if(dynamicTexture != null)
            dynamicTexture.update();
    }

    @Override
    public void draw(Canvas canvas, Rect distRect) {
        if(dynamicTexture != null)
            dynamicTexture.draw(canvas, distRect);
        else
            canvas.drawBitmap(texture, null, distRect, Constants.getRenderPaint());
    }
}
