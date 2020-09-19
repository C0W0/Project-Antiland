package com.walfen.antiland.items.equipment.armours;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.Equipment;
import com.walfen.antiland.items.equipment.auxiliaries.SimpleShield;

public class SimpleArmour extends Armour {

    private final int magicDefence, physicalDefence;

    public SimpleArmour(Bitmap texture, String name, int magicDefence, int physicalDefence, int id) {
        super(texture, name, id, (int)(Math.pow((magicDefence*2+physicalDefence), 2)*100));
        this.magicDefence = magicDefence;
        this.physicalDefence = physicalDefence;
    }

    @Override
    public void onEquip(Player player) {
        player.changeDefence(physicalDefence);
        player.changeMagicalDefence(magicDefence);
    }

    @Override
    protected void onRemove() {
        handler.getPlayer().changeDefence(-physicalDefence);
        handler.getPlayer().changeMagicalDefence(-magicDefence);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleArmour i = new SimpleArmour(texture, name, magicDefence, physicalDefence, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleArmour i = new SimpleArmour(texture, name, magicDefence, physicalDefence, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "Body Armour";
    }

    @Override
    public String[] getEffect() {
        return new String[]{"defence +"+physicalDefence, "magical defence +"+magicDefence};
    }
}
