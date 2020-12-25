package com.walfen.antiland.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.bars.BarA;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.decorative.UITextDecoration;
import com.walfen.antiland.untils.Utils;
import com.walfen.antiland.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class GameState extends State {

    private boolean disabled;
    private int transparency;
    private World world;
    private Player player;
    private String currentPath;
    private ArrayList<World> worlds;
    private int worldIndex;

    public GameState(Handler handler){
        super(handler);
        worlds = new ArrayList<>();
        disabled = false;
        transparency = 0;
    }

    @Override
    public void update() {
        if(world != null) {
            world.update();
            uiManager.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(world != null && transparency <= 255){
            world.draw(canvas);
            uiManager.draw(canvas);
            player.postDraw(canvas);
        }
        if(disabled){
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            transparency += 5;
            paint.setAlpha(Math.min(transparency, 255));
            canvas.drawRect(new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), paint);
        }
        uiManager.postDraw(canvas);
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(uiManager != null)
            uiManager.onTouchEvent(event);
    }

    @Override
    public void onKeyDown(int keyCode, KeyEvent event) {
        if(uiManager != null)
            uiManager.getKeyIOManager().onKeyDown(keyCode, event);
    }

    @Override
    public void onKeyLongPress(int keyCode, KeyEvent event) {
        if(uiManager != null)
            uiManager.getKeyIOManager().onKeyLongPress(keyCode, event);
    }

    public void init(String path) {
        disabled = false;
        transparency = 0;
        uiManager = new UIManager(handler);
        player = new Player(handler);
        initDefaultUI();
        player.loadPlayer(path, uiManager);
        worlds.add(new World(handler, path, 0));
        worlds.add(new World(handler, path, 1));
        worlds.add(new World(handler, path, 2));
//        worlds.add(new World(handler, path, 2));
        try {
            worldIndex = Utils.parseInt(Utils.loadFileAsArrayList(new FileInputStream(new File(path+"/save.wld"))).get(1));
            world = worlds.get(worldIndex);
            handler.setWorld(world);
        }catch (IOException e){
            e.printStackTrace();
        }
        currentPath = path;
    }

    private void initDefaultUI(){
        uiManager.getCGUI().initCriticalGameUI(player);
        uiManager.addUIObject(new UIImageButton(16, 16, 144, 144,
                new Bitmap[]{Assets.player_icon, Assets.player_icon}, () -> handler.getPlayer().getStatsWindow().setActive()));
        uiManager.addUIObject(new BarA(192,32,600, Assets.hp_bar,
                () -> handler.getPlayer().getMaxHp(), () -> handler.getPlayer().getHealth()));
        uiManager.addUIObject(new BarA(192, 102, 512, Assets.mp_bar,
                () -> handler.getPlayer().getMaxMp(), () -> handler.getPlayer().getMp()));
        uiManager.addUIObject(new BarA(Constants.SCREEN_WIDTH/2.f-512, Constants.SCREEN_HEIGHT-35,
                1024, 32, Assets.dark_blue_bar, () -> handler.getPlayer().getCurrLevelMaxXp(),
                () -> handler.getPlayer().getCurrLevelXp()));
        uiManager.addUIObject(new UITextDecoration(Constants.SCREEN_WIDTH/2.f-512-64, Constants.SCREEN_HEIGHT-25,
                () -> Integer.toString(handler.getPlayer().getLevel()), 40, Constants.TEXT_COLOUR));
        uiManager.addUIObject(new UIImageButton(64, 224, 128, 128,
                Assets.save, this::saveGame));
        uiManager.addUIObject(new UIImageButton(8, Constants.SCREEN_HEIGHT-8-128, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getInventory().setActive()));
        uiManager.addUIObject(new UIImageButton(Constants.SCREEN_WIDTH-8-128, Constants.SCREEN_HEIGHT-8-128, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getMapManager().setActive()));
        uiManager.addUIObject(new TextButton(128, 416, 40, "Debug", Color.BLUE, this::test));
    }

    private void saveGame(){
        try{
            String date = Calendar.getInstance().getTime().toString();
            File saveFile = new File(Constants.DIR+"/main/save.wld");
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            PrintWriter saveWriter = new PrintWriter(saveFile);
            saveWriter.println(date);
            saveWriter.println(world.getIndex());
            saveWriter.close();
            for(World w: worlds)
                w.saveMap(Constants.DIR+"/main");
            player.savePlayer(Constants.DIR+"/main");
            Toast.makeText(handler.getGame().getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            uiManager.popUpMessage("Game file corrupted, please re-install the game");
        }
    }

    public void autoSave(){
        try{
            String date = Calendar.getInstance().getTime().toString();
            File saveFile = new File(Constants.DIR+"/auto/save.wld");
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            PrintWriter saveWriter = new PrintWriter(saveFile);
            saveWriter.println(date);
            saveWriter.println(world.getIndex());
            saveWriter.close();
            for(World w: worlds)
                w.saveMap(Constants.DIR+"/auto");
            player.savePlayer(Constants.DIR+"/auto");
        }catch (IOException e){
            e.printStackTrace();
            uiManager.popUpMessage("Game file corrupted, please re-install the game");
        }
    }

    public void playerDeath(){
        disabled = true;
        uiManager.popUpAction("You died", "Return to the menu",
                () -> {State.setState(handler.getGame().getMenuState()); worlds.clear();});
    }

    public Player getPlayer(){
        return player;
    }

    //for debugging purpose
    private void test(){
        handler.setGameWorld(2, 2048, 2048);
    }

    public void changePlayerRegion(int world, int playerX, int playerY) {
        autoSave();
        this.world = worlds.get(world);
        worldIndex = world;
        player.setLocation(playerX, playerY);
    }

    public ArrayList<World> getWorlds() {
        return worlds;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public int getWorldIndex() {
        return worldIndex;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
