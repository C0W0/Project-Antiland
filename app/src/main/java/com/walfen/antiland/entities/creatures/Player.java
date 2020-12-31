package com.walfen.antiland.entities.creatures;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerDefaultAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.effect.Shield;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.inventory.Fabricator;
import com.walfen.antiland.inventory.Inventory;
import com.walfen.antiland.inventory.Trade;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.items.equipment.Equipment;
import com.walfen.antiland.map.MapManager;
import com.walfen.antiland.mission.Mission;
import com.walfen.antiland.mission.MissionManager;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.statswindow.PlayerSkillsManager;
import com.walfen.antiland.statswindow.PlayerStatsWindow;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.entities.creatures.npc.NPC.InteractionType;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Player extends Creature implements TouchEventListener {

    //animations
    private Animation downAnim, upAnim, rightAnim, leftAnim, neutralAnim;
    private Animation downAttackAnim, upAttackAnim, rightAttackAnim, leftAttackAnim;
    private Animation currentAnimation;

    //attack
    private RangedAttack attack;
    private RangedAttack defaultAttack;

    //attack speed
    private long lastAttackTime, attackCooldown, attackTimer;

    //inventory
    private Inventory inventory;
    private Fabricator fabricator;
    private Trade trade;

    //mission system
    private MissionManager missionManager;
    private KillTracker tracker;

    //map
    private MapManager mapManager;

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


    public Player(Handler handler) {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 0);

        physicalDamage = 1;
        magicalDamage = 0;
        maxHp = 10;
        maxMp = 5;

        this.handler = handler;

        bounds.left = 30;
        bounds.top = 48;
        bounds.right = bounds.left+70;
        bounds.bottom = bounds.top+110;


        //attack timer
        attackCooldown = 800;
        attackTimer = attackCooldown;

        //animation
        downAnim = new Animation(0.5f, Assets.player_down);
        upAnim = new Animation(0.5f, Assets.player_up);
        rightAnim = new Animation(0.5f, Assets.player_right);
        leftAnim = new Animation(0.5f, Assets.player_left);

        downAttackAnim = new Animation(0.55f, Assets.player_attack_down);
        upAttackAnim = new Animation(0.55f, Assets.player_attack_up);
        rightAttackAnim = new Animation(0.55f, Assets.player_attack_right);
        leftAttackAnim = new Animation(0.55f, Assets.player_attack_left);

        neutralAnim = new Animation(1, new Bitmap[]{Assets.player_neutral});
        currentAnimation = neutralAnim;

        defaultAttack = new PlayerDefaultAttack(handler, Attack.Type.PHYSICAL, 256, 10, () -> physicalDamage);
        attack = defaultAttack;

        inventory = new Inventory(handler);
        fabricator = new Fabricator(handler, inventory);
        trade = new Trade(handler, inventory);

        mapManager = new MapManager(handler);

        missionManager = new MissionManager(handler);
        tracker = new KillTracker(handler);

        interactButton = new UIImageButton(Constants.SCREEN_WIDTH-160, Constants.SCREEN_HEIGHT-504, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::onInteract);
        event = Constants.EMPTY_EVENT;
        interactButton.setActive(false);

        skillsManager = new PlayerSkillsManager(handler);
        statsWindow = new PlayerStatsWindow(handler);


        //only for temp. use

//        missionManager.addMission(Mission.collect10Woods.getId());
//        missionManager.addMission(Mission.collect5Woods.getId());
//        missionManager.addMission(Mission.collect10Apples.getId());
    }

    public void loadPlayer(String path, UIManager manager){
        //files
        File playerFile = new File(path+"/player/player.wld");
        File inventoryFile = new File(path+"/player/inventory.wld");
        File missionFile = new File(path+"/player/missions.wld");
        ArrayList<String> tokens;

        //player base stats
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
        }catch (IOException e){
            e.printStackTrace();
        }

        for(int i = 2; i <= level; i++)
            maxHp += Math.floor((level/10.f)+1);

        //inventory and equipments
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

        //mission io and initial kill count calculation
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

        File statusFile = new File(path+"/player/effects.wld");
        try {
            tokens = Utils.loadFileAsArrayList(new FileInputStream(statusFile));
            int count = Utils.parseInt(tokens.get(0));
            for(int i = 1; i < count+1; i++){
                String[] token = tokens.get(i).split("\\s+");
                int id = Utils.parseInt(token[0]);
                long duration = Utils.parseLong(token[1]);
                if(id == -127){ // special effects such as shields are negative number
                    int durability = Utils.parseInt(token[2]);
                    int[] dmgModifier = new int[token.length-3];
                    for(int j = 3; j < token.length; j++)
                        dmgModifier[j-3] = Utils.parseInt(token[j]);
                    setShield(new Shield(durability, duration, dmgModifier));
                }else {
                    StatusEffect e = StatusEffect.statusEffects[id].clone();
                    e.initialize(this, duration);
                    addEffect(e);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //skills
        skillsManager.initSkills(path, manager);

        //map
        mapManager.loadMap(path);
    }

    private void checkAttacks(){

        attackTimer += System.currentTimeMillis() - lastAttackTime;
        lastAttackTime = System.currentTimeMillis();
        if(attackTimer < attackCooldown) {
            return;
        }
        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive() ||
                skillsManager.isActive() || statsWindow.isActive() || trade.isActive() ||
                mapManager.isActive()){
            return;
        }
        float inputX = handler.getUIManager().getCGUI().getAttackJoystick().getMappedInputX();
        float inputY = handler.getUIManager().getCGUI().getAttackJoystick().getMappedInputY();
        if(inputX == 0 && inputY == 0)
            return;
        int radius = handler.getUIManager().getCGUI().getAttackJoystick().getRadius();
        inputX*= radius/Utils.Py.getC(inputX*radius, inputY*radius);
        inputY*= radius/Utils.Py.getC(inputX*radius, inputY*radius);

        if(Math.abs(inputX) > Math.abs(inputY)){
            //left or right
            if(inputX > 0){
                currentAnimation = rightAttackAnim;
                leftAttackAnim.reset();
            } else{
                currentAnimation = leftAttackAnim;
                rightAttackAnim.reset();
            }
            downAttackAnim.reset();
            upAttackAnim.reset();
        }
        else{
            //up or down
            if(inputY > 0){
                currentAnimation = downAttackAnim;
                upAttackAnim.reset();
            } else {
                currentAnimation = upAttackAnim;
                downAttackAnim.reset();
            }
            rightAttackAnim.reset();
            leftAttackAnim.reset();
        }
        attack.generateAttack(inputX, inputY);

        attackTimer = 0;

    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if(inventory.isActive() || fabricator.isActive() || missionManager.isActive() ||
                skillsManager.isActive() || statsWindow.isActive() || trade.isActive() ||
                mapManager.isActive()){
            return;
        }
        xMove = handler.getUIManager().getCGUI().getMovementJoystick().getInputX()*speed;
        yMove = handler.getUIManager().getCGUI().getMovementJoystick().getInputY()*speed;
    }

    private void setCurrentMovementAnimation(){
        if(attackTimer < 800)
            return;
        if(xMove == 0 && yMove == 0) {
            currentAnimation = neutralAnim;
            downAnim.reset();
            upAnim.reset();
            rightAnim.reset();
            leftAnim.reset();
        }else if(Math.abs(xMove) > Math.abs(yMove)){
            //left or right
            if(xMove > 0){
                currentAnimation = rightAnim;
                leftAnim.reset();
            } else{
                currentAnimation = leftAnim;
                rightAnim.reset();
            }
            downAnim.reset();
            upAnim.reset();
        } else{
            //up or down
            if(yMove > 0){
                currentAnimation = downAnim;
                upAnim.reset();
            } else {
                currentAnimation = upAnim;
                downAnim.reset();
            }
            rightAnim.reset();
            leftAnim.reset();
        }
    }


    @Override
    public void update() {
        super.update();
//        System.out.println(attackTimer);
//        System.out.println((int)x/128+" "+(int)y/128);
//        System.out.println(x+" "+y);
        if(disable){
            handler.getGameCamera().centerOnEntity(this);
            return;
        }

        //animation
        setCurrentMovementAnimation();
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
        mapManager.update();

        //skills
        skillsManager.update();

        //stats screen
        if(statsWindow.isActive())
            statsWindow.update();

        //missions
        missionManager.update();
        tracker.update();

        //shield
        if(shield != null)
            if((!shield.isValid()) || shield.getDurability() <= 0)
                shield = null;

    }

    @Override
    public void draw(Canvas canvas) {
        attack.draw(canvas);
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top-96, left+Assets.player_neutral.getWidth(), top-96+Assets.player_neutral.getHeight()));
//        canvas.drawRect(new Rect(left+bounds.left,top+bounds.top,left+bounds.right,top+bounds.bottom), Constants.getRenderPaint());
    }

    public void onTouchEvent(MotionEvent event){
        inventory.onTouchEvent(event);
        fabricator.onTouchEvent(event);
        missionManager.onTouchEvent(event);
        skillsManager.onTouchEvent(event);
        statsWindow.onTouchEvent(event);
        trade.onTouchEvent(event);

        mapManager.onTouchEvent(event);

        interactButton.onTouchEvent(event);
    }

    @Override
    public void receiveDamage(int num, int type) {
        if(shield != null)
            num = shield.receiveDamage(num, type);
        super.receiveDamage(num, type);
    }

    @Override
    protected void onDeath() {
        if(!handler.getGame().getGameState().isDisabled())
            handler.getGame().getGameState().playerDeath();
    }

    public void postDraw(Canvas canvas){
        interactButton.draw(canvas);
        inventory.draw(canvas);
        missionManager.draw(canvas);
        fabricator.draw(canvas);
        skillsManager.draw(canvas);
        statsWindow.draw(canvas);
        trade.draw(canvas);

        mapManager.draw(canvas);
    }

    @Override
    public void addEffect(StatusEffect effect) {
        super.addEffect(effect);
        statsWindow.addStatusIcon(effect);
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
        //status effects
        File effectFile = new File(path+"/player/effects.wld");
        effectFile.delete();
        effectFile.createNewFile();
        editor = new PrintWriter(effectFile);
        int length = getEffects().size();
        editor.println(length+(shield==null?0:1));
        for(StatusEffect e: getEffects())
            editor.println(e.getId()+" "+e.getMSRemainingDuration());
        if(shield != null){
            editor.print("-127 "+shield.getMSRemainingDuration()+" "+shield.getDurability()+" ");
            int[] dmgPercentMod = shield.getDmgPercentMod();
            for (int value : dmgPercentMod)
                editor.print(value + " ");
            editor.println();
        }
        editor.close();

        skillsManager.saveSkills(path);
        mapManager.saveMap(path);
    }

    public void equip(int id, int location){
        if(inventory.getEquipments()[location] != null)
            inventory.getEquipments()[location].onActive();
        Equipment e = (Equipment)Item.items[id].addToInv(1);
        e.setEquippedOn(true);
        e.setHandler(handler);
        inventory.getEquipments()[location] = e;
        inventory.deductItem(e.getId(), 1);
    }

    public void removeEquipment(int location){
        inventory.addItem(inventory.getEquipments()[location].addToInv(1));
        inventory.getEquipments()[location] = null;
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.player_icon, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Player";
    }

    public void onInteract(){
        event.onChange();
        handler.getUIManager().getCGUI().resetJoystick();
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
        return 3*level*level-5*level+10;
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

    public void setInteractionEvent(ChangeEvent event, int type) {
        this.event = event;
        switch (type){
            case InteractionType.TALK_TO_NPC:
                interactButton.setTexture(Assets.chat);
                break;
            case InteractionType.TRADE:
                interactButton.setTexture(Assets.tradeInteract);
                break;
            case InteractionType.TRIGGER_ENTITY:
                interactButton.setTexture(Assets.operate);
                break;
            case InteractionType.USE_TELEPORTATION:
                break;
            default:
        }
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

    public void setAttack(RangedAttack attack) {
        this.attack = attack;
    }

    public RangedAttack getAttack() {
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

    public MapManager getMapManager() {
        return mapManager;
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

    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setLocation(Point location){
        setLocation(location.x, location.y);
    }

    public Handler getHandler(){
        return handler;
    }
}
