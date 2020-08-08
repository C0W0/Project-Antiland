package com.walfen.antiland.items.functionless;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class NeutralItem extends Item {

    private String desc, effect;

    public NeutralItem(Bitmap texture, String name, int id,
                       String desc, String effect) {
        super(texture, name, id);
        this.desc = desc;
        this.effect = effect;
    }

    @Override
    public void onActive() {}

    @Override
    public Item addToInv(int count) {
        NeutralItem i = new NeutralItem(texture, name, id, desc, effect);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public NeutralItem createNew(int x, int y, int count){
        NeutralItem i = new NeutralItem(texture, name, id, desc, effect);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getEffect() {
        return effect;
    }
}
