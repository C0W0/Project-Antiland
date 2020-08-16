package com.walfen.antiland.entities.creatures.npc.trader;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.npc.NPC;
import com.walfen.antiland.items.Item;

import java.util.ArrayList;

public abstract class Trader extends NPC {

    protected ArrayList<Item> traderInventory;

    public Trader(int width, int height, int id) {
        super(width, height, id);
        traderInventory = initInvItems();
    }

    protected abstract ArrayList<Item> initInvItems();

    @Override
    protected void interact() {
        if(!handler.getPlayer().getTrade().isActive())
            handler.getPlayer().getTrade().openShop(this);
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
            addItem(handler, Item.lvOneHpPotion.addToInv(10), items);
            addItem(handler, Item.lvOneGreenPotion.addToInv(10), items);
            addItem(handler, Item.bottle.addToInv(20), items);
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
