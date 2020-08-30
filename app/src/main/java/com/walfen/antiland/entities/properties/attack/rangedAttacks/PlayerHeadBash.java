package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.special.Stung;

import java.util.function.IntSupplier;

public class PlayerHeadBash extends PlayerMeleeAttack {

    private IntSupplier stungChance;

    public PlayerHeadBash(Handler handler, IntSupplier damageSupplier, IntSupplier stungChance) {
        super(handler, Type.PHYSICAL, damageSupplier);
        this.stungChance = stungChance;
    }

    @Override
    public void checkAttackCollision() {
        for(Entity e: handler.getWorld().getEntityManager().getEntities()){
            if (e.equals(handler.getPlayer()))
                continue;
            for(int i = 0; i < collisionQueue.size(); i++){
                RangedAttackCollision r = collisionQueue.get(i);
                if (r.intersect(e.getCollisionBounds(0, 0))) {
                    e.receiveDamage(baseDamage, type);
                    if(e.getHealth() > 0) {
                        handler.getPlayer().getTracker().addTracking(e);
                        try {
                            Creature c = (Creature)e;
                            if(Math.random() < (float)stungChance.getAsInt()/100)
                                c.addEffect(new Stung(c, 2000));
                        } catch (ClassCastException ignored){}
                    }
                    collisionQueue.remove(i);
                    i --;
                }
            }
        }
    }
}
