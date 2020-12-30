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
            beachShoreDown1, beachShoreDown2, beachGrassFlower, beachGrass1, beachGrass2, beachVerticalEast, beachVerticalWest,
            beachOceanTransition;
    public static Bitmap[][] beach_diagonals, beach_Verticals;

    public static Bitmap tt2_grass, tt3_grass;
    public static Bitmap tt2_pathVerticalLeft, tt2_pathVerticalRight, tt2_pathHorizontalTop, tt2_pathHorizontalBottom,
            tt2_pathCornerUpRight, tt2_pathCornerUpLeft, tt2_pathCornerDownLeft, tt2_pathCornerDownRight, tt2_pathCross;
    public static Bitmap tt2_roofTop, tt2_roofCommon, tt2_roofLeftTop, tt2_roofLeftMid, tt2_roofLeftBottom,
            tt2_roofRightTop, tt2_roofRightMid, tt2_roofRightBottom, tt2_halfRoofTop, tt2_SmallARoof;
    public static Bitmap tt2_window, tt2_smokestack, tt2_balcony, tt2_doorTop, tt2_doorBottom,
            tt2_wallLeftTop, tt2_wallLeftMid, tt2_wallLeftBottom, tt2_wallMidTop, tt2_wallCommon1, tt2_wallMidBottom,
            tt2_wallRightTop, tt2_wallRightMid, tt2_wallRightBottom;
    public static Bitmap tt3_pathVerticalLeft, tt3_pathVerticalRight, tt3_pathHorizontalTop, tt3_pathHorizontalBottom,
            tt3_pathCornerUpRight, tt3_pathCornerUpLeft, tt3_pathCornerDownLeft, tt3_pathCornerDownRight, tt3_pathCross;
    public static Bitmap tt3_roofTop, tt3_roofCommon, tt3_roofLeftTop, tt3_roofLeftMid, tt3_roofLeftBottom,
            tt3_roofRightTop, tt3_roofRightMid, tt3_roofRightBottom, tt3_halfRoofTop, tt3_SmallARoof;
    public static Bitmap tt3_window, tt3_smokestack, tt3_balcony, tt3_doorTop, tt3_doorBottom,
            tt3_wallLeftTop, tt3_wallLeftMid, tt3_wallLeftBottom, tt3_wallMidTop, tt3_wallCommon1, tt3_wallMidBottom,
            tt3_wallRightTop, tt3_wallRightMid, tt3_wallRightBottom;
    public static Bitmap[] drySand_diagonals, wetSand_diagonals, grassTransition;
    public static Bitmap sandTransitionLeft, sandTransitionRight, drySandVertical, wetSandVertical;
    public static Bitmap[] transition_diagonals;
    public static Bitmap holeTop, holeBottom, holeGrass;


    public static Bitmap NULL;
    public static Bitmap grey_transparent;

    //player
    public static Bitmap player_icon;
    public static Bitmap player_neutral;
    public static Bitmap[] player_down, player_up, player_left, player_right;
    public static Bitmap[] player_attack_down, player_attack_up, player_attack_left, player_attack_right;
    public static Bitmap player_Attack;
    public static Bitmap player_sharpWind;
    public static Bitmap[] player_wind;
    public static Bitmap[] explosion;

    public static Bitmap[] arcaneMissile, fireBall;

    //joystick
    public static Bitmap joystick_pad, joystick_controller;

    //ui
    public static Bitmap hp_bar, mp_bar, dark_blue_bar, bar_frame;
    public static Bitmap horizontalSlideTrack, horizontalSlider, horizontalTickMark;
    public static Bitmap adjusterUp, adjusterDown;
    public static Bitmap popup1, popup2, popupButton1, popupButton2;
    public static Bitmap greyDisk;
    public static Bitmap switchFlip, close;
    public static Bitmap save, chat, operate, tradeInteract, menuButton;
    public static Bitmap headSignOrange, headSignGray, hsoMissionComplete, hsgMissionComplete;
    public static Bitmap hsoGetMission, hsgGetMission, hsoTrade, hsgTrade;
    public static Bitmap extend, collapse;
    public static Bitmap splashScreen, splashBackground;
    public static Bitmap[] invButton, mapButton;

    //entities
    public static Bitmap tree;

    public static Bitmap[] npcSlime, slimeAttackLeft, slimeAttackRight, slimeMovementLeft, slimeMovementRight;
    public static Bitmap[] trappedSpiritAttackLeft, trappedSpiritAttackRight, trappedSpiritMovementLeft, trappedSpiritMovementRight;
    public static Bitmap  trappedSpiritAttackUp, trappedSpiritAttackDown, trappedSpiritMovementUp, trappedSpiritMovementDown;
    public static Bitmap[] npcCrab, npcMushroom;
    public static Bitmap[] iceSlimeAttackLeft, iceSlimeAttackRight, iceSlimeMovementLeft, iceSlimeMovementRight;
    public static Bitmap[] ice_spike;

    public static Bitmap rock, tree1, tree2, tree3, pier, pierHorizontal, boat1, boat2;
    public static Bitmap[] spiritWarriorMovement, spiritWarriorAttack, lostGhostMovement, lostGhostAttack;
    public static Bitmap[] crabSmith, foxKeeper, hermit, spiritLeak;
    public static Bitmap foxKeeperIcon;
    public static Bitmap npcChicken, npcCactus;

    public static Bitmap[] targetIndicator;

    //items
    public static Bitmap wood, stone, bottle, slimeGel;
    public static Bitmap apple, syrup, evilBook, steelNugget;
    public static Bitmap leatherBoots, steelBoots;
    public static Bitmap[] redPotions, greenPotions, bluePotions;
    public static Bitmap[] roundShields, heaterShields, swords, axes, armours;
    public static Bitmap key, brokenHammer, fish;
    public static Bitmap inventoryScreen, missionScreen, craftingScreen, statsScreen, skillScreen, tradeScreen;
    public static Bitmap mapBackground, worldMap_0, localMap_0,localMap_1;
    public static Bitmap portalIcon, exitIcon, combatIcon, bossIcon, unknownIcon, objectiveIcon;
    public static Bitmap blueSqr, redSqr;

    //skills
    public static Bitmap strength, endurance, agility, knowledge, intelligence;
    public static Bitmap strengthR, enduranceR, agilityR, knowledgeR, intelligenceR;
    public static Bitmap[] strengthSkillsG, strengthSkills, enduranceSkillsG, enduranceSkills, agilitySkillsG, agilitySkills;
    public static Bitmap[] knowledgeSkillsG, knowledgeSkills, intelligenceSkillsG, intelligenceSkills;

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
        SpriteSheet potions = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.potions));
        SpriteSheet items = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.items));
        SpriteSheet iceSlimeMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_slime_movement));
        SpriteSheet iceSlimeAttack = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_slime_attack));
        SpriteSheet headSignSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.head_sign));
        SpriteSheet area1Tiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.area_1_tiles));
        SpriteSheet mapIconSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.map_icons));
        SpriteSheet characterMovement = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.character_movement));
        SpriteSheet characterAttack = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.character_attack));
        SpriteSheet trappedSpirit = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.mini_boss));
        SpriteSheet beachIslandTile1 = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.beach));

        SpriteSheet newTownTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.port_town));
        SpriteSheet oldTownTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.town_tiles));
        SpriteSheet harbourTiles = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.far_harbour));
        SpriteSheet npcSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.island_npc));
        SpriteSheet ghostEntitySheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.spirit_entity));
        SpriteSheet staticEntities = new SpriteSheet(ImageLoader.loadImage(R.drawable.island_static_entity));

        SpriteSheet magicSheet = new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.magic_sheet));

        grass = newTownTiles.crop(width*11,0,width,height);
        grassStone = sheet1.crop(width*3,height,width,height);
        dirt = townTiles.crop(width,height,width,height);
        dirtStone = sheet1.crop(width*6,height,width,height);
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

        tt2_grass = oldTownTiles.crop(width*11,0,width,height);

        tt2_pathVerticalLeft = oldTownTiles.crop(width*6,height,width,height);
        tt2_pathVerticalRight = oldTownTiles.crop(width*8,height,width,height);
        tt2_pathHorizontalTop = oldTownTiles.crop(width*7,0,width,height);
        tt2_pathHorizontalBottom = oldTownTiles.crop(width*7,height*2,width,height);
        tt2_pathCornerUpRight = oldTownTiles.crop(width*6,0,width,height);
        tt2_pathCornerUpLeft = oldTownTiles.crop(width*8,0,width,height);
        tt2_pathCornerDownLeft = oldTownTiles.crop(width*6,height*2,width,height);
        tt2_pathCornerDownRight = oldTownTiles.crop(width*8,height*2,width,height);
        tt2_pathCross = oldTownTiles.crop(width*11,height,width,height);

        tt2_roofTop = oldTownTiles.crop(0, 0, width, height);
        tt2_roofCommon = oldTownTiles.crop(width, 0, width, height);
        tt2_roofLeftTop = oldTownTiles.crop(width*9, 0, width, height);
        tt2_roofLeftMid = oldTownTiles.crop(width*9, height, width, height);
        tt2_roofLeftBottom = oldTownTiles.crop(width*9, height*2, width, height);
        tt2_roofRightTop = oldTownTiles.crop(width*10, 0, width, height);
        tt2_roofRightMid = oldTownTiles.crop(width*10, height, width, height);
        tt2_roofRightBottom = oldTownTiles.crop(width*10, height*2, width, height);
        tt2_halfRoofTop = oldTownTiles.crop(width, height, width, height);
        tt2_SmallARoof = oldTownTiles.crop(0, height, width, height);
        tt2_window = oldTownTiles.crop(0, height*2, width, height);
        tt2_smokestack = oldTownTiles.crop(width*5, height*2, width, height);
        tt2_balcony = oldTownTiles.crop(width, height*2, width, height);
        tt2_doorTop = oldTownTiles.crop(width*5, 0, width, height);
        tt2_doorBottom = oldTownTiles.crop(width*5, height, width, height);
        tt2_wallLeftTop = oldTownTiles.crop(width*2, 0, width, height);
        tt2_wallLeftMid = oldTownTiles.crop(width*2, height, width, height);
        tt2_wallLeftBottom = oldTownTiles.crop(width*2, height*2, width, height);
        tt2_wallMidTop = oldTownTiles.crop(width*3, 0, width, height);
        tt2_wallCommon1 = oldTownTiles.crop(width*3, height, width, height);
        tt2_wallMidBottom = oldTownTiles.crop(width*3, height*2, width, height);
        tt2_wallRightTop = oldTownTiles.crop(width*4, 0, width, height);
        tt2_wallRightMid = oldTownTiles.crop(width*4, height, width, height);
        tt2_wallRightBottom = oldTownTiles.crop(width*4, height*2, width, height);

        tt3_grass = harbourTiles.crop(width*11,0,width,height);

        tt3_pathVerticalLeft = harbourTiles.crop(width*6,height,width,height);
        tt3_pathVerticalRight = harbourTiles.crop(width*8,height,width,height);
        tt3_pathHorizontalTop = harbourTiles.crop(width*7,0,width,height);
        tt3_pathHorizontalBottom = harbourTiles.crop(width*7,height*2,width,height);
        tt3_pathCornerUpRight = harbourTiles.crop(width*6,0,width,height);
        tt3_pathCornerUpLeft = harbourTiles.crop(width*8,0,width,height);
        tt3_pathCornerDownLeft = harbourTiles.crop(width*6,height*2,width,height);
        tt3_pathCornerDownRight = harbourTiles.crop(width*8,height*2,width,height);
        tt3_pathCross = harbourTiles.crop(width*11,height,width,height);

        tt3_roofTop = harbourTiles.crop(0, 0, width, height);
        tt3_roofCommon = harbourTiles.crop(width, 0, width, height);
        tt3_roofLeftTop = harbourTiles.crop(width*9, 0, width, height);
        tt3_roofLeftMid = harbourTiles.crop(width*9, height, width, height);
        tt3_roofLeftBottom = harbourTiles.crop(width*9, height*2, width, height);
        tt3_roofRightTop = harbourTiles.crop(width*10, 0, width, height);
        tt3_roofRightMid = harbourTiles.crop(width*10, height, width, height);
        tt3_roofRightBottom = harbourTiles.crop(width*10, height*2, width, height);
        tt3_halfRoofTop = harbourTiles.crop(width, height, width, height);
        tt3_SmallARoof = harbourTiles.crop(0, height, width, height);
        tt3_window = harbourTiles.crop(0, height*2, width, height);
        tt3_smokestack = harbourTiles.crop(width*5, height*2, width, height);
        tt3_balcony = harbourTiles.crop(width, height*2, width, height);
        tt3_doorTop = harbourTiles.crop(width*5, 0, width, height);
        tt3_doorBottom = harbourTiles.crop(width*5, height, width, height);
        tt3_wallLeftTop = harbourTiles.crop(width*2, 0, width, height);
        tt3_wallLeftMid = harbourTiles.crop(width*2, height, width, height);
        tt3_wallLeftBottom = harbourTiles.crop(width*2, height*2, width, height);
        tt3_wallMidTop = harbourTiles.crop(width*3, 0, width, height);
        tt3_wallCommon1 = harbourTiles.crop(width*3, height, width, height);
        tt3_wallMidBottom = harbourTiles.crop(width*3, height*2, width, height);
        tt3_wallRightTop = harbourTiles.crop(width*4, 0, width, height);
        tt3_wallRightMid = harbourTiles.crop(width*4, height, width, height);
        tt3_wallRightBottom = harbourTiles.crop(width*4, height*2, width, height);

        drySand_diagonals = new Bitmap[4];
        drySand_diagonals[0] = beachIslandTile1.crop(0, height*4, width, height);
        drySand_diagonals[1] = beachIslandTile1.crop(width, height*4, width, height);
        drySand_diagonals[2] = beachIslandTile1.crop(0, height*5, width, height);
        drySand_diagonals[3] = beachIslandTile1.crop(width, height*5, width, height);
        wetSand_diagonals = new Bitmap[4];
        wetSand_diagonals[0] = beachIslandTile1.crop(0, height*6, width, height);
        wetSand_diagonals[1] = beachIslandTile1.crop(width, height*6, width, height);
        wetSand_diagonals[2] = beachIslandTile1.crop(0, height*7, width, height);
        wetSand_diagonals[3] = beachIslandTile1.crop(width, height*7, width, height);
        grassTransition = new Bitmap[12];
        for(int i = 0; i < 6; i++)
            grassTransition[i] = beachIslandTile1.crop(width*i, height*8, width, height);
        grassTransition[6] = beachIslandTile1.crop(width*6, height*5, width, height);
        grassTransition[7] = beachIslandTile1.crop(width*7, height*5, width, height);
        grassTransition[8] = beachIslandTile1.crop(width*6, height*6, width, height);
        grassTransition[9] = beachIslandTile1.crop(width*7, height*6, width, height);
        grassTransition[10] = beachIslandTile1.crop(width*6, height*7, width, height);
        grassTransition[11] = beachIslandTile1.crop(width*7, height*7, width, height);
        sandTransitionRight = beachIslandTile1.crop(width*2, height*4, width, height);
        sandTransitionLeft = beachIslandTile1.crop(width*2, height*5, width, height);
        wetSandVertical = beachIslandTile1.crop(width*3, height*4, width, height);
        drySandVertical = beachIslandTile1.crop(width*3, height*5, width, height);

        transition_diagonals = new Bitmap[8];
        transition_diagonals[0] = beachIslandTile1.crop(width*2, height*6, width, height);
        transition_diagonals[1] = beachIslandTile1.crop(width*3, height*6, width, height);
        transition_diagonals[2] = beachIslandTile1.crop(width*2, height*7, width, height);
        transition_diagonals[3] = beachIslandTile1.crop(width*3, height*7, width, height);
        transition_diagonals[4] = beachIslandTile1.crop(width*6, height*8, width, height);
        transition_diagonals[5] = beachIslandTile1.crop(width*7, height*8, width, height);
        transition_diagonals[6] = beachIslandTile1.crop(width*6, height*9, width, height);
        transition_diagonals[7] = beachIslandTile1.crop(width*7, height*9, width, height);

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
        beachOceanTransition = beachIslandTile1.crop(0, height*3, width, height);
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
        holeTop = newTownTiles.crop(width*12, 0, width, height);
        holeBottom = newTownTiles.crop(width*12, height, width, height);
        holeGrass = newTownTiles.crop(width*12, height*2, width, height);


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

        player_attack_left = new Bitmap[4];
        player_attack_left[3] = characterAttack.crop(0,0,width,height*2);
        player_attack_left[2] = characterAttack.crop(width,0,width,height*2);
        player_attack_left[1] = characterAttack.crop(width*2,0,width,height*2);
        player_attack_left[0] = characterAttack.crop(width*3,0,width,height*2);
        player_attack_right = new Bitmap[4];
        player_attack_right[3] = characterAttack.crop(0,height*2,width,height*2);
        player_attack_right[2] = characterAttack.crop(width,height*2,width,height*2);
        player_attack_right[1] = characterAttack.crop(width*2,height*2,width,height*2);
        player_attack_right[0] = characterAttack.crop(width*3,height*2,width,height*2);
        player_attack_up = new Bitmap[4];
        player_attack_up[3] = characterAttack.crop(0,height*4,width,height*2);
        player_attack_up[2] = characterAttack.crop(width,height*4,width,height*2);
        player_attack_up[1] = characterAttack.crop(width*2,height*4,width,height*2);
        player_attack_up[0] = characterAttack.crop(width*3,height*4,width,height*2);
        player_attack_down = new Bitmap[4];
        player_attack_down[3] = characterAttack.crop(0,height*6,width,height*2);
        player_attack_down[2] = characterAttack.crop(width,height*6,width,height*2);
        player_attack_down[1] = characterAttack.crop(width*2,height*6,width,height*2);
        player_attack_down[0] = characterAttack.crop(width*3,height*6,width,height*2);


        player_Attack = sheet.crop(width*3, height*2, width, height);
        player_sharpWind = sheet1.crop(0, height*6, width, height);
        player_wind = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.magic_gray_big)), 4, 4, width, height);
        explosion = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.explosion_1)), 6, 1, width, height*2);

        fireBall = new Bitmap[4];
        for(int i = 0; i < 4; i++)
            fireBall[i] = magicSheet.crop(width*i, 0, width, height);
        arcaneMissile = new Bitmap[4];
        for(int i = 0; i < 4; i++)
            arcaneMissile[i] = magicSheet.crop(width*i, height, width, height);

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

        spiritWarriorMovement = new Bitmap[4];
        for(int i = 0; i < 4; i++)
            spiritWarriorMovement[i] = ghostEntitySheet.crop(width*i, 0, width, height);
        spiritWarriorAttack = new Bitmap[4];
        for(int i = 0; i < 4; i++)
            spiritWarriorAttack[i] = ghostEntitySheet.crop(width*(i+4), 0, width, height);
        lostGhostMovement = new Bitmap[3];
        for(int i = 0; i < 3; i++)
            lostGhostMovement[i] = ghostEntitySheet.crop(width*i, height, width, height);
        lostGhostAttack = new Bitmap[3];
        for(int i = 0; i < 3; i++)
            lostGhostAttack[i] = ghostEntitySheet.crop(width*(i+3), height, width, height);
        
        rock = staticEntities.crop(0, 0, width, height);
        tree1 = staticEntities.crop(width, 0, width*2, height*4);
        tree2 = staticEntities.crop(width*3, 0, width*3, height*4);
        pier = staticEntities.crop(width*6, 0, width*2, height*2);
        pierHorizontal = staticEntities.crop(0, height*7, width*3, height*2);
        tree3 = staticEntities.crop(0, height*4, width*3, height*3);
        boat1 = staticEntities.crop(width*5, height*4, width*3, height*2);
        boat2 = staticEntities.crop(width*5, height*6, width*3, height*2);
        spiritLeak = new Bitmap[2];
        spiritLeak[0] = staticEntities.crop(width*6, height*2, width, height);
        spiritLeak[1] = staticEntities.crop(width*7, height*2, width, height);

        targetIndicator = new Bitmap[2];
        targetIndicator[0] = staticEntities.crop(0, height, width, height);
        targetIndicator[1] = staticEntities.crop(0, height*2, width, height);

        ice_spike = loadSpriteAsArray(new SpriteSheet(ImageLoader.loadSpriteSheet(R.drawable.ice_spike)), 7, 1, 128, 128);

        foxKeeper = new Bitmap[8];
        foxKeeperIcon = npcSheet.crop(0, height, width, height);
        for(int i = 0; i < 4; i++)
            foxKeeper[i] = npcSheet.crop(width*2*i, 0, width*2, height);
        for(int i = 0; i < 4; i++)
            foxKeeper[i+4] = npcSheet.crop(width*2*i, height, width*2, height);
        crabSmith = new Bitmap[8];
        for(int i = 0; i < 8; i++)
            crabSmith[i] = npcSheet.crop(width*i, height*2, width, height);
        npcCactus = npcSheet.crop(0, height*3, width, height);
        npcChicken = npcSheet.crop(width, height*3, width, height);
        hermit = new Bitmap[8];
        for(int i = 0; i < 8; i++)
            hermit[i] = npcSheet.crop(width*i, height*4, width, height);

        //items
        wood = sheet1.crop(width*7,height,width,height);
        bottle = items.crop(iWidth, 0, iWidth, iHeight);
        slimeGel = items.crop(0, 0, iWidth, iHeight);

        apple = ImageLoader.loadImage(R.drawable.apple);
        syrup = items.crop(iWidth*2, 0, iWidth, iHeight);
        redPotions = new Bitmap[3];
        redPotions[0] = potions.crop(0, iHeight, iWidth, iHeight);
        redPotions[1] = potions.crop(0, iHeight*6, iWidth, iHeight);
        redPotions[2] = potions.crop(iWidth*8, iHeight, iWidth, iHeight);
        greenPotions = new Bitmap[3];
        greenPotions[0] = potions.crop(0, 0, iWidth, iHeight);
        greenPotions[1] = potions.crop(0, iHeight*5, iWidth, iHeight);
        greenPotions[2] = potions.crop(iWidth*8, 0, iWidth, iHeight);
        bluePotions = new Bitmap[3];
        bluePotions[0] = potions.crop(0, iHeight*2, iWidth, iHeight);
        bluePotions[1] = potions.crop(0, iHeight*7, iWidth, iHeight);
        bluePotions[2] = potions.crop(iWidth*8, iHeight*2, iWidth, iHeight);

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
        leatherBoots = items.crop(iWidth*3, iHeight*3, iWidth, iHeight);
        steelBoots = items.crop(iWidth*4, iHeight*3, iWidth, iHeight);
        brokenHammer = items.crop(iWidth*3, 0, iWidth, iHeight);
        key = items.crop(iWidth*4, 0, iWidth, iHeight);
        fish = items.crop(iWidth*3, iHeight, iWidth, iHeight);
        steelNugget = items.crop(iWidth*4, iHeight, iWidth, iHeight);
        evilBook = items.crop(iWidth*3, iHeight*2, iWidth, iHeight);


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
        invButton = new Bitmap[2];
        invButton[0] = sheet1.crop(0, height*4, width, height);
        invButton[1] = sheet1.crop(width, height*4, width, height);
        mapButton = new Bitmap[2];
        mapButton[0] = sheet1.crop(width*2, height*4, width, height);
        mapButton[1] = sheet1.crop(width*3, height*4, width, height);
        menuButton = sheet1.crop(0, height*5, width, height);

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
        localMap_1 = ImageLoader.loadImage(R.drawable.localmap_1);
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
        strengthSkillsG[5] = skillSheet.crop(0, height*3, width, height);
        strengthSkills[5] = skillSheet.crop(width, height*3, width, height);

        enduranceSkills = new Bitmap[4];
        enduranceSkillsG = new Bitmap[4];
        for(int i = 0; i < 3; i++){
            enduranceSkillsG[i] = skillSheet.crop(width*2*(i+1), height*3, width, height);
            enduranceSkills[i] = skillSheet.crop(width*(2*(i+1)+1), height*3, width, height);
        }
        enduranceSkillsG[3] = skillSheet.crop(0, height*4, width, height);
        enduranceSkills[3] = skillSheet.crop(width, height*4, width, height);

        agilitySkills = new Bitmap[4];
        agilitySkillsG = new Bitmap[4];
        for(int i = 0; i < 3; i++){
            agilitySkillsG[i] = skillSheet.crop(width*2*(i+1), height*4, width, height);
            agilitySkills[i] = skillSheet.crop(width*(2*(i+1)+1), height*4, width, height);
        }
        agilitySkillsG[3] = skillSheet.crop(0, height*5, width, height);
        agilitySkills[3] = skillSheet.crop(width, height*5, width, height);

        knowledgeSkills = new Bitmap[5];
        knowledgeSkillsG = new Bitmap[5];
        for(int i = 0; i < 3; i++){
            knowledgeSkillsG[i] = skillSheet.crop(width*2*(i+1), height*5, width, height);
            knowledgeSkills[i] = skillSheet.crop(width*(2*(i+1)+1), height*5, width, height);
        }
        knowledgeSkillsG[3] = skillSheet.crop(0, height*6, width, height);
        knowledgeSkills[3] = skillSheet.crop(width, height*6, width, height);
        knowledgeSkillsG[4] = skillSheet.crop(width*2, height*6, width, height);
        knowledgeSkills[4] = skillSheet.crop(width*3, height*6, width, height);

        intelligenceSkills = new Bitmap[3];
        intelligenceSkillsG = new Bitmap[3];
        for(int i = 0; i < 2; i++){
            intelligenceSkillsG[i] = skillSheet.crop(width*2*(i+2), height*6, width, height);
            intelligenceSkills[i] = skillSheet.crop(width*(2*(i+2)+1), height*6, width, height);
        }
        intelligenceSkillsG[2] = skillSheet.crop(0, height*7, width, height);
        intelligenceSkills[2] = skillSheet.crop(width, height*7, width, height);

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
