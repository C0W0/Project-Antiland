package com.walfen.antiland.entities;


import android.graphics.Rect;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.active.Slime;
import com.walfen.antiland.entities.creatures.npc.NPC1;
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
    protected int health;
    protected int maxHP;
    protected boolean active;
    protected int faction;

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
        health = DEFAULT_HEALTH; //TODO: CHANGE THIS!!!
        maxHP = health;
        this.id = id;
        entityList[id] = this;

        bounds = new Rect(0, 0, width, height);//default
    }

    public abstract void die();


    public void receiveDamage(int num){
        health -= num;
        if(health <= 0){
            active = false;
            die();
        }
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

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getId() {
        return id;
    }

    public int getFaction() {
        return faction;
    }
}
