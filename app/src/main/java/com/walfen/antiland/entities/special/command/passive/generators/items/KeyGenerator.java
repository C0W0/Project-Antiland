package com.walfen.antiland.entities.special.command.passive.generators.items;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;
import com.walfen.antiland.items.Item;

public class KeyGenerator extends ItemGenerator {

    public KeyGenerator() {
        super(Item.key.getId(), 256, 1, 1, GenerationConstant.FAST_GENERATION, 1201);
        spawnChance = 0.2f;
    }

    @Override
    public void update() {
        super.update();
        for(Item i: handler.getPlayer().getInventory().getInventoryItems()){
            if(i.getId() == generatedItem){
                health = 0;
                active = false;
                die();
            }
        }
    }

    @Override
    protected boolean isActionAllowed() {
        for(Item i: handler.getWorld().getItemManager().getItems())
            if(i.getId() == generatedItem)
                return false;
        return super.isActionAllowed();
    }

}
