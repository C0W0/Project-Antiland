package com.walfen.antiland.entities;


import android.graphics.Rect;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.Slime;
import com.walfen.antiland.entities.creatures.npc.NPC1;
import com.walfen.antiland.entities.properties.attack.Attacks;
import com.walfen.antiland.entities.statics.AirWall;
import com.walfen.antiland.entities.statics.Tree;
import com.walfen.antiland.untils.Utils;

public abstract class Entity implements GameHierarchyElement, Cloneable {

    public static Entity[] entityList = new Entity[256];
    public static AirWall airWall = new AirWall();
    public static Tree tree = new Tree();
    public static Slime slime = new Slime();
    public static NPC1 crab = new NPC1();

    //Entities
    public static final int DEFAULT_HEALTH = 10;
    protected int health, maxHp;
    protected boolean active;
    protected int faction;
    protected int[] resistance;
    protected int[] dmgPercentMod;

    protected float x,y;
    protected int oX, oY; // o stands for offset
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
        resistance = new int[10];
        dmgPercentMod = new int[10];
        for(int i = 0; i < 10; i++){
            resistance[i] = 0;
            dmgPercentMod[i] = 100;
        }

        bounds = new Rect(0, 0, width, height);//default
    }

    public abstract void die();


    public void receiveDamage(int num, int type){
        num *= dmgPercentMod[type]/100.f;
        if(type == Attacks.Type.SPECIAL_IGNORE_DEFENCE)
            health -= num;
        else
            applyDefaultDamageFormula(num, resistance[type]);
        if(health <= 0){
            active = false;
            die();
        }
    }

    private void applyDefaultDamageFormula(int num, int resistance){
        int delta = (int) (num-4*Math.sqrt(resistance)+0.5);
        health -= Math.max(delta, 1);
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
        return resistance[Attacks.Type.PHYSICAL];
    }

    public void setDefence(int defence) {
        resistance[Attacks.Type.PHYSICAL] = defence;
    }

    public void changeDefence(int defenceValue) {
        resistance[Attacks.Type.PHYSICAL] += defenceValue;
    }
}
