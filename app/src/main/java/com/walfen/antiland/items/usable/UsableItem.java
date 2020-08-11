package com.walfen.antiland.items.usable;


import android.graphics.Bitmap;

import com.walfen.antiland.items.Item;

public class UsableItem extends Item {

    private Usable onUseEvent;
    private String desc;
    private String[] effect;

    public UsableItem(Bitmap texture, String name, int id, int value, String desc, String[] effect, Usable onUseEvent) {
        super(texture, name, id, value);
        this.onUseEvent = onUseEvent;
        this.desc = desc;
        this.value = value;
        this.effect = effect;
    }
    
    @Override
    public void onActive(){
        onUseEvent.onUse();
        handler.getPlayer().getInventory().deductItem(id, 1);
    }

    @Override
    public UsableItem createNew(int x, int y, int count){
        UsableItem i = new UsableItem(texture, name, id, value, desc, effect, onUseEvent);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String[] getEffect() {
        return effect;
    }

    @Override
    public Item addToInv(int count) {
        UsableItem i = new UsableItem(texture, name, id, value, desc, effect, onUseEvent);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }


}
