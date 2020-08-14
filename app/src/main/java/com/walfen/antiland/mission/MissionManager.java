package com.walfen.antiland.mission;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.mission.killing.KillTracker;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.untils.Utils;


import java.util.ArrayList;

public class MissionManager implements TouchEventListener {

    private int misHeight, misWidth, xDispute, yDispute;
    private int misListCentreX, misListCentreY, misListSpacing;
    private int misTtlX, misTtlY;
    private int progressY;
    private int selectedMission, scroll;

    private Handler handler;
    private ArrayList<Mission> missions;
    private boolean active = false, buttonJustPressed = false;
    private final Bitmap missionScreen;
    private UIImageButton closeButton;

    public MissionManager(Handler handler){
        this.handler = handler;
        missions = new ArrayList<Mission>();
        missionScreen = ImageEditor.scaleBitmap(Assets.missionScreen,
                Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        misHeight = missionScreen.getHeight();
        misWidth = missionScreen.getWidth();
        xDispute = Constants.SCREEN_WIDTH/2 - misWidth/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - misHeight/2;
        misListCentreX = (int)(170.f/512*misWidth+xDispute);
        misListCentreY = (int)(46.f/384*misHeight+yDispute);
        misListSpacing = (int)(30.f/384*misHeight);
        misTtlX = (int)(415.f/512*misWidth+xDispute);
        misTtlY = (int)(70.f/384*misHeight+yDispute);
        progressY = (int)(190.f/384*misHeight+yDispute);
        scroll = 0;
        closeButton = new UIImageButton(xDispute+misWidth-Constants.UI_CLOSE_SIZE, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));
    }

    public void addMission(int missionID){
        addMission(Mission.missions[missionID]);
    }

    public void addMission(Mission mission){
        for(int i = 0; i < missions.size(); i++){
            if(missions.get(i).getId() == mission.getId()){
                return;
            }
        }
        mission.setHandler(handler);
        mission.setStatus(1);
        missions.add(mission);
    }

    @Override
    public void update(){

        for(int i = 0; i < missions.size(); i++){
            missions.get(i).update();
            if(missions.get(i).getStatus() == 2){
                missions.get(i).setHandler(null);
                missions.remove(i);
                i --;
            }
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        if(buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        closeButton.onTouchEvent(event);
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            selectedMission = computeSelectedMission(event.getX(pointerIndex), event.getY(pointerIndex));
        }
    }

    @Override
    public void draw(Canvas canvas){
        if(!active){
            return;
        }
        canvas.drawBitmap(missionScreen, null, new Rect
                (xDispute, yDispute, xDispute+misWidth, yDispute+misHeight), Constants.getRenderPaint());
        Paint paint = new Paint();
        paint.setTextSize(36);
        Rect r = new Rect();
        paint.setColor(Color.BLUE);
        String text;
        for(int i = 0; i < 11; i++){
            if(scroll+i >= missions.size())
                break;
            if(scroll + i == selectedMission){
                text = ">  "+missions.get(selectedMission).getTitle()+"  <";
            }else{
                text = missions.get(scroll + i).getTitle();
            }
            paint.getTextBounds(text, 0, text.length(), r);
            canvas.drawText(text, misListCentreX-r.width()/2.f, misListCentreY+i*misListSpacing+r.height()/2.f, paint);
        }
        closeButton.draw(canvas);
        if(selectedMission >= missions.size())
            return;
        //desc
        paint.setTextSize(28);
        ArrayList<String> desc = Utils.splitString(missions.get(selectedMission).getDesc(), 20);
        for(int i = 0; i < desc.size(); i++){
            text = desc.get(i);
            paint.getTextBounds(text, 0, text.length(), r);
            canvas.drawText(text, misTtlX-r.width()/2.f, misTtlY+((float)misListSpacing/2.f)*i+r.height()/2.f, paint);
        }

        //progress
        paint.setColor(Color.RED);
        for(int i = 0; i < missions.get(selectedMission).getFinalProgress().length; i++){ // 190
            text = missions.get(selectedMission).getProgress()[i]+" / "+
                    missions.get(selectedMission).getFinalProgress()[i];
            paint.getTextBounds(text, 0, text.length(), r);
            canvas.drawText(text, misTtlX-r.width()/2.f, progressY+((float)misListSpacing/2.f)*i+r.height()/2.f, paint);
        }

        //complete
        if(missions.get(selectedMission).isCompleted()){
            text = "Complete";
            paint.getTextBounds(text, 0, text.length(), r);
            canvas.drawText(text, misTtlX-r.width()/2.f, progressY+(70.f/384*misHeight)+r.height()/2.f, paint);
        }
    }

    public boolean hasMission(int missionId){
        for(Mission m: missions)
            if(m.getId() == missionId)
                return true;
        return false;
    }

    //ui
    private int computeSelectedMission(float x, float y){
        if(x < 20.f/512*misWidth+xDispute || x > 323.f/512*misWidth+xDispute)
            return scroll;
        if(y < 33.f/384*misHeight+yDispute || y > 367.f/384*misHeight+yDispute)
            return scroll;
        return Math.floorDiv((int)(y-(33.f/384*misHeight+yDispute)), misListSpacing) + scroll;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(){
        buttonJustPressed = true;
        active = !active;
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }
}
