package com.walfen.antiland.entities.special.command.passive.generators.entities;

import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;

public class GiantSlimeSpawner extends EntityGenerator {

    public GiantSlimeSpawner() {
        super(209, 256, 1, GenerationConstant.SLOW_GENERATION, 1104);
    }

    @Override
    protected void commandAction() {
        lastGeneration = System.currentTimeMillis();

        //out of sight check
        Point p = new Point((int)(x-handler.getGameCamera().getxOffset()), (int)(y-handler.getGameCamera().getyOffset()));
        if(p.x < -128 || p.x > Constants.SCREEN_WIDTH+128 ||
                p.y < -128 || p.y > Constants.SCREEN_HEIGHT+128)
            return;

        //critical density check
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            if(e.getId() == generatedEntity) {
                return;
            }
        }

        //chance to spawn in check
        float spawnChance = 0.99f;
        if(Math.random() >= spawnChance)
            spawnEntity();

    }

}
