package com.walfen.antiland.mission.killing;

public class TestOfPower extends DestroyEntity {

    public TestOfPower() {
        super("Test of Power", "Destroy the magical tree in one hit", new int[]{706}, 13, new int[]{1}, 15, 1, 0);
    }

    @Override
    public void receiveReward() {
        handler.getWorld().getEntityManager().modifyEntityHot("Captain Cactus", 2);
        handler.getPlayer().increaseXp(xpGain);
    }
}
