package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.creatures.npc.secondary.RepeatedMissionNPC;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class ChickenNPC extends MajorMissionNPC {

    public ChickenNPC() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 505, new int[]{-1, 9, 15, 17});
        interRange = 2;
    }

    @Override
    public void receiveDamage(int num, int type) { }

    @Override
    protected void assignConversationProcedure(UIManager manager) {
        ArrayList<Conversation> c = new ArrayList<>();
        boolean control = false;
        switch (status){
            case 1:
                c.clear();
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if(m.getId() == 9 || m.getId() == 10 || m.getId() == 11 || m.getId() == 12){
                        convBoxOn = true;
                        UIManager uiManager = handler.getUIManager();
                        uiManager.hideUI();
                        c = getIncompleteConversation()[status];
                        uiManager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                        uiManager.getConvBox().setActive();
                        return;
                    }
                }
                c.add(new Conversation("Hello.", Assets.player_icon, false));
                c.add(new Conversation("It’s you!", Assets.npcChicken, true));
                c.add(new Conversation("?!?!", Assets.player_icon, false));
                c.add(new Conversation("You‘re the one who went into the Abandoned Port!", Assets.npcChicken, true));
                c.add(new Conversation("Uhh...", Assets.player_icon, false));
                c.add(new Conversation("I saw you running off. I thought you’d never come back!", Assets.npcChicken, true));
                c.add(new Conversation("You’ve got some guts going there. A real spooky place that is.", Assets.npcChicken, true));
                c.add(new Conversation("Anyways, I want to see how well you can run. I already have a course planned.", Assets.npcChicken, true));
                c.add(new Conversation("Just get through the entire course without taking damage. Simple!", Assets.npcChicken, true));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission();
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                });
                break;
            case 2:
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if (m.getId() == 14) {
                        control = true;
                        break;
                    }
                }
                if(!control){
                    c.clear();
                    c.add(new Conversation("Good Day.", Assets.npcChicken, false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                    break;
                }
                c.clear();
                c.add(new Conversation("Answer me...", Assets.npcChicken, false));
                c.add(new Conversation("Pears or oranges?", Assets.npcChicken, false));
                c.add(new Conversation("...", Assets.player_icon, true));
                c.add(new Conversation("Oh, come on. Surely you have a side.", Assets.npcChicken, false));
                c.add(new Conversation("or perhaps not, since pears are CLEARLY the best!", Assets.npcChicken, false));
                c.add(new Conversation("No they’re not!", Assets.npcCactus, true));
                c.add(new Conversation("Quit eavesdropping on our private conversation!", Assets.npcChicken, false));
                c.add(new Conversation("pfft! Why don’t you go tell him how good pears are!", Assets.npcChicken, false));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    Mission.missions[missionSet[status]-1].complete();
                    Mission.missions[missionSet[status]].setCompleteMessage("Go talk to Captain Cactus");
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission(handler.getPlayer().getMissionManager().getMissions().size()-2);
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    status ++;
                });
                break;
            case 3:
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if (m.getId() == 16) {
                        control = true;
                        break;
                    }
                }
                if(!control){
                    c.clear();
                    c.add(new Conversation("Good Day.", Assets.npcChicken, false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                    break;
                }
                c.clear();
                c.add(new Conversation("Where’d you get an orange.", Assets.npcChicken, true));
                c.add(new Conversation("Uhg. Don’t tell me he tried to convince you.", Assets.npcChicken, true));
                c.add(new Conversation("Ha! Yeah right.", Assets.npcChicken, true));
                c.add(new Conversation("Only us pear folk will know the truth.", Assets.npcChicken, true));
                c.add(new Conversation("(Chi-Ken grabs the orange from out of your hand and stomps it into the ground)", Assets.NULL, false));
                c.add(new Conversation("Pears will live on forever!", Assets.npcChicken, true));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    Mission.missions[missionSet[status]-1].complete();
                    Mission.missions[missionSet[status]].setCompleteMessage("Go talk to Captain Cactus");
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission(handler.getPlayer().getMissionManager().getMissions().size()-2);
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    status ++;
                });
                break;
            default:
                c.clear();
                c.add(new Conversation("Greetings!", Assets.npcChicken, false));
                manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
        }
        manager.hideUI();
        manager.getConvBox().setActive();
    }

    @Override
    protected ArrayList<Conversation>[] getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcChicken, false));
        return new ArrayList[]{(ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone()};
    }

    @Override
    protected ArrayList<Conversation>[] getCompleteConversation() {
        return new ArrayList[0];
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.npcChicken, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.npcChicken, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Ms. Chi-Ken";
    }
}
