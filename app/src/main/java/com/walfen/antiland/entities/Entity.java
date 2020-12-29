package com.walfen.antiland.entities;


import android.graphics.Bitmap;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.EvilSpirit;
import com.walfen.antiland.entities.creatures.active.IceSlime;
import com.walfen.antiland.entities.creatures.active.LostGhost;
import com.walfen.antiland.entities.creatures.active.Slime;
import com.walfen.antiland.entities.creatures.active.SpiritWarrior;
import com.walfen.antiland.entities.creatures.active.TrappedSpirit;
import com.walfen.antiland.entities.creatures.npc.primary.CrabSmith;
import com.walfen.antiland.entities.creatures.npc.secondary.NPC1;
import com.walfen.antiland.entities.creatures.npc.trader.FoxKeeper;
import com.walfen.antiland.entities.creatures.npc.trader.MushroomTrader;
import com.walfen.antiland.entities.creatures.npc.trader.WandererCrab;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.special.command.active.TempleBossCoffin;
import com.walfen.antiland.entities.special.command.passive.generators.entities.EntityGenerator;
import com.walfen.antiland.entities.special.command.passive.generators.GenerationConstant;
import com.walfen.antiland.entities.special.command.passive.generators.entities.SlimeGenerator;
import com.walfen.antiland.entities.special.command.passive.generators.entities.SpiritLeak;
import com.walfen.antiland.entities.special.command.passive.WorldGate;
import com.walfen.antiland.entities.special.command.passive.generators.items.KeyGenerator;
import com.walfen.antiland.entities.special.command.passive.generators.items.MapleSyrupGenerator;
import com.walfen.antiland.entities.special.command.passive.island.IslandMessagers;
import com.walfen.antiland.entities.special.command.passive.tutorial.TutorialMessagers;
import com.walfen.antiland.entities.statics.AirWall;
import com.walfen.antiland.entities.statics.BarrierTree;
import com.walfen.antiland.entities.statics.BeachTree;
import com.walfen.antiland.entities.statics.Boat;
import com.walfen.antiland.entities.statics.ForestTree;
import com.walfen.antiland.entities.statics.HorizontalPier;
import com.walfen.antiland.entities.statics.MagicalTree;
import com.walfen.antiland.entities.statics.Pier;
import com.walfen.antiland.entities.statics.Rock1;
import com.walfen.antiland.entities.statics.StaticSpirit;
import com.walfen.antiland.entities.statics.Tree;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.untils.Utils;

import java.util.Arrays;

public abstract class Entity implements GameHierarchyElement, Cloneable {

    /* Entity id:
    (UPDATE HERE)
    ------------------------
    Hostile/neutral: 101 ~ 400
    Slime: 201
    IceSlime: 202
    TrappedSpirit: 204
    EvilSpirit: 205
    Ghost: 206
    SpiritWorrier: 207
    ------------------------
    NPC: 401 ~ 700
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        traders: 401 ~ 500
        WandererCrab (trade crab): 401
        MushroomTrader: 402
        FoxKeeper: 403
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        mission: 501 ~ 600
        NPC1 (mission crab): 501
        CrabSmith: 502
        cactus: 504
        chicken: 505
        hermit: 506
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        other: 601 ~ 700
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ------------------------
    Static: 701 ~ 1000
    AirWall: 701
    Tree: 702 (not used)
    Trees: 703 ~ 706
    Pier: 720
    Boat1: 721
    Boat2: 722
    HorizontalPier: 723
    Rock: 730
    StaticSpirit: 740
    ------------------------
    Special: 1001+
    WorldGate(temple 0-1): 1001
    WorldGate(temple - world): 1002
    Tutorial messagers: 1003-1013
    Island Tutorials: 1014 & 1015
    Island messagers: 1016-1017
    SlimeSpawner: 1101
    SpiritSpawner: 1102
    SpiritLeak: 1103
    KeyGenerator: 1201
    MapleSyrupGenerator: 1202
    TempleBossCoffin: 1301
     */

    public static Entity[] entityList = new Entity[2048];
    public static Slime slime = new Slime();
    public static IceSlime iceSlime = new IceSlime();
    public static Tree tree = new Tree();

    public static void initEntities(){
        new TrappedSpirit();
        new WandererCrab();
        new MushroomTrader();
        new NPC1();
        new AirWall();
        new WorldGate(1001, 1, 6656, 2250);
        new WorldGate(1002, 2, 6656, 1152);
        new TutorialMessagers.TutorialMovement();
        new TutorialMessagers.TutorialMission();
        new TutorialMessagers.TutorialAttack();
        new TutorialMessagers.TutorialPortal();
        new TutorialMessagers.TutorialUnleash();
        new SlimeGenerator();
        new TempleBossCoffin();

        new EvilSpirit();
        new LostGhost();
        new SpiritWarrior();
        new EntityGenerator(205, 256, 5, GenerationConstant.FAST_GENERATION, 1102);
        new SpiritLeak();
        new KeyGenerator();
        new MapleSyrupGenerator();
        new ForestTree();
        new BarrierTree();
        new BeachTree();
        new MagicalTree();
        new Pier();
        new HorizontalPier();
        new Rock1();
        new Boat(Assets.boat1, 721);
        new Boat(Assets.boat2, 722);

        new CrabSmith();
        new FoxKeeper();
        new IslandMessagers.EncounterMonster();
        new IslandMessagers.BarricadeBroken();
        new StaticSpirit();
    }

    //Entities
    public static final int DEFAULT_HEALTH = 10;
    protected int health, maxHp;
    protected boolean active;
    protected int faction;
    protected int[] resistance;
    protected int[] dmgPercentMod;

    protected float x,y;
    protected int oX, oY; // o stands for original
    protected int status;
    protected Handler handler;
    protected int width, height; //the size of the entity
    protected Rect bounds; //collision detection

    protected int id;

    public Entity (int width, int height, int id){
        x = 0;
        y = 0;
        this.width = width;
        this.height = height;
        active = true;
        health = DEFAULT_HEALTH;
        maxHp = health;
        this.id = id;
        entityList[id] = this;
        resistance = new int[3];
        dmgPercentMod = new int[10];
        Arrays.fill(dmgPercentMod, 100);

        bounds = new Rect(0, 0, width, height);//default
    }

    public void die(){
        onDeath();
    }

    protected abstract void onDeath();


    public void receiveDamage(int num, int type){
        if(health <= 0)
            return;
        num *= dmgPercentMod[type]/100.f;
        if(type == Attack.Type.SPECIAL_IGNORE_DEFENCE)
            health -= num;
        else if(type == Attack.Type.PHYSICAL)
            applyDefaultDamageFormula(num, resistance[1]);
        else
            applyDefaultDamageFormula(num, resistance[2]);
        if(health <= 0){
            active = false;
            die();
        }
    }

    private void applyDefaultDamageFormula(int num, int resistance){
        int delta = (int) (num-4*Math.sqrt(resistance)+0.5);
        health -= num==0?0:Math.max(delta, 1);
    }

    public Rect getCollisionBounds(float xOffset, float yOffset){
        int left = (int)(x + bounds.left + xOffset);
        int top = (int)(y + bounds.top + yOffset);
        return new Rect(left, top, left+bounds.width(), top+bounds.height());
    }

    public boolean checkEntityCollision(float xOffset, float yOffset){
        for(int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++){ // this needs to be changed to a more efficient method
            Entity e = handler.getWorld().getEntityManager().getEntities().get(i);
            if(e.equals(this)){
                continue;
            }
            if(e.getCollisionBounds(0f, 0f).intersect(getCollisionBounds(xOffset, yOffset))){
                return true;
            }
        }
        return false;
    }

    //utilities

    protected boolean isInRange(Entity e, int distance){
        return Utils.getDistance(this, e) <= distance;
    }

    @NonNull
    @Override
    public Entity clone() {
        Entity result = null;
        try {
            result = (Entity)super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        assert result != null;
        return result;
    }

    public void initialize(Handler handler, float x, float y, int oX, int oY, int status){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.oX = oX;
        this.oY = oY;
        this.status = status;
    }

    public abstract Bitmap getTexture(int xSize, int ySize);

    public abstract String getName();

    protected void setEntityHealth(int health){
        maxHp = health;
        this.health = health;
    }

    //property method

    public boolean isBackground(){
        return false;
    }

    //Getters and Setters

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getId() {
        return id;
    }

    public int getFaction() {
        return faction;
    }

    public int getOX() {
        return oX;
    }

    public int getOY(){
        return oY;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDefence(){
        return resistance[Attack.Type.PHYSICAL];
    }

    public void setDefence(int defence) {
        resistance[Attack.Type.PHYSICAL] = defence;
    }

    public int getMagicalDefence(){
        return resistance[2];
    }

    public void setMagicalDefence(int defence){
        resistance[2] = defence;
    }

    public void changeDefence(int defenceValue) {
        resistance[Attack.Type.PHYSICAL] += defenceValue;
    }

    public void changeMagicalDefence(int defenceValue){
        resistance[2] += defenceValue;
    }

    public int getDmgModPercent(int type){
        return dmgPercentMod[type];
    }

    public void setDmgPercentMod(int percent, int type){
        dmgPercentMod[type] = percent;
    }
}
