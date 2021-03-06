package com.walfen.antiland.items.equipment.weapons;

import android.graphics.Bitmap;

import com.walfen.antiland.items.equipment.Equipment;

public abstract class Weapon extends Equipment {

    public Weapon(Bitmap texture, String name, int id, int value) {
        super(texture, name, id, value);
    }

    @Override
    public void onActive() {
        if(!equippedOn){
            onEquip();
            handler.getPlayer().equip(id, Equipment.WEAPON);
        } else {
            onRemove();
            handler.getPlayer().removeEquipment(Equipment.WEAPON);
        }
    }
}
