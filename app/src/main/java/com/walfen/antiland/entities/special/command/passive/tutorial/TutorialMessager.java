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
        handler.getUIManager().getCGUI().onTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, 0, 0, 0));
        displayMessage();
    }

    @Override
    protected boolean isActionAllowed() {
        return super.isActionAllowed() && status == 0;
    }

    protected abstract void displayMessage();
}
