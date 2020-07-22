package com.walfen.antiland.items.equipment.auxiliaries;

import android.graphics.Bitmap;

import com.walfen.antiland.items.equipment.Equipment;

public abstract class Auxiliary extends Equipment {
    public Auxiliary(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }

    @Override
    public void onActive() {
        if(!equippedOn){
            onEquip();
            handler.getPlayer().equip(id, Equipment.AUXILIARY);
        } else {
            onRemove();
            handler.getPlayer().removeEquipment(Equipment.AUXILIARY);
        }
    }
}