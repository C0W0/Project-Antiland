package com.walfen.antiland.items.equipment;

import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class SimpleSword extends Equipment {

    public SimpleSword(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }

    @Override
    public void onEquip() {
        handler.getPlayer().changeBaseDamage(1);
    }

    @Override
    public void onRemove() {
        handler.getPlayer().changeBaseDamage(-1);
        addToInv(1);
    }

    @Override
    public Item addToInv(int count) {
        SimpleSword i = new SimpleSword(texture, name, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Item createNew(int x, int y, int count) {
        SimpleSword i = new SimpleSword(texture, name, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }
}
