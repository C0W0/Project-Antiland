package com.walfen.antiland.untils;

public class FrameTimeController {

    private int frames, target;
    private boolean timeControllerOn;

    public FrameTimeController(){
        frames = 0;
        target = 0;
        timeControllerOn = false;
    }

    /**
     * @param target the target frame number since calling the start method
     */
    public void start(int target){
        reset();
        this.target = target;
        timeControllerOn = true;
    }

    /**
     * if only counting frames is needed
     */
    public void start(){
        reset();
        timeControllerOn = true;
    }

    public void update(){
        if(!timeControllerOn)
            return;
        frames ++;
        if(atTarget()){
            timeControllerOn = false;
        }
    }

    public boolean atTarget(){
        return (frames >= target) && (target != 0);
    }

    public int getStatus(){
        return frames;
    }

    public void reset(){
        timeControllerOn = false;
        frames = 0;
        target = 0;
    }

    public boolean isTimeControllerOn() {
        return timeControllerOn;
    }
}
