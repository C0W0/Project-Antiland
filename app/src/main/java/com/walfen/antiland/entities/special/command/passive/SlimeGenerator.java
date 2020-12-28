package com.walfen.antiland.entities.special.command.passive;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.untils.Utils;

public class SlimeGenerator extends EntityGenerator {

    public SlimeGenerator() {
        super(slime.getId(), 256, 5, EntityGenerator.GenerationSpeed.FAST_GENERATION, 1101);
    }

    @Override
    protected void spawnEntity() {
        int locationX, locationY;

        locationX = Utils.pickNumberBetween((int) (x - spawnRange), (int) (x + spawnRange));
        locationY = Utils.pickNumberBetween((int) (y - spawnRange), (int) (y + spawnRange));
        int ge = Math.random()>0.1?generatedEntity:iceSlime.getId();
        Entity e = Entity.entityList[ge].clone();
        e.initialize(handler, locationX, locationY, locationX, locationY, 0);
        handler.getWorld().getEntityManager().addEntityHot(e);
    }
}
