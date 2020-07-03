package com.walfen.antiland.items.equipment;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public abstract class Equipments extends Item {
    public Equipments(Bitmap texture, String name, int id) {
        super(texture, name, id);
    }

    @Override
    public void onActive() {
        onEquip();
//        for(Item i: handler.getWorld().getPlayer().getInventory().getInventoryItems())
//            if(i.getId() == id)
//                i.setCount(i.getCount()-1);
    }

    public abstract void onEquip();

    public abstract void onRemove();
}
