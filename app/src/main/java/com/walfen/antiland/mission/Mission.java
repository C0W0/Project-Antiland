package com.walfen.antiland.mission;


import androidx.annotation.NonNull;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.colloector.CollectApple;
import com.walfen.antiland.mission.colloector.CollectWood;
import com.walfen.antiland.mission.colloector.CollectorMission;
import com.walfen.antiland.mission.colloector.SimpleCollectorMission;
import com.walfen.antiland.mission.explore.TempleEscape;
import com.walfen.antiland.mission.killing.CutTrees;
import com.walfen.antiland.mission.killing.DestroyEntity;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.mission.killing.TempleMasterMind;

public abstract class Mission implements Cloneable{

    public static Mission[] missions = new Mission[256];
    public static Mission templeEscape = new TempleEscape(); //id: 1
    public static Mission templeMasterMind = new TempleMasterMind(); //id: 2
    public static Mission gotASpare = new SimpleCollectorMission("Go a Spare?", "Return the shop key", Item.key.getId(), 1, 3);
    public static Mission ghostTown = new SimpleCollectorMission("Ghost Town", "Fetch the Blacksmith's hammer", Item.hammer.getId(), 1, 4);
//    public static Mission

    public static Mission collect10Woods = new CollectWood("Collect 10 woods",
            "Collect 10 woods for the construction of our town", 0, 10);
    public static Mission cutDown5Trees = new CutTrees("Cut Down 5 Trees",
            "Cut down 5 trees to gain living space", 3, 5);
    public static Mission kill5Slimes = new DestroyEntity("Eliminate 5 slimes",
            "Reduce the number of slimes by eliminating 5 of them.", new int[]{Entity.slime.getId()}, 4, new int[]{5},
        10, Item.lvOneHpPotion.getId(), 10);

    protected int status;
    /** 0 - not active
     *  1 - in progress
     *  2 - completed
     */

    protected int stage;
    protected int[] finalProgress, progress;

    protected int rewardItemID, rewardItemCount;
    protected int expReward;

    protected Handler handler;
    protected final int id;
    protected final String title;
    protected final String desc;
    protected String completeMessage;
    protected int xpGain;

    public Mission(String title, String desc, int id, int xpGain){
        status = 0;
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.xpGain = xpGain;
        completeMessage = "";
        missions[id] = this;
    }

    public abstract void update();

    public abstract boolean isCompleted();

    public void complete(){
        status = 2;
        receiveReward();
        resetCompleteMessage();
    }

    public abstract void receiveReward();

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

    public String[] getTextProgress() {
        if(isCompleted() && !completeMessage.equals(""))
            return new String[]{completeMessage};
        String[] texts = new String[progress.length];
        for(int i = 0; i < texts.length; i++){
            texts[i] = progress[i]+" / "+finalProgress[i];
        }
        return texts;
    }

    public void setCompleteMessage(String completeMessage) {
        this.completeMessage = completeMessage;
    }

    public void resetCompleteMessage(){
        setCompleteMessage("");
    }
}
