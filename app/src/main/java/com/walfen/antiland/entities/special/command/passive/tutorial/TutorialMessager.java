package com.walfen.antiland.entities.special.command.passive.tutorial;

import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.entities.special.command.passive.Messager;

public abstract class TutorialMessager extends Messager {

    private boolean isMessageTriggered;

    public TutorialMessager(int id) {
        super(id);
        isMessageTriggered = false;
    }

    @Override
    protected void commandAction() {
        isMessageTriggered = true;
        handler.getUIManager().getCGUI().onTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, 0, 0, 0));
        displayMessage();
    }

    @Override
    protected boolean isActionAllowed() {
        return super.isActionAllowed() && !isMessageTriggered;
    }

    protected abstract void displayMessage();
}
