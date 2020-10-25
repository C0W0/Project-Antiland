package com.walfen.antiland.world;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.EntityManager;
import com.walfen.antiland.items.ItemManager;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.ui.ChangeEvent;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class World implements GameHierarchyElement {

    private Handler handler;
    private int width, height;
    private int [][] worldTiles;//a 2d array which associates the type of tiles to the location (x,y)

    private int xStart;
    private int xEnd;
    private int yStart;
    private int yEnd;

    private int index;

    private String TILE_FILENAME;
    private String ENTITY_FILENAME;

    private ChangeEvent[] worldEvents;

    //entities
    private EntityManager entityManager;
    private ArrayList<String> loadedEntities = new ArrayList<>();

    //items
    private ItemManager itemManager;

    public World(Handler handler, String saveDirectory, int worldId){
        entityManager = new EntityManager(handler, handler.getPlayer());
        this.handler = handler;
        TILE_FILENAME = "tiles"+worldId+".wld";
        ENTITY_FILENAME = "entity"+worldId+".wld";
//        saveDirectory = saveDirectory+"/world";
        index = worldId;
        worldEvents = WorldEvents.allWorldEvents[worldId];

        loadWorld(saveDirectory);

        itemManager = new ItemManager(handler);
    }

    private void loadWorld(String saveDirectory){
        //loading the map file
        try {
            String[] tokens = Utils.loadFileAsString(
                    new FileInputStream(saveDirectory+"/"+TILE_FILENAME)).split("\\s+");
            width = Utils.parseInt(tokens[0]);
            height = Utils.parseInt(tokens[1]);

            worldTiles = new int[width][height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    worldTiles[x][y] = Utils.parseInt(tokens[x + y * width + 2]);
                }
            }

            //loading the entity file
            loadedEntities = Utils.loadFileAsArrayList(new FileInputStream(saveDirectory+"/"+ENTITY_FILENAME));
            for (int i = 0; i < loadedEntities.size(); i++) {
                String[] entities = loadedEntities.get(i).split("\\s+");
                entityManager.addEntity(getEntityWithID(Utils.parseInt(entities[0]), // id
                        Utils.parseInt(entities[1]), Utils.parseInt(entities[2]), // initial x and y
                        Utils.parseInt(entities[3]), Utils.parseInt(entities[4]), // original x and y
                        Utils.parseInt(entities[5]))); // status
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param id the id of the entity
     * @param x the x position of the entity
     * @param y the y position of the entity
     * @param ox the original x position of the entity, pass in 0 if not applicable
     * @param oy the original y position of the entity, pass in 0 if not applicable
     * @param status the status of the entity, pass in 0 if not applicable
     */
    private Entity getEntityWithID(int id, int x, int y, int ox, int oy, int status){
        Entity e = Entity.entityList[id].clone();
        e.initialize(handler, x, y, ox, oy, status);
        return e;
    }

    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= width || y >= height){
            return Tile.dirtTile;
        }

        Tile t = Tile.tiles[worldTiles[x][y]];//get the tile type located at (x,y)
        if(t == null){
            return Tile.dirtTile;
        }
        return t;
    }

    public void saveMap(String path) throws IOException {
        File mapFile = new File(path+"/"+TILE_FILENAME);
        File entityFile = new File(path+"/"+ENTITY_FILENAME);
        mapFile.delete();
        entityFile.delete();
        mapFile.createNewFile();
        PrintWriter mapEditor = new PrintWriter(mapFile);
        mapEditor.println(width+" "+height);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                mapEditor.print(worldTiles[x][y]+" ");
            }
            mapEditor.println();
        }
        mapEditor.close();

        entityFile.createNewFile();
        PrintWriter entityEditor = new PrintWriter(entityFile);
        for(int i = 0; i < entityManager.getEntities().size(); i++){
            Entity e = entityManager.getEntities().get(i);
            if(e.getId() == 0)
                continue;
            String line = e.getId()+" "+(int)e.getX()+" "+
                    (int)e.getY()+" "+e.getOX()+" "+e.getOY()+" "+e.getStatus();
            entityEditor.println(line);
        }
        entityEditor.close();
    }

    @Override
    public void update(){

        xStart = (int) Math.max(0,handler.getGameCamera().getxOffset() / Constants.DEFAULT_SIZE);
        xEnd = (int) Math.min(width,(handler.getGameCamera().getxOffset() + Constants.SCREEN_WIDTH) / Constants.DEFAULT_SIZE + 1);
        yStart = (int) Math.max(0,handler.getGameCamera().getyOffset() / Constants.DEFAULT_SIZE);
        yEnd = (int) Math.min(height,(handler.getGameCamera().getyOffset() + Constants.SCREEN_HEIGHT) / Constants.DEFAULT_SIZE + 1);

        for(int y = yStart; y < yEnd; y++){
            for(int x = xStart; x < xEnd; x++){
                getTile(x,y).update();
            }
        }

        entityManager.update();
        itemManager.update();

    }

    @Override
    public void draw(Canvas canvas){

        for(int y = yStart; y < yEnd; y++){
            for(int x = xStart; x < xEnd; x++){
                int left = (int)(x * Constants.DEFAULT_SIZE - handler.getGameCamera().getxOffset());
                int top = (int)(y * Constants.DEFAULT_SIZE - handler.getGameCamera().getyOffset());
                getTile(x,y).draw(canvas, new Rect(left, top,
                        left + Constants.DEFAULT_SIZE, top + Constants.DEFAULT_SIZE));
            }
        }

        entityManager.draw(canvas);
        itemManager.draw(canvas);

    }

    public void removeLocationEntity(int entityX, int entityY, int precision){
        for(int i = 0; i < entityManager.getEntities().size(); i++){
            if( ((int)(entityManager.getEntities().get(i).getX()) >= entityX - precision &&
                    (int)(entityManager.getEntities().get(i).getX()) <= entityX + precision )
                    &&
                    ((int)(entityManager.getEntities().get(i).getY()) >= entityY - precision &&
                            (int)(entityManager.getEntities().get(i).getY()) <= entityY + precision ))
            {
                if (entityManager.getEntities().get(i) != handler.getPlayer()) {
                    entityManager.getEntities().get(i).setActive(false);
                }
            }
        }
        int k = 0;
        for(int i = 0; i < loadedEntities.size() - k; i++){
            if( (Utils.parseInt(loadedEntities.get(i).split("\\s+")[1]) >= entityX - precision &&
                    (Utils.parseInt(loadedEntities.get(i).split("\\s+")[1])) <= entityX + precision )
                    &&
                    (Utils.parseInt(loadedEntities.get(i).split("\\s+")[2])) >= entityY - precision &&
                    (Utils.parseInt(loadedEntities.get(i).split("\\s+")[2])) <= entityY + precision )
            {
                loadedEntities.remove(i);
                i --;
                k ++;
            }
        }
    }

    public void setTile(int xLocation, int yLocation, int tileID){
        worldTiles[xLocation][yLocation] = tileID;
    }

    public void triggerWorldEvent(int eventID){
        worldEvents[eventID].onChange();
    }

    //getters and setters
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIndex() {
        return index;
    }
}
