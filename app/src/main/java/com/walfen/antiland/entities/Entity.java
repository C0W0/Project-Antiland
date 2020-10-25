package com.walfen.antiland.entities;


import android.graphics.Bitmap;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.IceSlime;
import com.walfen.antiland.entities.creatures.active.Slime;
import com.walfen.antiland.entities.creatures.npc.secondary.NPC1;
import com.walfen.antiland.entities.creatures.npc.trader.MushroomTrader;
import com.walfen.antiland.entities.creatures.npc.trader.WandererCrab;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.special.command.active.TempleBossCoffin;
import com.walfen.antiland.entities.special.command.passive.EntityGenerator;
import com.walfen.antiland.entities.special.command.passive.SlimeGenerator;
import com.walfen.antiland.entities.special.command.passive.WorldGate;
import com.walfen.antiland.entities.special.command.passive.tutorial.TutorialMessagers;
import com.walfen.antiland.entities.statics.AirWall;
import com.walfen.antiland.entities.statics.Tree;
import com.walfen.antiland.untils.Utils;

import java.util.Arrays;

public abstract class Entity implements GameHierarchyElement, Cloneable {

    /* Entity id:
    (UPDATE HERE)
    ------------------------
    Hostile/neutral: 101 ~ 400
    Slime: 201
    IceSlime: 202
    ------------------------
    NPC: 401 ~ 700
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        traders: 401 ~ 500
        WandererCrab (trade crab): 401
        MushroomTrader: 402
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        mission: 501 ~ 600
        NPC1 (mission crab): 501
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        other: 601 ~ 700
        ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ------------------------
    Static: 701 ~ 1000
    AirWall: 701
    Tree: 702
    ------------------------
    Special: 1001+
    WorldGate: 1001
    Tutorial messagers: 1002-1012
    SlimeSpawner: 1101
    TempleBossCoffin: 1301
     */

    public static Entity[] entityList = new Entity[2048];
    public static Slime slime = new Slime();
    public static IceSlime iceSlime = new IceSlime();
    public static Tree tree = new Tree();

    public static void initEntities(){
        new WandererCrab();
        new MushroomTrader();
        new NPC1();
        new AirWall();
        new WorldGate(1001, 1, 6656, 2304);
        new TutorialMessagers.TutorialMovement();
        new TutorialMessagers.TutorialAttack();
        new TutorialMessagers.TutorialPortal();
        new TutorialMessagers.TutorialUnleash();
        new SlimeGenerator();
        new TempleBossCoffin();
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

    public void initialize(Handler handler, float x, float y, int oX, int oY){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.oX = oX;
        this.oY = oY;
    }

    public abstract Bitmap getTexture(int xSize, int ySize);

    public abstract String getName();

    protected void setEntityHealth(int health){
        maxHp = health;
        this.health = health;
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
