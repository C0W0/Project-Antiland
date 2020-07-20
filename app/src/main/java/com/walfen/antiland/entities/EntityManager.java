package com.walfen.antiland.entities;




import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class EntityManager implements GameHierarchyElement {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;

    private Comparator<Entity> renderComparator = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            if(a.getY() + a.getHeight() < b.getY() + b.getHeight()){
                return -1;
            }
            return 1;
        }
    };


    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<Entity>();
        addEntity(player);
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    @Override
    public void update(){
        Iterator<Entity> it = entities.iterator();

        while(it.hasNext()){
            Entity e = it.next();
            e.update();
            if(!e.isActive()){
                handler.getWorld().removeLocationEntity((int)(e.getX()),(int)(e.getY()), 0);
                it.remove();
            }
        }
        entities.sort(renderComparator);

    }

    @Override
    public void draw(Canvas canvas) {
        for(Entity e: entities) {
            Rect r = e.getCollisionBounds(0 ,0);
            int xOffset = (int) handler.getGameCamera().getxOffset();
            int yOffset = (int) handler.getGameCamera().getyOffset();
            if(r.right - xOffset < -128 || r.left - xOffset > Constants.SCREEN_WIDTH+128 ||
            r.bottom - yOffset < -128 || r.top - yOffset > Constants.SCREEN_HEIGHT+128){
                continue;
            }
            e.draw(canvas);
        }
    }

    //getters and setters
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
