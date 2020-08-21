package com.walfen.antiland.entities.properties.effect;

import java.util.Arrays;

public class Shield {

    protected int[] dmgPercentMod;
    protected int maxDurability;
    protected int durability;
    protected long shieldActiveTime, shieldActiveDuration;

    public Shield(int durability, long shieldActiveDuration, int... dmgPercentMod){
        this.durability = maxDurability = durability;
        this.dmgPercentMod = new int[10];
        for(int i = 0; i < 10; i++){
            if(dmgPercentMod.length > i)
                this.dmgPercentMod[i] = dmgPercentMod[i];
            else
                this.dmgPercentMod[i] = 100;
        }
        this.shieldActiveDuration = shieldActiveDuration;
        shieldActiveTime = System.currentTimeMillis();
    }

    public int receiveDamage(int dmg, int type){
        dmg *= dmgPercentMod[type]/100.f;
        int remainingDmg = Math.max(dmg-durability, 0);
        durability = Math.max(durability-dmg, 0);
        return remainingDmg;
    }

    public boolean isValid(){
        return System.currentTimeMillis() - shieldActiveTime < shieldActiveDuration;
    }

    public boolean isShieldStillUp(){
        return durability > 0;
    }

    public void restoreShield(){
        durability = maxDurability;
    }

    public void setDurability(int durability) {
        durability = Math.max(Math.min(durability, maxDurability), 0);
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }
}
