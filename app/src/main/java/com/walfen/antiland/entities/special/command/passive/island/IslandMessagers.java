package com.walfen.antiland.entities.special.command.passive.island;

import android.graphics.Rect;

import com.walfen.antiland.entities.special.command.passive.tutorial.TutorialMessager;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class IslandMessagers {

    public static class TutorialStatus extends TutorialMessager{

    public TutorialStatus() {
        super(1014);
    }

    @Override
    protected void displayMessage() {

    }
}

    public static class TutorialInventory extends TutorialMessager{

        public TutorialInventory() {
            super(1015);
        }

        @Override
        protected void displayMessage() {

        }
    }

    public static class EncounterMonster extends TutorialMessager{

        public EncounterMonster() {
            super(1016);
            actionBounds = new Rect(0, 0, 384, 256);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpMessage("As you approach the Abandoned Port, you see a monster fiddling with an object.");
        }
    }

    public static class BarricadeBroken extends TutorialMessager{

        public BarricadeBroken() {
            super(1017);
            actionBounds = new Rect(0, 0, 384, 384);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("You recognize the barricade from the Blacksmith’s description but the gaping hole suggests otherwise." +
                    "As you examine the barricade, you feel the ancient helmet shaking", "...",
                    () ->{
                        ArrayList<Conversation> c = new ArrayList<>();
                        c.add(new Conversation("... come ...", Assets.NULL, false));
                        handler.getUIManager().getConvBox().setConversationList(c, () -> handler.getUIManager().popUpAction("Looking past the barricade, you notice an object radiating light and coughing out monsters." +
                                "The monsters have not noticed you but they seem rather violent." +
                                "As their numbers level out, you realize the threat that they could pose to the village.", "It seems I’ll have to deal with it soon",
                                () -> handler.getPlayer().getMissionManager().addMission(6)));
                        handler.getUIManager().hideUI();
                        handler.getUIManager().getConvBox().setActive();
                    });
        }
    }

}
