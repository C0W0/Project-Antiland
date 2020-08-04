package com.walfen.antiland;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.walfen.antiland.untils.FPSTimer;

public class MainThread extends Thread {

    public static final int MAX_FPS = 30;
    public static Canvas canvas;
    private FPSTimer fpsTimer;
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        fpsTimer = new FPSTimer(MAX_FPS);
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {

        while(running) {
            if (fpsTimer.check()) {
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gamePanel.update();
                        if (canvas != null)
                            gamePanel.draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getFPS(){
        return fpsTimer.getFPS();
    }
}
