package com.walfen.antiland.entities.creatures.npc;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.tiles.Tile;

public class NPC1 extends NPC {

    private Animation dynamicTexture;

    public NPC1() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 3);
        interRange = 1;
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = Tile.TILEWIDTH;
        bounds.bottom = Tile.TILEHEIGHT;
        dynamicTexture = new Animation(0.05f, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void receiveDamage(int num) {}

    @Override
    protected void interact() {
        assignMission(1);
    }

    @Override
    protected void assignMission(int missionID) {
        handler.getWorld().getPlayer().getMissionManager().addMission(missionID);
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
