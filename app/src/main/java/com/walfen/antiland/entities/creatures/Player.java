package com.walfen.antiland.entities.creatures;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerDefaultAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttacks;
import com.walfen.antiland.entities.properties.skills.passive.SimplePlayerSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.inventory.Fabricator;
import com.walfen.antiland.inventory.Inventory;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.Equipment;
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
    private int currLevelXp;
    private SimplePlayerSkill strength, endurance, agility, knowledge, intelligence;


    public Player(Handler handler, String path) {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0);

        strength = new SimplePlayerSkill(handler, 10, () -> {physicalDamage += strength.getLevel(); maxHp += 1;});
        endurance = new SimplePlayerSkill(handler, 10, () -> {maxHp += endurance.getLevel(); defence += 1;});
        agility = new SimplePlayerSkill(handler, 10, () -> speed += (int)Math.floor(agility.getLevel()/2.f+0.5));
        knowledge = new SimplePlayerSkill(handler, 10, () -> {maxMp += knowledge.getLevel(); magicalDamage += 1;});
        intelligence = new SimplePlayerSkill(handler, 10, () -> magicalDamage += (int)Math.floor(intelligence.getLevel()/2.f+0.5));

        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        ArrayList<String> tokens;
        physicalDamage = 1;
        magicalDamage = 0;
        maxHp = 10;
        maxMp = 5;
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(playerFile));
            x = Utils.parseInt(tokens.get(0).split("\\s+")[0]);
            y = Utils.parseInt(tokens.get(0).split("\\s+")[1]);
            health = Utils.parseInt(tokens.get(1).split("\\s+")[0]);
            mp = Utils.parseInt(tokens.get(1).split("\\s+")[1]);
            level = Utils.parseInt(tokens.get(2).split("\\s+")[0]);
            currLevelXp = Utils.parseInt(tokens.get(2).split("\\s+")[1]);
            String[] line = tokens.get(3).split("\\s+");
            strength.setLevel(Utils.parseInt(line[0]));
            endurance.setLevel(Utils.parseInt(line[1]));
            agility.setLevel(Utils.parseInt(line[2]));
            knowledge.setLevel(Utils.parseInt(line[3]));
            intelligence.setLevel(Utils.parseInt(line[4]));
        }catch (IOException e){
            e.printStackTrace();
        }


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


        attack = new PlayerDefaultAttack(handler, () -> physicalDamage);


        inventory = new Inventory(handler);
        fabricator = new Fabricator(handler, inventory, "res/worlds/worldSDK");
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(inventoryFile));
            for(String str: tokens){
                String[] line = str.split("\\s+");
                inventory.addItem(Item.items[Utils.parseInt(line[0])].
                        addToInv(Utils.parseInt(line[1])));
            }
            tokens = Utils.loadFileAsArrayList(new FileInputStream(playerFile));
            String[] equips = tokens.get(4).split("\\s+");
            for(int i = 0; i < 4; i++){
                if(!equips[i].equals("-1")) {
                    Equipment e = (Equipment) Item.items[Utils.parseInt(equips[i])].addToInv(1);
                    e.onEquip(this);
                    equip(e.getId(), i);
                }
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
        /*
        IMPORTANT: player.wld format (update here):
        x y
        hp mp
        level exp
        strength endurance agility knowledge intelligence
        weaponId, auxiliaryId, armourId, bootsId
         */
        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        playerFile.delete();
        playerFile.createNewFile();
        PrintWriter editor = new PrintWriter(playerFile);
        editor.println((int)x+" "+(int)y);
        editor.println(health+" "+ mp);
        editor.println(level+" "+currLevelXp);
        editor.print(strength.getLevel()+" ");
        editor.print(endurance.getLevel()+" ");
        editor.print(agility.getLevel()+" ");
        editor.print(knowledge.getLevel()+" ");
        editor.println(intelligence.getLevel()+" ");
        for(Equipment e: inventory.getEquipments()) {
            if (e != null)
                editor.print(e.getId() + " ");
            else
                editor.print(-1 + " ");
        }
        editor.close();
        editor = new PrintWriter(inventoryFile);
        for(Item i: inventory.getInventoryItems()){
            editor.println(i.getId()+" "+i.getCount());
        }
        editor.close();
    }

    public void equip(int id, int location){
        removeEquipment(location);
        Equipment e = (Equipment)Item.items[id].addToInv(1);
        e.setEquippedOn(true);
        e.setHandler(handler);
        inventory.getEquipments()[location] = e;
        inventory.deductItem(e.getId(), 1);
    }

    public void removeEquipment(int location){
        if(inventory.getEquipments()[location] == null)
            return;
        inventory.addItem(inventory.getEquipments()[location].addToInv(1));
        inventory.getEquipments()[location] = null;
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
            maxHp += Math.floor((level/10.f)+1);
            health = maxHp;
            currLevelXp = xp-remainingXp;
        }else {
            currLevelXp += xp;
        }
    }
}
