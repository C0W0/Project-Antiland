package com.walfen.antiland.items.functionless;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class NeutralItem extends Item {
    public NeutralItem(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }

    @Override
    public void onActive() {}

    @Override
    public Item addToInv(int count) {
        NeutralItem i = new NeutralItem(texture, name, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public NeutralItem createNew(int x, int y, int count){
        NeutralItem i = new NeutralItem(texture, name, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }
}
