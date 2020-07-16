package com.walfen.antiland.tiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.GraphicalTerminalElement;

public class Tile implements GraphicalTerminalElement {

    //static variables

    public static Tile[] tiles = new Tile[512];
    public static Tile grassTile = new Tile(Assets.grass, 0, false);
    public static Tile grassRockTile = new Tile(Assets.grassStone, 1, true);
    public static Tile dirtTile = new Tile(Assets.dirt, 2, false);
    public static Tile dirtRockTile = new Tile(Assets.dirtStone, 3, true);
//    public static Tile horizontalPath = new HorizontalPath(4);
//    public static Tile verticalPath = new VerticalPath(5);
//    public static Tile pathUpRight = new PathUpRight(6);
//    public static Tile pathUpLeft = new PathUpLeft(7);
//    public static Tile pathDownRight = new PathDownRight(8);
//    public static Tile pathDownLeft = new PathDownLeft(9);
    public static Tile waterTile = new Tile(new Animation(0.4f, Assets.water), 10, true);

    public static void initTiles(){
        for(int i =  0; i < Assets.houseTiles.length; i++){
            new Tile(Assets.houseTiles[i], 11+i, true);
        }
    }



    //default values
    public static final int TILEHEIGHT = Constants.DEFAULT_SIZE;
    public static final int TILEWIDTH = Constants.DEFAULT_SIZE;


    //tiles
    private Bitmap texture;
    private Animation dynamicTexture;
    private final int id;
    private boolean barrier;

    public Tile(Bitmap texture, int id, boolean barrier){
        this.texture = texture;
        this.id = id;
        this.barrier = barrier;

        tiles[id] = this;
    }

    public Tile(Animation dynamicTexture, int id, boolean barrier){
        this.dynamicTexture = dynamicTexture;
        this.id = id;
        this.barrier = barrier;

        tiles[id] = this;
    }


    @Override
    public void update(){
        if(dynamicTexture != null)
            dynamicTexture.update();
    }

    @Override
    public void draw(Canvas canvas, Rect distRect) {
        if(dynamicTexture != null)
            dynamicTexture.draw(canvas, distRect);
        else
            canvas.drawBitmap(texture, null, distRect, Constants.getRenderPaint());
    }


    //property methods
    public boolean isBarrier(){
        return barrier;
    }


    //getters and setters
    public int getId() {
        return id;
    }

}
