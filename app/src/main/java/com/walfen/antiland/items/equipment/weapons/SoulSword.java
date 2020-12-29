package com.walfen.antiland.items.equipment.weapons;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.Equipment;

public class SoulSword extends Weapon {


    public SoulSword() {
        super(Assets.swords[2], "Soul Sword", 209, 500);

    }

    @Override
    public void onEquip(Player player) {
        RangedAttack attack = new PlayerAbilityAttack(player.getHandler(), Attack.Type.MAGICAL_LIGHT, 256, 10, player::getMagicalDamage,
                () -> new Animation(10, new Bitmap[]{Assets.player_Attack}));
        player.setAttack(attack);
    }

    @Override
    protected void onRemove() {
        handler.getPlayer().resetAttack();
    }

    @Override
    public Equipment addToInv(int count) {
        SoulSword i = new SoulSword();
        i.setPickedUP(true);
        i.count = count;
        return i;
    }

    @Override
    public Item createNew(int x, int y, int count) {
        SoulSword i = new SoulSword();
        i.count = count;
        i.setPosition(x, y);
        return i;
    }

    @Override
    public String getDesc() {
        return "Hermit's old Sword";
    }

    @Override
    public String[] getEffect() {
        return new String[]{"Regular attack deals", "LIGHT type damage"};
    }

}
