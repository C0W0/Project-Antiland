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
    private ArrayList<Entity> addingQueue;
    private ArrayList<Integer> modifyingQueue;
    private ArrayList<String> modifyingKey;

    private Comparator<Entity> renderComparator = (a, b) -> {
        if(a.isBackground() && !b.isBackground())
            return -1;
        if (!b.isBackground() && a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
            return -1;
        }
        return 1;
    };


    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        addingQueue = new ArrayList<>();
        modifyingQueue = new ArrayList<>();
        modifyingKey = new ArrayList<>();
        addEntity(player);
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    @Override
    public void update(){

        if(addingQueue.size() > 0){
            entities.addAll(addingQueue);
            addingQueue = new ArrayList<>();
        }

        if(modifyingQueue.size() > 0){
            for(int i = 0; i < modifyingQueue.size(); i++){
                for(Entity e: entities)
                    if(e.getName().equals(modifyingKey.get(i)))
                        e.setStatus(modifyingQueue.get(i));
            }
        }

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
            if(r.width() != 0 && (r.right - xOffset < -512 || r.left - xOffset > Constants.SCREEN_WIDTH+512 ||
            r.bottom - yOffset < -512 || r.top - yOffset > Constants.SCREEN_HEIGHT+512)){
                continue;
            }
            e.draw(canvas);
        }
    }

    //getters and setters
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntityHot(Entity e){
        addingQueue.add(e);
    }

    public void modifyEntityHot(String key, int status){
        modifyingKey.add(key);
        modifyingQueue.add(status);
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
