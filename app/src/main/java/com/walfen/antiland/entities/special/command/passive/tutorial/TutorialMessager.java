package com.walfen.antiland.entities.special.command.passive.tutorial;

import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.entities.special.command.passive.Messager;

public abstract class TutorialMessager extends Messager {

    public TutorialMessager(int id) {
        super(id);
    }

    @Override
    protected void commandAction() {
        status = 1;
        handler.getUIManager().getCGUI().resetJoystick();
        displayMessage();
    }

    @Override
    protected boolean isActionAllowed() {
        return super.isActionAllowed() && status == 0;
    }

    protected abstract void displayMessage();
}
