package com.walfen.antiland.entities.creatures.npc;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class NPC1 extends NPC {

    private Animation dynamicTexture;
    private boolean convBoxOn = false;

    public NPC1() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3);
        interRange = 2;
        dynamicTexture = new Animation(6, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    protected void interact() {
        if(convBoxOn)
            return;
        if(handler.getPlayer().getMissionManager().hasMission(4)){
            if(Mission.missions[4].isCompleted()){
                handler.getPlayer().getMissionManager().getMissions().remove(Mission.missions[4]);
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = new ArrayList<>();
                c.add(new Conversation("Thanks for your help.", Assets.npcCrab[0], false));
                c.add(new Conversation("My duty.", Assets.player_neutral, true));
                c.add(new Conversation("(You have received 10 potions)", Assets.NULL, false));
                uiManager.getConvBox().setConversationList(c, () -> {Mission.missions[4].receiveReward(); convBoxOn = false;});
                uiManager.getConvBox().setActive();
            }else {
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = new ArrayList<>();
                c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcCrab[0], false));
                uiManager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                uiManager.getConvBox().setActive();
            }
            return;
        }
        convBoxOn = true;
        UIManager uiManager = handler.getUIManager();
        uiManager.hideUI();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("We are suffering from increasing number of slime attacks.", Assets.npcCrab[0], false));
        c.add(new Conversation("What?! How can I help?", Assets.player_neutral, true));
        c.add(new Conversation("Go down to the forests and kills 5 of them to decrease their number. " +
                "Once you finish with them come back to me. I have some rewards for you.", Assets.npcCrab[0], false));
        uiManager.getConvBox().setConversationList(c, () -> {assignMission(4); convBoxOn = false;});
        uiManager.getConvBox().setActive();
    }

    private void assignMission(int missionID) {
        handler.getPlayer().getMissionManager().addMission(missionID);
    }

    @Override
    public void update() {
        super.update();
        dynamicTexture.update();
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        dynamicTexture.draw(canvas, new Rect(left, top, left+width, top+height));
    }



    @Override
    public void onDeath() {

    }
}
