package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class CactusNPC extends MajorMissionNPC {

    public CactusNPC() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 504, new int[]{-1, 13, 14, 16});
        interRange = 2;
    }

    @Override
    public void receiveDamage(int num, int type) { }

    @Override
    protected void interact() {
        if(status >= 1 && status <= 4)
            super.interact();
        else {
            if(Mission.missions[20].isCompleted()){
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getCompleteConversation()[2];
                uiManager.getConvBox().setConversationList(c, () -> {Mission.missions[20].complete(); convBoxOn = false;});
                uiManager.getConvBox().setActive();
            }else {
                convBoxOn = true;
                assignConversationProcedure(handler.getUIManager());
            }
        }
    }

    @Override
    protected void assignConversationProcedure(UIManager manager) {
        ArrayList<Conversation> c = new ArrayList<>();
        boolean control = false;
        switch (status){
            case 1:
                c.clear();
                c.add(new Conversation("Hello.", Assets.player_icon, false));
                c.add(new Conversation("Hey there.", Assets.npcCactus, true));
                c.add(new Conversation("Wait, you’re the one who killed a port monster.", Assets.npcCactus, true));
                c.add(new Conversation("?", Assets.player_icon, false));
                c.add(new Conversation("It wasn't difficult hearing that Blacksmith scream.", Assets.npcCactus, true));
                c.add(new Conversation("Anyways, I want to see if you’re actually strong.", Assets.npcCactus, true));
                c.add(new Conversation("There’s a magical tree to the north east of town that nobody can break.", Assets.npcCactus, true));
                c.add(new Conversation("It’s really tough so don’t bother trying to chip it away, we’ve tried.", Assets.npcCactus, true));
                c.add(new Conversation("If you’re really strong enough to defeat a monster, break that tree in one hit.", Assets.npcCactus, true));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    Mission.missions[missionSet[status]].setCompleteMessage("Report back to Captain Cactus");
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission();
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    status ++;
                });
                break;
            case 2:
                c.clear();
                c.add(new Conversation("Hel-", Assets.player_icon, false));
                c.add(new Conversation("You are so stubborn!", Assets.npcChicken, false));
                c.add(new Conversation("I’m not stubborn!", Assets.npcCactus, true));
                c.add(new Conversation("Ack, you're so annoying!", Assets.npcChicken, false));
                c.add(new Conversation("What!?!", Assets.npcCactus, true));
                c.add(new Conversation("Can you please be quiet!?", Assets.crabSmith[0], false));
                c.add(new Conversation("Why can’t you like both?!", Assets.foxKeeperIcon, false));
                c.add(new Conversation("No one asked you!", Assets.npcChicken, true));
                c.add(new Conversation("No one asked you!", Assets.npcCactus, true));
                c.add(new Conversation("(A heated argument seems to have engulfed the town)", Assets.NULL, true));
                c.add(new Conversation("grr...", Assets.npcChicken, false));
                c.add(new Conversation("Hey you.", Assets.npcChicken, false));
                c.add(new Conversation("!", Assets.player_icon, true));
                c.add(new Conversation("I need to talk to you.", Assets.npcChicken, false));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    Mission.missions[missionSet[status]].setCompleteMessage("Go talk to Ms. Chi-Ken");
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission();
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    status ++;
                });
                break;
            case 3:
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if (m.getId() == 15) {
                        control = true;
                        break;
                    }
                }
                if(!control){
                    c.clear();
                    c.add(new Conversation("Good Day.", Assets.npcCactus, false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                    break;
                }
                c.clear();
                c.add(new Conversation("What do you want?", Assets.npcCactus, true));
                c.add(new Conversation("Did you just come here to preach pear prophets?", Assets.npcCactus, true));
                c.add(new Conversation("Or, are you a mind of reason?", Assets.npcCactus, true));
                c.add(new Conversation("Think about it! Oranges are far superior!", Assets.npcCactus, true));
                c.add(new Conversation("You’re WRONG!", Assets.npcChicken, false));
                c.add(new Conversation("grr...", Assets.npcCactus, true));
                c.add(new Conversation("I know you’re smart so surely you know that oranges are the best.", Assets.npcCactus, true));
                c.add(new Conversation("Oh, I have an idea! Take this orange and eat it in his face. She’ll absolutely flip.", Assets.npcCactus, true));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    Mission.missions[missionSet[status]-1].complete();
                    Mission.missions[missionSet[status]].setCompleteMessage("Go talk to Ms. Chi-Ken");
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission(handler.getPlayer().getMissionManager().getMissions().size()-2);
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    status ++;
                });
                break;
            case 4:
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if (m.getId() == 17) {
                        control = true;
                        break;
                    }
                }
                if(!control){
                    c.clear();
                    c.add(new Conversation("Good Day.", Assets.npcCactus, false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                    break;
                }
                c.clear();
                c.add(new Conversation("Heh, I heard him yell. She must be furious.", Assets.npcCactus, true));
                c.add(new Conversation("...", Assets.player_icon, true));
                c.add(new Conversation("Err take this...", Assets.npcCactus, true));
                c.add(new Conversation("Thanks for your help, I’ll take it from here.", Assets.npcCactus, true));
                manager.getConvBox().setConversationList(c, () -> {
                    Mission.missions[missionSet[status-1]+1].complete();
                    convBoxOn = false;
                    status ++;
                });
                break;
            default:
                c.clear();
                double r = Math.random();
                boolean killingSlimes = false;
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if(m.getId() == 20){
                        killingSlimes = true;
                        break;
                    }
                }
                if (r < 0.5 || killingSlimes) {
                    System.out.println(killingSlimes);
                    c.add(new Conversation("Good Day.", Assets.npcCactus, false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                }else {
                    ArrayList<Conversation> c2 = new ArrayList<>();
                    ArrayList<Conversation> c3 = new ArrayList<>();
                    c.add(new Conversation("Hello", Assets.player_icon, false));
                    c.add(new Conversation("Hello", Assets.npcCactus, true));
                    c.add(new Conversation("Do you, by chance, happen to have some time to spare?", Assets.npcCactus, true));
                    c.add(new Conversation("?", Assets.player_icon, false));
                    c.add(new Conversation("The slime population west of town has been steadily increasing.", Assets.npcCactus, true));
                    c.add(new Conversation("It may soon pose a danger to our village.", Assets.npcCactus, true));
                    c.add(new Conversation("Would you take a few out?", Assets.npcCactus, true));

                    c2.add(new Conversation("Thank you.", Assets.npcCactus, true));
                    c2.add(new Conversation("I’ll have something for you when you finish.", Assets.npcCactus, true));

                    c3.add(new Conversation("Alright then, but those slimes won't slay themselves.", Assets.npcCactus, true));

                    manager.getConvBox().setConversationList(c, () -> {
                        convBoxOn = false;
                        manager.popUpOptions("How would you respond?", new String[]{"Leave", "\"Yes\""}, new ClickListener[]{() -> {
                            manager.hideUI();
                            manager.getConvBox().setConversationList(c3, () -> {
                            });
                            manager.getConvBox().setActive();
                        }, () -> {
                            manager.hideUI();
                            manager.getConvBox().setConversationList(c2, () -> {
                                handler.getPlayer().getMissionManager().addMission(20);
                                convBoxOn = false;
                                Mission.missions[20].setCompleteMessage("Report back to Captain Cactus");
                                handler.getPlayer().getMissionManager().setSelectedMission();
                                handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                                Entity e = Entity.entityList[740].clone();
                                e.initialize(handler, 5120, 7552, 5120, 7552, 0);
                                handler.getWorld().getEntityManager().addEntityHot(e);
                            });
                            manager.getConvBox().setActive();
                        }}, false);
                    });
                }
        }
        manager.hideUI();
        manager.getConvBox().setActive();
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(status > missionSet.length)
            return;
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        if(status == missionSet.length){
            canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
            canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
            return;
        }
        if(handler.getPlayer().getMissionManager().hasMission(missionSet[status])){
            if(Mission.missions[missionSet[status]].isCompleted()){
                canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
                canvas.drawBitmap(Assets.hsoMissionComplete, null, destRect, Constants.getRenderPaint());
            }else {
                canvas.drawBitmap(Assets.headSignGray, null, destRect, Constants.getRenderPaint());
                canvas.drawBitmap(Assets.hsgMissionComplete, null, destRect, Constants.getRenderPaint());
            }
        }else {
            canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
            canvas.drawBitmap(Assets.hsoGetMission, null, destRect, Constants.getRenderPaint());
        }
    }

    @Override
    protected ArrayList<Conversation>[] getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.npcCactus, false));
        return new ArrayList[]{(ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone()};
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ArrayList<Conversation>[] getCompleteConversation() {
        ArrayList<ArrayList<Conversation>> conversations = new ArrayList<>();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("...", Assets.NULL, false));
        conversations.add((ArrayList<Conversation>) c.clone());
        c.clear();
        c.add(new Conversation("Here you go.", Assets.player_icon, false));
        c.add(new Conversation("(The villager looks in awe)", Assets.NULL, false));
        c.add(new Conversation("Woah... I’m impressed.", Assets.npcCactus, true));
        c.add(new Conversation("...", Assets.npcCactus, true));
        c.add(new Conversation("...", Assets.player_icon, false));
        c.add(new Conversation("I know this may seem rude, but I’d like to give you some advice.", Assets.npcCactus, true));
        c.add(new Conversation("?", Assets.player_icon, false));
        c.add(new Conversation("To be frank, don’t hold your actions too lightly.", Assets.npcCactus, true));
        c.add(new Conversation("No matter what you do, every action you make can change your life.", Assets.npcCactus, true));
        c.add(new Conversation("It’s not just about cutting down a tree, It’s about changing the world around you.", Assets.npcCactus, true));
        c.add(new Conversation("I hope what I’ve said can help you with your adventures. I’m sorry I ever doubted you.", Assets.npcCactus, true));
        conversations.add((ArrayList<Conversation>) c.clone());

        c.clear();
        c.add(new Conversation("Excellent.", Assets.npcCactus, true));
        c.add(new Conversation("Here, take this.", Assets.npcCactus, true));
        conversations.add((ArrayList<Conversation>) c.clone());
        return conversations.toArray(new ArrayList[0]);
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.npcCactus, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.npcChicken, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Captain Cactus";
    }
}
