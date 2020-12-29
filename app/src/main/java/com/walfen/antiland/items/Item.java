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
import com.walfen.antiland.items.equipment.armours.SimpleArmour;
import com.walfen.antiland.items.equipment.auxiliaries.SimpleShield;
import com.walfen.antiland.items.equipment.weapons.SimpleSword;
import com.walfen.antiland.items.functionless.NeutralItem;
import com.walfen.antiland.items.usable.UsableItem;

public abstract class Item implements GameHierarchyElement {

    //handler

    public static Item[] items = new Item[512];

    public static NeutralItem woodItem, bottle, slimeGel, key, hammer; //id 0-99
    public static UsableItem apple, lvOneHpPotion, lvOneGreenPotion, mapleSyrup, fish, coconut; //id 100-199
    public static Equipment shield1, sword1, shield2, sword2, shield3, sword3; //id 200-299
    public static Equipment armour1, armour2, armour3;

    public static void initItems(Handler handler){

        woodItem = new NeutralItem(Assets.wood, "wood", 0,
                1, new String[]{"crafting", "material"}, "A piece of ordinary wood.");
        bottle = new NeutralItem(Assets.bottle, "bottle", 1,
                5, new String[]{"Liquid", "Container"}, "A bottle for liquids");
        slimeGel = new NeutralItem(Assets.slimeGel, "Slime Gel", 2,
                5, new String[]{"crafting", "material"}, "Green-ish jelly stuff");
        key = new NeutralItem(Assets.key, "Key", 3, 0, new String[]{"Mission", "Item"}, "Do not sell");
        hammer = new NeutralItem(Assets.brokenHammer, "Hammer", 4, 0, new String[]{"Mission", "Item"}, "Do not sell");

        apple = new UsableItem(Assets.apple, "apple", 100,
                5, "A commonly seen fruit.", new String[]{"hp regen: 1"},
                () -> handler.getPlayer().changeHealth(1));
        lvOneGreenPotion = new UsableItem(Assets.greenPotion1, "potion", 101,
                20, "Tastes bitter", new String[]{"mp regen: 10", "poison: 1"},
                () -> {handler.getPlayer().changeHealth(-1); handler.getPlayer().changeMp(10);});
        lvOneHpPotion = new UsableItem(Assets.redPotion1, "potion", 102,
                20, "Also know as apple juice.", new String[]{"hp regen: 10"},
                () -> handler.getPlayer().changeHealth(10));
        mapleSyrup = new UsableItem(Assets.syrup, "brown syrup", 103,
                500, "Very rare sweet syrup.", new String[]{"hp regen: 100", "mp regen: 50"},
                () -> {handler.getPlayer().changeHealth(100); handler.getPlayer().changeMp(50);});
        fish = new UsableItem(Assets.fish, "Fish", 104,
                5, "A Fish", new String[]{"hp regen: 1", "mp regen: 1"},
                () -> {handler.getPlayer().changeHealth(1); handler.getPlayer().changeMp(1);});
        coconut = new UsableItem(Assets.apple, "coconut", 105,
                5, "This coconut has an unusual appearance", new String[]{"hp regen: 1"},
                () -> handler.getPlayer().changeHealth(1));

        shield1 = new SimpleShield(Assets.roundShields[0], "wooden shield", 1, 200);
        sword1 = new SimpleSword(Assets.swords[0], "old sword", 1, 201);
        shield2 = new SimpleShield(Assets.roundShields[1], "shield", 3, 202);
        sword2 = new SimpleSword(Assets.swords[1], "sword", 3, 203);
        shield3 = new SimpleShield(Assets.roundShields[2], "durable shield", 7, 204);
        sword3 = new SimpleSword(Assets.swords[2], "sharp sword", 8, 205);
        armour1 = new SimpleArmour(Assets.armours[0], "copper body armour", 0, 3, 206);
        armour2 = new SimpleArmour(Assets.armours[1], "iron body armour", 1, 5, 207);
        armour3 = new SimpleArmour(Assets.armours[2], "black steel body armour", 5, 11, 208);
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

    public Item(Bitmap texture, String name, int id, int value){
        this.texture = texture;
        this.name = name;
        this.id = id;
        this.value = value;

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

    public abstract String[] getEffect();

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

    public int getValue() {return value;}
}
