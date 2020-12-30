package com.walfen.antiland.entities.creatures.npc.primary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.conversation.Conversation;

import java.util.ArrayList;

public class Hermit extends MajorMissionNPC{


    private Animation dynamicTexture;

    public Hermit() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 506, new int[]{-1, 18});
        interRange = 2;
        dynamicTexture = new Animation(1, Assets.hermit);
        dynamicTexture.scale(width, height);
    }

    @Override
    protected void interact() {
        if(status == 1)
            super.interact();
        else {
            if(Mission.missions[19].isCompleted()){
                convBoxOn = true;
                UIManager uiManager = handler.getUIManager();
                uiManager.hideUI();
                ArrayList<Conversation> c = getCompleteConversation()[2];
                uiManager.getConvBox().setConversationList(c, () -> {Mission.missions[19].complete(); convBoxOn = false;});
                uiManager.getConvBox().setActive();
            }else {
                convBoxOn = true;
                assignConversationProcedure(handler.getUIManager());
            }
        }
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
        switch (status){
            case 1:
                c.add(new Conversation("Hello", Assets.player_icon, false));
                c.add(new Conversation("...", Assets.hermit[0], true));
                c.add(new Conversation("What? Are you expecting a greeting?", Assets.hermit[0], true));
                c.add(new Conversation("The world doesn’t always give you what you want.", Assets.hermit[0], true));
                c.add(new Conversation("If I were you, I’d stop your expeditions right now.", Assets.hermit[0], true));
                c.add(new Conversation("You’re treading too far into something you don’t know.", Assets.hermit[0], true));
                c.add(new Conversation("And for what? Huh? Glory?", Assets.hermit[0], true));
                c.add(new Conversation("I want you to look east and to take in what you see.", Assets.hermit[0], true));
                c.add(new Conversation("That thing’s called a Spirit, and it can take away your very life.", Assets.hermit[0], true));
                c.add(new Conversation("You.", Assets.hermit[0], true));
                c.add(new Conversation("Can.", Assets.hermit[0], true));
                c.add(new Conversation("Die.", Assets.hermit[0], true));
                c.add(new Conversation("If you still want to challenge the world, I want you to think about that.", Assets.hermit[0], true));
                manager.getConvBox().setConversationList(c, () -> {
                    assignMission();
                    convBoxOn = false;
                    handler.getPlayer().getMissionManager().setSelectedMission();
                    handler.getUIManager().getCGUI().getMissionPanel().extendPanel();
                    Entity e = Entity.entityList[208].clone();
                    e.initialize(handler, 8576, 2944, 8576, 2944, 0);
                    handler.getWorld().getEntityManager().addEntityHot(e);
                });
                break;
            default:
                c.clear();
                double r = Math.random();
                boolean gettingApples = false;
                for(Mission m: handler.getPlayer().getMissionManager().getMissions()){
                    if(m.getId() == 19){
                        gettingApples = true;
                        break;
                    }
                }
                if (r < 0.75 || gettingApples) {
                    if(r < 0.25)
                        c.add(new Conversation("Why do I live out here?", Assets.hermit[0], false));
                    else if(r >= 0.25 && r < 0.5)
                        c.add(new Conversation("I have my reasons.", Assets.hermit[0], false));
                    else if(r >= 0.5 && r < 0.75)
                        c.add(new Conversation("Now get going.", Assets.hermit[0], false));
                    manager.getConvBox().setConversationList(c, () -> convBoxOn = false);
                }else {
                    ArrayList<Conversation> c2 = new ArrayList<>();
                    ArrayList<Conversation> c3 = new ArrayList<>();
                    c.add(new Conversation("Hello", Assets.player_icon, false));
                    c.add(new Conversation("What do you want?", Assets.hermit[0], true));
                    c.add(new Conversation("...", Assets.player_icon, false));
                    c.add(new Conversation("...", Assets.hermit[0], true));
                    c.add(new Conversation("Seriously?", Assets.hermit[0], true));
                    c.add(new Conversation("Yup.", Assets.player_icon, false));
                    c.add(new Conversation("If you really want something to occupy yourself with, then how about you get me an apple.", Assets.hermit[0], true));
                    c.add(new Conversation("And not those store bought apples or whatever you’re carrying in your pockets.", Assets.hermit[0], true));
                    c.add(new Conversation("Get me one fresh off a tree.", Assets.hermit[0], true));

                    c2.add(new Conversation("All the time in the world...", Assets.hermit[0], true));
                    c2.add(new Conversation("and you do this...", Assets.hermit[0], true));

                    c3.add(new Conversation("Then you just wanted to bother me, Huh.", Assets.hermit[0], true));
                    c3.add(new Conversation("(The Hermit does a painfully slow clap to perfectly accentuate the time you’ve wasted)", Assets.NULL, true));

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
                                handler.getPlayer().getMissionManager().addMission(19);
                                convBoxOn = false;
                                Mission.missions[19].setCompleteMessage("Give the Apple to the Hermit");
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
    protected ArrayList<Conversation>[] getIncompleteConversation() {
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("You haven't complete the mission yet. Come back to me later.", Assets.hermit[0], false));
        return new ArrayList[]{(ArrayList)c.clone(), (ArrayList)c.clone(), (ArrayList)c.clone()};
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ArrayList<Conversation>[] getCompleteConversation() {
        ArrayList<ArrayList<Conversation>> conversations = new ArrayList<>();
        ArrayList<Conversation> c = new ArrayList<>();
        conversations.add((ArrayList<Conversation>) c.clone());
        c.clear();
        c.add(new Conversation("Your skull is too darn thick.", Assets.hermit[0], false));
        c.add(new Conversation("*sigh* but I should’ve seen that coming.", Assets.hermit[0], false));
        c.add(new Conversation("(The Hermit enters his house and comes back out with a peculiar object)", Assets.NULL, false));
        c.add(new Conversation("You’re going to get hurt one day.", Assets.hermit[0], false));
        c.add(new Conversation("...", Assets.hermit[0], false));
        c.add(new Conversation("but I’ll be damned if I don’t help you get through it.", Assets.hermit[0], false));
        conversations.add((ArrayList<Conversation>) c.clone());
        c.clear();
        c.add(new Conversation("...", Assets.hermit[0], false));
        c.add(new Conversation("Thanks", Assets.hermit[0], false));
        c.add(new Conversation("...", Assets.hermit[0], false));
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
        return ImageEditor.scaleBitmapForced(Assets.hermit[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "The Hermit";
    }
}
