package com.walfen.antiland.entities.creatures;


import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.tiles.Tile;

import java.util.ArrayList;

public abstract class Creature extends Entity {

    //default values
    public static final float DEFAULT_SPEED = 12.0f;
    public static final int DEFAULT_MP = 0;
    public static final int DEFAULT_CREATURE_WIDTH = Constants.DEFAULT_SIZE;
    public static final int DEFAULT_CREATURE_HEIGHT = Constants.DEFAULT_SIZE;


    //creature status
    protected float speed;
    protected int physicalDamage, magicalDamage, level, mp, maxMp;
    protected ArrayList<StatusEffect> effects;
    protected boolean disable;


    protected float xMove, yMove; // movement

    public Creature(int width, int height, int id) {
        super(width, height, id);
        mp = DEFAULT_MP;
        maxMp = mp;
        physicalDamage = 0;
        magicalDamage = 0;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
        effects = new ArrayList<>();
        disable = false;
    }

    //methods
    public void move(){
        if(!checkEntityCollision(xMove, 0f)){
            moveX();
        }
        if(!checkEntityCollision(0f, yMove)){
            moveY();
        }

    }

    //collision detection
    public void moveX(){
        if(xMove > 0){//right

            int tx = (int) (x + xMove + bounds.left + bounds.width()) / Tile.TILEWIDTH;

            if(!collisionWithTile(tx,(int)(y+bounds.top) / Tile.TILEHEIGHT) &&
            !collisionWithTile(tx,(int)(y+bounds.top+bounds.height()) / Tile.TILEHEIGHT)){
                x += xMove;
            } else{
                x = tx*Tile.TILEWIDTH - bounds.left - bounds.width() - 1;
            }

        } else if(xMove < 0){//left

            int tx = (int) (x + xMove + bounds.left) / Tile.TILEWIDTH;

            if(!collisionWithTile(tx,(int)(y+bounds.top) / Tile.TILEHEIGHT) &&
                    !collisionWithTile(tx,(int)(y+bounds.top+bounds.height()) / Tile.TILEHEIGHT)){
                x += xMove;
            } else{
                x = tx*Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.left;
            }
        }
    }

    public void moveY(){
        if(yMove > 0){//down

            int ty = (int) (y + yMove + bounds.top + bounds.height()) / Tile.TILEHEIGHT;

            if(!collisionWithTile((int)(x+bounds.left) / Tile.TILEHEIGHT,ty) &&
                    !collisionWithTile((int)(x+bounds.left+bounds.width()) / Tile.TILEHEIGHT,ty)){
                y += yMove;
            } else{
               y = ty*Tile.TILEHEIGHT - bounds.top - bounds.height() - 1;
            }

        } else if(yMove < 0){//up

            int ty = (int) (y + yMove + bounds.top) / Tile.TILEWIDTH;

            if(!collisionWithTile((int)(x+bounds.left) / Tile.TILEHEIGHT,ty) &&
                    !collisionWithTile((int)(x+bounds.left+bounds.width()) / Tile.TILEHEIGHT,ty)){
                y += yMove;
            } else{
                y = ty*Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.top;
            }
        }
    }

    @Override
    public void update() {
        if(effects != null){
            for(int i = 0; i < effects.size(); i++){
                StatusEffect e = effects.get(i);
                if(!e.isValid()){
                    e.onEffectRemoved();
                    effects.remove(i);
                    i --;
                }
            }
        }
    }

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY, int status) {
        super.initialize(handler, x, y, oX, oY, status);
        effects = new ArrayList<>();
    }

    protected boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x,y).isBarrier();
    }

    //changers
    public void changeHealth(int deltaHealth){
        health = Math.min(health + deltaHealth, maxHp);
        if(health < 0)
            health = 0;
    }

    public void changeMaxHp(int deltaHealth){
        maxHp += deltaHealth;
    }

    public void changeSpeed(int deltaSpeed){
        speed += deltaSpeed;
    }

    public void changePhysicalDamage(int damage) {
        physicalDamage += damage;
    }

    public void changeMagicalDamage(int damage){
        magicalDamage += damage;
    }

    public void changeMaxMp(int deltaMp){
        maxMp += deltaMp;
    }

    public void changeMp(int deltaMp){
        mp =  Math.min(mp + deltaMp, maxMp);
        if(mp < 0)
            mp = 0;
    }

    //getters and setters
    public float getSpeed() {
        return speed;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public int getMagicalDamage() {
        return magicalDamage;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public void setMagicalDamage(int magicalDamage) {
        this.magicalDamage = magicalDamage;
    }

    public int getLevel() {
        return level;
    }

    public void addEffect(StatusEffect effect){
        effect.onEffectActive();
        effects.add(effect);
    }

    public ArrayList<StatusEffect> getEffects() {
        return effects;
    }

    public void disable(){
        disable = true;
    }

    public void enable(){
        disable = false;
    }
}
