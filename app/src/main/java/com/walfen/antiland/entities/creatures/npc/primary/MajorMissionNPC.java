package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public abstract class MajorMissionNPC extends NPC {

    protected boolean convBoxOn = false;
    protected int[] missionSet;

    public MajorMissionNPC(int width, int height, int id, int[] missionSet) {
        super(width, height, id);
        this.missionSet = missionSet;
    }

    @Override
    protected void interact() {
        if(convBoxOn)
            return;
        if(status < missionSet.length && handler.getPlayer().getMissionManager().hasMission(missionSet[status])){
            if(Mission.missions[missionSet[status]].isCompleted()){
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getCompleteConversation()[status];
                uiManager.getConvBox().setConversationList(c, () -> {Mission.missions[missionSet[status]].complete(); convBoxOn = false;
                    status ++;});
                uiManager.getConvBox().setActive();
            }else {
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getIncompleteConversation()[status];
                uiManager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                uiManager.getConvBox().setActive();
            }
            return;
        }
        convBoxOn = true;
        assignConversationProcedure(handler.getUIManager());
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(status >= missionSet.length)
            return;
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        if(handler.getPlayer().getMissionManager().hasMission(missionSet[status])){
            if(Mission.missions[missionSet[status]].isCompleted()){
                canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
                canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
            }else {
                canvas.drawBitmap(Assets.headSignGray, null, destRect, Constants.getRenderPaint());
                canvas.drawBitmap(Assets.hsgMissionComplete, null, destRect, Constants.getRenderPaint());
            }
        }else {
            canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
            canvas.drawBitmap(Assets.hsoGetMission, null, destRect, Constants.getRenderPaint());
        }
    }

    protected void assignMission() {
        handler.getPlayer().getMissionManager().addMission(missionSet[status]);
    }

    protected abstract void assignConversationProcedure(UIManager manager);

    protected abstract ArrayList<Conversation>[] getIncompleteConversation();

    protected abstract ArrayList<Conversation>[] getCompleteConversation();

}
