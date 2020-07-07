package com.walfen.antiland.gfx;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.Image;
import android.provider.ContactsContract;

import com.walfen.antiland.Constants;
import com.walfen.antiland.R;

import java.util.Arrays;

public class Assets {

    public static Bitmap grass, grassStone, dirt, dirtStone;
    public static Bitmap pathVertical, pathHorizontal, pathCornerUpRight, pathCornerUpLeft, pathCornerDownLeft, pathCornerDownRight;

    //player
    public static Bitmap player_neutral;
    public static Bitmap [] player_down, player_up, player_left, player_right;
    public static Bitmap player_Attack;

    public static Bitmap [] water;

    //joystick
    public static Bitmap joystick_pad, joystick_controller;

    //ui
    public static Bitmap hp_bar, mp_bar, bar_frame;

    //entities
    public static Bitmap tree;

    public static Bitmap[] npcSlime;
    public static Bitmap[] slimeAttackLeft;
    public static Bitmap[] slimeAttackRight;
    public static Bitmap[] slimeMovementLeft;
    public static Bitmap[] slimeMovementRight;
    public static Bitmap[] npcCrab;

    //items
    public static Bitmap wood, apple, stone, potion, shield, sword;
    public static Bitmap inventoryScreen, missionScreen, craftingScreen;
    public static Bitmap blueSqr, redSqr;

    //TODO: private field to increase performance. FINAL

    public static void init(){
        final int height = Constants.DEFAULT_SIZE;
        final int width = Constants.DEFAULT_SIZE;
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.spritesheet));
        SpriteSheet sheet1 = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.spritesheet1));
        SpriteSheet townTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.remix_town_tiles));
        SpriteSheet slimeAttack = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.slime_attack));
        SpriteSheet slimeMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.slime_movement));
        SpriteSheet idleSlime = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.idle_slime));
        SpriteSheet crabSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.crab));

        grass = townTiles.crop(0,height,width,height);
        grassStone = sheet1.crop(width*3,height,width,height);
        dirt = townTiles.crop(width,height,width,height);
        dirtStone = sheet1.crop(width*6,height,width,height);
        water = new Bitmap[4];
        water[0] = townTiles.crop(0,height*4,width,height);
        water[1] = townTiles.crop(width,height*4,width,height);
        water[2] = townTiles.crop(width*2,height*4,width,height);
        water[3] = townTiles.crop(width*3,height*4,width,height);
        
        player_down = new Bitmap[2];
        player_down[0] = sheet.crop(0,0,width,height);
        player_down[1] = sheet.crop(width,0,width,height);
        player_up = new Bitmap[2];
        player_up[0] = sheet.crop(width*2,0,width,height);
        player_up[1] = sheet.crop(width*3,0,width,height);
        player_right = new Bitmap[2];
        player_right[0] = sheet.crop(0,height,width,height);
        player_right[1] = sheet.crop(width,height,width,height);
        player_left = new Bitmap[2];
        player_left[0] = sheet.crop(width*2,height,width,height);
        player_left[1] = sheet.crop(width*3,height,width,height);
        player_neutral = sheet.crop(0,height*2,width,height);;
        player_Attack = sheet.crop(width*3, height*2, width, height);

        joystick_pad = sheet1.crop(0, 0, width*3, height*3);
        joystick_controller = sheet1.crop(width*3, 0, width, height);
        hp_bar = ImageLoader.loadImage(R.drawable.hp_bar);
        mp_bar = ImageLoader.loadImage(R.drawable.mp);
        bar_frame = ImageLoader.loadImage(R.drawable.bar_frame);

        //entity
        tree = townTiles.crop(width*5,height*3,width,height*2);
        npcSlime = loadSpriteAsArray(idleSlime, 2, 1, 64, 64);
        npcCrab = loadSpriteAsArray(crabSheet, 6, 4, 64, 64);
        slimeAttackLeft = new Bitmap[3];
        slimeAttackLeft[0] = slimeAttack.crop(0,0,width,height);
        slimeAttackLeft[1] = slimeAttack.crop(width,0,width,height);
        slimeAttackLeft[2] = slimeAttack.crop(width*2,0,width,height);
        slimeAttackRight = new Bitmap[3];
        slimeAttackRight[0] = slimeAttack.crop(0,height,width,height);
        slimeAttackRight[1] = slimeAttack.crop(width,height,width,height);
        slimeAttackRight[2] = slimeAttack.crop(width*2,height,width,height);
        slimeMovementLeft = new Bitmap[3];
        slimeMovementLeft[0] = slimeMovement.crop(0,0,width,height);
        slimeMovementLeft[1] = slimeMovement.crop(width,0,width,height);
        slimeMovementLeft[2] = slimeMovement.crop(width*2,0,width,height);
        slimeMovementRight = new Bitmap[3];
        slimeMovementRight[0] = slimeMovement.crop(0,height,width,height);
        slimeMovementRight[1] = slimeMovement.crop(width,height,width,height);
        slimeMovementRight[2] = slimeMovement.crop(width*2,height,width,height);

        //items
        wood = sheet1.crop(width*7,height,width,height);
        apple = ImageLoader.loadImage(R.drawable.apple);
        potion = ImageLoader.loadImage(R.drawable.potion);
        inventoryScreen = ImageLoader.loadImage(R.drawable.inventory_screen_new);
        craftingScreen = ImageLoader.loadImage(R.drawable.craft_screen);
        missionScreen = ImageLoader.loadImage(R.drawable.mission_screen);
        blueSqr = ImageLoader.loadImage(R.drawable.selected);
        redSqr = ImageLoader.loadImage(R.drawable.red_square);

    }

    /** Loading an entire SpriteSheet as a Bitmap array (for animations)
     * @param xBlocks number of sub images in the width of the SpriteSheet
     * @param yBlocks number of sub images in the height of the SpriteSheet
     * @param height the height of a sub image, in pixels
     * @param width the width of a sub image, in pixels
     * @return the array of sub images in order
     */
    public static Bitmap[] loadSpriteAsArray(SpriteSheet spriteSheet, int xBlocks, int yBlocks, int height, int width){
        Bitmap[] images = new Bitmap[xBlocks*yBlocks];
        for(int y = 0; y < yBlocks; y++){
            for(int x = 1; x <= xBlocks; x++){
                images[y * xBlocks + x - 1] = spriteSheet.crop((x-1)*width, y*height, width, height);
            }
        }
        return images;
    }

}
