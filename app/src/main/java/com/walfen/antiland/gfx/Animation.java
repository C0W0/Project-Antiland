package com.walfen.antiland.gfx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.untils.Utils;

public class Animation implements GraphicalTerminalElement{

    private Bitmap[] frames;
    private int frameIndex;
    private float frameTime;
    private long lastFrame;
    private boolean distorted;

    public Animation(float animTime, Bitmap[] frames){
        this.frames = frames;
        frameIndex = 0;
        frameTime = animTime/frames.length;
        lastFrame = System.currentTimeMillis();
        distorted = false;
    }

    @Override
    public void update() {
        if(System.currentTimeMillis() - lastFrame > frameTime*1000){
            frameIndex = frameIndex+1 >= frames.length ? 0 : frameIndex+1;
            lastFrame = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(Canvas canvas, Rect distRect) {
        if(distorted)
            distRect.right = distRect.left+frames[0].getWidth();
            distRect.bottom = distRect.top+frames[0].getHeight();
        canvas.drawBitmap(frames[frameIndex], null, distRect, Constants.getRenderPaint());
    }

    private void scaleRectangle(Rect rect){
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
            rect.left = (int)(rect.right - rect.height()*whRatio - rect.left)/2 + rect.left;
        else
            rect.top = (int)(rect.bottom - rect.width()*(1/whRatio) - rect.top)/2 + rect.top;
    }

    public void reset() {
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void setMatrix(Matrix matrix) {
        Bitmap[] temp = new Bitmap[frames.length];
        for(int i = 0; i < frames.length; i++) {
            Bitmap b = frames[i];
            temp[i] = Bitmap.createBitmap(b, 0, 0, 128, 128, matrix, true);
        }
        frames = temp;
        distorted = true;
    }

    public void scale(int xSize, int ySize){
        Bitmap[] temp = new Bitmap[frames.length];
        for(int i = 0; i < frames.length; i++) {
            Bitmap b = frames[i];
            temp[i] = ImageEditor.scaleBitmap(b, xSize, ySize);
        }
        frames = temp;
    }

    public void scaleForced(int xSize, int ySize){
        Bitmap[] temp = new Bitmap[frames.length];
        for(int i = 0; i < frames.length; i++) {
            Bitmap b = frames[i];
            temp[i] = ImageEditor.scaleBitmapForced(b, xSize, ySize);
        }
        frames = temp;
    }

    public int getFrameIndex() {
        return frameIndex;
    }

    public long getCycleDuration(){
        return (long)(frameTime*frames.length*1000);
    }
}
