package com.walfen.antiland.entities.special.command.passive.generators.entities;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.special.command.passive.PassiveCommandEntity;
import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;
import com.walfen.antiland.untils.Utils;

public class EntityGenerator extends PassiveCommandEntity {

    protected long lastGeneration, generationCooldown;
    protected int generatedEntity, spawnRange, criticalDensity;

    public EntityGenerator(int generatedEntity, int spawnRange, int criticalDensity, int generationSpeed, int id) {
        super(id);
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
        this.generatedEntity = generatedEntity;
        this.spawnRange = spawnRange;
        this.criticalDensity = criticalDensity;
    }

    protected void spawnEntity(){
        int locationX, locationY;

        locationX = Utils.pickNumberBetween((int)(x-spawnRange), (int)(x+spawnRange));
        locationY = Utils.pickNumberBetween((int)(y-spawnRange), (int)(y+spawnRange));
        Entity e = Entity.entityList[generatedEntity].clone();
        e.initialize(handler, locationX, locationY, locationX, locationY, 0);
        handler.getWorld().getEntityManager().addEntityHot(e);
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
        Rect r = new Rect((int)x-spawnRange, (int)y-spawnRange, (int)x+spawnRange, (int)y+spawnRange);
        int density = 0;
        for (Entity e: handler.getWorld().getEntityManager().getEntities()){
            if(e.getId() == generatedEntity && r.contains((int)e.getX(), (int)e.getY())) {
                density++;
                if(density >= criticalDensity)
                    return;
            }
        }

        //chance to spawn in check
        float spawnChance = density >= criticalDensity/2?0.7f:0.5f;
        if(Math.random() >= spawnChance)
            spawnEntity();

    }

    @Override
    protected boolean isActionAllowed() {
        return System.currentTimeMillis()-lastGeneration > generationCooldown;
    }

    @Override
    public void draw(Canvas canvas) { }

}
