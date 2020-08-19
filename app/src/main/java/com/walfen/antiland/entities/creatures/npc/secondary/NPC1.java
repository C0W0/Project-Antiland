package com.walfen.antiland.entities.creatures.npc.secondary;


import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class NPC1 extends RepeatedMissionNPC {

    private Animation dynamicTexture;

    public NPC1() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3, 4);
        interRange = 2;
        dynamicTexture = new Animation(6, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    protected ArrayList<Conversation> getAssigningConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("We are suffering from increasing number of slime attacks.", Assets.npcCrab[0], false));
        c.add(new Conversation("What?! How can I help?", Assets.player_neutral, true));
        c.add(new Conversation("Go down to the forests and kills 5 of them to decrease their number. " +
                "Once you finish with them come back to me. I have some rewards for you.", Assets.npcCrab[0], false));
        return c;
    }

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
