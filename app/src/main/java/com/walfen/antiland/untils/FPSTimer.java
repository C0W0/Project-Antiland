package com.walfen.antiland.untils;

public class FPSTimer {

    private double timePerUpdate;
    private double delta;
    private long now;
    private long lastTime;
    private long time;
    private int frames, fps;
    //The code that are commented out are for FPS display


    public FPSTimer(int FPS){
        timePerUpdate = 1e9/FPS;
        delta = 0;
        lastTime = System.nanoTime();
    }

    public boolean check(){
        now = System.nanoTime();
        delta += (now - lastTime)/timePerUpdate;
        time += now - lastTime;
        lastTime = now;
        if (delta >= 1){
            delta --;
            frames ++;

            if(time >= 1e9){
                fps = frames;
                time = 0;
                frames = 0;
            }

            return true;
        } else{
            return false;
        }
    }

    public int getFPS(){
        return fps;
    }

}
