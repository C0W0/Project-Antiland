package com.walfen.antiland.mission;


import com.walfen.antiland.Handler;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.colloector.CollectApple;
import com.walfen.antiland.mission.colloector.CollectWood;

public abstract class Mission {

    public static Mission[] missions = new Mission[256];
    public static Mission collect10Woods = new CollectWood("Collect 10 woods",
            "Collect 10 woods for the construction of our town", 0, 10);
    public static Mission collect5Woods = new CollectWood("Collect 5 woods",
           "Collect 5 woods for the construction of our town", 1, 5);
    public static Mission collect10Apples = new CollectApple("Collect 10 apples",
            "Collect 10 apples for little Alice",2, 10);

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

    public int[] getProgress() {
        return progress;
    }

    public int[] getFinalProgress() {
        return finalProgress;
    }
}
