package com.walfen.antiland.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.BarA;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.decorative.UITextDecoration;
import com.walfen.antiland.world.World;

import java.io.IOException;

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
        player = new Player(handler, path);
        world = new World(handler, path);
        initDefaultUI();
        handler.setWorld(world);
        player.getSkillsManager().initSkills(path, uiManager);
    }

    private void initDefaultUI(){
        uiManager.createJoystick();
        uiManager.createSkillButtons();
        uiManager.addUIObject(new UIImageButton(16, 16, 144, 144,
                new Bitmap[]{Assets.player_neutral, Assets.player_neutral}, () -> handler.getPlayer().getStatsWindow().setActive()));
        uiManager.addUIObject(new BarA(handler,192,32,600, Assets.hp_bar,
                () -> handler.getPlayer().getMaxHp(), () -> handler.getPlayer().getHealth()));
        uiManager.addUIObject(new BarA(handler, 192, 102, 512, Assets.mp_bar,
                () -> handler.getPlayer().getMaxMp(), () -> handler.getPlayer().getMp()));
        uiManager.addUIObject(new BarA(handler, Constants.SCREEN_WIDTH/2.f-512, Constants.SCREEN_HEIGHT-35,
                1024, 32, Assets.dark_blue_bar, () -> handler.getPlayer().getCurrLevelMaxXp(),
                () -> handler.getPlayer().getCurrLevelXp()));
        uiManager.addUIObject(new UITextDecoration(Constants.SCREEN_WIDTH/2.f-512-64, Constants.SCREEN_HEIGHT-25,
                () -> Integer.toString(handler.getPlayer().getLevel()), 40, Constants.TEXT_COLOUR));
        uiManager.addUIObject(new UIImageButton(64, 224, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, this::saveGame));
        uiManager.addUIObject(new UIImageButton(32, Constants.SCREEN_HEIGHT-232, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getMissionManager().setActive()));
        uiManager.addUIObject(new UIImageButton(32, Constants.SCREEN_HEIGHT-456, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getInventory().setActive()));

//        uiManager.hideUI();
//        ArrayList<Conversation> c = new ArrayList<>();
//        c.add(new Conversation("test build 1 wetega reeeeeeee eeeeeeee eee eeeeee eee eeeeee rerrr rrrrrrrr rrr rrrrrrr r rrrrrr rrrrrrrrrr rrr a", Assets.player_neutral, false));
//        c.add(new Conversation("test build 2 ftr saf grwww wwwww wwww wwwwww wwww wwwwww wewet agrwg wr ggggg ggg gggggg gggg gg gggggggg ggggg ggg", Assets.npcCrab[0], true));
//        uiManager.getConvBox().setConversationList(c, () -> System.out.println("complete"));
//        uiManager.getConvBox().setActive();
    }

    private void saveGame(){
        try{
            world.saveMap(Constants.DIR+"/main");
            player.savePlayer(Constants.DIR+"/main");
        }catch (IOException e){
            e.printStackTrace();
            uiManager.popUpMessage("Game file corrupted, please re-install the game");
        }
    }

    public Player getPlayer(){
        return player;
    }
}
