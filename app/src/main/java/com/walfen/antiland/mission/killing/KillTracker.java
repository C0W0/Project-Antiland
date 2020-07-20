package com.walfen.antiland.mission.killing;

import android.graphics.Canvas;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;

import java.util.ArrayList;

public class KillTracker implements GameHierarchyElement {

    private ArrayList<Entity> trackingEntities;
    private ArrayList<Long> trackingTime;
    private ArrayList<Integer> trackingTargetId, trackingTargetCount;
    private Entity topEntity;
    private Handler handler;

    private final long TRACK_TIME = 5*1000;

    public KillTracker(Handler handler){
        this.handler = handler;
        trackingEntities = new ArrayList<>();
        trackingTime = new ArrayList<>();
        trackingTargetId = new ArrayList<>();
        trackingTargetCount = new ArrayList<>();
    }

    @Override
    public void update() {
        if(trackingEntities.size() > 0)
            topEntity = trackingEntities.get(trackingEntities.size()-1);
        else
            topEntity = null;
        for(int i = 0; i < trackingTime.size(); i++){
            Entity e = trackingEntities.get(i);
            if(e.getHealth() == 0){
                for(int j = 0; j < trackingTargetId.size(); j++){
                    if(e.getId() == trackingTargetId.get(j)){
                        int c = trackingTargetCount.get(j);
                        trackingTargetCount.set(j, c+1);
                        break;
                    }
                }
                trackingEntities.remove(i);
                trackingTime.remove(i);
                i--;
                continue;
            }
            if(System.currentTimeMillis() - trackingTime.get(i) >= TRACK_TIME){
                trackingTime.remove(i);
                trackingEntities.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(topEntity == null)
            return;
    }

    public void addTracking(Entity e){
        if(trackingEntities.contains(e)){
            int i = trackingEntities.indexOf(e);
            trackingTime.set(i, System.currentTimeMillis());
        }else {
            trackingEntities.add(e);
            trackingTime.add(System.currentTimeMillis());
        }
    }

    public int trackKillCount(int id){
        if(trackingTargetId.contains(id)){
            int i = trackingTargetId.indexOf(id);
            return trackingTargetCount.get(i);
        }
        trackingTargetId.add(id);
        trackingTargetCount.add(0);
        return 0;
    }

    public int getTrackingCount(int id){
        int i = trackingTargetId.indexOf(id);
        return trackingTargetCount.get(i);
    }

    public void resetKillTracking(){
        trackingTargetId = new ArrayList<>();
        trackingTargetCount = new ArrayList<>();
    }
}
