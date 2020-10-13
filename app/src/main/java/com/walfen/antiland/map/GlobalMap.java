package com.walfen.antiland.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;

public class GlobalMap extends Map {

    private final Bitmap map;

    public GlobalMap(Handler handler) {
        super(handler);
        map = ImageEditor.scaleBitmap(Assets.worldMap_0, mapWidth, mapHeight);
    }

    @Override
    protected void postTouchEvent(MotionEvent event) {

    }

    @Override
    public void update() {

    }

    @Override
    protected void postDraw(Canvas canvas) {
        canvas.drawBitmap(map, null, new Rect
                (mapXDispute, mapYDispute, mapXDispute+mapWidth, mapYDispute+mapHeight),
                Constants.getRenderPaint());
    }
}
