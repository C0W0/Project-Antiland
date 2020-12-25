package com.walfen.antiland.gfx;

import android.graphics.Bitmap;

import com.walfen.antiland.Constants;
import com.walfen.antiland.R;

public class Assets {

    //tiles
    public static Bitmap grass, grassStone, dirt, dirtStone;
    public static Bitmap pathVerticalLeft, pathVerticalRight, pathHorizontalTop, pathHorizontalBottom,
            pathCornerUpRight, pathCornerUpLeft, pathCornerDownLeft, pathCornerDownRight, pathCross;
    public static Bitmap[] simpleHouseTiles;
    public static Bitmap[][] walls;
    public static Bitmap interior1T, interior1B, stair1T, window1, window1T, window1B, window2T, window2B;
    public static Bitmap[][] roofs;
    public static Bitmap roofBroken1, roofBroken2;
    public static Bitmap tt1_roofTop, tt1_roofCommon, tt1_roofLeftTop, tt1_roofLeftMid, tt1_roofLeftBottom,
            tt1_roofRightTop, tt1_roofRightMid, tt1_roofRightBottom, tt1_halfRoofTop, tt1_SmallARoof;
    public static Bitmap tt1_window, tt1_smokestack, tt1_balcony, tt1_doorTop, tt1_doorBottom,
            tt1_wallLeftTop, tt1_wallLeftMid, tt1_wallLeftBottom, tt1_wallMidTop, tt1_wallCommon1, tt1_wallMidBottom,
            tt1_wallRightTop, tt1_wallRightMid, tt1_wallRightBottom;
    public static Bitmap[][] a1_tiles;
    public static Bitmap beachDrySand, beachDryCrater, beachTransitionUp, beachTransitionDown,
            beachWetSand, beachWetCrater, beachShoreUp1, beachShoreUp2, beachOcean1, beachOcean2,
            beachShoreDown1, beachShoreDown2, beachGrassFlower, beachGrass1, beachGrass2, beachVerticalEast, beachVerticalWest;
    public static Bitmap[][] beach_diagonals, beach_Verticals;

    public static Bitmap NULL;
    public static Bitmap grey_transparent;

    //player
    public static Bitmap player_icon;
    public static Bitmap player_neutral;
    public static Bitmap[] player_down, player_up, player_left, player_right;
    public static Bitmap player_Attack;
    public static Bitmap[] player_SharpWind;
    public static Bitmap[] explosion;


    //joystick
    public static Bitmap joystick_pad, joystick_controller;

    //ui
    public static Bitmap hp_bar, mp_bar, dark_blue_bar, bar_frame;
    public static Bitmap horizontalSlideTrack, horizontalSlider, horizontalTickMark;
    public static Bitmap adjusterUp, adjusterDown;
    public static Bitmap popup1, popup2, popupButton1, popupButton2;
    public static Bitmap greyDisk;
    public static Bitmap switchFlip, close;
    public static Bitmap save, chat, operate, tradeInteract;
    public static Bitmap headSignOrange, headSignGray, hsoMissionComplete, hsgMissionComplete;
    public static Bitmap hsoGetMission, hsgGetMission, hsoTrade, hsgTrade;
    public static Bitmap extend, collapse;
    public static Bitmap splashScreen, splashBackground;

    //entities
    public static Bitmap tree;

    public static Bitmap[] npcSlime, slimeAttackLeft, slimeAttackRight, slimeMovementLeft, slimeMovementRight;
    public static Bitmap[] trappedSpiritAttackLeft, trappedSpiritAttackRight, trappedSpiritMovementLeft, trappedSpiritMovementRight;
    public static Bitmap  trappedSpiritAttackUp, trappedSpiritAttackDown, trappedSpiritMovementUp, trappedSpiritMovementDown;
    public static Bitmap[] npcCrab, npcMushroom;
    public static Bitmap[] iceSlimeAttackLeft, iceSlimeAttackRight, iceSlimeMovementLeft, iceSlimeMovementRight;
    public static Bitmap[] ice_spike;

    //items
    public static Bitmap wood, stone, bottle, slimeGel;
    public static Bitmap apple, syrup, redPotion1, greenPotion1;
    public static Bitmap[] roundShields, heaterShields, swords, axes, armours;
    public static Bitmap inventoryScreen, missionScreen, craftingScreen, statsScreen, skillScreen, tradeScreen;
    public static Bitmap mapBackground, worldMap_0, localMap_0;
    public static Bitmap portalIcon, exitIcon, combatIcon, bossIcon, unknownIcon, objectiveIcon;
    public static Bitmap blueSqr, redSqr;

    //skills
    public static Bitmap strength, endurance, agility, knowledge, intelligence;
    public static Bitmap strengthR, enduranceR, agilityR, knowledgeR, intelligenceR;
    public static Bitmap[] strengthSkillsG, strengthSkills;
//    public static Bitmap sharpWindG, sharpWind;
    public static Bitmap unlock;

    public static void init(){
        final int height = Constants.DEFAULT_SIZE;
        final int width = Constants.DEFAULT_SIZE;
        final int iHeight = height/2;
        final int iWidth = width/2;
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.spritesheet));
        SpriteSheet sheet1 = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.spritesheet1));
        SpriteSheet townTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.remix_town_tiles));
        SpriteSheet slimeAttack = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.slime_attack));
        SpriteSheet slimeMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.slime_movement));
        SpriteSheet skillSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.skills_sprite));
        SpriteSheet rockHouseSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.house_tiles));
        SpriteSheet newTownTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.town_tiles));
        SpriteSheet potions = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.potions));
        SpriteSheet items = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.items));
        SpriteSheet iceSlimeMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_slime_movement));
        SpriteSheet iceSlimeAttack = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_slime_attack));
        SpriteSheet headSignSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.head_sign));
        SpriteSheet area1Tiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.area_1_tiles));
        SpriteSheet mapIconSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.map_icons));
        SpriteSheet characterMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.character_movement));
        SpriteSheet trappedSpirit = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.mini_boss));
        SpriteSheet beachIslandTile1 = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.beach));

        grass = newTownTiles.crop(width*11,0,width,height);
        grassStone = sheet1.crop(width*3,height,width,height);
        dirt = townTiles.crop(width,height,width,height);
        dirtStone = sheet1.crop(width*6,height,width,height);
//        water = new Bitmap[4];
//        water[0] = townTiles.crop(0,height*4,width,height);
//        water[1] = townTiles.crop(width,height*4,width,height);
//        water[2] = townTiles.crop(width*2,height*4,width,height);
//        water[3] = townTiles.crop(width*3,height*4,width,height);
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

        pathVerticalLeft = newTownTiles.crop(width*6,height,width,height);
        pathVerticalRight = newTownTiles.crop(width*8,height,width,height);
        pathHorizontalTop = newTownTiles.crop(width*7,0,width,height);
        pathHorizontalBottom = newTownTiles.crop(width*7,height*2,width,height);
        pathCornerUpRight = newTownTiles.crop(width*6,0,width,height);
        pathCornerUpLeft = newTownTiles.crop(width*8,0,width,height);
        pathCornerDownLeft = newTownTiles.crop(width*6,height*2,width,height);
        pathCornerDownRight = newTownTiles.crop(width*8,height*2,width,height);
        pathCross = newTownTiles.crop(width*11,height,width,height);


        tt1_roofTop = newTownTiles.crop(0, 0, width, height);
        tt1_roofCommon = newTownTiles.crop(width, 0, width, height);
        tt1_roofLeftTop = newTownTiles.crop(width*9, 0, width, height);
        tt1_roofLeftMid = newTownTiles.crop(width*9, height, width, height);
        tt1_roofLeftBottom = newTownTiles.crop(width*9, height*2, width, height);
        tt1_roofRightTop = newTownTiles.crop(width*10, 0, width, height);
        tt1_roofRightMid = newTownTiles.crop(width*10, height, width, height);
        tt1_roofRightBottom = newTownTiles.crop(width*10, height*2, width, height);
        tt1_halfRoofTop = newTownTiles.crop(width, height, width, height);
        tt1_SmallARoof = newTownTiles.crop(0, height, width, height);
        tt1_window = newTownTiles.crop(0, height*2, width, height);
        tt1_smokestack = newTownTiles.crop(width*5, height*2, width, height);
        tt1_balcony = newTownTiles.crop(width, height*2, width, height);
        tt1_doorTop = newTownTiles.crop(width*5, 0, width, height);
        tt1_doorBottom = newTownTiles.crop(width*5, height, width, height);
        tt1_wallLeftTop = newTownTiles.crop(width*2, 0, width, height);
        tt1_wallLeftMid = newTownTiles.crop(width*2, height, width, height);
        tt1_wallLeftBottom = newTownTiles.crop(width*2, height*2, width, height);
        tt1_wallMidTop = newTownTiles.crop(width*3, 0, width, height);
        tt1_wallCommon1 = newTownTiles.crop(width*3, height, width, height);
        tt1_wallMidBottom = newTownTiles.crop(width*3, height*2, width, height);
        tt1_wallRightTop = newTownTiles.crop(width*4, 0, width, height);
        tt1_wallRightMid = newTownTiles.crop(width*4, height, width, height);
        tt1_wallRightBottom = newTownTiles.crop(width*4, height*2, width, height);

        a1_tiles = new Bitmap[3][4];
        for(int y = 0; y < a1_tiles.length; y++){
            for(int x = 0; x < a1_tiles[y].length; x++){
                a1_tiles[y][x] = area1Tiles.crop(x*width, y*height, width, height);
            }
        }

        beachDryCrater = beachIslandTile1.crop(0, 0, width, height);
        beachDrySand = beachIslandTile1.crop(width, 0, width, height);
        beachTransitionUp = beachIslandTile1.crop(width*2, 0, width, height);
        beachTransitionDown = beachIslandTile1.crop(width*3, 0, width, height);
        beachWetCrater = beachIslandTile1.crop(0, height, width, height);
        beachWetSand = beachIslandTile1.crop(width, height, width, height);
        beachShoreUp1 = beachIslandTile1.crop(width*2, height, width, height);
        beachShoreUp2 = beachIslandTile1.crop(width*3, height, width, height);
        beachOcean1 = beachIslandTile1.crop(0, height*2, width, height);
        beachOcean2 = beachIslandTile1.crop(width, height*2, width, height);
        beachShoreDown1 = beachIslandTile1.crop(width*2, height*2, width, height);
        beachShoreDown2 = beachIslandTile1.crop(width*3, height*2, width, height);
        beachGrassFlower = beachIslandTile1.crop(0, height*3, width, height);
        beachGrass1 = beachIslandTile1.crop(width, height*3, width, height);
        beachGrass2 = beachIslandTile1.crop(width*2, height*3, width, height);
        beach_diagonals = new Bitmap[4][4];
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 4; x++){
                beach_diagonals[y][x] = beachIslandTile1.crop(width*(x+4), height*y, width, height);
            }
        }
        beach_Verticals = new Bitmap[4][2];
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 2; x++){
                beach_Verticals[y][x] = beachIslandTile1.crop(width*(x+4), height*(y+4), width, height);
            }
        }
        beachVerticalWest = beachIslandTile1.crop(width*6, height*4, width, height);
        beachVerticalEast = beachIslandTile1.crop(width*7, height*4, width, height);


        player_down = new Bitmap[4];
        player_down[0] = characterMovement.crop(0,0,width,height*2);
        player_down[1] = characterMovement.crop(width,0,width,height*2);
        player_down[2] = characterMovement.crop(0,0,width,height*2);
        player_down[3] = characterMovement.crop(width*2,0,width,height*2);
        player_up = new Bitmap[4];
        player_up[0] = characterMovement.crop(width*3,0,width,height*2);
        player_up[1] = characterMovement.crop(width*4,0,width,height*2);
        player_up[2] = characterMovement.crop(width*3,0,width,height*2);
        player_up[3] = characterMovement.crop(width*5,0,width,height*2);
        player_left = new Bitmap[4];
        player_left[0] = characterMovement.crop(0,height*2,width,height*2);
        player_left[1] = characterMovement.crop(width,height*2,width,height*2);
        player_left[2] = characterMovement.crop(0,height*2,width,height*2);
        player_left[3] = characterMovement.crop(width*2,height*2,width,height*2);
        player_right = new Bitmap[4];
        player_right[0] = characterMovement.crop(width*3,height*2,width,height*2);
        player_right[1] = characterMovement.crop(width*4,height*2,width,height*2);
        player_right[2] = characterMovement.crop(width*3,height*2,width,height*2);
        player_right[3] = characterMovement.crop(width*5,height*2,width,height*2);
        player_neutral = characterMovement.crop(0,0,width,height*2);
        player_icon = characterMovement.crop(0, 0, width, height);


        player_Attack = sheet.crop(width*3, height*2, width, height);
        player_SharpWind = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.magic_firelion_big)), 4, 4, width, height);
        explosion = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.explosion_1)), 6, 1, width, height*2);

        //entity
        tree = newTownTiles.crop(0, height*3, width, height*4);
        npcSlime = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.idle_slime)), 2, 1, 128, 128);
        npcCrab = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.crab)), 6, 4, 128, 128);
        npcMushroom = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.mushroom_trader)), 1, 5, 192, 232);
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
        iceSlimeAttackLeft = new Bitmap[3];
        iceSlimeAttackLeft[0] = iceSlimeAttack.crop(0,height,width,height);
        iceSlimeAttackLeft[1] = iceSlimeAttack.crop(width,height,width,height);
        iceSlimeAttackLeft[2] = iceSlimeAttack.crop(width*2,height,width,height);
        iceSlimeAttackRight = new Bitmap[3];
        iceSlimeAttackRight[0] = iceSlimeAttack.crop(0,0,width,height);
        iceSlimeAttackRight[1] = iceSlimeAttack.crop(width,0,width,height);
        iceSlimeAttackRight[2] = iceSlimeAttack.crop(width*2,0,width,height);
        iceSlimeMovementLeft = new Bitmap[3];
        iceSlimeMovementLeft[0] = iceSlimeMovement.crop(0,height,width,height);
        iceSlimeMovementLeft[1] = iceSlimeMovement.crop(width,height,width,height);
        iceSlimeMovementLeft[2] = iceSlimeMovement.crop(width*2,height,width,height);
        iceSlimeMovementRight = new Bitmap[3];
        iceSlimeMovementRight[0] = iceSlimeMovement.crop(0,0,width,height);
        iceSlimeMovementRight[1] = iceSlimeMovement.crop(width,0,width,height);
        iceSlimeMovementRight[2] = iceSlimeMovement.crop(width*2,0,width,height);

        trappedSpiritMovementRight = new Bitmap[3];
        trappedSpiritMovementRight[0] = trappedSpirit.crop(0,0,width,height);
        trappedSpiritMovementRight[1] = trappedSpirit.crop(width,0,width,height);
        trappedSpiritMovementRight[2] = trappedSpirit.crop(width*2,0,width,height);
        trappedSpiritMovementUp = trappedSpirit.crop(width*3,0,width,height);
        trappedSpiritMovementLeft = new Bitmap[3];
        trappedSpiritMovementLeft[0] = trappedSpirit.crop(0,height,width,height);
        trappedSpiritMovementLeft[1] = trappedSpirit.crop(width,height,width,height);
        trappedSpiritMovementLeft[2] = trappedSpirit.crop(width*2,height,width,height);
        trappedSpiritMovementDown = trappedSpirit.crop(width*3,height,width,height);

        trappedSpiritAttackRight = new Bitmap[3];
        trappedSpiritAttackRight[0] = trappedSpirit.crop(0,height*2,width,height);
        trappedSpiritAttackRight[1] = trappedSpirit.crop(width,height*2,width,height);
        trappedSpiritAttackRight[2] = trappedSpirit.crop(width*2,height*2,width,height);
        trappedSpiritAttackUp = trappedSpirit.crop(width*3,height*2,width,height);
        trappedSpiritAttackLeft = new Bitmap[3];
        trappedSpiritAttackLeft[0] = trappedSpirit.crop(0,height*3,width,height);
        trappedSpiritAttackLeft[1] = trappedSpirit.crop(width,height*3,width,height);
        trappedSpiritAttackLeft[2] = trappedSpirit.crop(width*2,height*3,width,height);
        trappedSpiritAttackDown = trappedSpirit.crop(width*3,height*3,width,height);

        ice_spike = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_spike)), 7, 1, 128, 128);

        //items
        wood = sheet1.crop(width*7,height,width,height);
        bottle = items.crop(iWidth, 0, iWidth, iHeight);
        slimeGel = items.crop(0, 0, iWidth, iHeight);

        apple = ImageLoader.loadImage(R.drawable.apple);
        syrup = items.crop(iWidth*2, 0, iWidth, iHeight);
        redPotion1 = potions.crop(0, iHeight, iWidth, iHeight);
        greenPotion1 = potions.crop(0, 0, iWidth, iHeight);

        swords = new Bitmap[5];
        for(int y = 0; y < 3; y++)
            swords[y] = items.crop(0, iHeight+2*y*iHeight, iWidth, iHeight);
        axes = new Bitmap[5];
        for(int y = 0; y < 3; y++)
            axes[y] = items.crop(iWidth, iHeight+2*y*iHeight, iWidth, iHeight);
        roundShields = new Bitmap[5];
        for(int y = 0; y < 3; y++)
            roundShields[y] = items.crop(iWidth*2, iHeight+2*y*iHeight, iWidth, iHeight);
        armours = new Bitmap[5];
        for(int y = 0; y < 3; y++)
            armours[y] = items.crop(0, iHeight*2+2*y*iHeight, iWidth, iHeight);
        heaterShields = new Bitmap[5];
        for(int y = 0; y < 3; y++)
            heaterShields[y] = items.crop(iWidth*2, iHeight*2+2*y*iHeight, iWidth, iHeight);


        blueSqr = ImageLoader.loadImage(R.drawable.selected);
        redSqr = ImageLoader.loadImage(R.drawable.red_square);

        //UI
        splashScreen = ImageLoader.loadImage(R.drawable.antiland_splash_page);
        splashBackground = ImageLoader.loadImage(R.drawable.splash_background);

        inventoryScreen = ImageLoader.loadImage(R.drawable.inventory_screen_new);
        craftingScreen = ImageLoader.loadImage(R.drawable.craft_screen);
        missionScreen = ImageLoader.loadImage(R.drawable.mission_screen);
        skillScreen = ImageLoader.loadImage(R.drawable.skill_selection_screen);
        statsScreen = ImageLoader.loadImage(R.drawable.status_screen);
        tradeScreen = ImageLoader.loadImage(R.drawable.trade_screen);
        mapBackground = ImageLoader.loadImage(R.drawable.map_paper);

        popup1 = ImageLoader.loadImage(R.drawable.popup_1);
        popup2 = ImageLoader.loadImage(R.drawable.popup_2);
        popupButton1 = sheet1.crop(width*4, height*2, width*2, height);
        popupButton2 = sheet1.crop(width*6, height*2, width*2, height);
        greyDisk = sheet1.crop(width*3, height*2, width, height);
        switchFlip = sheet1.crop(width*3, height, width, height);
        close = sheet1.crop(width*6, height, width, height);
        save = sheet1.crop(0, height*3, width, height);
        chat = sheet1.crop(width, height*3, width, height);
        operate = sheet1.crop(width*2, height*3, width, height);
        tradeInteract = sheet1.crop(width*3, height*3, width, height);

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

        //map
        worldMap_0 = ImageLoader.loadImage(R.drawable.map_0);
        localMap_0 = ImageLoader.loadImage(R.drawable.localmap_0);
        portalIcon = mapIconSheet.crop(0, 0, iWidth, iHeight);
        exitIcon = mapIconSheet.crop(iWidth, 0, iWidth, iHeight);
        combatIcon = mapIconSheet.crop(iWidth*2, 0, iWidth, iHeight);
        bossIcon = mapIconSheet.crop(iWidth*3, 0, iWidth, iHeight);
        unknownIcon = mapIconSheet.crop(iWidth*4, 0, iWidth, iHeight);
        objectiveIcon = mapIconSheet.crop(iWidth*5, 0, iWidth, iHeight);

        //headSign
        headSignOrange = headSignSheet.crop(0, 0, 64, 64);
        headSignGray = headSignSheet.crop(0, 64, 64, 64);
        hsoGetMission = headSignSheet.crop(64, 0, 64, 64);
        hsgGetMission = headSignSheet.crop(64, 64, 64, 64);
        hsoMissionComplete = headSignSheet.crop(64*2, 0, 64, 64);
        hsgMissionComplete = headSignSheet.crop(64*2, 64, 64, 64);
        hsoTrade = headSignSheet.crop(64*3, 0, 64, 64);
        hsgTrade = headSignSheet.crop(64*3, 64, 64, 64);

        //overlays
        extend = sheet1.crop(width*5, height*3, width, height);
        collapse = sheet1.crop(width*6, height*3, width, height);

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

        strengthSkills = new Bitmap[6];
        strengthSkillsG = new Bitmap[6];
        strengthSkillsG[0] = skillSheet.crop(width*5, height, width, height);
        strengthSkills[0] = skillSheet.crop(width*6, height, width, height);
        for(int i = 0; i < 4; i++){
            strengthSkillsG[i+1] = skillSheet.crop(width*2*i, height*2, width, height);
            strengthSkills[i+1] = skillSheet.crop(width*(2*i+1), height*2, width, height);
        }

        unlock = skillSheet.crop(width*5, 0, 250, 128);

        NULL = skillSheet.crop(width*7, 0, width, height);
        grey_transparent = sheet1.crop(width*4, height*3, width, height);

    }

    /** Loading an entire SpriteSheet as a Bitmap array (for animations)
     * @param xBlocks number of sub images in the width of the SpriteSheet
     * @param yBlocks number of sub images in the height of the SpriteSheet
     * @param height the height of a sub image, in pixels
     * @param width the width of a sub image, in pixels
     * @return the array of sub images in order
     */
    public static Bitmap[] loadSpriteAsArray(SpriteSheet spriteSheet, int xBlocks, int yBlocks, int width, int height){
        Bitmap[] images = new Bitmap[xBlocks*yBlocks];
        for(int y = 0; y < yBlocks; y++){
            for(int x = 1; x <= xBlocks; x++){
                images[y * xBlocks + x - 1] = spriteSheet.crop((x-1)*width, y*height, width, height);
            }
        }
        return images;
    }

}
