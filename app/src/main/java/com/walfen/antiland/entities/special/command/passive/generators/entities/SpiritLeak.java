package com.walfen.antiland.entities.special.command.passive.generators.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;

public class SpiritLeak extends EntityGenerator {

    private Animation gapAnimation;
    private ChangeEvent playerInteractEvent;

    public SpiritLeak() {
        super(206, 256, 5, GenerationConstant.NORMAL_GENERATION, 1103);
        gapAnimation = new Animation(0.3f, Assets.spiritLeak);
    }

    private boolean interactionCheck(int innerDeduction){
        if(health == 0)
            return false;
        int i = 2 - innerDeduction;
        return new Rect((int)(x-i* Tile.TILEWIDTH), (int)(y-i*Tile.TILEHEIGHT),
                (int)(x+i*Tile.TILEWIDTH+128), (int)(y+i*Tile.TILEHEIGHT+128)).
                contains((int)(handler.getPlayer().getX()+64), (int)(handler.getPlayer().getY()+64));
        // block distance
    }

    private void interact(){
        handler.getPlayer().getTracker().addTracking(this);
        handler.getUIManager().popUpAction("The sounds of spirits are leaking into the air around you. " +
                "As you approach the spiritual leak, the book you got from the temple begins to react. You expose the book to the leak and it absorbs the energy.",
                "seal the leak!", () -> {health = 0;
                    active = false;
                    handler.getPlayer().setInteractionEvent(Constants.EMPTY_EVENT, -1);
                    handler.getPlayer().setInterButtonVisibility(false);
        });
    }

    private void drawHeadSign(Canvas canvas){
        int iX = (int)(x+128/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
        canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
    }

    @Override
    public void update() {
        super.update();
        gapAnimation.update();
        if(interactionCheck(0)){
            if(interactionCheck(1)) {
                if(handler.getPlayer().getInteractionEvent().equals(playerInteractEvent))
                    return;
                handler.getPlayer().setInteractionEvent(playerInteractEvent, NPC.InteractionType.TRIGGER_ENTITY);
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

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY, int status) {
        super.initialize(handler, x, y, oX, oY, status);
        playerInteractEvent = this::interact;
    }

    @Override
    protected void spawnEntity() {
        int locationX, locationY;

        locationX = Utils.pickNumberBetween((int) (x - spawnRange), (int) (x + spawnRange));
        locationY = Utils.pickNumberBetween((int) (y - spawnRange), (int) (y + spawnRange));
        int ge = Math.random()>0.4?generatedEntity:205;
        Entity e = Entity.entityList[ge].clone();
        e.initialize(handler, locationX, locationY, locationX, locationY, 0);
        handler.getWorld().getEntityManager().addEntityHot(e);
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        gapAnimation.draw(canvas, new Rect(left, top, left+128, top+128));
        drawHeadSign(canvas);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.spiritLeak[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Spirit Leak";
    }
}
