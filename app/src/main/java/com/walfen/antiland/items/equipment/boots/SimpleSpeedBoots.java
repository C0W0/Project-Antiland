package com.walfen.antiland.items.equipment.boots;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.items.equipment.Equipment;

public class SimpleSpeedBoots extends Boots {

    private final int defence, speed;

    public SimpleSpeedBoots(Bitmap texture, String name, int speed, int id) {
        super(texture, name, id, 100*speed*speed);
        this.speed = speed;
        this.defence = 1;
    }

    @Override
    public void onEquip(Player player) {
        player.changeDefence(defence);
        player.changeSpeed(speed);
    }

    @Override
    protected void onRemove() {
        handler.getPlayer().changeDefence(-defence);
        handler.getPlayer().changeSpeed(speed);
    }

    @Override
    public Equipment addToInv(int count) {
        SimpleSpeedBoots i = new SimpleSpeedBoots(texture, name, defence, id);
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Equipment createNew(int x, int y, int count) {
        SimpleSpeedBoots i = new SimpleSpeedBoots(texture, name, defence, id);
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "Leather boots";
    }

    @Override
    public String[] getEffect() {
        return new String[]{"defence +"+defence, "speed +"+speed};
    }

}
