package com.walfen.antiland.entities.special.command.passive.tutorial;

import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.ui.overlay.MissionPanel;

public class TutorialMessagers {

    public static class TutorialMovement extends TutorialMessager{

        public TutorialMovement() {
            super(1009);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"Can you still move?\" A weird and spooky voices wakes you up from inside.", "(Where am I)",
                    () -> handler.getUIManager().activeTutorial("Tutorial: Use the left joystick to move around", handler.getUIManager().getCGUI().getMovementJoystick().getBounds()));
            handler.getPlayer().getMissionManager().addMission(1);
            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
        }
    }

    public static class TutorialMission extends TutorialMessager{

        public TutorialMission() {
            super(1010);
            actionBounds = new Rect(0, 0, 128, 512);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
            handler.getUIManager().activeTutorial("Tutorial: Check your current mission by opening up the mission tab", handler.getUIManager().getCGUI().getMissionPanel().getBounds(),
                    () -> handler.getUIManager().popUpMessage("You can expand and collapse the mission panel by pressing on the arrow on the side"));
        }
    }

    public static class TutorialAttack extends TutorialMessager{

        public TutorialAttack() {
            super(1011);
            actionBounds = new Rect(0, 0, 128, 512);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"It looks like you are fine. Good.\" The sound continues: \"But it looks like you will have some trouble in the front. " +
                    "There are slimes guarding the path. Still remember how to fight?\"", "(What's going on)", () -> handler.getUIManager().activeTutorial(
                            "Tutorial: Use the right joystick to attack. /sl/Hint: try to stay outside of enemy's attack range while fighting",
                    handler.getUIManager().getCGUI().getAttackJoystick().getBounds()));
        }
    }

    public static class TutorialPortal extends TutorialMessager{

        public TutorialPortal() {
            super(1012);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().activeTutorial("Tutorial: check the map", new Rect(Constants.SCREEN_WIDTH-8-128, Constants.SCREEN_HEIGHT-8-128,
                    Constants.SCREEN_WIDTH-8, Constants.SCREEN_HEIGHT-8), () ->
                    handler.getUIManager().popUpAction("This is the global map of the world.", "Ok", () ->
                            handler.getUIManager().activeTutorial("Tutorial: switch to local map to check your position", handler.getPlayer().getMapManager().getSwitchButtonBounds(),
                                    () -> handler.getUIManager().popUpMessage("The red dot indicates you current location"))));
        }
    }

    public static class TutorialUnleash extends TutorialMessager{

        public TutorialUnleash() {
            super(1013);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"Break the container in front and free me. Once that's done I can break open the temple for us.\" The sound is getting louder", "(This feels strange)",
                    () -> handler.getUIManager().activeTutorial("Tutorial: The interact button will appear in the red box when you can interact with the environment. /sl/Hint: click in the red box to continue",
                            new Rect(Constants.SCREEN_WIDTH-160, Constants.SCREEN_HEIGHT-504, Constants.SCREEN_WIDTH-32, Constants.SCREEN_HEIGHT-376)));
        }
    }
}
