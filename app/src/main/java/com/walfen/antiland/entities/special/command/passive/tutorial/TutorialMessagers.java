package com.walfen.antiland.entities.special.command.passive.tutorial;

import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.special.command.passive.Messager;

public class TutorialMessagers {

    public static class TutorialMovement extends TutorialMessager{

        public TutorialMovement() {
            super(1002);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"Can you still move?\" A weird and spooky voices wakes you up from inside.", "...",
                    () -> handler.getUIManager().activeTutorial("Tutorial: Use the left joystick to move around", handler.getUIManager().getCGUI().getMovementJoystick().getBounds()));
        }
    }

    public static class TutorialAttack extends TutorialMessager{

        public TutorialAttack() {
            super(1003);
            actionBounds = new Rect(0, 0, 128, 512);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"It looks like you are fine. Good.\" The sound continues: \"But it looks like you will have some trouble in the front. " +
                    "There are slimes guarding the path. Still remember how to fight?\"", "...", () -> handler.getUIManager().activeTutorial(
                            "Tutorial: Use the right joystick to attack", handler.getUIManager().getCGUI().getAttackJoystick().getBounds()));
        }
    }

    public static class TutorialPortal extends TutorialMessager{

        public TutorialPortal() {
            super(1004);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"Keep going, I'll meet you in the next room.\" The sound appeared again.", "\"Who are you?\"",
                    () -> handler.getUIManager().popUpMessage("\"Another trapped soul in this temple. If you don't want to fall into the same fate as me, then follow my guide.\" The sound answers"));
        }
    }

    public static class TutorialUnleash extends TutorialMessager{

        public TutorialUnleash() {
            super(1005);
        }

        @Override
        protected void displayMessage() {
            handler.getUIManager().popUpAction("\"Break the container in front and free me. Once that's done I can break open the temple for us.\" The sound is getting louder", "...",
                    () -> handler.getUIManager().activeTutorial("Tutorial: Usually you can get close to entities with symbols on top to interact with them. To do so, click on the interact button that appears"+
                     " after you get close to the entity.", new Rect(Constants.SCREEN_WIDTH-160, Constants.SCREEN_HEIGHT-504, Constants.SCREEN_WIDTH-32, Constants.SCREEN_HEIGHT-376)));
        }
    }
}
