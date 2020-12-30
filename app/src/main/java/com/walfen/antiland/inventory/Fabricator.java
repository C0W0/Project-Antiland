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
import com.walfen.antiland.ui.buttons.TextImageButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Fabricator implements TouchEventListener {
    private boolean active = false, buttonJustPressed = false;
    private Handler handler;
    private Inventory inventory;
    private HashMap<Integer,Recipe> recipes;
    private HashMap<Integer,Integer> missingItems;
    private int selectedX = 0, selectedY = 0, scroll;
    private int recipeBaseX, recipeBaseY, iconSize, recipeDXConstant, recipeDYConstant;
    private int craftingWindowBaseX, craftingWindowBaseY, itemDescCX, itemDescY;
    private int[][] recipeLocations;
    private int lastSelection; // efficiency mechanic
    private UIImageButton invSwitchButton, closeButton;
    private TextImageButton craftButtonText;
    private final Bitmap craftingScreen, blueSquare, redSquare;

    public Fabricator(Handler handler, Inventory inventory){
        this.handler = handler;
        this.inventory = inventory;
        recipes = new HashMap<>();
        missingItems = new HashMap<>();
        craftingScreen = ImageEditor.scaleBitmap(Assets.craftingScreen,
                Constants.UI_SCREEN_WIDTH, Constants.UI_SCREEN_HEIGHT);
        blueSquare = inventory.blueSquare;
        recipeBaseX = inventory.itemBaseX;
        recipeBaseY = inventory.itemBaseY;
        iconSize = Constants.iconSize;
        redSquare = ImageEditor.scaleBitmap(Assets.redSqr, iconSize+4);
        recipeDXConstant = inventory.itemDXConstant;
        recipeDYConstant = inventory.itemDYConstant;
        craftingWindowBaseX = (int)(321.f/512*inventory.invWidth+inventory.xDispute);
        craftingWindowBaseY = (int)(50.f/384*inventory.invHeight+inventory.yDispute);
        itemDescCX = (int)(380.f/512*inventory.invWidth+inventory.xDispute);
        itemDescY = (int)(200.f/384*inventory.invHeight+inventory.yDispute);
        scroll = 0; // WIP
        lastSelection = -1;
        recipeLocations = new int[35][5];
        craftButtonText = new TextImageButton(379.f/512*inventory.invWidth+inventory.xDispute, 322.f/384*inventory.invHeight+inventory.yDispute,
                38, "C r a f t", Color.argb(255, 0, 138, 0), Assets.popupButton2, this::craft);
        invSwitchButton = new UIImageButton(inventory.xDispute+5, inventory.yDispute+inventory.invHeight-Constants.UI_CLOSE_SIZE-5,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.switchFlip, this::setActive);
        closeButton = new UIImageButton(inventory.xDispute+inventory.invWidth-Constants.UI_CLOSE_SIZE, inventory.yDispute,
                Constants.UI_CLOSE_SIZE, Constants.UI_CLOSE_SIZE,
                Assets.close, () -> setActive(false));
        loadRecipes();
    }

    private void loadRecipes(){
        ArrayList<String> loadedRecipes = Utils.loadFileAsArrayList("recipes.wld");
        int x = 0, y = 0;
        for(String str: loadedRecipes){
            String[] data = str.split("\\s+");
            recipes.put(Utils.parseInt(data[0]), new Recipe(data));
            recipeLocations[y][x] = Utils.parseInt(data[0]);
            x = x < 4?x+1:0;
            y = x == 0?y+1:y;
        }
    }

    private boolean isCraftable(){
        if(recipes.get(recipeLocations[selectedY][selectedX]) == null ||
                !recipes.get(recipeLocations[selectedY][selectedX]).isActive)
            return false;
        for(Map.Entry e: recipes.get(recipeLocations[selectedY][selectedX]).requitedItemsCount.entrySet()){
            if(inventory.getItemCount((int)e.getKey()) < (int)e.getValue())
                return false;
        }
        return true;
    }

    public void craft(){
        if(!isCraftable())
            return;
        buttonJustPressed = true;
        for(Map.Entry e: recipes.get(recipeLocations[selectedY][selectedX]).requitedItemsCount.entrySet()) {
            for (int i = 0; i < inventory.getInventoryItems().size(); i++) {
                if (inventory.getInventoryItems().get(i).getId() == (int) e.getKey()) {
                    inventory.getInventoryItems().get(i).setCount(
                            inventory.getInventoryItems().get(i).getCount() - (int) e.getValue());
                    i = inventory.getInventoryItems().size();
                }
            }
        }
        handler.getPlayer().increaseXp(2);
        inventory.addItem(Item.items[recipeLocations[selectedY][selectedX]].addToInv(1)); // TODO: Variable number
        lastSelection = -1;
    }

    @Override
    public void update(){
        if(!active){
            return;
        }
        if (lastSelection == recipeLocations[selectedY][selectedX])
            return;
        else
            lastSelection = recipeLocations[selectedY][selectedX];
        if (!isCraftable())
            missingItems = computeMissingItemCount();
        else
            missingItems.clear();
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active)
            return;
        craftButtonText.onTouchEvent(event);
        if(buttonJustPressed) {
            buttonJustPressed = false;
            return;
        }
        invSwitchButton.onTouchEvent(event);
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
        if(!active)
            return;
        canvas.drawBitmap(craftingScreen, null,
                new Rect(inventory.xDispute, inventory.yDispute,
                        inventory.xDispute+inventory.invWidth, inventory.yDispute+inventory.invHeight),
                Constants.getRenderPaint());
        for(int y = scroll; y < scroll+7; y++){
            for(int x = 0; x < 5; x++){
                if(recipeLocations[y][x] == 0)
                    break;
                int left = x*recipeDXConstant + recipeBaseX;
                int top = (y-scroll)*recipeDYConstant + recipeBaseY;
                if(recipes.get(recipeLocations[selectedY][selectedX]) != null &&
                        !recipes.get(recipeLocations[selectedY][selectedX]).isActive)
                    canvas.drawBitmap(redSquare, null,
                            new Rect(left-2, top-2, left+iconSize+4, top+iconSize+4),
                            Constants.getRenderPaint());
                if(x == selectedX && y == selectedY)
                    canvas.drawBitmap(blueSquare, null,
                            new Rect(left-2, top-2, left+iconSize+4, top+iconSize+4),
                            Constants.getRenderPaint());
                canvas.drawBitmap(Item.items[recipeLocations[y][x]].getInvTexture(), null,
                        new Rect(left, top, left+iconSize, top+iconSize),
                        Constants.getRenderPaint());
            }
        }
        craftButtonText.draw(canvas);
        invSwitchButton.draw(canvas);
        closeButton.draw(canvas);
        Recipe re = recipes.get(recipeLocations[selectedY][selectedX]);
        if(re == null)
            return;
        HashMap<Integer, Integer> tempItems = (HashMap<Integer, Integer>)missingItems.clone();
        for(int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if(re.requiredItems[y][x] != -1){
                    int left = x*recipeDXConstant + craftingWindowBaseX;
                    int top = y * recipeDYConstant + craftingWindowBaseY;
                    if (tempItems.get(re.requiredItems[y][x]) != null &&
                            tempItems.get(re.requiredItems[y][x]) != 0) {
                        canvas.drawBitmap(redSquare, null,
                            new Rect(left-2, top-2, left+iconSize+2, top+iconSize+2),
                            Constants.getRenderPaint());
                        tempItems.put(re.requiredItems[y][x], tempItems.get(re.requiredItems[y][x]) - 1);
                    }
                    canvas.drawBitmap(Item.items[re.requiredItems[y][x]].getInvTexture(), null,
                            new Rect(left, top, left+iconSize, top+iconSize),
                            Constants.getRenderPaint());
                }
            }
        }

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        Rect r = new Rect();
        int top = itemDescY;
        int left;
        paint.setTextSize(32);
        Item selectedItem = Item.items[recipeLocations[selectedY][selectedX]];
        ArrayList<String> tokens = Utils.splitString(selectedItem.getDesc(), 30);
        for(String str: tokens){
            paint.getTextBounds(str, 0, str.length(), r);
            left = itemDescCX-r.width()/2;
            canvas.drawText(str, left, top+r.height(), paint);
            top += 5+r.height();
        }
        tokens = new ArrayList<>(Arrays.asList(selectedItem.getEffect()));
        top += 20;
        paint.setColor(Color.MAGENTA);
        for(String str: tokens){
            paint.getTextBounds(str, 0, str.length(), r);
            left = itemDescCX-r.width()/2;
            canvas.drawText(str, left, top+r.height(), paint);
            top += 5+r.height();
        }
    }

    private int computeSelectedLocationX(float x){
        x = x-recipeBaseX<0?0:x-recipeBaseX;
        int i = Math.floorDiv((int)x, recipeDXConstant);
        return i>4?0:i;
    }

    private int computeSelectedLocationY(float y){
        y = y-recipeBaseY<0?0:y-recipeBaseY;
        int i = Math.floorDiv((int)y, recipeDYConstant);
        return (i>6?0:i)+scroll;
    }

    private Point computeSelectedPoint(float x, float y){
        x = Math.floorDiv((int)(x-recipeBaseX), recipeDXConstant);
        y = Math.floorDiv((int)(y-recipeBaseY), recipeDYConstant);
        if(x < 0 || x > 4 || y < 0 || y > 4)
            return new Point(0, scroll);
        return new Point((int)x, (int)y+scroll);
    }

    private HashMap<Integer,Integer> computeMissingItemCount(){
        HashMap<Integer,Integer> missingItemCount = new HashMap<>();
        if(recipeLocations[selectedY][selectedX] == 0)
            return missingItemCount;
        for(Map.Entry e: recipes.get(recipeLocations[selectedY][selectedX]).requitedItemsCount.entrySet()){
            int count = -(inventory.getItemCount((int)e.getKey()) - (int)e.getValue());
            if(count > 0){
                missingItemCount.put((int)e.getKey(), count);
            }
        }
        return missingItemCount;
    }

    public static class Recipe{
        public int craftingLevel;
        public boolean isActive = true;
        public int[][] requiredItems = new int[3][3];
        public HashMap<Integer,Integer> requitedItemsCount = new HashMap<>();

        public Recipe(String[] data){
            //the format of data: id, recipe*9, level
            for(int y = 0; y < 3; y++){
                for(int x = 0; x < 3; x++){
                    requiredItems[y][x] = Integer.parseInt(data[y*3+x+1]);
                    if(requiredItems[y][x] == -1) //omits -1, as it represents nothing
                        continue;
                    if(requitedItemsCount.get(requiredItems[y][x]) != null){
                        requitedItemsCount.put(requiredItems[y][x], requitedItemsCount.get(requiredItems[y][x])+1);
                    }else{
                        requitedItemsCount.put(requiredItems[y][x], 1);
                    }
                }
            }
            craftingLevel = Utils.parseInt(data[10]);
        }
    }



    public void setActive(){
        active = !active;
        buttonJustPressed = true;
        lastSelection = -1;
        inventory.setActive(!active);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public int getLastSelection() {
        return lastSelection;
    }

    public void setLastSelection(int lastSelection) {
        this.lastSelection = lastSelection;
    }
}
