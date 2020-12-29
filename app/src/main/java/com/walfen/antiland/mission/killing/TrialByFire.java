package com.walfen.antiland.mission.killing;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class TrialByFire extends DestroyEntity {

    private boolean ctrl;

    public TrialByFire() {
        super("Trial by Fire", "Seal all spiritual leaks in the Far Harbour", new int[]{1103}, 6, new int[]{6}, 30, 100, 0);
        ctrl = false;
    }

    @Override
    public void update() {
        super.update();
        if(isCompleted() && !ctrl){
            handler.getUIManager().popUpAction("With the last leak sealed, you hear an ominous voice.", "...",
                    () ->{
                        ArrayList<Conversation> c = new ArrayList<>();
                        c.add(new Conversation("... come ...", Assets.NULL, false));
                        handler.getUIManager().getConvBox().setConversationList(c, () -> handler.getUIManager().popUpAction("You feel the voice echo through your body." +
                                        "The ancient helmet begins shaking more vigorously than before.", "What is happening?",
                                () -> {handler.getPlayer().getMissionManager().addMission(7); complete(); handler.getGame().autoSave();}));
                        handler.getUIManager().hideUI();
                        handler.getUIManager().getConvBox().setActive();
                    });
            ctrl = true;
        }
    }

    @Override
    public void setHandler(Handler handler) {
        super.setHandler(handler);
        if(handler != null)
            ctrl = false;
    }
}
