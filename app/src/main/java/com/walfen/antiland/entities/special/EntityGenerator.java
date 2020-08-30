package com.walfen.antiland.entities.special;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.untils.Utils;

public class EntityGenerator extends CommandEntity {

    protected long lastGeneration, generationCooldown;
    protected int generatedEntity, spawnRange, criticalDensity;

    public EntityGenerator(Handler handler, int generatedEntity, int spawnRange, int criticalDensity, int generationSpeed, int id) {
        super(handler, id);
        switch (generationSpeed){
            case GenerationSpeed.NO_PERIODIC_GENERATION:
                generationCooldown = -1;
                break;
            case GenerationSpeed.SLOW_GENERATION:
                generationCooldown = 10*1000;
                break;
            case GenerationSpeed.FAST_GENERATION:
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

        while (true){
            locationX = Utils.pickNumberBetween((int)(x-spawnRange), (int)(x+spawnRange));
            locationY = Utils.pickNumberBetween((int)(y-spawnRange), (int)(y+spawnRange));
            if(!handler.getWorld().getTile(locationX/128, locationX/128).isBarrier())
                break;
        }
        Entity e = Entity.entityList[generatedEntity].clone();
        e.initialize(handler, locationX, locationY, locationX, locationY);
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
        float spawnChance = 0.7f;
        if(density >= criticalDensity/2)
            spawnChance = 0.5f;
        if(Math.random() >= spawnChance)
            spawnEntity();

    }

    @Override
    protected boolean isActionAllowed() {
        return System.currentTimeMillis()-lastGeneration > generationCooldown;
    }

    @Override
    protected void onDeath() { }

    @Override
    public void draw(Canvas canvas) { }

    @Override
    public void receiveDamage(int num, int type) { }

    public static class GenerationSpeed{

        public static final int NO_PERIODIC_GENERATION = 0;
        public static final int SLOW_GENERATION = 1;
        public static final int NORMAL_GENERATION = 2;
        public static final int FAST_GENERATION = 3;

    }

}
