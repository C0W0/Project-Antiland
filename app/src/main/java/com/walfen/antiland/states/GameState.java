package com.walfen.antiland.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.HealthBar;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.world.World;

import java.util.ArrayList;

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

    @Override
    public void init() {
        uiManager = new UIManager(handler);
        player = new Player(handler, Constants.DEFAULT_SIZE,Constants.DEFAULT_SIZE);
        world = new World(handler);
        initDefaultUI();
        handler.setWorld(world);
    }

    private void initDefaultUI(){
        uiManager.createJoystick();
        uiManager.addUIObject(new HealthBar(handler,256,100,640));
        uiManager.addUIObject(new UIImageButton(64, 64, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getInventory().setActive()));
        uiManager.addUIObject(new UIImageButton(64, 256, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getFabricator().setActive()));
        uiManager.addUIObject(new UIImageButton(64, 448, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getPlayer().getMissionManager().setActive()));
//        uiManager.hideUI();
//        ArrayList<Conversation> c = new ArrayList<>();
//        c.add(new Conversation("test build 1 wetega reeeeeeee eeeeeeee eee eeeeee eee eeeeee rerrr rrrrrrrr rrr rrrrrrr r rrrrrr rrrrrrrrrr rrr a", Assets.player_neutral, false));
//        c.add(new Conversation("test build 2 ftr saf grwww wwwww wwww wwwwww wwww wwwwww wewet agrwg wr ggggg ggg gggggg gggg gg gggggggg ggggg ggg", Assets.npcCrab[0], true));
//        uiManager.getConvBox().setConversationList(c, () -> System.out.println("complete"));
//        uiManager.getConvBox().setActive();
    }

    public Player getPlayer(){
        return player;
    }
}
