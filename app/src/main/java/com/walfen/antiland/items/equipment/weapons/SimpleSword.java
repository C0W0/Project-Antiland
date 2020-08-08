package com.walfen.antiland.items.equipment.weapons;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.equipment.Equipment;

public class SimpleSword extends Weapon {

    public SimpleSword(Bitmap texture, String name, int id, int value) {
        super(texture, name, id);
    }


    @Override
    public void onEquip(Player player) {
        player.changePhysicalDamage(1);
    }

    @Override
    public void onRemove() {
        handler.getPlayer().changePhysicalDamage(-1);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleSword i = new SimpleSword(texture, name, id, value);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleSword i = new SimpleSword(texture, name, id, value);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "The door handle of the shield.";
    }

    @Override
    public String getEffect() {
        return "dmg +1";
    }
}
