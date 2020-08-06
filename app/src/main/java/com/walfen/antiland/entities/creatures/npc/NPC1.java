package com.walfen.antiland.entities.creatures.npc;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
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
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = Tile.TILEWIDTH;
        bounds.bottom = Tile.TILEHEIGHT;
        dynamicTexture = new Animation(0.05f, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    protected void interact() {
        if(convBoxOn)
            return;
        if(handler.getPlayer().getMissionManager().hasMission(3))
            return;
        convBoxOn = true;
        UIManager uiManager = handler.getUIManager();
        uiManager.hideUI();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("We need to more spaces for our village. Do you want to help us to clear a field?", Assets.npcCrab[0], false));
        c.add(new Conversation("Absolutely!", Assets.player_neutral, true));
        uiManager.getConvBox().setConversationList(c, () -> assignMission(3));
        uiManager.getConvBox().setActive();
    }

    @Override
    protected void assignMission(int missionID) {
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
    public void die() {

    }
}
