package com.walfen.antiland.mission.killing;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class RestlessSouls extends KillingMission {

    private boolean ctrl;

    public RestlessSouls() {
        super("Restless Souls", "Defeat the Spirit Warrior", 8, 1, 50);
        finalProgress = new int[]{1};
        targetEntityID = new int[]{207};
    }

    @Override
    public void update() {
        super.update();
        if(isCompleted() && !ctrl){
            handler.getUIManager().popUpAction("With one final hit, the warrior submits." +
                            "With its power spent, the spirit warrior dissipates into the air.", "Finally!",
                    () ->{
                        ArrayList<Conversation> c = new ArrayList<>();
                        c.add(new Conversation("Your time will come...", Assets.spiritWarriorAttack[0], false));
                        c.add(new Conversation("This island will fall...", Assets.spiritWarriorAttack[0], false));
                        c.add(new Conversation("My fight is not over.", Assets.spiritWarriorAttack[0], false));
                        c.add(new Conversation("...", Assets.player_icon, false));
                        c.add(new Conversation("The spirit disappears and your senses slowly return to normal.", Assets.NULL, false));
                        c.add(new Conversation("...", Assets.NULL, false));
                        c.add(new Conversation("The wind and waves sing a pleasant song.", Assets.NULL, false));
                        c.add(new Conversation("...", Assets.NULL, false));
                        c.add(new Conversation("The smell of salt fills your nose.", Assets.NULL, false));
                        c.add(new Conversation("...", Assets.NULL, false));
                        c.add(new Conversation("The spirit is gone.", Assets.NULL, false));

                        handler.getUIManager().getConvBox().setConversationList(c, () -> {
                            handler.getUIManager().popUpAction("Thank you for playing Antiland, we hope you enjoyed our game. " +
                                    "Your story might be over... for now... but there's more to see! " +
                                    "Feel free to explore around the map, work on side missions, or simply wait for an update.",
                                    "Thank you so much!", () -> {
                                        handler.getUIManager().popUpMessage("Once again, we sincerely thank you for playing this game. /sl/ The Antiland development team");
                                        complete();
                                    });
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
