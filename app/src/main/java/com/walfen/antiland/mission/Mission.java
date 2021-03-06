package com.walfen.antiland.mission;


import android.graphics.Point;

import androidx.annotation.NonNull;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.collector.CollectApple;
import com.walfen.antiland.mission.collector.SimpleCollectorMission;
import com.walfen.antiland.mission.explore.CheckBarricade;
import com.walfen.antiland.mission.explore.FaintWhisper;
import com.walfen.antiland.mission.explore.TempleEscape;
import com.walfen.antiland.mission.explore.TestOfEndurance;
import com.walfen.antiland.mission.killing.DestroyEntity;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.mission.killing.RestlessSouls;
import com.walfen.antiland.mission.killing.TempleMasterMind;
import com.walfen.antiland.mission.killing.TestOfPower;
import com.walfen.antiland.mission.killing.TheAncientBattlefield;
import com.walfen.antiland.mission.killing.TrialByFire;
import com.walfen.antiland.mission.talking.TalkingMission;
import com.walfen.antiland.mission.talking.VillageRivalryEnd;

public abstract class Mission implements Cloneable{

    public static Mission[] missions = new Mission[256];

    //main story missions
    public static Mission templeEscape = new TempleEscape(); //id: 1
    public static Mission templeMasterMind = new TempleMasterMind(); //id: 2
    public static Mission gotASpare = new SimpleCollectorMission.GotASpare();
    public static Mission ghostTown = new SimpleCollectorMission.GhostTown();
    public static Mission toBlockOrNot = new CheckBarricade(); //id: 5
    public static Mission trialByFore = new TrialByFire(); //id: 6
    public static Mission faintWhisper = new FaintWhisper(); //id: 7
    public static Mission restlessSouls = new RestlessSouls(); //id: 8

    //side missions
    public static Mission testOfEndurance1 = new TestOfEndurance(9, new Point(1408, 3968));
    public static Mission testOfEndurance2 = new TestOfEndurance(10, new Point(1664, 2176));
    public static Mission testOfEndurance3 = new TestOfEndurance(11, new Point(2432, 2304));
    public static Mission testOfEndurance4 = new TestOfEndurance.EndTestOfEndurance(); //12
    public static Mission testOfPower = new TestOfPower(); //13
    public static Mission villageRivalry1 = new TalkingMission("Village Rivalry 1", "Settle the dispute between two villagers", 14, 5);
    public static Mission villageRivalry2 = new TalkingMission("Village Rivalry 2", "Settle the dispute between two villagers", 15, 5);
    public static Mission villageRivalry3 = new TalkingMission("Village Rivalry 3", "Settle the dispute between two villagers", 16, 5);
    public static Mission villageRivalry4 = new VillageRivalryEnd(); //17
    public static Mission theAncientBattlefield = new TheAncientBattlefield(); //18

    //repeated missions
    public static Mission aIsForApple = new CollectApple("A is for Apple", "Collect one Apple from a tree and give it to the Hermit.", 19, 1);
    public static Mission slimySquishy = new DestroyEntity("Slimy n’ Squishy", "Kill three Slimes", new int[]{Entity.slime.getId()}, 20, new int[]{3}, 10,
            1, -1);

//    public static Mission collect10Woods = new CollectWood("Collect 10 woods",
//            "Collect 10 woods for the construction of our town", 0, 10);
//    public static Mission cutDown5Trees = new CutTrees("Cut Down 5 Trees",
//            "Cut down 5 trees to gain living space", 3, 5);
//    public static Mission kill5Slimes = new DestroyEntity("Eliminate 5 slimes",
//            "Reduce the number of slimes by eliminating 5 of them.", new int[]{Entity.slime.getId()}, 4, new int[]{5},
//        10, Item.lvOneHpPotion.getId(), 10);

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
        receiveReward();
        resetCompleteMessage();
        status = 2;
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
