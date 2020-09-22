package com.walfen.antiland.entities.creatures.npc.secondary;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.ui.overlay.MissionPanel;

import java.util.ArrayList;

public class NPC1 extends RepeatedMissionNPC {

    private Animation dynamicTexture;

    public NPC1() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 501, 4);
        interRange = 2;
        dynamicTexture = new Animation(6, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    protected ArrayList<Conversation> getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcCrab[0], false));
        return c;
    }

    @Override
    protected ArrayList<Conversation> getCompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("Thanks for your help.", Assets.npcCrab[0], false));
        c.add(new Conversation("My duty.", Assets.player_neutral, true));
        return c;
    }

    @Override
    protected void assignConversationProcedure(UIManager manager) {
        manager.hideUI();
        ArrayList<Conversation> c = new ArrayList<>();
        ArrayList<Conversation> c2 = new ArrayList<>();
        c.add(new Conversation("We are suffering from increasing number of slime attacks.", Assets.npcCrab[0], false));
        manager.getConvBox().setConversationList(c, () -> {convBoxOn = false;
        manager.popUpOptions("How would you reply?", new String[]{"(Simply walk away)", "How can I help?"}, new ClickListener[]{() -> {},
                () -> {
            c2.add(new Conversation("How can I help?",  Assets.player_neutral, true));
            c2.add((new Conversation("Go down to the forests and kills 5 of them to decrease their number. " +
                    "Once you finish with them come back to me. I have some rewards for you.", Assets.npcCrab[0], false)));
            manager.hideUI();
            manager.getConvBox().setConversationList(c2, () -> {assignMission(); convBoxOn = false;
                Mission.missions[missionID].setCompleteMessage("Report back to Mr. Krab.");
                handler.getPlayer().getMissionManager().setSelectedMission();
                handler.getUIManager().getCGUI().getMissionPanel().changePosition(MissionPanel.EXTEND);});
            manager.getConvBox().setActive();
        }}, false);});
        manager.getConvBox().setActive();
    }

    @Override
    public void update() {
        super.update();
        dynamicTexture.update();
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        dynamicTexture.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    public void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.npcCrab[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Crab";
    }
}
