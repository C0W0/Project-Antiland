package com.walfen.antiland.entities.creatures.active;

public class EvilSpirit extends TrappedSpirit {

    public EvilSpirit(){
        super(205);
        setEntityHealth(10);
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public String getName() {
        return "Evil Spirit";
    }
}
