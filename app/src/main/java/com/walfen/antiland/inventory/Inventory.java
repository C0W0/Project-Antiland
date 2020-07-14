package com.walfen.antiland.inventory;


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
import com.walfen.antiland.items.Item;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.buttons.UIImageButton;

import java.util.ArrayList;

public class Inventory implements TouchEventListener {

    private Handler handler;
    private boolean active = false, buttonJustPressed = false;
    private ArrayList<Item> inventoryItems;
    int invHeight, invWidth;
    int xDispute, yDispute;
    private int selectedX = 0, selectedY = 0, scroll;
    int itemBaseX, itemBaseY, iconSize, itemDXConstant, itemDYConstant;
    private int invImageX, invImageY, invNameX, invNameY, numOffsetX, numOffsetY;
    private final Bitmap inventoryScreen;
    final Bitmap blueSquare;
    private UIImageButton useButton, fabSwitchButton, closeButton;

    public Inventory(Handler handler){
        this.handler = handler;
        inventoryItems = new ArrayList<>();
        inventoryScreen = ImageEditor.scaleBitmap(Assets.inventoryScreen,
                Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        invWidth = inventoryScreen.getWidth();
        invHeight = inventoryScreen.getHeight();
        itemDXConstant = (int)(41.f/512*invWidth);
        itemDYConstant = (int)(41.f/384*invHeight);
        scroll = 0; // WIP
        xDispute = Constants.SCREEN_WIDTH/2 - invWidth/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - invHeight/2;
        iconSize = Constants.iconSize;
        blueSquare = ImageEditor.scaleBitmap(Assets.blueSqr, iconSize+4);
        itemBaseX = (int)(54.f/512*invWidth+xDispute);
        itemBaseY = (int)(54.f/384*invHeight+yDispute);
        invImageX = (int)(363.f/512*invWidth+xDispute);
        invImageY = (int)(67.f/384*invHeight+yDispute);
        invNameX = (int)(378.f/512*invWidth+xDispute);
        invNameY = (int)(130.f/384*invHeight+yDispute);
        numOffsetX = (int)(36.f/512*invWidth);
        numOffsetY = (int)(36.f/384*invHeight);
        useButton = new UIImageButton(362.f/512*invWidth+xDispute, 146.f/384*invHeight+yDispute,
                (int) (32.f/512*invWidth), (int) (16.f/384*invHeight),
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::use);
        fabSwitchButton = new UIImageButton(xDispute, yDispute*2+invHeight-itemBaseY,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getFabricator().setActive());
        closeButton = new UIImageButton(xDispute*2+invWidth-itemBaseX, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> setActive(false));
    }

    @Override
    public void update(){
        if(!active){
            return;
        }

        for(int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).getCount() == 0) {
                inventoryItems.remove(i);
                i--;
            }
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        useButton.onTouchEvent(event);
        if(buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        fabSwitchButton.onTouchEvent(event);
        closeButton.onTouchEvent(event);
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
        event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            Point p = computeSelectedPoint(event.getX(pointerIndex), event.getY(pointerIndex));
            selectedX = p.x;
            selectedY = p.y;
        }
    }

    @Override
    public void draw(Canvas canvas){
        if(!active){
            return;
        }
        canvas.drawBitmap(inventoryScreen,null, new Rect
                (xDispute, yDispute, xDispute+invWidth, yDispute+invHeight), Constants.getRenderPaint());

        for(int y = scroll; y < scroll+7; y++){
            for(int x = 0; x < 5; x++){
                if(inventoryItems.size() <= y*5+x)
                    break;
                int left = x*itemDXConstant + itemBaseX;
                int top = (y-scroll)*itemDYConstant + itemBaseY;
                if(x == selectedX && y == selectedY)
                    canvas.drawBitmap(blueSquare, null,
                            new Rect(left-2, top-2, left+iconSize+4, top+iconSize+4),
                            Constants.getRenderPaint());
                canvas.drawBitmap(inventoryItems.get(y*5+x).getInvTexture(), null,
                        new Rect(left, top, left+iconSize, top+iconSize),
                        Constants.getRenderPaint());
                Paint paint = new Paint();
                paint.setTextSize(26);
                Rect r = new Rect();
                String count = Integer.toString(inventoryItems.get(y*5+x).getCount());
                paint.getTextBounds(count, 0, count.length(), r);
                paint.setColor(Color.BLACK);
                canvas.drawText(count, left+numOffsetX-r.width()-2, top+numOffsetY-2, paint);
            }
        }
        useButton.draw(canvas);
        fabSwitchButton.draw(canvas);
        closeButton.draw(canvas);
        if(inventoryItems.size() <= selectedY*5+selectedX)
            return;
        Item item = inventoryItems.get(selectedY*5+selectedX);
        canvas.drawBitmap(item.getInvTexture(), null, new Rect(
                invImageX, invImageY, invImageX+iconSize, invImageY+iconSize),Constants.getRenderPaint());
        Paint paint = new Paint();
        paint.setTextSize(34);
        Rect r = new Rect();
        String name = item.getName();
        paint.getTextBounds(name.toUpperCase(), 0, name.length(), r);
        paint.setColor(Color.BLACK);
        canvas.drawText(name, invNameX-r.width()/2.f, invNameY+r.height()/2.f, paint);
    }

    private int computeSelectedLocationX(float x){
        x = x-itemBaseX<0?0:x-itemBaseX;
        int i = Math.floorDiv((int)x, itemDXConstant);
        return i>4?0:i;
    }

    private int computeSelectedLocationY(float y){
        y = y-itemBaseY<0?0:y-itemBaseY;
        int i = Math.floorDiv((int)y, itemDYConstant);
        return (i>6?0:i)+scroll;
    }

    private Point computeSelectedPoint(float x, float y){
        x = Math.floorDiv((int)(x-itemBaseX), itemDXConstant);
        y = Math.floorDiv((int)(y-itemBaseY), itemDYConstant);
        if(x < 0 || x > 4 || y < 0 || y > 4)
            return new Point(0, scroll);
        return new Point((int)x, (int)y+scroll);
    }

    //inventory methods
    public void addItem(Item item){
        if(item.getHandler() == null)
            item.setHandler(handler);
        for(int i = 0; i < inventoryItems.size(); i++){
            Item tempItem = inventoryItems.get(i);
            if(tempItem.getId() == item.getId()){
                inventoryItems.get(i).setCount(tempItem.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
    }

    public void use(){
        buttonJustPressed = true;
        if(inventoryItems.size() <= selectedY*5+selectedX)
            return;
        inventoryItems.get(selectedY*5+selectedX).onActive();
    }

    public int getItemCount(int id){
        for(Item i : inventoryItems)
            if(i.getId() == id)
                return i.getCount();

        return 0;
    }

    public void deductItem(int id, int count){
        for(Item i : inventoryItems)
            if(i.getId() == id)
                i.setCount(i.getCount()-count);
    }
    //getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

    public ArrayList<Item> getInventoryItems() {
        return inventoryItems;
    }

    public void setActive(){
        buttonJustPressed = true;
        active = !active;
    }

    public void setActive(boolean active) {
        buttonJustPressed = true;
        this.active = active;
    }
}
