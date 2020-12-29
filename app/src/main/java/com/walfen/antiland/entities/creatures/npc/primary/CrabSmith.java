package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.Entity;
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
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 502, new int[]{3, 4, 5});
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
    public void receiveDamage(int num, int type) { }

    @Override
    protected void assignConversationProcedure(UIManager manager) {
        ArrayList<Conversation> c = new ArrayList<>();
        ArrayList<Conversation> c2 = new ArrayList<>();
        ArrayList<Conversation> c3 = new ArrayList<>();
        switch (status){
            case 0:
                c.add(new Conversation("Hello", Assets.player_icon, false));
                c.add(new Conversation("Oh, hello. Uhh, do you mind helping us?", Assets.crabSmith[0], true));
                c.add(new Conversation("?", Assets.player_icon, false));
                c.add(new Conversation("This dingus really outdid himself this time.", Assets.crabSmith[0], true));
                c.add(new Conversation("It’s not my fault I dropped the keys!", Assets.foxKeeperIcon, true));
                c.add(new Conversation("Would you rather I get attacked by slimes instead?", Assets.foxKeeperIcon, true));
                c.add(new Conversation("You wouldn’t have been attacked if you hadn’t gone to the beach.", Assets.crabSmith[0], true));
                c.add(new Conversation("Well, where else am I going to enjoy such a refreshing breeze?", Assets.foxKeeperIcon, true));
                c.add(new Conversation("You’ve sure got your priorities straight.", Assets.crabSmith[0], true));
                c.add(new Conversation("...", Assets.player_icon, false));
                c.add(new Conversation("If you’re here to shop, then I’m afraid we can’t serve you right now.", Assets.crabSmith[0], true));
                c.add(new Conversation("Unless you can get my keys back, otherwise, this shop is closed.", Assets.foxKeeperIcon, true));


                c2.add(new Conversation("Really?!?", Assets.foxKeeperIcon, true));
                c2.add(new Conversation("Wow, that was easy.", Assets.foxKeeperIcon, true));
                c2.add(new Conversation("I hope you didn’t just agree to something you haven’t thought through.", Assets.crabSmith[0], false));
                c2.add(new Conversation("Nevertheless, I appreciate your kindness.", Assets.crabSmith[0], false));
                c2.add(new Conversation("The key should be around Sunshine Beach. About south-west of our town.", Assets.foxKeeperIcon, true));
                c2.add(new Conversation("Be careful, those slimes may look cute, but they pack a real punch.", Assets.foxKeeperIcon, true));

                c3.add(new Conversation("Thought so.", Assets.crabSmith[0], false));
                c3.add(new Conversation("Hey, I wouldn’t help myself either. Those slimes are aggressive.", Assets.foxKeeperIcon, true));

                manager.hideUI();
                manager.getConvBox().setConversationList(c, () -> {
                    convBoxOn = false;
                    manager.popUpOptions("What would you do?", new String[]{"Leave", "Help"}, new ClickListener[]{() -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c3, () -> {
                        });
                        manager.getConvBox().setActive();
                    }, () -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c2, () -> {
                            assignMission();
                            convBoxOn = false;
                            Mission.missions[missionSet[status]].setCompleteMessage("Report back to Mr. Krab.");
                            handler.getPlayer().getMissionManager().setSelectedMission();
                            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                            Entity e = Entity.entityList[1201].clone();
                            e.initialize(handler, 1408, 4736, 1408, 4736, 0);
                            handler.getWorld().getEntityManager().addEntityHot(e);
                        });
                        manager.getConvBox().setActive();
                    }}, false);
                });
                manager.getConvBox().setActive();
                break;
            case 1:
                c.clear();
                c2.clear();
                c3.clear();
                c.add(new Conversation("Hello", Assets.player_icon, false));
                c.add(new Conversation("Greetings. Nice to see you again.", Assets.crabSmith[0], true));
                c.add(new Conversation("Please excuse me, but, could you help me with a favour?", Assets.crabSmith[0], true));
                c.add(new Conversation("?", Assets.player_icon, false));
                c.add(new Conversation("I’m sure you’ve noticed, but, I don’t quite fit into this town.", Assets.crabSmith[0], true));
                c.add(new Conversation("My home town used to be that Abandoned Port south of here.", Assets.crabSmith[0], true));
                c.add(new Conversation("In fact, most of my things are still there.", Assets.crabSmith[0], true));
                c.add(new Conversation("Listen, I left behind my trusty hammer and I want you to get it back for me. " +
                        "I’d go there myself, but, you see..", Assets.crabSmith[0], true));
                c.add(new Conversation("I have to take care of this bozo.", Assets.crabSmith[0], true));
                c.add(new Conversation("You called?", Assets.foxKeeperIcon, true));
                c.add(new Conversation("Would you please bring back my hammer from the Abandoned Port?", Assets.crabSmith[0], true));

                c2.add(new Conversation("Thank you.", Assets.crabSmith[0], true));
                c2.add(new Conversation("(The Blacksmith cracks a small smile)", Assets.NULL, false));
                c2.add(new Conversation("Are you smiling?", Assets.foxKeeperIcon, false));
                c2.add(new Conversation("*cough* No.", Assets.crabSmith[0], true));
                c2.add(new Conversation("*chuckles*", Assets.foxKeeperIcon, false));
                c2.add(new Conversation("What are you laughing about!", Assets.crabSmith[0], true));

                c3.add(new Conversation("*sigh* That’s alright, I won’t hold you against it.", Assets.crabSmith[0], true));
                c3.add(new Conversation("I mean...", Assets.crabSmith[0], true));
                c3.add(new Conversation("We left that port for a reason.", Assets.crabSmith[0], true));
                c3.add(new Conversation("...", Assets.foxKeeperIcon, true));

                manager.hideUI();
                manager.getConvBox().setConversationList(c, () -> {
                    convBoxOn = false;
                    manager.popUpOptions("What would you do?", new String[]{"Leave", "Help"}, new ClickListener[]{() -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c3, () -> {
                        });
                        manager.getConvBox().setActive();
                    }, () -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c2, () -> {
                            assignMission();
                            convBoxOn = false;
                            Mission.missions[missionSet[status]].setCompleteMessage("Report back to Mr. Krab.");
                            handler.getPlayer().getMissionManager().setSelectedMission();
                            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                            Entity e = Entity.entityList[740].clone();
                            e.initialize(handler, 5120, 7552, 5120, 7552, 0);
                            handler.getWorld().getEntityManager().addEntityHot(e);
                            e = Entity.entityList[1016].clone();
                            e.initialize(handler, 4992, 7552, 4992, 7552, 0);
                            handler.getWorld().getEntityManager().addEntityHot(e);
                        });
                        manager.getConvBox().setActive();
                    }}, false);
                });
                manager.getConvBox().setActive();
                break;
            case 2:
                c.clear();
                c2.clear();
                c3.clear();
                c.add(new Conversation("Are you ok?", Assets.player_icon, false));
                c.add(new Conversation("I’m fine.", Assets.crabSmith[0], true));
                c.add(new Conversation("...", Assets.crabSmith[0], true));
                c.add(new Conversation("I need you to check up on the barricade.", Assets.crabSmith[0], true));
                c.add(new Conversation("Before we left the port, we tried to set up a barricade to prevent monsters from attacking us.", Assets.crabSmith[0], true));
                c.add(new Conversation("If there are monsters in the port, then...", Assets.crabSmith[0], true));
                c.add(new Conversation("...", Assets.crabSmith[0], true));
                c.add(new Conversation("I know you’re capable so can you help?", Assets.crabSmith[0], true));

                c2.add(new Conversation("Thank goodness.", Assets.crabSmith[0], true));
                c2.add(new Conversation("The barrier is near the Far Harbour.", Assets.crabSmith[0], true));
                c2.add(new Conversation("You’ll find it further east from the Port.", Assets.crabSmith[0], true));
                c2.add(new Conversation("Just... Please be careful.", Assets.crabSmith[0], true));;

                c3.add(new Conversation("I can’t force you.", Assets.crabSmith[0], true));
                c3.add(new Conversation("But, please think about it.", Assets.crabSmith[0], true));

                manager.hideUI();
                manager.getConvBox().setConversationList(c, () -> {
                    convBoxOn = false;
                    manager.popUpOptions("What would you do?", new String[]{"Leave", "Help"}, new ClickListener[]{() -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c3, () -> {
                        });
                        manager.getConvBox().setActive();
                    }, () -> {
                        manager.hideUI();
                        manager.getConvBox().setConversationList(c2, () -> {
                            assignMission();
                            convBoxOn = false;
                            handler.getPlayer().getMissionManager().setSelectedMission();
                            handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                            Entity e = Entity.entityList[1017].clone();
                            e.initialize(handler, 6912, 8576, 6912, 8576, 0);
                            handler.getWorld().getEntityManager().addEntityHot(e);
                            status ++;
                        });
                        manager.getConvBox().setActive();
                    }}, false);
                });
                manager.getConvBox().setActive();
                break;
            default:
                c.clear();
                c.add(new Conversation("I need to get a new hammer...", Assets.crabSmith[0], false));
                manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                manager.hideUI();
                manager.getConvBox().setActive();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ArrayList<Conversation>[] getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcCrab[0], false));
        return new ArrayList[]{(ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone()};
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ArrayList<Conversation>[] getCompleteConversation() {
        ArrayList<ArrayList<Conversation>> conversations = new ArrayList<>();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("Here you go.", Assets.player_icon, false));
        c.add(new Conversation("(The shop keeper swiftly grabs the key and unlocked the door to the shop)", Assets.NULL, false));
        c.add(new Conversation("We’re back in business!", Assets.foxKeeperIcon, true));
        c.add(new Conversation("Thank you for helping us.", Assets.crabSmith[0], true));
        c.add(new Conversation("(The Blacksmith rummages through their pockets and pulls out some gold pieces)", Assets.NULL, false));
        c.add(new Conversation("I’d feel bad if I didn’t give you something in return, take this for your help.", Assets.crabSmith[0], true));
        c.add(new Conversation("(You have received 50 gold)", Assets.NULL, false));
        conversations.add((ArrayList<Conversation>) c.clone());
        c.clear();
        c.add(new Conversation("Uhh…", Assets.player_icon, false));
        c.add(new Conversation("Hey there! Do you have my hammer?", Assets.crabSmith[0], true));
        c.add(new Conversation("(You explain what happened)", Assets.NULL, false));
        c.add(new Conversation("A monster!? You saw a monster?!", Assets.crabSmith[0], true));
        c.add(new Conversation("The Blacksmith seems visibly distressed", Assets.NULL, false));
        conversations.add((ArrayList<Conversation>) c.clone());
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
