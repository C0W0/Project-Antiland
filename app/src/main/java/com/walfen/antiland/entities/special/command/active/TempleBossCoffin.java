package com.walfen.antiland.entities.special.command.active;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.ui.overlay.MissionPanel;

import java.util.ArrayList;

public class TempleBossCoffin extends ActiveCommandEntity {

    public TempleBossCoffin() {
        super(1, 1, 3, 1301);
    }

    @Override
    public boolean checkEntityCollision(float xOffset, float yOffset) {
        return false; //no collision
    }

    @Override
    public Rect getCollisionBounds(float xOffset, float yOffset) {
        return new Rect(0, 0, 0, 0);
    }

    @Override
    public void update() {
        super.update();
        if(status == 1){
            handler.getPlayer().setInteractionEvent(Constants.EMPTY_EVENT, -1);
            handler.getPlayer().setInterButtonVisibility(false);
        }
    }

    @Override
    protected void interact() {
        if(status == 1)
            return;
        status = 1;
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("Finally, after an eternity of suffering, I'm free!", Assets.trappedSpiritMovementDown, false));
        c.add(new Conversation("But I still can't break through the temple...", Assets.trappedSpiritMovementDown, false));
        c.add(new Conversation("...", Assets.player_icon, true));
        c.add(new Conversation("What's next? (I have a bad feeling about this)", Assets.player_icon, true));
        c.add(new Conversation("Ah, you just have to do me one more favour. I just need the energy of...", Assets.trappedSpiritMovementDown, false));
        c.add(new Conversation("YOUR LIFE!", Assets.trappedSpiritAttackDown, false));
        handler.getUIManager().hideUI();
        handler.getUIManager().getConvBox().setConversationList(c, () -> handler.getWorld().triggerWorldEvent(0));
        handler.getUIManager().getConvBox().setActive();
        handler.getPlayer().getMissionManager().addMission(2);
        handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(status == 1)
            return;
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
        canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        if(status != 1)
            return;
        //draw the cracks on the coffin
    }
}
