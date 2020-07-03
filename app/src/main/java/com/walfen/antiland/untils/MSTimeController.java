package com.walfen.antiland.untils;

public class MSTimeController {

    private long target, beginning;

    public MSTimeController(){
        target = 0;
        beginning = 0;
    }

    /**
     * @param target the target ms number since calling the start method
     */
    public void start(long target){
        this.target = target;
        beginning = System.currentTimeMillis();
    }

    /**
     * if only counting ms is needed
     */
    public void start(){
        beginning = System.currentTimeMillis();
    }

    public boolean atTarget(){
        return System.currentTimeMillis()-beginning >= target;
    }

    public long getStatus(){
        return System.currentTimeMillis()-beginning;
    }

    public void reset(){
        target = 0;
    }

}
