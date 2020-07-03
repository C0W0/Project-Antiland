package com.walfen.antiland.items.usable;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class UsableItems extends Item {

    private Usable onUseEvent;

    public UsableItems(Bitmap texture, String name, int id, Usable onUseEvent) {
        super(texture, name, id);
        this.onUseEvent = onUseEvent;
    }
    
    @Override
    public void onActive(){
        onUseEvent.onUse();
        for(Item i: handler.getWorld().getPlayer().getInventory().getInventoryItems())
            if(i.getId() == id)
                i.setCount(i.getCount()-1);
    }

    @Override
    public UsableItems createNew(int x, int y, int count){
        UsableItems i = new UsableItems(texture, name, id, onUseEvent);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public Item addToInv(int count) {
        UsableItems i = new UsableItems(texture, name, id, onUseEvent);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }


}
