package com.walfen.antiland.entities.creatures;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerDefaultAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttacks;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.inventory.Fabricator;
import com.walfen.antiland.inventory.Inventory;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.mission.MissionManager;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player extends Creature{

    //animations
    private Animation downAnim, upAnim, rightAnim, leftAnim, neutralAnim;
    private Animation currentAnimation;
    private RangedAttacks attack;
    //attack speed
    private long lastAttackTime, attackCooldown, attackTimer;
    //inventory
    private Inventory inventory;
    private Fabricator fabricator;

    //player stats
    private MissionManager missionManager;
    private int level;
    private int currLevelXp;
    private int baseDamage;


    public Player(Handler handler, String path) {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0);

        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        ArrayList<String> tokens;
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(playerFile));
            x = Utils.parseInt(tokens.get(0).split("\\s+")[0]);
            y = Utils.parseInt(tokens.get(0).split("\\s+")[1]);
            health = Utils.parseInt(tokens.get(1).split("\\s+")[0]);
            maxHP = Utils.parseInt(tokens.get(1).split("\\s+")[1]);
            mp = Utils.parseInt(tokens.get(2).split("\\s+")[0]);
            maxMP = Utils.parseInt(tokens.get(2).split("\\s+")[1]);
            level = Utils.parseInt(tokens.get(3).split("\\s+")[0]);
            currLevelXp = Utils.parseInt(tokens.get(3).split("\\s+")[1]);
        }catch (IOException e){
            e.printStackTrace();
        }
        baseDamage = 1;

        this.handler = handler;

        bounds.left = 42;
        bounds.top = 60;
        bounds.right = bounds.left+64;
        bounds.bottom = bounds.top+64;


        //attack timer
        attackCooldown = 800;
        attackTimer = attackCooldown;

        //animation
        downAnim = new Animation(0.15f, Assets.player_down);
        upAnim = new Animation(0.15f, Assets.player_up);
        rightAnim = new Animation(0.15f, Assets.player_right);
        leftAnim = new Animation(0.15f, Assets.player_left);
        neutralAnim = new Animation(0.15f, new Bitmap[]{Assets.player_neutral});


        attack = new PlayerDefaultAttack(handler, () -> baseDamage);


        inventory = new Inventory(handler);
        fabricator = new Fabricator(handler, inventory, "res/worlds/worldSDK");
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(inventoryFile));
            for(String str: tokens){
                String[] line = str.split("\\s+");
                inventory.addItem(Item.items[Utils.parseInt(line[0])].
                        addToInv(Utils.parseInt(line[1])));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        missionManager = new MissionManager(handler);

        //only for temp. use

//        missionManager.addMission(Mission.collect10Woods.getId());
//        missionManager.addMission(Mission.collect5Woods.getId());
//        missionManager.addMission(Mission.collect10Apples.getId());
    }

    private void checkAttacks(){

        attackTimer += System.currentTimeMillis() - lastAttackTime;
        lastAttackTime = System.currentTimeMillis();
        if(attackTimer < attackCooldown) {
            return;
        }
        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive()){
            return;
        }
        float inputX = handler.getUIManager().getAttackJoystick().getMappedInputX();
        float inputY = handler.getUIManager().getAttackJoystick().getMappedInputY();
        if(inputX == 0 && inputY == 0)
            return;
        int radius = handler.getUIManager().getAttackJoystick().getRadius();
        inputX*= radius/Utils.Py.getC(inputX*radius, inputY*radius);
        inputY*= radius/Utils.Py.getC(inputX*radius, inputY*radius);

        attack.generateAttack(inputX, inputY);

        attackTimer = 0;

    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive()){
            return;
        }
        xMove = handler.getUIManager().getMovementJoystick().getInputX()*speed;
        yMove = handler.getUIManager().getMovementJoystick().getInputY()*speed;
    }

    private void setCurrentAnimation(){
        if(yMove < 0){
            currentAnimation = upAnim;
            downAnim.reset();
            rightAnim.reset();
            leftAnim.reset();
        } else if(xMove > 0){
            currentAnimation = rightAnim;
            downAnim.reset();
            upAnim.reset();
            leftAnim.reset();
        } else if(xMove < 0){
            currentAnimation = leftAnim;
            downAnim.reset();
            upAnim.reset();
            rightAnim.reset();
        } else if(yMove > 0){
            currentAnimation = downAnim;
            upAnim.reset();
            rightAnim.reset();
            leftAnim.reset();
        } else {
            currentAnimation = neutralAnim;
            downAnim.reset();
            upAnim.reset();
            rightAnim.reset();
            leftAnim.reset();
        }
    }


    @Override
    public void update() {

        //animation
        setCurrentAnimation();
        currentAnimation.update();

        //movement
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);

        //attack
        checkAttacks();
        attack.update();

        //inventory
        inventory.update();
        fabricator.update();

        //missions
        missionManager.update();

    }

    @Override
    public void draw(Canvas canvas) {
        attack.draw(canvas);
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top, left+Assets.player_neutral.getWidth(), top+Assets.player_neutral.getHeight()));
    }

    @Override
    public void die() {
        System.out.println("You lose");
    }

    public void postdraw(Canvas canvas){
        inventory.draw(canvas);
        missionManager.draw(canvas);
        fabricator.draw(canvas);
    }

    public void saveMap(String path) throws IOException{
        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        playerFile.delete();
        playerFile.createNewFile();
        PrintWriter editor = new PrintWriter(playerFile);
        editor.println((int)x+" "+(int)y);
        editor.println(health+" "+maxHP);
        editor.println(mp+" "+maxMP);
        editor.println(level+" "+currLevelXp);
        editor.close();
        editor = new PrintWriter(inventoryFile);
        for(Item i: inventory.getInventoryItems()){
            editor.println(i.getId()+" "+i.getCount());
        }
        editor.close();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Fabricator getFabricator() {
        return fabricator;
    }

    public MissionManager getMissionManager(){
        return missionManager;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrLevelXp() {
        return currLevelXp;
    }

    public int getCurrLevelMaxXp(){
        return 5*level*level-5*level+10;
    }

    public void increaseXp(int xp) {
        int remainingXp = getCurrLevelMaxXp()-currLevelXp;
        if(xp >= remainingXp) {
            level++;
            currLevelXp = xp-remainingXp;
        }else {
            currLevelXp += xp;
        }
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void changeBaseDamage(int damage) {
        baseDamage += damage;
    }
}
