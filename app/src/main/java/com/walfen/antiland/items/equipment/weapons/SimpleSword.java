package com.walfen.antiland.items.equipment.weapons;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.equipment.Equipment;

public class SimpleSword extends Weapon {

    private final int dmg;

    public SimpleSword(Bitmap texture, String name, int dmg, int id) {
        super(texture, name, id, 100*dmg*dmg);
        this.dmg = dmg;
    }


    @Override
    public void onEquip(Player player) {
        player.changePhysicalDamage(dmg);
    }

    @Override
    public void onRemove() {
        handler.getPlayer().changePhysicalDamage(-dmg);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleSword i = new SimpleSword(texture, name, dmg, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleSword i = new SimpleSword(texture, name, dmg, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "Simple but effective weapon";
    }

    @Override
    public String[] getEffect() {
        return new String[]{"dmg +"+dmg};
    }
}
