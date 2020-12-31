package com.walfen.antiland.entities.special.command.passive.island;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.R;
import com.walfen.antiland.entities.special.command.passive.tutorial.TutorialMessager;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class IslandMessagers {

    public static class TutorialStatus extends TutorialMessager{

    public TutorialStatus() {
        super(1014);
        actionBounds = new Rect(0, 0, 256, 384);
    }

    @Override
    protected void displayMessage() {
        handler.getGame().getMusicController().playMusic(R.raw.antiland_beach);
        handler.getUIManager().activeTutorial("Tutorial: use your perk points", new Rect(16, 16,
                16+144, 16+144), () ->
                handler.getUIManager().popUpAction("This is your player status screen.",
                        "Ok", () -> handler.getUIManager().activeTutorial("Tutorial: switch to the skills screen",
                                handler.getPlayer().getMapManager().getSwitchButtonBounds(),
                                () -> handler.getUIManager().popUpMessage("You can upgrade skills by spending perk points. You will get a perk point each time you upgrades."))));
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
            handler.getUIManager().popUpAction("You recognize the barricade from the Blacksmith’s description but the gaping hole suggests otherwise. " +
                    "As you examine the barricade, you feel the ancient magic book shaking", "...",
                    () ->{
                        ArrayList<Conversation> c = new ArrayList<>();
                        c.add(new Conversation("... come ...", Assets.NULL, false));
                        handler.getUIManager().getConvBox().setConversationList(c, () -> handler.getUIManager().popUpAction("Looking past the barricade, you notice an object radiating light and coughing out monsters. " +
                                "The monsters have not noticed you but they seem rather violent. " +
                                "As their numbers level out, you realize the threat that they could pose to the village. ", "It seems I’ll have to deal with it soon ",
                                () -> handler.getPlayer().getMissionManager().addMission(6)));
                        handler.getUIManager().hideUI();
                        handler.getUIManager().getConvBox().setActive();
                    });
        }
    }

    public static class Indicator extends TutorialMessager{

        private Animation dynamicTexture;

        public Indicator() {
            super(1018);
            dynamicTexture = new Animation(0.2f, Assets.targetIndicator);
            actionBounds = new Rect(0, 0, 384, 384);
        }

        @Override
        protected void displayMessage() {
            health = 0;
            active = false;
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
            dynamicTexture.draw(canvas, new Rect(left, top, left+128, top+128));
        }
    }

}
