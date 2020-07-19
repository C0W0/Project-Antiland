package com.walfen.antiland.items.equipment;


import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.weapons.SimpleSword;

public abstract class Equipment extends Item {

    public static final int WEAPON = 0;
    public static final int AUXILIARY = 1;
    public static final int ARMOUR = 2;
    public static final int BOOTS = 3;

    protected boolean equippedOn = false;

    public Equipment(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }


    public void onEquip(){
        onEquip(handler.getPlayer());
    }

    public abstract void onEquip(Player player);

    protected abstract void onRemove();

    public boolean isEquippedOn() {
        return equippedOn;
    }

    public void setEquippedOn(boolean equippedOn) {
        this.equippedOn = equippedOn;
    }
}
