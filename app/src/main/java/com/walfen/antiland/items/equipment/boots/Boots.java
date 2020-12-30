package com.walfen.antiland.items.equipment.boots;

import android.graphics.Bitmap;

import com.walfen.antiland.items.equipment.Equipment;

public abstract class Boots extends Equipment {

    public Boots(Bitmap texture, String name, int id, int value) {
        super(texture, name, id, value);
    }

    @Override
    public void onActive() {
        if(!equippedOn){
            onEquip();
            handler.getPlayer().equip(id, Equipment.BOOTS);
        } else {
            onRemove();
            handler.getPlayer().removeEquipment(Equipment.BOOTS);
        }
    }

}
