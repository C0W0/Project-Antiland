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
import com.walfen.antiland.entities.creatures.npc.trader.Trader;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.ui.TouchEventListener;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.slider.Slider;
import com.walfen.antiland.ui.slider.SliderAdjuster;

import java.util.ArrayList;

public class Trade implements TouchEventListener {

    private Handler handler;
    //POINTERS
    private ArrayList<Item> inventoryItems, shopKeeperItems;
    private Trader currentTrader;
    //ACTUAL ARRAY DATA STUFF
    private ArrayList<Item> tempInventoryItems, tempShopKeeperItems;


    private boolean active = false, buttonJustPressed = false,
            dragging = false, selling = false;
    private final int SLOTWIDTH = 4, SLOTHEIGHT = 6;
    private int tradeScreenHeight, tradeScreenWidth;
    private int invBaseX, invBaseY, shopBaseX, shopBaseY, iconSize, itemDXConstant, itemDYConstant;
    private int invImageX, invImageY, invNameX, invNameY, numOffsetX, numOffsetY;
    private int selectedX = 0, selectedY = 0, scrollP = 0, scrollS = 0;
    private int fingerX, fingerY;
    private int xDispute, yDispute;
    private final Bitmap trade;
    private Rect playerInvRect, shopRect;

    private final Bitmap blueSquare;

    private TextButton confirmButton, revertButton;
    private UIImageButton closeButton;

    private TradePanel panel;

    private Item selectedItem;

    public Trade(Handler handler, Inventory inventory) {
        this.handler = handler;
        inventoryItems = inventory.getInventoryItems();
        trade = ImageEditor.scaleBitmap(Assets.tradeScreen, Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        panel = new TradePanel();
        tradeScreenWidth = trade.getWidth();
        tradeScreenHeight = trade.getHeight();
        itemDXConstant = (int) (41.f / 512 * tradeScreenWidth);
        itemDYConstant = (int) (41.f / 384 * tradeScreenHeight);
        xDispute = Constants.SCREEN_WIDTH / 2 - tradeScreenWidth / 2;
        yDispute = Constants.SCREEN_HEIGHT / 2 - tradeScreenHeight / 2;
        iconSize = Constants.iconSize;
        blueSquare = ImageEditor.scaleBitmap(Assets.blueSqr, iconSize+4);
        invBaseX = (int) (38.f / 512 * tradeScreenWidth + xDispute);
        invBaseY = (int) (117.f / 384 * tradeScreenHeight + yDispute);
        shopBaseX = (int) (322.f / 512 * tradeScreenWidth + xDispute);
        shopBaseY = (int) (114.f / 384 * tradeScreenHeight + yDispute);
        invImageX = (int) (242.f / 512 * tradeScreenWidth + xDispute);
        invImageY = (int) (128.f / 384 * tradeScreenHeight + yDispute);
        invNameX = (int) (257.f / 512 * tradeScreenWidth + xDispute);
        invNameY = (int) (192.f / 384 * tradeScreenHeight + yDispute);
        numOffsetX = (int) (36.f / 512 * tradeScreenWidth);
        numOffsetY = (int) (36.f / 384 * tradeScreenHeight);
        playerInvRect = new Rect((int)(33.f/512*tradeScreenWidth+xDispute),
                (int)(112.f/384*tradeScreenHeight+yDispute),
                (int)(196.f/512*tradeScreenWidth+xDispute),
                (int)(356.f/384*tradeScreenHeight+yDispute));
        shopRect = new Rect((int)(317.f/512*tradeScreenWidth+xDispute),
                (int)(109.f/384*tradeScreenHeight+yDispute),
                (int)(480.f/512*tradeScreenWidth+xDispute),
                (int)(353.f/384*tradeScreenHeight+yDispute));

        selectedItem = null;

        int buttonCentreX = (int) (256.f / 512 * tradeScreenWidth + xDispute);

        confirmButton = new TextButton(buttonCentreX, 304.f/384*tradeScreenHeight+yDispute, 35,
                "Confirm", Color.BLACK, this::confirmTrade);
        revertButton = new TextButton(buttonCentreX-1, 335.f/384*tradeScreenHeight+yDispute, 35,
                "Revert", Color.BLACK, this::revertTrade);
        closeButton = new UIImageButton(xDispute * 2 + tradeScreenWidth - invBaseX, yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::closeShop);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if (!active)
            return;
        if(panel.isActive()){
            panel.onTouchEvent(event);
            buttonJustPressed = true;
            return;
        }
        closeButton.onTouchEvent(event);
        if (buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        confirmButton.onTouchEvent(event);
        revertButton.onTouchEvent(event);

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            float touchX = event.getX(pointerIndex);
            float touchY = event.getY(pointerIndex);
            Point p = computeSlotPosition(touchX, touchY);
            selectedX = p.x;
            selectedY = p.y;
            dragging = true;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            int eX = (int) event.getX(pointerIndex);
            int eY = (int) event.getY(pointerIndex);
            if(!new Rect(confirmButton.getBounds()).contains(eX, eY) &&
            !new Rect(revertButton.getBounds()).contains(eX, eY)){
                fingerX = eX;
                fingerY = eY;
            }
        }

        if (event.getActionMasked() == MotionEvent.ACTION_UP ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            dragging = false;
            fingerX = 0;
            fingerY = 0;
            onRelease((int)event.getX(pointerIndex), (int)event.getY(pointerIndex));
        }
    }

    @Override
    public void update() {
        if (!active) {
            return;
        }
        if(panel.isActive())
            panel.update();

        if(selectedY >= 6){
            int selectionIndex = (selectedY-6)* SLOTWIDTH +selectedX;
            if(tempShopKeeperItems.size() > selectionIndex) {
                selectedItem = tempShopKeeperItems.get(selectionIndex);
                selling = false;
            }else {
                selectedItem = null;
            }
        }else {
            int selectionIndex = selectedY* SLOTWIDTH +selectedX;
            if(tempInventoryItems.size() > selectionIndex) {
                selectedItem = tempInventoryItems.get(selectionIndex);
                selling = true;
            }else {
                selectedItem = null;
            }
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
        if (!active || tempInventoryItems == null || tempShopKeeperItems == null) {
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
                int top = (y+scrollP) * itemDYConstant + invBaseY;
                int fingerOffsetX = 0, fingerOffsetY = 0;
                if (x == selectedX && y == selectedY){
                    if(dragging){
                        fingerOffsetX = fingerX-left;
                        fingerOffsetY = fingerY-top;
                    }else {
                        canvas.drawBitmap(blueSquare, null,
                                new Rect(left - 2, top - 2, left + iconSize + 4, top + iconSize + 4),
                                Constants.getRenderPaint());
                    }
                }
                canvas.drawBitmap(tempInventoryItems.get(y * SLOTWIDTH + x).getInvTexture(), null,
                        new Rect(left+fingerOffsetX, top+fingerOffsetY, left+iconSize+fingerOffsetX,
                                top+iconSize+fingerOffsetY), Constants.getRenderPaint());
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
                int top = (y+scrollS) * itemDYConstant + shopBaseY;
                int fingerOffsetX = 0, fingerOffsetY = 0;
                if (x == selectedX && y+6 == selectedY){
                    if(dragging){
                        fingerOffsetX = fingerX-left;
                        fingerOffsetY = fingerY-top;
                    }else {
                        canvas.drawBitmap(blueSquare, null,
                                new Rect(left - 2, top - 2, left + iconSize + 4, top + iconSize + 4),
                                Constants.getRenderPaint());
                    }
                }
                canvas.drawBitmap(tempShopKeeperItems.get(y * SLOTWIDTH + x).getInvTexture(), null,
                        new Rect(left+fingerOffsetX, top+fingerOffsetY, left+iconSize+fingerOffsetX,
                                top+iconSize+fingerOffsetY), Constants.getRenderPaint());
                Paint paint = new Paint();
                paint.setTextSize(26);
                Rect r = new Rect();
                String count = Integer.toString(tempShopKeeperItems.get(y * SLOTWIDTH + x).getCount());
                paint.getTextBounds(count, 0, count.length(), r);
                paint.setColor(Color.BLACK);
                canvas.drawText(count, left + numOffsetX - r.width() - 2, top + numOffsetY - 2, paint);
            }
        }

        //Draw buttons
        confirmButton.draw(canvas);
        revertButton.draw(canvas);
        closeButton.draw(canvas);


        if (selectedItem != null){
            canvas.drawBitmap(selectedItem.getInvTexture(), null, new Rect(
                    invImageX, invImageY, invImageX + iconSize, invImageY + iconSize), Constants.getRenderPaint());

            //draw name, effect
            Paint paint = new Paint();
            paint.setTextSize(36);
            Rect r = new Rect();
            String token = selectedItem.getName();
            paint.getTextBounds(token, 0, token.length(), r);
            paint.setColor(Color.BLACK);
            paint.setFakeBoldText(true);
            int bottomLine = (int) (invNameY + r.height() / 2.f);
            canvas.drawText(token, invNameX - r.width() / 2.f, bottomLine, paint);
            bottomLine += 20;
            paint.setFakeBoldText(false);
            paint.setTextSize(32);
            paint.setColor(Color.MAGENTA);
            token = selectedItem.getEffect();
            paint.getTextBounds(token, 0, token.length(), r);
            canvas.drawText(token, invNameX - r.width() / 2.f, bottomLine + r.height(), paint);
        }

        if(panel.isActive())
            panel.draw(canvas);
    }

    private void onRelease(int x, int y){
        if((selling && shopRect.contains(x, y)) ||
                (!selling && playerInvRect.contains(x, y))){
            panel.openPanel(selectedItem);
        }
    }

    private Point computeSlotPosition(float oX, float oY){
        int x = Math.floorDiv((int)(oX-invBaseX), itemDXConstant);
        int y = Math.floorDiv((int)(oY-invBaseY), itemDYConstant);
        if(x < 0 || y < 0 || y >= 6)
            return new Point(0, 0);
        if(x > 3){
            x = Math.floorDiv((int)(oX-shopBaseX), itemDXConstant);
            if(x < 0 || x > 3)
                return new Point(0, 0);
            y += 6;
        }
        return new Point(x, y+(y>5?scrollS:scrollP));
    }

    public void confirmTrade() {
        selectedItem = null;
        handler.getPlayer().getInventory().setInventoryItems(cloneItemArrayList(tempInventoryItems));
        currentTrader.setTraderInventory(cloneItemArrayList(tempShopKeeperItems));
    }

    public void revertTrade() {
        selectedItem = null;
        tempInventoryItems = cloneItemArrayList(inventoryItems);
        tempShopKeeperItems = cloneItemArrayList(shopKeeperItems);
    }

    public void openShop(Trader trader) {
        buttonJustPressed = true;
        currentTrader = trader;
        shopKeeperItems = trader.getTraderInventory();
        inventoryItems = handler.getPlayer().getInventory().getInventoryItems();
        tempInventoryItems = cloneItemArrayList(inventoryItems);
        tempShopKeeperItems = cloneItemArrayList(shopKeeperItems);
        active = true;

    }

    public void closeShop() {
        buttonJustPressed = true;
        tempInventoryItems = null;
        tempShopKeeperItems = null;
        active = false;
    }

    private class TradePanel extends UIObject{

        private TextButton proceedButton, cancelButton;
        private Slider slider;
        private SliderAdjuster adjusterUp, adjusterDown;
        private final Bitmap panel;
        private Item tradingItem;

        public TradePanel() {
            super(0, 0, 0, 0);
            active = false;
            panel = ImageEditor.scaleBitmap(Assets.popup2, Constants.UI_SCREEN_WIDTH/2.f, Constants.UI_SCREEN_HEIGHT/2.f);
            x = Constants.SCREEN_WIDTH/2.f-panel.getWidth()/2.f;
            y = Constants.SCREEN_HEIGHT/2.f-panel.getHeight()/2.f;
            width = panel.getWidth();
            height = panel.getHeight();
            bounds = new Rect((int)x, (int)y, (int) (x+width), (int) (y+height));

            slider = new Slider(Constants.SCREEN_WIDTH/2-192, Constants.SCREEN_HEIGHT/2-64,
                    384, 96, 100, 0, 10, "Count: ");
            adjusterUp = new SliderAdjuster(Constants.SCREEN_WIDTH/2.f+200, Constants.SCREEN_HEIGHT/2.f-64,
                    48, 48, 1, Assets.adjusterUp, slider);
            adjusterDown = new SliderAdjuster(Constants.SCREEN_WIDTH/2.f+200, Constants.SCREEN_HEIGHT/2.f,
                    48, 48, -1, Assets.adjusterDown, slider);
            proceedButton = new TextButton(120.f/512*width+x, 296.f/384*height+y, 40, "Proceed", this::proceed);
            cancelButton = new TextButton(386.f/512*width+x, 298.f/384*height+y, 40, "Cancel", this::closePanel);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if(!active)
                return;
            slider.onTouchEvent(event);
            adjusterUp.onTouchEvent(event);
            adjusterDown.onTouchEvent(event);
            proceedButton.onTouchEvent(event);
            cancelButton.onTouchEvent(event);
        }

        @Override
        public void update() {
            slider.update();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawBitmap(panel, null, new Rect((int)x, (int)y, (int)(x+width), (int)(y+height)), Constants.getRenderPaint());
            slider.draw(canvas);
            adjusterUp.draw(canvas);
            adjusterDown.draw(canvas);
            proceedButton.draw(canvas);
            cancelButton.draw(canvas);
        }

        public void openPanel(Item item){
            tradingItem = item;
            active = true;
            int count = item.getCount();
            slider.setMax(count);
            slider.setValue(0);
            if(count > 20)
                slider.setTickSpacing(count/10);
            else
                slider.setTickSpacing(count/5);
        }

        public void closePanel(){
            tradingItem = null;
            slider.setMax(100);
            slider.setTickSpacing(10);
            active = false;
        }

        private void proceed(){
            if(slider.getValue() == 0)
                closePanel();
            if(selling){
                transferItem(tempInventoryItems, tempShopKeeperItems, tradingItem.getId(), slider.getValue());
            }else {
                transferItem(tempShopKeeperItems, tempInventoryItems, tradingItem.getId(), slider.getValue());
            }
            closePanel();
        }

        private void transferItem(ArrayList<Item> original, ArrayList<Item> target, int id, int count){
            changeItemCount(original, id, -count);
            changeItemCount(target, id, count);
        }

        private void changeItemCount(ArrayList<Item> list, int itemID, int count){
            for(int i = 0; i < list.size(); i++){
                Item item = list.get(i);
                if(item.getId() == itemID){
                    item.setCount(item.getCount()+count);
                    if(item.getCount() <= 0)
                        list.remove(i);
                    return;
                }
            }
            if(count > 0){
                Item item = Item.items[itemID].addToInv(count);
                item.setHandler(handler);
                list.add(item);
            }
        }

    }

    private ArrayList<Item> cloneItemArrayList(ArrayList<Item> list){
        ArrayList<Item> newList = new ArrayList<>();
        for(Item i: list){
            Item item = Item.items[i.getId()].addToInv(i.getCount());
            item.setHandler(handler);
            newList.add(item);
        }
        return newList;
    }

    public boolean isActive() {
        return active;
    }
}
