package com.walfen.antiland.tiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.GraphicalTerminalElement;
import com.walfen.antiland.gfx.ImageEditor;

public class Tile implements GraphicalTerminalElement {

    //static variables

    public static Tile[] tiles = new Tile[512];
    public static Tile grassTile = new Tile(Assets.grass, 0, false);
    public static Tile grassRockTile = new Tile(Assets.grassStone, 1, true);
    public static Tile dirtTile = new Tile(Assets.dirt, 2, false);
    public static Tile dirtRockTile = new Tile(Assets.dirtStone, 3, true);
//    public static Tile waterTile = new Tile(new Animation(0.4f, Assets.water), 10, true);

    public static void initTiles(){
//        for(int i = 0; i < Assets.simpleHouseTiles.length; i++){
//            new Tile(Assets.simpleHouseTiles[i], 11+i, true);
//        }
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                new Tile(Assets.walls[y][x], 11+y*3+x, true);
            }
        }
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                new Tile(Assets.roofs[y][x], 20+y*3+x, true);
            }
        }
        new Tile(Assets.pathHorizontalTop, 4, false);
        new Tile(Assets.pathVerticalLeft, 5, false);
        new Tile(Assets.pathCornerUpRight, 6, false);
        new Tile(Assets.pathCornerUpLeft, 7, false);
        new Tile(Assets.pathCornerDownRight, 8, false);
        new Tile(Assets.pathCornerDownLeft, 9, false);
        new Tile(Assets.pathCross, 10, false);
        new Tile(Assets.roofBroken1, 29, true);
        new Tile(Assets.roofBroken2, 30, true);
        new Tile(Assets.stair1T, 31, false);
        new Tile(Assets.interior1T, 32, true);
        new Tile(Assets.interior1B, 33, true);
        new ComponentTile(Assets.walls[0][1], 34, true, new TileAddonComponent(Assets.window1));
        new ComponentTile(Assets.walls[0][1], 35, true, new TileAddonComponent(Assets.window1T));
        new ComponentTile(Assets.walls[0][1], 36, true, new TileAddonComponent(Assets.window2T));
        new ComponentTile(Assets.walls[1][1], 37, true, new TileAddonComponent(Assets.window1));
        new ComponentTile(Assets.walls[1][1], 38, true, new TileAddonComponent(Assets.window1T));
        new ComponentTile(Assets.walls[1][1], 39, true, new TileAddonComponent(Assets.window1B));
        new ComponentTile(Assets.walls[1][1], 40, true, new TileAddonComponent(Assets.window2T));
        new ComponentTile(Assets.walls[1][1], 41, true, new TileAddonComponent(Assets.window2B));
        new ComponentTile(Assets.walls[2][1], 42, true, new TileAddonComponent(Assets.window1));
        new ComponentTile(Assets.walls[2][1], 43, true, new TileAddonComponent(Assets.window1B));
        new ComponentTile(Assets.walls[2][1], 44, true, new TileAddonComponent(Assets.window2B));

        new Tile(Assets.tt1_roofTop, 50, true);
        new Tile(Assets.tt1_roofCommon, 51, true);
        new ComponentTile(Assets.tt1_roofCommon, 52, true,
                new TileAddonComponent(Assets.tt1_SmallARoof));
        new ComponentTile(Assets.grass, 53, true,
                new TileAddonComponent(Assets.tt1_roofLeftTop));
        new ComponentTile(Assets.tt1_roofTop, 54, true,
                new TileAddonComponent(Assets.tt1_roofLeftTop));
        new ComponentTile(Assets.tt1_roofCommon, 55, true,
                new TileAddonComponent(Assets.tt1_roofLeftTop));

        new Tile(Assets.tt1_roofLeftMid, 56, true);
        new ComponentTile(Assets.tt1_roofLeftMid, 57, true,
                new TileAddonComponent(Assets.tt1_smokestack));
        new ComponentTile(Assets.tt1_wallCommon1, 58, true,
                new TileAddonComponent(Assets.tt1_roofLeftBottom));
        new ComponentTile(Assets.grass, 59, true,
                new TileAddonComponent(Assets.tt1_roofRightTop));
        new ComponentTile(Assets.tt1_roofTop, 60, true,
                new TileAddonComponent(Assets.tt1_roofRightTop));
        new ComponentTile(Assets.tt1_roofCommon, 61, true,
                new TileAddonComponent(Assets.tt1_roofRightTop));

        new Tile(Assets.tt1_roofRightMid, 62, true);
        new ComponentTile(Assets.tt1_roofRightMid, 63, true,
                new TileAddonComponent(Assets.tt1_smokestack));
        new ComponentTile(Assets.tt1_wallCommon1, 64, true,
                new TileAddonComponent(Assets.tt1_roofRightBottom));
        new Tile(Assets.tt1_wallLeftTop, 65, true);
        new Tile(Assets.tt1_wallLeftMid, 66, true);
        new Tile(Assets.tt1_wallLeftBottom, 67, true);
        new Tile(Assets.tt1_wallMidTop, 68, true);
        new ComponentTile(Assets.tt1_wallMidTop, 69, true,
                new TileAddonComponent(Assets.tt1_halfRoofTop));
        new Tile(Assets.tt1_wallCommon1, 70, true);
        new Tile(Assets.tt1_wallMidBottom, 71, true);
        new ComponentTile(Assets.tt1_wallMidBottom, 72, true,
                new TileAddonComponent(Assets.tt1_balcony));
        new Tile(Assets.tt1_wallRightTop, 73, true);
        new Tile(Assets.tt1_wallRightMid, 74, true);
        new Tile(Assets.tt1_wallRightBottom, 75, true);
        new Tile(Assets.tt1_window, 76, true);
        new Tile(Assets.tt1_doorTop, 77, true);
        new Tile(Assets.tt1_doorBottom, 78, false);

        new Tile(Assets.a1_tiles[0][0], 79, true);
        new Tile(Assets.a1_tiles[0][1], 80, true);
        new Tile(Assets.a1_tiles[0][2], 81, true);
        new Tile(Assets.a1_tiles[0][3], 82, true);
        new Tile(Assets.a1_tiles[1][3], 83, true);
        new Tile(Assets.a1_tiles[2][3], 84, true);

        new Tile(Assets.a1_tiles[1][2], 85, true);
        new Tile(Assets.a1_tiles[2][2], 86, false);
        new Tile(Assets.a1_tiles[2][1], 87, true);
        new Tile(Assets.a1_tiles[1][0], 88, true);
        new Tile(Assets.a1_tiles[1][1], 89, true);

        new Tile(Assets.a1_tiles[2][0], 90, false);
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
        this.texture = ImageEditor.scaleBitmapForced(texture, TILEWIDTH, TILEHEIGHT);
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
