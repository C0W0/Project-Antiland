package com.walfen.antiland.items.usable;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class UsableItem extends Item {

    private Usable onUseEvent;

    public UsableItem(Bitmap texture, String name, int id, Usable onUseEvent) {
        super(texture, name, id);
        this.onUseEvent = onUseEvent;
    }
    
    @Override
    public void onActive(){
        onUseEvent.onUse();
        for(Item i: handler.getPlayer().getInventory().getInventoryItems())
            if(i.getId() == id)
                i.setCount(i.getCount()-1);
    }

    @Override
    public UsableItem createNew(int x, int y, int count){
        UsableItem i = new UsableItem(texture, name, id, onUseEvent);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public Item addToInv(int count) {
        UsableItem i = new UsableItem(texture, name, id, onUseEvent);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }


}
