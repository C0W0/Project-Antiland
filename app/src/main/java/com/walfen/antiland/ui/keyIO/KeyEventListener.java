package com.walfen.adrdtest.ui;

import android.view.KeyEvent;

public interface KeyEventListener {

    public void onKeyDown(int keyCode, KeyEvent event);

    public void onKeyLongPress(int keyCode, KeyEvent event);

}
