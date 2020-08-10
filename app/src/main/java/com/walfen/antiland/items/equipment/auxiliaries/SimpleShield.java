package com.walfen.antiland.items.equipment.auxiliaries;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.equipment.Equipment;

public class SimpleShield extends Auxiliary {
    public SimpleShield(Bitmap texture, String name, int id) {
        super(texture, name, id, 100);
    }


    @Override
    public void onEquip(Player player) {
        player.changeDefence(1);
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

    @Override
    public String getDesc() {
        return "Just an old wooden door.";
    }

    @Override
    public String getEffect() {
        return "defence +1";
    }
}
