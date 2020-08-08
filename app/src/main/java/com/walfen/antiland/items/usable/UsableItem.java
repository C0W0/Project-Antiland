package com.walfen.antiland.items.usable;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class UsableItem extends Item {

    private Usable onUseEvent;
    private String desc, effect;

    public UsableItem(Bitmap texture, String name, int id, Usable onUseEvent,
                      String desc, String effect) {
        super(texture, name, id);
        this.onUseEvent = onUseEvent;
        this.desc = desc;
        this.effect = effect;
    }
    
    @Override
    public void onActive(){
        onUseEvent.onUse();
        handler.getPlayer().getInventory().deductItem(id, 1);
    }

    @Override
    public UsableItem createNew(int x, int y, int count){
        UsableItem i = new UsableItem(texture, name, id, onUseEvent, desc, effect);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public Item addToInv(int count) {
        UsableItem i = new UsableItem(texture, name, id, onUseEvent, desc, effect);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }


}
