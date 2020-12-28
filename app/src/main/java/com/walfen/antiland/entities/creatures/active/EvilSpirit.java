package com.walfen.antiland.entities.creatures.active;

import com.walfen.antiland.items.Item;

public class EvilSpirit extends TrappedSpirit {

    public EvilSpirit(){
        super(205);
        setEntityHealth(10);
    }

    @Override
    protected void onDeath() {
        handler.getWorld().getItemManager().addItem(Item.hammer.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
    }

    @Override
    public String getName() {
        return "Evil Spirit";
    }
}
