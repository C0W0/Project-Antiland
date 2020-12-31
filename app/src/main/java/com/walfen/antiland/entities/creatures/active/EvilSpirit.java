package com.walfen.antiland.entities.creatures.active;

import com.walfen.antiland.items.Item;

public class EvilSpirit extends TrappedSpirit {

    public EvilSpirit(){
        super(205);
        setEntityHealth(10);
    }

    @Override
    protected void onDeath() {
        handler.getPlayer().changeWealth((int)(Math.random()*10+2));
    }

    @Override
    public String getName() {
        return "Evil Spirit";
    }
}
