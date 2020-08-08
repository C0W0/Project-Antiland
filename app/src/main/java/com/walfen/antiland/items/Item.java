package com.walfen.antiland.items;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.equipment.Equipment;
import com.walfen.antiland.items.equipment.auxiliaries.SimpleShield;
import com.walfen.antiland.items.equipment.weapons.SimpleSword;
import com.walfen.antiland.items.functionless.NeutralItem;
import com.walfen.antiland.items.usable.UsableItem;

public abstract class Item implements GameHierarchyElement {

    //handler

    public static Item[] items = new Item[256];

    public static NeutralItem woodItem;
    public static UsableItem appleItem;
    public static UsableItem potionItem;
    public static NeutralItem stoneItem;
    public static Equipment shieldItem;
    public static Equipment swordItem;

    public static void initItems(Handler handler){
        woodItem = new NeutralItem(Assets.wood, "wood", 0,
                1, "crafting material", "A piece of ordinary wood.");
        appleItem = new UsableItem(Assets.apple, "apple", 1,
                5, "A commonly seen fruit.", "heal: 1", () -> handler.getPlayer().changeHealth(1)
        );
        potionItem = new UsableItem(Assets.potion, "potion", 2,
                20, "Also know as apple juice.", "heal: 10", () -> handler.getPlayer().changeHealth(10)
        );
//        stoneItem = new NeutralItems(Assets.stone, "stone", 3);
        shieldItem = new SimpleShield(Assets.shield, "shield", 4, 100);
        swordItem = new SimpleSword(Assets.sword, "sword", 5, 100);
    }

    //class
    public static final int ITEMWIDTH = 64;
    public static final int ITEMHEIGHT = 64;

    protected Handler handler;
    protected Bitmap texture, invTexture;
    protected String name;
    protected final int id;
    protected int value;

    protected Rect bounds;

    protected int x, y;
    protected int count;
    protected boolean pickedUP;

    public Item(Bitmap texture, String name, int id){
        this.texture = texture;
        this.name = name;
        this.id = id;

        bounds = new Rect(x, y, x+ITEMWIDTH, y+ITEMHEIGHT);
        invTexture = ImageEditor.scaleBitmap(texture, Constants.iconSize);
        pickedUP = false;

        items[id] = this;
    }

    @Override
    public void update(){

        if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0f, 0f).intersect(bounds)){
            pickedUP = true;
            handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this);
        }

    }

    @Override
    public void draw(Canvas canvas){
        if(handler == null){
            return;
        }
        int left = (int) (x - handler.getGameCamera().getxOffset());
        int top = (int) (y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(texture, null, new Rect(
                left, top, left+ITEMWIDTH, top+ITEMHEIGHT
        ), Constants.getRenderPaint());
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
        bounds.left = x;
        bounds.top = y;
        bounds.right = x+ITEMWIDTH;
        bounds.bottom = y+ITEMHEIGHT;
    }

    //this is for testing
//    public Item createNew(int count){
//        Item i = new Item(texture, name, id);
//        i.setPickedUP(true);
//        i.count = count;
//        return i;
//    }

    public abstract void onActive();

    public abstract Item addToInv(int count);

    public abstract Item createNew(int x, int y, int count);

    public abstract String getDesc();

    public abstract String getEffect();

    //getters and setters
    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public Bitmap getInvTexture() {
        return invTexture;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPickedUP() {
        return pickedUP;
    }

    public void setPickedUP(boolean pickedUP) {
        this.pickedUP = pickedUP;
    }

}
