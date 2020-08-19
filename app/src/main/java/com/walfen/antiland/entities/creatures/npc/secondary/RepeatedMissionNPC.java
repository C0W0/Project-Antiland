package com.walfen.antiland.entities.creatures.npc.secondary;

import android.graphics.Canvas;

import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public abstract class RepeatedMissionNPC extends NPC {

    protected boolean convBoxOn = false;
    protected int missionID;

    public RepeatedMissionNPC(int width, int height, int id, int missionID) {
        super(width, height, id);
        this.missionID = missionID;
    }

    @Override
    protected void interact() {
        if(convBoxOn)
            return;
        if(handler.getPlayer().getMissionManager().hasMission(missionID)){
            if(Mission.missions[missionID].isCompleted()){
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getCompleteConversation();
                uiManager.getConvBox().setConversationList(c, () -> {Mission.missions[missionID].complete(); convBoxOn = false;});
                uiManager.getConvBox().setActive();
            }else {
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getIncompleteConversation();
                uiManager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                uiManager.getConvBox().setActive();
            }
            return;
        }
        convBoxOn = true;
        UIManager uiManager = handler.getUIManager();
        uiManager.hideUI();
        ArrayList<Conversation> c = getAssigningConversation();
        uiManager.getConvBox().setConversationList(c, () -> {assignMission(); convBoxOn = false;});
        uiManager.getConvBox().setActive();
    }

    private void assignMission() {
        handler.getPlayer().getMissionManager().addMission(missionID);
    }

    protected abstract ArrayList<Conversation> getAssigningConversation();

    protected abstract ArrayList<Conversation> getIncompleteConversation();

    protected abstract ArrayList<Conversation> getCompleteConversation();

}
