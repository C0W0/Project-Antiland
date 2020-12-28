package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.creatures.npc.secondary.RepeatedMissionNPC;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class CrabSmith extends MajorMissionNPC {

    private Animation dynamicTexture;

    public CrabSmith() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 502, new int[]{3, 4});
        interRange = 2;
        dynamicTexture = new Animation(1, Assets.crabSmith);
        dynamicTexture.scale(width, height);
    }

    @Override
    public void update() {
        super.update();
        dynamicTexture.update();
    }

    @Override
    protected void assignConversationProcedure(UIManager manager) {
        switch (status){
            case 0:
                ArrayList<Conversation> c = new ArrayList<>();
                ArrayList<Conversation> c2 = new ArrayList<>();
                ArrayList<Conversation> c3 = new ArrayList<>();
                c.add(new Conversation("Hello", Assets.player_icon, false));
                c.add(new Conversation("Oh, hello. Uhh, do you mind helping us?", Assets.crabSmith[0], true));
                c.add(new Conversation("?", Assets.player_icon, false));
                c.add(new Conversation("This dingus really outdid himself this time.", Assets.crabSmith[0], true));
                c.add(new Conversation("It’s not my fault I dropped the keys!", Assets.foxKeeper[0], true));
                c.add(new Conversation("Would you rather I get attacked by slimes instead?", Assets.foxKeeper[0], true));
                c.add(new Conversation("You wouldn’t have been attacked if you hadn’t gone to the beach.", Assets.crabSmith[0], true));
                c.add(new Conversation("Well, where else am I going to enjoy such a refreshing breeze?", Assets.foxKeeper[0], true));
                c.add(new Conversation("You’ve sure got your priorities straight.", Assets.crabSmith[0], true));
                c.add(new Conversation("...", Assets.player_icon, false));
                c.add(new Conversation("If you’re here to shop, then I’m afraid we can’t serve you right now.", Assets.crabSmith[0], true));
                c.add(new Conversation("Unless you can get my keys back, otherwise, this shop is closed.", Assets.foxKeeper[0], true));


                c2.add(new Conversation("Really?!?", Assets.foxKeeper[0], true));
                c2.add(new Conversation("Wow, that was easy.", Assets.foxKeeper[0], true));
                c2.add(new Conversation("I hope you didn’t just agree to something you haven’t thought through.", Assets.crabSmith[0], false));
                c2.add(new Conversation("evertheless, I appreciate your kindness.", Assets.crabSmith[0], false));
                c2.add(new Conversation("The key should be around Sunshine Beach. About south-west of our town.", Assets.foxKeeper[0], true));
                c2.add(new Conversation("Be careful, those slimes may look cute, but they pack a real punch.", Assets.foxKeeper[0], true));

                c3.add(new Conversation("Thought so.", Assets.crabSmith[0], false));
                c3.add(new Conversation("Hey, I wouldn’t help myself either. Those slimes are aggressive.", Assets.foxKeeper[0], true));

                manager.hideUI();
                manager.getConvBox().setConversationList(c, () -> {
                    convBoxOn = false;
                    manager.popUpOptions("What would you do?", new String[]{"Help", "Leave"}, new ClickListener[]{() -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c2, () -> {
                            assignMission();
                            convBoxOn = false;
                            Mission.missions[missionSet[status]].setCompleteMessage("Report back to Mr. Krab.");
                            handler.getPlayer().getMissionManager().setSelectedMission();
                            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                        });
                        manager.getConvBox().setActive();
                    }, () -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c3, () -> {
                        });
                        manager.getConvBox().setActive();
                    }}, false);
                });
                manager.getConvBox().setActive();
                break;
        }
    }

    @Override
    protected ArrayList<Conversation>[] getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcCrab[0], false));
        return new ArrayList[]{c};
    }

    @Override
    protected ArrayList<Conversation>[] getCompleteConversation() {
        ArrayList<ArrayList<Conversation>> conversations = new ArrayList<>();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("Here you go.", Assets.player_icon, false));
        c.add(new Conversation("(The shop keeper swiftly grabs the key and unlocked the door to the shop)", Assets.NULL, false));
        c.add(new Conversation("We’re back in business!", Assets.foxKeeper[0], true));
        c.add(new Conversation("Thank you for helping us.", Assets.crabSmith[0], true));
        c.add(new Conversation("(The Blacksmith rummages through their pockets and pulls out some gold pieces)", Assets.NULL, false));
        c.add(new Conversation("I’d feel bad if I didn’t give you something in return, take this for your help.", Assets.crabSmith[0], true));
        c.add(new Conversation("(You have received 50 gold)", Assets.NULL, false));
        conversations.add(c);
        return conversations.toArray(new ArrayList[0]);
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        dynamicTexture.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.crabSmith[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Mr. Krab Smith";
    }
}
