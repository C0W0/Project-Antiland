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

public class Trade implements TouchEventListener {

    private Handler handler;
    //POINTERS
    private ArrayList<Item> inventoryItems, shopKeeperItems;
    //ACTUAL ARRAY DATA STUFF
    private ArrayList<Item> tempInventoryItems, tempShopKeeperItems;


    private boolean active = false, buttonJustPressed = false;
    private final int SLOTWIDTH=4, SLOTHEIGHT=6;
    private int tradeScreenHeight, tradeScreenWidth;
    private int invBaseX, invBaseY, shopBaseX, shopBaseY, iconSize, itemDXConstant, itemDYConstant;
    private int invImageX, invImageY, invNameX, invNameY, numOffsetX, numOffsetY;
    private int selectedX = 0, selectedY = 0, scroll = 0;
    private int xDispute, yDispute;
    private final Bitmap trade;

    private final Bitmap blueSquare;

    private UIImageButton confirmButton, revertButton, closeButton;

    private Item selectedItem;

    public Trade(Handler handler) {
        this.handler = handler;
        inventoryItems= handler.getPlayer().getInventory().getInventoryItems();
        trade = null;
        ImageEditor.scaleBitmap(Assets.tradeScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        tradeScreenWidth = trade.getWidth();
        tradeScreenHeight = trade.getHeight();
        itemDXConstant = (int) (41.f / 512 * tradeScreenWidth);
        itemDYConstant = (int) (41.f / 384 * tradeScreenHeight);
        xDispute = Constants.SCREEN_WIDTH / 2 - tradeScreenWidth / 2;
        yDispute = Constants.SCREEN_HEIGHT / 2 - tradeScreenHeight / 2;
        iconSize = Constants.iconSize;
        blueSquare = ImageEditor.scaleBitmap(Assets.blueSqr, iconSize+4);
        invBaseX = (int) (54.f / 512 * tradeScreenWidth + xDispute);
        invBaseY = (int) (54.f / 384 * tradeScreenHeight + yDispute);
        invImageX = (int) (363.f / 512 * tradeScreenWidth + xDispute);
        invImageY = (int) (67.f / 384 * tradeScreenHeight + yDispute);
        invNameX = (int) (378.f / 512 * tradeScreenWidth + xDispute);
        invNameY = (int) (130.f / 384 * tradeScreenHeight + yDispute);
        numOffsetX = (int) (36.f / 512 * tradeScreenWidth);
        numOffsetY = (int) (36.f / 384 * tradeScreenHeight);

        selectedItem = null;

        confirmButton = new UIImageButton(362.f / 512 * tradeScreenWidth + xDispute, 146.f / 384 * tradeScreenHeight + yDispute,
                (int) (32.f / 512 * tradeScreenWidth), (int) (16.f / 384 * tradeScreenHeight),
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::confirmTrade);
        revertButton = new UIImageButton(xDispute, yDispute * 2 + tradeScreenHeight - invBaseY,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::revertTrade);
        closeButton = new UIImageButton(xDispute * 2 + tradeScreenWidth - invBaseX, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::closeShop);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (!active)
            return;
        if (buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        confirmButton.onTouchEvent(event);
        revertButton.onTouchEvent(event);
        closeButton.onTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);
            Point p = computeSelectedPoint(touchX, touchY);
            selectedX = p.x;
            selectedY = p.y;
            selectedItem = inventoryItems.get(p.y* SLOTWIDTH +p.x);
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);

            Point p = computeSelectedPoint(touchX, touchY);
            selectedX = p.x;
            selectedY = p.y;
        }
    }

    @Override
    public void update() {
        if (!active) {
            return;
        }
        if(tempInventoryItems.size() > selectedY* SLOTWIDTH +selectedX) {
            selectedItem = tempInventoryItems.get(selectedY * SLOTWIDTH + selectedX);
        }else {
            selectedItem = null;
        }

        for(int i = 0; i < tempInventoryItems.size(); i++) {
            if (tempInventoryItems.get(i).getCount() == 0) {
                tempInventoryItems.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!active) {
            return;
        }
        //Draw trade screen
        canvas.drawBitmap(trade, null, new Rect
                (xDispute, yDispute, xDispute + tradeScreenWidth, yDispute + tradeScreenHeight), Constants.getRenderPaint());

        //Draw items and item count

        //Draw Inventory
        for (int y = 0; y < SLOTHEIGHT; y++) {
            for (int x = 0; x < SLOTWIDTH; x++) {
                if (tempInventoryItems.size() <= y * SLOTWIDTH + x)
                    break;
                int left = x * itemDXConstant + invBaseX;
                int top = y * itemDYConstant + invBaseY;
                if (x == selectedX && y == selectedY)
                    canvas.drawBitmap(blueSquare, null,
                            new Rect(left - 2, top - 2, left + iconSize + 4, top + iconSize + 4),
                            Constants.getRenderPaint());
                canvas.drawBitmap(tempInventoryItems.get(y * SLOTWIDTH + x).getInvTexture(), null,
                        new Rect(left, top, left + iconSize, top + iconSize),
                        Constants.getRenderPaint());
                Paint paint = new Paint();
                paint.setTextSize(26);
                Rect r = new Rect();
                String count = Integer.toString(tempInventoryItems.get(y * SLOTWIDTH + x).getCount());
                paint.getTextBounds(count, 0, count.length(), r);
                paint.setColor(Color.BLACK);
                canvas.drawText(count, left + numOffsetX - r.width() - 2, top + numOffsetY - 2, paint);
            }
        }

        //Draw Shop
        for (int y = 0; y < SLOTHEIGHT; y++) {
            for (int x = 0; x < SLOTWIDTH; x++) {
                if (tempShopKeeperItems.size() <= y * SLOTWIDTH + x)
                    break;
                int left = x * itemDXConstant + shopBaseX;
                int top = (y) * itemDYConstant + shopBaseY;
                if (x == selectedX && y == selectedY)
                    canvas.drawBitmap(blueSquare, null,
                            new Rect(left - 2, top - 2, left + iconSize + 4, top + iconSize + 4),
                            Constants.getRenderPaint());
                canvas.drawBitmap(tempShopKeeperItems.get(y * 5 + x).getInvTexture(), null,
                        new Rect(left, top, left + iconSize, top + iconSize),
                        Constants.getRenderPaint());
                Paint paint = new Paint();
                paint.setTextSize(26);
                Rect r = new Rect();
                String count = Integer.toString(tempShopKeeperItems.get(y * 5 + x).getCount());
                paint.getTextBounds(count, 0, count.length(), r);
                paint.setColor(Color.BLACK);
                canvas.drawText(count, left + numOffsetX - r.width() - 2, top + numOffsetY - 2, paint);
            }
        }

        //Draw buttons
        confirmButton.draw(canvas);
        revertButton.draw(canvas);
        closeButton.draw(canvas);

        //Draw selected item while selected
        if (selectedItem == null)
            return;
        /*if(dragging){
            canvas.drawBitmap(selectedItem.getInvTexture(), null, new Rect(
                    selectedX, selectedY, selectedX + iconSize, selectedY + iconSize), Constants.getRenderPaint());
            Paint paint = new Paint();
            paint.setTextSize(34);
            Rect r = new Rect();
            String name = selectedItem.getName();
            paint.getTextBounds(name.toUpperCase(), 0, name.length(), r);
            paint.setColor(Color.BLACK);
            canvas.drawText(name, selectedX - r.width() / 2.f, selectedY + r.height() / 2.f, paint);
        }
        else*/
        canvas.drawBitmap(selectedItem.getInvTexture(), null, new Rect(
                invImageX, invImageY, invImageX + iconSize, invImageY + iconSize), Constants.getRenderPaint());
        Paint paint = new Paint();
        paint.setTextSize(34);
        Rect r = new Rect();
        String name = selectedItem.getName();
        paint.getTextBounds(name.toUpperCase(), 0, name.length(), r);
        paint.setColor(Color.BLACK);
        canvas.drawText(name, invNameX - r.width() / 2.f, invNameY + r.height() / 2.f, paint);
    }

   //Well make this when we get the trade screen
    private Point computeSelectedPoint(float oX, float oY){
        int x = Math.floorDiv((int)(oX- invBaseX), itemDXConstant);
        int y = Math.floorDiv((int)(oY-invBaseY), itemDYConstant);
        if(x < 0 || x > 4 || y < 0 || y > 4){
            return null;
        }
        return new Point((int)x, (int)y+scroll);
    }

    public void confirmTrade() {
        selectedItem = null;
        handler.getPlayer().getInventory().setInventoryItems((ArrayList<Item>) tempInventoryItems.clone());
        shopKeeperItems= (ArrayList<Item>) tempShopKeeperItems.clone();
    }

    public void revertTrade() {
        selectedItem = null;
        tempInventoryItems= (ArrayList<Item>) handler.getPlayer().getInventory().getInventoryItems().clone();
        tempShopKeeperItems= (ArrayList<Item>) shopKeeperItems.clone();
    }

    public void openShop(ArrayList<Item> shopItems) {
        buttonJustPressed = true;
        shopKeeperItems=shopItems;
        tempInventoryItems = (ArrayList<Item>) inventoryItems.clone();
        tempShopKeeperItems = (ArrayList<Item>) shopKeeperItems.clone();
        active = true;

    }

    public void closeShop() {
        active=false;
    }
}
