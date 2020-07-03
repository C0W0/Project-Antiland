package com.walfen.antiland.gfx;

import android.graphics.Canvas;
import android.graphics.Rect;

public interface GraphicalTerminalElement {

    public void update();

    public void draw(Canvas canvas, Rect distRect);

}
