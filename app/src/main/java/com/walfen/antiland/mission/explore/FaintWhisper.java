package com.walfen.antiland.mission.explore;

import android.graphics.Point;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class FaintWhisper extends ExploreMission {

    private boolean ctrl;

    public FaintWhisper() {
        super("Faint Whisper", "Identify the voice", 7, 25, new Point(10240, 10880), 2, 3);
        ctrl = false;
    }

    @Override
    public void update() {
        if(isCompleted() && !ctrl){
            handler.getUIManager().popUpAction("As you approach the peninsula, you see the spirit from the ruins. " +
                            "It gestures towards you and the helmet starts shaking violently. " +
                            "You pull it out and spiritual energy explodes into the air. " +
                            "The tempest torrent concentrates around the spirit as the figure of a warrior takes shape.", "So you are the sound in my head",
                    () ->{
                        ArrayList<Conversation> c = new ArrayList<>();
                        c.add(new Conversation("Perish...", Assets.spiritWarriorAttack[0], false));
                        handler.getUIManager().getConvBox().setConversationList(c, () -> {
                            handler.getPlayer().getMissionManager().addMission(8);
                            Entity e = Entity.entityList[207].clone();
                            e.initialize(handler, 10880, 11136, 10880, 11136, 0);
                            handler.getWorld().getEntityManager().addEntityHot(e);
                            complete();
                        });
                        handler.getUIManager().hideUI();
                        handler.getUIManager().getConvBox().setActive();
                    });
            ctrl = true;
        }
    }

    @Override
    public void receiveReward() {

    }

    @Override
    public void setHandler(Handler handler) {
        super.setHandler(handler);
        if(handler != null)
            ctrl = false;
    }
}
