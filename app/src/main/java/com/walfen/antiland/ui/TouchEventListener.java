package com.walfen.antiland.ui;

import android.view.MotionEvent;

import com.walfen.antiland.GameHierarchyElement;

public interface TouchEventListener extends GameHierarchyElement {

    public void onTouchEvent(MotionEvent event);

}
