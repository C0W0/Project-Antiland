package com.walfen.antiland.states;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.world.World;

import java.util.ArrayList;

public class GameState extends State {

    private World world;

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
            world.getPlayer().postdraw(canvas);
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(uiManager != null)
            uiManager.onTouchEvent(event);
    }

    @Override
    public void init() {
        uiManager = new UIManager(handler);
        world = new World(handler);
        initDefaultUI();
        handler.setWorld(world);
    }

    private void initDefaultUI(){
        uiManager.createJoystick();
        uiManager.addUIObject(new UIImageButton(64, 64, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getWorld().getPlayer().getInventory().setActive()));
        uiManager.addUIObject(new UIImageButton(64, 256, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getWorld().getPlayer().getFabricator().setActive()));
        uiManager.addUIObject(new UIImageButton(64, 448, 128, 128,
                new Bitmap[]{Assets.joystick_pad, Assets.joystick_controller}, () -> handler.getWorld().getPlayer().getMissionManager().setActive()));
//        uiManager.hideUI();
//        ArrayList<Conversation> c = new ArrayList<>();
//        c.add(new Conversation("test build 1 wetega reeeeeeee eeeeeeee eee eeeeee eee eeeeee rerrr rrrrrrrr rrr rrrrrrr r rrrrrr rrrrrrrrrr rrr a", Assets.player_neutral, false));
//        c.add(new Conversation("test build 2 ftr saf grwww wwwww wwww wwwwww wwww wwwwww wewet agrwg wr ggggg ggg gggggg gggg gg gggggggg ggggg ggg", Assets.npcCrab[0], true));
//        uiManager.getConvBox().setConversationList(c, () -> System.out.println("complete"));
//        uiManager.getConvBox().setActive();
    }
}
