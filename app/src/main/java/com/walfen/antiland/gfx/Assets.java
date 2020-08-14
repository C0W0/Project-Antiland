package com.walfen.antiland.gfx;

import android.graphics.Bitmap;

import com.walfen.antiland.Constants;
import com.walfen.antiland.R;

public class Assets {

    public static Bitmap grass, grassStone, dirt, dirtStone;
    public static Bitmap pathVertical, pathHorizontal, pathCornerUpRight, pathCornerUpLeft, pathCornerDownLeft, pathCornerDownRight;
    public static Bitmap[] simpleHouseTiles;
    public static Bitmap[][] walls;
    public static Bitmap interior1T, interior1B, stair1T, window1, window1T, window1B, window2T, window2B;
    public static Bitmap[][] roofs;
    public static Bitmap roofBroken1, roofBroken2;

    public static Bitmap NULL;

    //player
    public static Bitmap player_neutral;
    public static Bitmap [] player_down, player_up, player_left, player_right;
    public static Bitmap player_Attack;

    public static Bitmap [] water;

    //joystick
    public static Bitmap joystick_pad, joystick_controller;

    //ui
    public static Bitmap hp_bar, mp_bar, dark_blue_bar, bar_frame;
    public static Bitmap horizontalSlideTrack, horizontalSlider, horizontalTickMark;
    public static Bitmap adjusterUp, adjusterDown;
    public static Bitmap popup1, popup2;
    public static Bitmap greyDisk;

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
    public static Bitmap inventoryScreen, missionScreen, craftingScreen, statsScreen, skillScreen, tradeScreen;
    public static Bitmap blueSqr, redSqr;

    //skills
    public static Bitmap strength, endurance, agility, knowledge, intelligence;
    public static Bitmap strengthR, enduranceR, agilityR, knowledgeR, intelligenceR;
    public static Bitmap sharpWindG, sharpWind;
    public static Bitmap unlock;

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
        SpriteSheet skillSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.skills_sprite));
        SpriteSheet rockHouseSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.house_tiles));

        grass = townTiles.crop(0,height,width,height);
        grassStone = sheet1.crop(width*3,height,width,height);
        dirt = townTiles.crop(width,height,width,height);
        dirtStone = sheet1.crop(width*6,height,width,height);
        water = new Bitmap[4];
        water[0] = townTiles.crop(0,height*4,width,height);
        water[1] = townTiles.crop(width,height*4,width,height);
        water[2] = townTiles.crop(width*2,height*4,width,height);
        water[3] = townTiles.crop(width*3,height*4,width,height);
        simpleHouseTiles = new Bitmap[18];
        simpleHouseTiles = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.resident_test_2)), 6, 3, 128, 128);

        walls = new Bitmap[3][3];
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                walls[y][x] = rockHouseSheet.crop(x*width, y*height, width, height);
            }
        }
        roofs = new Bitmap[3][3];
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                roofs[y][x] = rockHouseSheet.crop((x+5)*width, y*height, width, height);
            }
        }
        interior1T = rockHouseSheet.crop(width*3, height, width, height);
        interior1B = rockHouseSheet.crop(width*3, height*2, width, height);
        stair1T = rockHouseSheet.crop(width*4, height, width, height);
        window1 = rockHouseSheet.crop(width*4, height*2, width, height);
        roofBroken1 = rockHouseSheet.crop(width*3, 0, width, height);
        roofBroken2 = rockHouseSheet.crop(width*4, 0, width, height);
        window1T = rockHouseSheet.crop(width*4, height*3, width, height);
        window1B = rockHouseSheet.crop(width*4, height*4, width, height);
        window2T = rockHouseSheet.crop(width*3, height*3, width, height);
        window2B = rockHouseSheet.crop(width*3, height*4, width, height);



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
        mp_bar = ImageLoader.loadImage(R.drawable.mp_bar);
        dark_blue_bar = ImageLoader.loadImage(R.drawable.dark_blue_bar);
        bar_frame = ImageLoader.loadImage(R.drawable.bar_frame);
        horizontalSlideTrack = sheet1.crop(width*4, 0, width*2, height);
        horizontalSlider = sheet1.crop(width*4, height, width, height);
        horizontalTickMark = sheet1.crop(width*5, height, width, height);
        adjusterUp = sheet1.crop(width*6, 0, width, height);
        adjusterDown = sheet1.crop(width*7, 0, width, height);

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
        sword = ImageLoader.loadImage(R.drawable.sword);
        shield = ImageLoader.loadImage(R.drawable.shield);
        blueSqr = ImageLoader.loadImage(R.drawable.selected);
        redSqr = ImageLoader.loadImage(R.drawable.red_square);

        //UI
        inventoryScreen = ImageLoader.loadImage(R.drawable.inventory_screen_new);
        craftingScreen = ImageLoader.loadImage(R.drawable.craft_screen);
        missionScreen = ImageLoader.loadImage(R.drawable.mission_screen);
        skillScreen = ImageLoader.loadImage(R.drawable.skill_selection_screen);
        statsScreen = ImageLoader.loadImage(R.drawable.status_screen);
        tradeScreen = ImageLoader.loadImage(R.drawable.trade_screen);
        popup1 = ImageLoader.loadImage(R.drawable.popup_1);
        popup2 = ImageLoader.loadImage(R.drawable.popup_2);
        greyDisk = sheet1.crop(width*3, height*2, width, height);

        //skills
        //icon circle: RGB: 233, 233, 233; 128*128, 4pt
        strength = skillSheet.crop(0, 0, width, height);
        strengthR = skillSheet.crop(0, height, width, height);
        endurance = skillSheet.crop(width, 0, width, height);
        enduranceR = skillSheet.crop(width, height, width, height);
        agility = skillSheet.crop(width*2, 0, width, height);
        agilityR = skillSheet.crop(width*2, height, width, height);
        knowledge = skillSheet.crop(width*3, 0, width, height);
        knowledgeR = skillSheet.crop(width*3, height, width, height);
        intelligence = skillSheet.crop(width*4, 0, width, height);
        intelligenceR = skillSheet.crop(width*4, height, width, height);
        sharpWindG = skillSheet.crop(width*5, height, width, height);
        sharpWind = skillSheet.crop(width*6, height, width, height);

        unlock = skillSheet.crop(width*5, 0, 250, 128);

        NULL = skillSheet.crop(width*7, 0, width, height);

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
