package com.walfen.antiland.entities.creatures.npc;


import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.untils.Utils;

public abstract class NPC extends Creature {

    protected int interRange;
    protected ChangeEvent playerInteractEvent;

    public NPC(int width, int height, int id) {
        super(width, height, id);
        interRange = 2; //default
    }

    public boolean interactionCheck(int innerDeduction){
        int i = interRange - innerDeduction;
        return new Rect((int)(x-i*Tile.TILEWIDTH), (int)(y-i*Tile.TILEHEIGHT),
                (int)(x+i*Tile.TILEWIDTH+width), (int)(y+i*Tile.TILEHEIGHT+height)).
                contains((int)(handler.getPlayer().getX()+64), (int)(handler.getPlayer().getY()+64));
        // block distance
    }

    protected abstract void interact();

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY) {
        super.initialize(handler, x, y, oX, oY);
        playerInteractEvent = this::interact;
    }

    @Override
    public void update() {
        super.update();
        if(interactionCheck(0)){
            if(interactionCheck(1)) {
                if(handler.getPlayer().getInteractionEvent().equals(playerInteractEvent))
                    return;
                handler.getPlayer().setInteractionEvent(playerInteractEvent, InteractionType.TALK_TO_NPC);
                if(!handler.getPlayer().getInteractButton().isActive())
                    handler.getPlayer().setInterButtonVisibility(true);
                return;
            }
            if(handler.getPlayer().getInteractionEvent().equals(playerInteractEvent)){
                handler.getPlayer().setInteractionEvent(Constants.EMPTY_EVENT, -1);
                handler.getPlayer().setInterButtonVisibility(false);
            }
        }
    }
    
    public static class InteractionType{
        
        public static final int TALK_TO_NPC = 1;
        public static final int TRADE = 2;
        public static final int TRIGGER_ENTITY = 3;
        public static final int USE_TELEPORTATION = 4;
        
    }
}
