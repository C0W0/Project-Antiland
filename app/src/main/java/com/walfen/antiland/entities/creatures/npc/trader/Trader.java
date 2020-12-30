package com.walfen.antiland.entities.creatures.npc.trader;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.items.Item;

import java.util.ArrayList;

public abstract class Trader extends NPC {

    protected ArrayList<Item> traderInventory;

    public Trader(int width, int height, int id) {
        super(width, height, id);
        traderInventory = initInvItems();
        setInteractType(InteractionType.TRADE);
    }

    protected abstract ArrayList<Item> initInvItems();

    @Override
    protected void interact() {
        if(!handler.getPlayer().getTrade().isActive())
            handler.getPlayer().getTrade().openShop(this);
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
        int iY = (int)(y-68-handler.getGameCamera().getyOffset());
        Rect destRect = new Rect(iX, iY, iX+64, iY+64);
        canvas.drawBitmap(Assets.headSignOrange, null, destRect, Constants.getRenderPaint());
        canvas.drawBitmap(Assets.hsoTrade, null, destRect, Constants.getRenderPaint());
    }

    public static class TraderInvManager{

        public static void addItem(Handler handler, Item item, ArrayList<Item> dest){
            if(item.getHandler() == null)
                item.setHandler(handler);
            for(int i = 0; i < dest.size(); i++){
                Item tempItem = dest.get(i);
                if(tempItem.getId() == item.getId()){
                    dest.get(i).setCount(tempItem.getCount() + item.getCount());
                    return;
                }
            }
            dest.add(item);
        }

        public static ArrayList<Item> getFootTraderInv(Handler handler){
            ArrayList<Item> items = new ArrayList<>();
            addItem(handler, Item.apple.addToInv(50), items);
            addItem(handler, Item.hpPotion1.addToInv(10), items);
            addItem(handler, Item.greenPotion1.addToInv(10), items);
            addItem(handler, Item.bottle.addToInv(20), items);
            return items;
        }


        public static ArrayList<Item> getPortTraderInv(Handler handler){
            ArrayList<Item> items = new ArrayList<>();
            addItem(handler, Item.apple.addToInv(50), items);
            addItem(handler, Item.coconut.addToInv(50), items);
            addItem(handler, Item.woodItem.addToInv(50), items);
            addItem(handler, Item.hpPotion1.addToInv(10), items);
            addItem(handler, Item.greenPotion1.addToInv(10), items);
            addItem(handler, Item.bluePotion1.addToInv(10), items);
            addItem(handler, Item.hpPotion2.addToInv(10), items);
            addItem(handler, Item.greenPotion2.addToInv(10), items);
            addItem(handler, Item.bluePotion2.addToInv(10), items);
            addItem(handler, Item.hpPotion3.addToInv(2), items);
            addItem(handler, Item.greenPotion3.addToInv(2), items);
            addItem(handler, Item.bluePotion3.addToInv(2), items);
            addItem(handler, Item.steelNugget.addToInv(50), items);
            addItem(handler, Item.bottle.addToInv(20), items);
            addItem(handler, Item.fish.addToInv(20), items);
            addItem(handler, Item.leatherBoots.addToInv(1), items);
            return items;
        }
    }

    public ArrayList<Item> getTraderInventory() {
        return traderInventory;
    }

    public void setTraderInventory(ArrayList<Item> traderInventory) {
        this.traderInventory = traderInventory;
    }
}
