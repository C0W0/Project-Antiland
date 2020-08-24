package com.walfen.antiland.entities.creatures;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerDefaultAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttacks;
import com.walfen.antiland.entities.properties.effect.Shield;
import com.walfen.antiland.entities.properties.skills.Skill;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.entities.properties.skills.active.SharpWind;
import com.walfen.antiland.entities.properties.skills.active.SwordStorm;
import com.walfen.antiland.entities.properties.skills.passive.SimplePlayerSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.inventory.Fabricator;
import com.walfen.antiland.inventory.Inventory;
import com.walfen.antiland.inventory.Trade;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.Equipment;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.mission.MissionManager;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.statswindow.PlayerSkillsManager;
import com.walfen.antiland.statswindow.PlayerStatsWindow;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player extends Creature implements TouchEventListener {

    //animations
    private Animation downAnim, upAnim, rightAnim, leftAnim, neutralAnim;
    private Animation currentAnimation;

    //attack
    private RangedAttacks attack;
    private RangedAttacks defaultAttack;

    //attack speed
    private long lastAttackTime, attackCooldown, attackTimer;

    //inventory
    private Inventory inventory;
    private Fabricator fabricator;
    private Trade trade;

    //mission system
    private MissionManager missionManager;
    private KillTracker tracker;

    //player stats
    private int currLevelXp;
    private int perkPoints;
    private PlayerSkillsManager skillsManager;
    private PlayerStatsWindow statsWindow;
    private int wealth;
    private Shield shield;

    //environment interaction
    private ChangeEvent event;
    private UIImageButton interactButton;


    public Player(Handler handler, String path) {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0);

        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        File missionFile = new File(path+"/player/missions.wld");
        ArrayList<String> tokens;
        physicalDamage = 1;
        magicalDamage = 0;
        maxHp = 10;
        maxMp = 5;
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(playerFile));
            String[] line = tokens.get(0).split("\\s+");
            x = Utils.parseInt(line[0]);
            y = Utils.parseInt(line[1]);
            line = tokens.get(1).split("\\s+");
            health = Utils.parseInt(line[0]);
            mp = Utils.parseInt(line[1]);
            wealth = Utils.parseInt(line[2]);
            line = tokens.get(2).split("\\s+");
            level = Utils.parseInt(line[0]);
            currLevelXp = Utils.parseInt(line[1]);
            perkPoints = Utils.parseInt(line[2]);
            line = tokens.get(3).split("\\s+");
        }catch (IOException e){
            e.printStackTrace();
        }

        for(int i = 2; i <= level; i++)
            maxHp += Math.floor((level/10.f)+1);


        this.handler = handler;

        bounds.left = 42;
        bounds.top = 60;
        bounds.right = bounds.left+64;
        bounds.bottom = bounds.top+64;


        //attack timer
        attackCooldown = 800;
        attackTimer = attackCooldown;

        //animation
        downAnim = new Animation(0.5f, Assets.player_down);
        upAnim = new Animation(0.5f, Assets.player_up);
        rightAnim = new Animation(0.5f, Assets.player_right);
        leftAnim = new Animation(0.5f, Assets.player_left);
        neutralAnim = new Animation(0.5f, new Bitmap[]{Assets.player_neutral});
        currentAnimation = neutralAnim;

        defaultAttack = new PlayerDefaultAttack(handler, () -> physicalDamage);
        attack = defaultAttack;


        inventory = new Inventory(handler);
        fabricator = new Fabricator(handler, inventory, "res/worlds/worldSDK");
        trade = new Trade(handler, inventory);
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(inventoryFile));
            for(String str: tokens){
                String[] line = str.split("\\s+");
                inventory.addItem(Item.items[Utils.parseInt(line[0])].
                        addToInv(Utils.parseInt(line[1])));
            }
            tokens = Utils.loadFileAsArrayList(new FileInputStream(playerFile));
            String[] equips = tokens.get(3).split("\\s+");
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
        tracker = new KillTracker(handler);
        try{
            tokens = Utils.loadFileAsArrayList(new FileInputStream(missionFile));
            for(String str: tokens){
                String[] line = str.split("\\s+");
                Mission tempMission = Mission.missions[Utils.parseInt(line[0])];
                int[] progress = new int[line.length-1];
                for(int i = 0; i < progress.length; i++){
                    progress[i] = Utils.parseInt(line[i+1]);
                }
                tempMission.setProgress(progress, tracker);
                missionManager.addMission(tempMission);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        interactButton = new UIImageButton(Constants.SCREEN_WIDTH-160, 576, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::onInteract);
        event = Constants.EMPTY_EVENT;
        interactButton.setActive(false);

        skillsManager = new PlayerSkillsManager(handler);
        statsWindow = new PlayerStatsWindow(handler);


        //only for temp. use

//        missionManager.addMission(Mission.collect10Woods.getId());
//        missionManager.addMission(Mission.collect5Woods.getId());
//        missionManager.addMission(Mission.collect10Apples.getId());
//        missionManager.addMission(Mission.cutDown5Trees.getId());
    }

    private void checkAttacks(){

        attackTimer += System.currentTimeMillis() - lastAttackTime;
        lastAttackTime = System.currentTimeMillis();
        if(attackTimer < attackCooldown) {
            return;
        }
        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive() ||
                skillsManager.isActive() || statsWindow.isActive() || trade.isActive()){
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

        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive() ||
                skillsManager.isActive() || trade.isActive()){
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
        super.update();

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
        trade.update();

        //skills
        skillsManager.update();

        //stats screen
        if(statsWindow.isActive())
            statsWindow.update();

        //missions
        missionManager.update();
        tracker.update();

    }

    @Override
    public void draw(Canvas canvas) {
        attack.draw(canvas);
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top, left+Assets.player_neutral.getWidth(), top+Assets.player_neutral.getHeight()));
    }

    public void onTouchEvent(MotionEvent event){
        inventory.onTouchEvent(event);
        fabricator.onTouchEvent(event);
        missionManager.onTouchEvent(event);
        skillsManager.onTouchEvent(event);
        statsWindow.onTouchEvent(event);
        trade.onTouchEvent(event);

        interactButton.onTouchEvent(event);
    }

    @Override
    public void receiveDamage(int num, int type) {
        if(shield != null)
            num = shield.receiveDamage(num, type);
        super.receiveDamage(num, type);
    }

    @Override
    public void die() {
        System.out.println("You lose");
    }

    @Override
    protected void onDeath() { }

    public void postdraw(Canvas canvas){
        interactButton.draw(canvas);
        inventory.draw(canvas);
        missionManager.draw(canvas);
        fabricator.draw(canvas);
        skillsManager.draw(canvas);
        statsWindow.draw(canvas);
        trade.draw(canvas);
    }

    public void savePlayer(String path) throws IOException{
        /*
        IMPORTANT: player.wld format (update here):
        x y
        hp mp wealth
        level exp perkPoints
        weaponId, auxiliaryId, armourId, bootsId
         */
        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        File missionFile = new File(path+"/player/missions.wld");
        playerFile.delete();
        playerFile.createNewFile();
        PrintWriter editor = new PrintWriter(playerFile);
        editor.println((int)x+" "+(int)y);
        editor.println(health+" "+mp+" "+wealth);
        editor.println(level+" "+currLevelXp+" "+perkPoints);
        for(Equipment e: inventory.getEquipments()) {
            if (e != null)
                editor.print(e.getId() + " ");
            else
                editor.print(-1 + " ");
        }
        editor.close();
        /*
        inventory.wld format
        itemId count
         */
        inventoryFile.delete();
        inventoryFile.createNewFile();
        editor = new PrintWriter(inventoryFile);
        for(Item i: inventory.getInventoryItems())
            editor.println(i.getId()+" "+i.getCount());
        editor.close();
        /*
        missions.wld format
        missionId progress[]
         */
        missionFile.delete();
        missionFile.createNewFile();
        editor = new PrintWriter(missionFile);
        for(Mission m: missionManager.getMissions()){
            editor.print(m.getId());
            for(int i: m.getProgress())
                editor.print(" "+i);
            editor.println();
        }
        editor.close();
        skillsManager.saveSkills(path);
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

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.player_neutral, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Player";
    }

    public void onInteract(){
        event.onChange();
    }

    //getters and setters

    public KillTracker getTracker() {
        return tracker;
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
            perkPoints ++;
            health = maxHp;
            currLevelXp = xp-remainingXp;
        }else {
            currLevelXp += xp;
        }
    }

    public void setInteractionEvent(ChangeEvent event) {
        this.event = event;
    }

    public ChangeEvent getInteractionEvent() {
        return event;
    }

    public UIImageButton getInteractButton() {
        return interactButton;
    }

    public void setInterButtonVisibility(boolean visible){
        interactButton.setActive(visible);
    }

    public void setAttack(RangedAttacks attack) {
        this.attack = attack;
    }

    public RangedAttacks getAttack() {
        return attack;
    }

    public void resetAttack(){
        attack = defaultAttack;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public void changeWealth(int income){
        wealth += income;
    }

    public PlayerSkillsManager getSkillsManager() {
        return skillsManager;
    }

    public PlayerStatsWindow getStatsWindow() {
        return statsWindow;
    }

    public int getPerkPoints() {
        return perkPoints;
    }

    public void setPerkPoints(int perkPoints) {
        this.perkPoints = perkPoints;
    }

    public void changePerkPoints(int addition){
        perkPoints += addition;
    }

    public Trade getTrade(){
        return trade;
    }

    public void setShield(Shield shield) {
        this.shield = shield;
    }

    public Shield getShield() {
        return shield;
    }
}
