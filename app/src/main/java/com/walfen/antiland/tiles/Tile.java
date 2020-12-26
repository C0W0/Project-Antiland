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

        new Tile(Assets.tt2_roofTop, 133, true);
        new Tile(Assets.tt2_roofCommon, 134, true);
        new ComponentTile(Assets.tt2_roofCommon, 135, true,
                new TileAddonComponent(Assets.tt2_SmallARoof));
        new ComponentTile(Assets.tt2_grass, 136, true,
                new TileAddonComponent(Assets.tt2_roofLeftTop));
        new ComponentTile(Assets.tt2_roofTop, 137, true,
                new TileAddonComponent(Assets.tt2_roofLeftTop));
        new ComponentTile(Assets.tt2_roofCommon, 138, true,
                new TileAddonComponent(Assets.tt2_roofLeftTop));

        new Tile(Assets.tt2_roofLeftMid, 139, true);
        new ComponentTile(Assets.tt2_roofLeftMid, 140, true,
                new TileAddonComponent(Assets.tt2_smokestack));
        new ComponentTile(Assets.tt2_wallCommon1, 141, true,
                new TileAddonComponent(Assets.tt2_roofLeftBottom));
        new ComponentTile(Assets.tt2_grass, 142, true,
                new TileAddonComponent(Assets.tt2_roofRightTop));
        new ComponentTile(Assets.tt2_roofTop, 143, true,
                new TileAddonComponent(Assets.tt2_roofRightTop));
        new ComponentTile(Assets.tt2_roofCommon, 144, true,
                new TileAddonComponent(Assets.tt2_roofRightTop));

        new Tile(Assets.tt2_roofRightMid, 145, true);
        new ComponentTile(Assets.tt2_roofRightMid, 146, true,
                new TileAddonComponent(Assets.tt2_smokestack));
        new ComponentTile(Assets.tt2_wallCommon1, 147, true,
                new TileAddonComponent(Assets.tt2_roofRightBottom));
        new Tile(Assets.tt2_wallLeftTop, 148, true);
        new Tile(Assets.tt2_wallLeftMid, 149, true);
        new Tile(Assets.tt2_wallLeftBottom, 150, true);
        new Tile(Assets.tt2_wallMidTop, 151, true);
        new ComponentTile(Assets.tt2_wallMidTop, 152, true,
                new TileAddonComponent(Assets.tt2_halfRoofTop));
        new Tile(Assets.tt2_wallCommon1, 153, true);
        new Tile(Assets.tt2_wallMidBottom, 154, true);
        new ComponentTile(Assets.tt2_wallMidBottom, 155, true,
                new TileAddonComponent(Assets.tt2_balcony));
        new Tile(Assets.tt2_wallRightTop, 156, true);
        new Tile(Assets.tt2_wallRightMid, 157, true);
        new Tile(Assets.tt2_wallRightBottom, 158, true);
        new Tile(Assets.tt2_window, 159, true);
        new Tile(Assets.tt2_doorTop, 160, true);
        new Tile(Assets.tt2_doorBottom, 161, false);

        new Tile(Assets.tt2_pathHorizontalTop, 162, false);
        new Tile(Assets.tt2_pathVerticalLeft, 163, false);
        new Tile(Assets.tt2_pathCornerUpRight, 164, false);
        new Tile(Assets.tt2_pathCornerUpLeft, 165, false);
        new Tile(Assets.tt2_pathCornerDownRight, 166, false);
        new Tile(Assets.tt2_pathCornerDownLeft, 167, false);
        new Tile(Assets.tt2_pathCross, 168, false);
        new Tile(Assets.tt2_grass, 169, false);

        new Tile(Assets.tt3_roofTop, 170, true);
        new Tile(Assets.tt3_roofCommon, 171, true);
        new ComponentTile(Assets.tt3_roofCommon, 172, true,
                new TileAddonComponent(Assets.tt3_SmallARoof));
        new ComponentTile(Assets.tt3_grass, 173, true,
                new TileAddonComponent(Assets.tt3_roofLeftTop));
        new ComponentTile(Assets.tt3_roofTop, 174, true,
                new TileAddonComponent(Assets.tt3_roofLeftTop));
        new ComponentTile(Assets.tt3_roofCommon, 175, true,
                new TileAddonComponent(Assets.tt3_roofLeftTop));

        new Tile(Assets.tt3_roofLeftMid, 176, true);
        new ComponentTile(Assets.tt3_roofLeftMid, 177, true,
                new TileAddonComponent(Assets.tt3_smokestack));
        new ComponentTile(Assets.tt3_wallCommon1, 178, true,
                new TileAddonComponent(Assets.tt3_roofLeftBottom));
        new ComponentTile(Assets.tt3_grass, 179, true,
                new TileAddonComponent(Assets.tt3_roofRightTop));
        new ComponentTile(Assets.tt3_roofTop, 180, true,
                new TileAddonComponent(Assets.tt3_roofRightTop));
        new ComponentTile(Assets.tt3_roofCommon, 181, true,
                new TileAddonComponent(Assets.tt3_roofRightTop));

        new Tile(Assets.tt3_roofRightMid, 182, true);
        new ComponentTile(Assets.tt3_roofRightMid, 183, true,
                new TileAddonComponent(Assets.tt3_smokestack));
        new ComponentTile(Assets.tt3_wallCommon1, 184, true,
                new TileAddonComponent(Assets.tt3_roofRightBottom));
        new Tile(Assets.tt3_wallLeftTop, 185, true);
        new Tile(Assets.tt3_wallLeftMid, 186, true);
        new Tile(Assets.tt3_wallLeftBottom, 187, true);
        new Tile(Assets.tt3_wallMidTop, 188, true);
        new ComponentTile(Assets.tt3_wallMidTop, 189, true,
                new TileAddonComponent(Assets.tt3_halfRoofTop));
        new Tile(Assets.tt3_wallCommon1, 190, true);
        new Tile(Assets.tt3_wallMidBottom, 191, true);
        new ComponentTile(Assets.tt3_wallMidBottom, 192, true,
                new TileAddonComponent(Assets.tt3_balcony));
        new Tile(Assets.tt3_wallRightTop, 193, true);
        new Tile(Assets.tt3_wallRightMid, 194, true);
        new Tile(Assets.tt3_wallRightBottom, 195, true);
        new Tile(Assets.tt3_window, 196, true);
        new Tile(Assets.tt3_doorTop, 197, true);
        new Tile(Assets.tt3_doorBottom, 198, false);

        new Tile(Assets.tt3_pathHorizontalTop, 199, false);
        new Tile(Assets.tt3_pathVerticalLeft, 200, false);
        new Tile(Assets.tt3_pathCornerUpRight, 201, false);
        new Tile(Assets.tt3_pathCornerUpLeft, 202, false);
        new Tile(Assets.tt3_pathCornerDownRight, 203, false);
        new Tile(Assets.tt3_pathCornerDownLeft, 204, false);
        new Tile(Assets.tt3_pathCross, 205, false);
        new Tile(Assets.tt3_grass, 206, false);

        new Tile(Assets.a1_tiles[0][0], 79, true);
        new Tile(Assets.a1_tiles[0][1], 80, true);
        new Tile(Assets.a1_tiles[0][2], 81, true);
        new Tile(Assets.a1_tiles[0][3], 82, true);
        new Tile(Assets.a1_tiles[1][3], 83, true);
        new Tile(Assets.a1_tiles[2][3], 84, true);

        new Tile(Assets.a1_tiles[1][2], 85, true);
        new Tile(Assets.a1_tiles[2][2], 86, false);
        new Tile(Assets.a1_tiles[2][0], 87, false);
        new Tile(Assets.a1_tiles[1][0], 88, true);
        new Tile(Assets.a1_tiles[1][1], 89, true);

        new Tile(Assets.beachDrySand, 91, false);
        new Tile(Assets.beachDryCrater, 92, false);
        new Tile(Assets.beachTransitionUp, 93, false);
        new Tile(Assets.beachTransitionDown, 94, false);
        new Tile(Assets.beachWetSand, 95, false);
        new Tile(Assets.beachWetCrater, 96, false);
        new Tile(Assets.beachShoreUp1, 97, false);
        new Tile(Assets.beachShoreUp2, 98, false);
        new Tile(Assets.beachShoreDown1, 99, false);
        new Tile(Assets.beachShoreDown2, 100, false);
        new Tile(Assets.beachGrassFlower, 101, false);
        new Tile(Assets.beachGrass1, 102, false);
        new Tile(Assets.beachGrass2, 103, false);

        new Tile(Assets.beachOcean1, 104, true);
        new Tile(Assets.beachOcean2, 105, true);

        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                new Tile(Assets.beach_diagonals[y][x], 106+4*y+x, false);
            }
        }

        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 2; x++){
                new Tile(Assets.beach_Verticals[y][x], 122+2*y+x, false);
            }
        }

        new Tile(Assets.beachVerticalWest, 130, false);
        new Tile(Assets.beachVerticalEast, 131, false);
        new Tile(Assets.beachOceanTransition, 132, true);

        for(int i = 0; i < Assets.drySand_diagonals.length; i++)
            new Tile(Assets.drySand_diagonals[i], 207+i, false);
        for(int i = 0; i < Assets.wetSand_diagonals.length; i++)
            new Tile(Assets.wetSand_diagonals[i], 211+i, false);
        for(int i = 0; i < Assets.grassTransition.length; i++)
            new Tile(Assets.grassTransition[i], 215+i, false);
        new Tile(Assets.drySandVertical, 227, false);
        new Tile(Assets.wetSandVertical, 228, false);
        new Tile(Assets.sandTransitionLeft, 229, false);
        new Tile(Assets.sandTransitionRight, 230, false);
        for(int i = 0; i < Assets.transition_diagonals.length; i++)
            new Tile(Assets.transition_diagonals[i], 231+i, false);
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
