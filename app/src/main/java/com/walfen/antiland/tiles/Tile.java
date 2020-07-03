package com.walfen.antiland.tiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.GraphicalTerminalElement;

public abstract class Tile implements GraphicalTerminalElement {

    //static variables

    public static Tile[] tiles = new Tile[512];
    public static Tile grassTile = new GrassTile(0);
    public static Tile grassRockTile = new GrassRockTile(1);
    public static Tile dirtTile = new DirtTile(2);
    public static Tile dirtRockTile = new DirtRockTile(3);
//    public static Tile horizontalPath = new HorizontalPath(4);
//    public static Tile verticalPath = new VerticalPath(5);
//    public static Tile pathUpRight = new PathUpRight(6);
//    public static Tile pathUpLeft = new PathUpLeft(7);
//    public static Tile pathDownRight = new PathDownRight(8);
//    public static Tile pathDownLeft = new PathDownLeft(9);
    public static Tile waterTile = new WaterTile(10);



    //default values
    public static final int TILEHEIGHT = Constants.DEFAULT_SIZE;
    public static final int TILEWIDTH = Constants.DEFAULT_SIZE;


    //tiles
    protected Bitmap texture;
    protected Animation dynamicTexture;
    protected final int id;

    public Tile(Bitmap texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public Tile(Animation dynamicTexture, int id){
        this.dynamicTexture = dynamicTexture;
        this.id = id;

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
        return false;
    }


    //getters and setters
    public int getId() {
        return id;
    }

}
