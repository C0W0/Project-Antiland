package com.walfen.antiland.items.equipment.auxiliaries;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.equipment.Equipment;

public class SimpleShield extends Auxiliary {

    private final int defence;

    public SimpleShield(Bitmap texture, String name, int defence, int id) {
        super(texture, name, id, 100*defence*defence);
        this.defence = defence;
    }


    @Override
    public void onEquip(Player player) {
        player.changeDefence(defence);
    }

    @Override
    protected void onRemove() {
        handler.getPlayer().changeDefence(-defence);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleShield i = new SimpleShield(texture, name, defence, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleShield i = new SimpleShield(texture, name, defence, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "Something to block incoming attacks";
    }

    @Override
    public String[] getEffect() {
        return new String[]{"defence +"+defence};
    }
}
