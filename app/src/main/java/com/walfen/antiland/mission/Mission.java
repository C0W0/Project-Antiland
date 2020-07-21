package com.walfen.antiland.mission;


import androidx.annotation.NonNull;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.colloector.CollectApple;
import com.walfen.antiland.mission.colloector.CollectWood;
import com.walfen.antiland.mission.killing.CutTrees;
import com.walfen.antiland.mission.killing.KillTracker;

public abstract class Mission implements Cloneable{

    public static Mission[] missions = new Mission[256];
    public static Mission collect10Woods = new CollectWood("Collect 10 woods",
            "Collect 10 woods for the construction of our town", 0, 10);
    public static Mission collect5Woods = new CollectWood("Collect 5 woods",
           "Collect 5 woods for the construction of our town", 1, 5);
    public static Mission collect10Apples = new CollectApple("Collect 10 apples",
            "Collect 10 apples for little Alice",2, 10);
    public static Mission cutDown5Trees = new CutTrees("Cut Down 5 Trees",
            "Cut down 5 trees to gain living space", 3, 5);

    protected int status;
    /** 0 - not active
     *  1 - in progress
     *  2 - completed
     *  any mission that cannot be completed multiple times
     *  will receive a status of 2 after completion
     */

    protected int stage;
    protected int[] finalProgress, progress;

    protected int rewardItemID;
    protected int expReward;

    protected Handler handler;
    protected final int id;
    protected final String title;
    protected final String desc;

    public Mission(String title, String desc, int id){
        status = 0;
        this.id = id;
        this.title = title;
        this.desc = desc;
        missions[id] = this;
    }

    public void update(){
        if(isCompleted()){
            complete();
        }
    }


    public abstract boolean isCompleted();

    public void complete(){
        status = 2;
        receiveReward();
    }

    protected void receiveReward(){
        handler.getPlayer().getInventory().addItem(
                Item.items[rewardItemID].addToInv(1)); // TODO: Variable number
    }

    @NonNull
    @Override
    public Mission clone() {
        Mission result = null;
        try {
            result = (Mission) super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        assert result != null;
        return result;
    }

    public int getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setProgress(int[] progress) {
        this.progress = progress;
    }

    public void setProgress(int[] progress, KillTracker tracker){
        setProgress(progress);
    }

    public int[] getProgress() {
        return progress;
    }

    public int[] getFinalProgress() {
        return finalProgress;
    }
}
