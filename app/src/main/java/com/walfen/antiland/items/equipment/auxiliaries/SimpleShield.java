package com.walfen.antiland.items.equipment.auxiliaries;

import android.graphics.Bitmap;

import com.walfen.antiland.items.equipment.Equipment;

public class SimpleShield extends Auxiliary {
    public SimpleShield(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }

    @Override
    protected void onEquip() {
        handler.getPlayer().changeDefence(1);
    }

    @Override
    protected void onRemove() {
        handler.getPlayer().changeDefence(-1);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleShield i = new SimpleShield(texture, name, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleShield i = new SimpleShield(texture, name, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }
}
