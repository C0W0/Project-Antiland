package com.walfen.antiland.entities.creatures;


import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.tiles.Tile;

public abstract class Creature extends Entity {

    //default values
    public static final float DEFAULT_SPEED = 12.0f;
    public static final int DEFAULT_CREATURE_WIDTH = Constants.DEFAULT_SIZE;
    public static final int DEFAULT_CREATURE_HEIGHT = Constants.DEFAULT_SIZE;


    //creatures
    protected float speed;

    protected float xMove, yMove; // movement

    public Creature(int width, int height, int id) {
        super(width, height, id);
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
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

    protected boolean collisionWithTile(int x, int y){
        return handler.getWorld().getTile(x,y).isBarrier();
    }

    public void changeHealth(int deltaHealth){
        health = Math.min(health + deltaHealth, maxHP);
    }

    //getters and setters
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
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
}
