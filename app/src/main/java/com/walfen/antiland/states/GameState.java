package com.walfen.antiland.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.properties.effect.special.BraveHeart;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.bars.BarA;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.overlay.MissionPanel;
import com.walfen.antiland.ui.overlay.EnemyInfoPanel;
import com.walfen.antiland.ui.decorative.UITextDecoration;
import com.walfen.antiland.world.World;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class GameState extends State {

    private World world;
    private Player player;

    public GameState(Handler handler){
        super(handler);
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
        if(world != null){
            world.draw(canvas);
            uiManager.draw(canvas);
            player.postdraw(canvas);
        }
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
        uiManager = new UIManager(handler);
        player = new Player(handler);
        initDefaultUI();
        player.loadPlayer(path, uiManager);
        world = new World(handler, path);
        handler.setWorld(world);
    }

    private void initDefaultUI(){
        uiManager.getCGUI().initCriticalGameUI(player);
        uiManager.addUIObject(new UIImageButton(16, 16, 144, 144,
                new Bitmap[]{Assets.player_neutral, Assets.player_neutral}, () -> handler.getPlayer().getStatsWindow().setActive()));
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
        uiManager.addUIObject(new UIImageButton(32, Constants.SCREEN_HEIGHT-456, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getInventory().setActive()));
        uiManager.addUIObject(new TextButton(128, 416, 40, "Debug", Color.BLUE, this::test));
    }

    private void saveGame(){
        try{
            String date = Calendar.getInstance().getTime().toString();
            File saveFile = new File(Constants.DIR+"/main/save.wld");
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            PrintWriter dateWriter = new PrintWriter(saveFile);
            dateWriter.println(date);
            dateWriter.close();
            world.saveMap(Constants.DIR+"/main");
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
            PrintWriter dateWriter = new PrintWriter(saveFile);
            dateWriter.println(date);
            dateWriter.close();
            world.saveMap(Constants.DIR+"/auto");
            player.savePlayer(Constants.DIR+"/auto");
        }catch (IOException e){
            e.printStackTrace();
            uiManager.popUpMessage("Game file corrupted, please re-install the game");
        }
    }

    public Player getPlayer(){
        return player;
    }

    //for debugging purpose
    private void test(){
//        uiManager.popUpAction("\"Can you still move?\" A weird and spooky voices wakes you up from inside.", "...",
//                () -> uiManager.activeTutorial("Tutorial: Use the left joystick to move around", uiManager.getCGUI().getMovementJoystick().getBounds()));
        player.addEffect(new BraveHeart(player, 5000, 5));
    }
}
