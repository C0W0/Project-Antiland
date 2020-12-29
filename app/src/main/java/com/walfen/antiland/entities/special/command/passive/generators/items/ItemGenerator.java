package com.walfen.antiland.entities.special.command.passive.generators.items;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.special.command.passive.PassiveCommandEntity;
import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.untils.Utils;

public class ItemGenerator extends PassiveCommandEntity {

    protected long lastGeneration, generationCooldown;
    protected int generatedItem, spawnRange, minCount, maxCount;
    protected float spawnChance;

    public ItemGenerator(int generatedItem, int spawnRange, int minCount, int maxCount, int generationSpeed, int id) {
        super(id);
        spawnChance = 0.5f;
        switch (generationSpeed){
            case GenerationConstant.NO_PERIODIC_GENERATION:
                generationCooldown = -1;
                break;
            case GenerationConstant.SLOW_GENERATION:
                generationCooldown = 10*1000;
                break;
            case GenerationConstant.FAST_GENERATION:
                generationCooldown = 3*1000;
                break;
            default:
                generationCooldown = 5*1000;
        }
        this.generatedItem = generatedItem;
        this.spawnRange = spawnRange;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    protected void spawnItem(){
        int locationX = Utils.pickNumberBetween((int)(x-spawnRange), (int)(x+spawnRange));
        int locationY = Utils.pickNumberBetween((int)(y-spawnRange), (int)(y+spawnRange));
        handler.getWorld().getItemManager().addItem(Item.items[generatedItem].createNew(locationX, locationY, Utils.pickNumberBetween(minCount, maxCount)));
    }

    @Override
    protected void commandAction() {
        lastGeneration = System.currentTimeMillis();

        //out of sight check
        Point p = new Point((int)(x-handler.getGameCamera().getxOffset()), (int)(y-handler.getGameCamera().getyOffset()));
        if(p.x < -128 || p.x > Constants.SCREEN_WIDTH+128 ||
                p.y < -128 || p.y > Constants.SCREEN_HEIGHT+128)
            return;

        //chance to spawn in check
        if(Math.random() >= spawnChance)
            spawnItem();

    }

    @Override
    protected boolean isActionAllowed() {
        return System.currentTimeMillis()-lastGeneration > generationCooldown;
    }

    @Override
    public void draw(Canvas canvas) { }

}
