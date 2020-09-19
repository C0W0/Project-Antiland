package com.walfen.antiland.items.equipment.armours;

import android.graphics.Bitmap;

import com.walfen.antiland.items.equipment.Equipment;

public abstract class Armour extends Equipment {

    public Armour(Bitmap texture, String name, int id, int value) {
        super(texture, name, id, value);
    }

    @Override
    public void onActive() {
        if(!equippedOn){
            onEquip();
            handler.getPlayer().equip(id, Equipment.ARMOUR);
        } else {
            onRemove();
            handler.getPlayer().removeEquipment(Equipment.ARMOUR);
        }
    }
}
