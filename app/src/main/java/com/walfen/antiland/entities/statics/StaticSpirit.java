package com.walfen.antiland.entities.statics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

public class StaticSpirit extends StaticEntity {

    public StaticSpirit() {
        super(Tile.TILEWIDTH, Tile.TILEHEIGHT, 740);
        setEntityHealth(1);
    }

    @Override
    protected void onDeath() {
        handler.getUIManager().popUpMessage("With a single hit, the monster disappears from sight. The monster left behind a rather mutilated hammer.");
        handler.getWorld().getItemManager().addItem(Item.hammer.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), 1));
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.lostGhostMovement[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "? ? ?";
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.lostGhostMovement[0], null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }
}
