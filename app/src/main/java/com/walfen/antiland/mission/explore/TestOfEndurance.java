package com.walfen.antiland.mission.explore;

import android.graphics.Point;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class TestOfEndurance extends ExploreMission {

    private Point destPos;
    private int startHealth;

    public TestOfEndurance(int id, Point destPos) {
        super("Test of Endurance "+(id-8), "Complete the course without taking damage", id, 10, destPos, 2, 2);
        this.destPos = destPos;
    }

    @Override
    public void update() {
        if(isCompleted()){
            if(handler.getPlayer().getHealth() < startHealth)
                handler.getUIManager().popUpMessage("Mission Failed: You have received damage");
            else
                handler.getPlayer().getMissionManager().addMission(id+1);
            complete();
        }
    }

    @Override
    public void receiveReward() {

    }

    @Override
    public void setHandler(Handler handler) {
        super.setHandler(handler);
        if(handler != null){
            startHealth = handler.getPlayer().getHealth();
            Entity e = Entity.entityList[1018].clone();
            e.initialize(handler, destPos.x, destPos.y, destPos.x, destPos.y, 0);
            handler.getWorld().getEntityManager().addEntityHot(e);
        }
    }

    public static class EndTestOfEndurance extends ExploreMission{

        private boolean ctrl;

        public EndTestOfEndurance() {
            super("Test of Endurance 4", "Test of Endurance", 12, 10, new Point(2816, 3712), 2, 2);
            ctrl = false;
        }

        @Override
        public void update() {
            if(isCompleted() && !ctrl){
                ArrayList<Conversation> c = new ArrayList<>();
                c.add(new Conversation("(You finish the course while gasping desperately for air)", Assets.NULL, false));
                c.add(new Conversation("Amazing, you looked so passionate while running.", Assets.npcChicken, true));
                c.add(new Conversation("I don’t know about you, but I think you should always be passionate!", Assets.npcChicken, true));
                c.add(new Conversation("There’s so much to do and so much to see, but, " +
                        "honestly, what’s the point if you don’t care about what you're doing?", Assets.npcChicken, true));
                c.add(new Conversation("Take your world and put in 100% of your effort. " +
                        "Only then will you get 100% of the results.", Assets.npcChicken, true));
                c.add(new Conversation("Do things like you mean it.", Assets.npcChicken, true));
                c.add(new Conversation("You've received 1 perk point", Assets.NULL, false));
                handler.getUIManager().getConvBox().setConversationList(c, () -> {
                    handler.getPlayer().changePerkPoints(1);
                    handler.getWorld().getEntityManager().modifyEntityHot("Ms. Chi-Ken", 2);
                    complete();
                });
                handler.getUIManager().hideUI();
                handler.getUIManager().getConvBox().setActive();
                ctrl = true;
            }
        }

        @Override
        public void receiveReward() {

        }

        @Override
        public void setHandler(Handler handler) {
            super.setHandler(handler);
            if(handler != null){
                ctrl = false;
                Entity e = Entity.entityList[1018].clone();
                e.initialize(handler, 2816, 3584, 2816, 3584, 0);
                handler.getWorld().getEntityManager().addEntityHot(e);
            }
        }
    }
}
