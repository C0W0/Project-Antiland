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
    private ArrayList<Item> inventoryItems, shopKeeperItems;
    private ArrayList<Item> originalInventoryItems, originalShopKeeperItems;
    private ArrayList<Item> buyArray, sellArray;


    private boolean active = false, buttonJustPressed = false, dragging = false;
    private int tradeHeight, tradeWidth;
    private int itemBaseX, itemBaseY, iconSize, itemDXConstant, itemDYConstant;
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
        //ImageEditor.scaleBitmap(Assets.skillScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        tradeWidth = trade.getWidth();
        tradeHeight = trade.getHeight();
        itemDXConstant = (int) (41.f / 512 * tradeWidth);
        itemDYConstant = (int) (41.f / 384 * tradeHeight);
        xDispute = Constants.SCREEN_WIDTH / 2 - tradeWidth / 2;
        yDispute = Constants.SCREEN_HEIGHT / 2 - tradeHeight / 2;
        iconSize = Constants.iconSize;
        blueSquare = ImageEditor.scaleBitmap(Assets.blueSqr, iconSize+4);
        itemBaseX = (int) (54.f / 512 * tradeWidth + xDispute);
        itemBaseY = (int) (54.f / 384 * tradeHeight + yDispute);
        invImageX = (int) (363.f / 512 * tradeWidth + xDispute);
        invImageY = (int) (67.f / 384 * tradeHeight + yDispute);
        invNameX = (int) (378.f / 512 * tradeWidth + xDispute);
        invNameY = (int) (130.f / 384 * tradeHeight + yDispute);
        numOffsetX = (int) (36.f / 512 * tradeWidth);
        numOffsetY = (int) (36.f / 384 * tradeHeight);

        selectedItem = null;

        confirmButton = new UIImageButton(362.f / 512 * tradeWidth + xDispute, 146.f / 384 * tradeHeight + yDispute,
                (int) (32.f / 512 * tradeWidth), (int) (16.f / 384 * tradeHeight),
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> confirmTrade());
        revertButton = new UIImageButton(xDispute, yDispute * 2 + tradeHeight - itemBaseY,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> revertTrade());
        closeButton = new UIImageButton(xDispute * 2 + tradeWidth - itemBaseX, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> closeShop());
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
            selectedItem = inventoryItems.get(p.y*5+p.x);
        }
        //WIP
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);
            selectedX = (int) touchX;
            selectedY = (int) touchY;
            dragging= true;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);

            Point p = computeSelectedPoint(touchX, touchY);
            selectedX = p.x;
            selectedY = p.y;
            dragging= false;
        }
    }

    @Override
    public void update() {
        if (!active) {
            return;
        }
        if(inventoryItems.size() > selectedY*5+selectedX) {
            selectedItem = inventoryItems.get(selectedY * 5 + selectedX);
        }else {
            selectedItem = null;
        }

        for(int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).getCount() == 0) {
                inventoryItems.remove(i);
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
                (xDispute, yDispute, xDispute + tradeWidth, yDispute + tradeHeight), Constants.getRenderPaint());

        //Draw items and item count
        //NOTE: NUMBERS FOR THE INVENTORY SLOTS ARE NOT YET CONFIRMED
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 5; x++) {
                if (inventoryItems.size() <= y * 5 + x)
                    break;
                int left = x * itemDXConstant + itemBaseX;
                int top = (y) * itemDYConstant + itemBaseY;
                if (x == selectedX && y == selectedY)
                    canvas.drawBitmap(blueSquare, null,
                            new Rect(left - 2, top - 2, left + iconSize + 4, top + iconSize + 4),
                            Constants.getRenderPaint());
                canvas.drawBitmap(inventoryItems.get(y * 5 + x).getInvTexture(), null,
                        new Rect(left, top, left + iconSize, top + iconSize),
                        Constants.getRenderPaint());
                Paint paint = new Paint();
                paint.setTextSize(26);
                Rect r = new Rect();
                String count = Integer.toString(inventoryItems.get(y * 5 + x).getCount());
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
        if(dragging){
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
        else
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
        int x = Math.floorDiv((int)(oX-itemBaseX), itemDXConstant);
        int y = Math.floorDiv((int)(oY-itemBaseY), itemDYConstant);
        if(x < 0 || x > 4 || y < 0 || y > 4){
            return null;
        }
        return new Point((int)x, (int)y+scroll);
    }

    public void confirmTrade() {
        selectedItem = null;
        for(Item e:buyArray){
            handler.getPlayer().getInventory().addItem(Item.items[e.getId()].addToInv(e.getCount()));
        }
        buyArray = null;
        for(int i = 0; i < sellArray.size(); i++) {
            boolean hasItem=false;
            Item item = sellArray.get(i);
            for (int j = 0; i < shopKeeperItems.size(); j++) {
                Item tempItem = shopKeeperItems.get(j);
                if (tempItem.getId() == item.getId()) {
                    shopKeeperItems.get(i).setCount(tempItem.getCount() + item.getCount());
                    hasItem=true;
                    break;
                }
            }
            if(!hasItem)
                inventoryItems.add(item);
        }
        sellArray = null;
        originalInventoryItems = (ArrayList<Item>) inventoryItems.clone();
        originalShopKeeperItems = (ArrayList<Item>) shopKeeperItems.clone();
    }

    public void revertTrade() {
        selectedItem = null;
        buyArray = null;
        sellArray = null;
        inventoryItems = originalInventoryItems;
        shopKeeperItems = originalShopKeeperItems;
    }

    public void openShop(ArrayList<Item> shopItems) {
        shopKeeperItems=shopItems;
        buttonJustPressed = true;
        originalInventoryItems= (ArrayList<Item>) inventoryItems.clone();
        originalShopKeeperItems= (ArrayList<Item>) shopKeeperItems.clone();
        active = true;

    }

    public void closeShop() {
        active=false;
    }
}
