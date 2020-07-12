package com.walfen.antiland.states;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.keyIO.SimpleInputField;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        uiManager.addUIObject(new TextButton(Constants.SCREEN_WIDTH/2.f, Constants.SCREEN_HEIGHT/2.f-300,
                40, "new game", Color.BLACK,
                () -> uiManager.popUpMessage("This will wipe out all existing save files, " +
                        "are you sure you want to proceed?", this::createNewSaveDocument)));
        uiManager.addUIObject(new TextButton(Constants.SCREEN_WIDTH/2.f, Constants.SCREEN_HEIGHT/2.f-100,
                40, "from save file", Color.BLACK, () -> startGameFromPath(Constants.DIR+"/main")));
        uiManager.addUIObject(new TextButton(Constants.SCREEN_WIDTH/2.f, Constants.SCREEN_HEIGHT/2.f+100,
                40, "from autosave file", Color.BLACK, () -> System.out.println("loading from autosave file")));
    }

    @Override
    public void update(){
        uiManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), paint); // placeholder for background
        paint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(Constants.SCREEN_WIDTH/2-300, Constants.SCREEN_HEIGHT/2-400,
                Constants.SCREEN_WIDTH/2+300, Constants.SCREEN_HEIGHT/2+400), paint); // placeholder for loading
        Rect r = new Rect();
        paint.setTextSize(30);
        paint.getTextBounds(Constants.GAME_VERSION_DISPLAY, 0, Constants.GAME_VERSION_DISPLAY.length(), r);
        canvas.drawText(Constants.GAME_VERSION_DISPLAY, Constants.SCREEN_WIDTH-r.width()-10, Constants.SCREEN_HEIGHT-10, paint);
        uiManager.draw(canvas);
    }

    private void createNewSaveDocument(){
        String tileName = "tiles.wld", entityName = "entity.wld";
        try {
            Utils.deleteDirectoryFiles(new File(Constants.DIR+"/main"));
            Utils.deleteDirectoryFiles(new File(Constants.DIR+"/auto"));
            copyFileFromAssets(Constants.DIR+"/main", tileName);
            copyFileFromAssets(Constants.DIR+"/main", entityName);
            copyFileFromAssets(Constants.DIR+"/auto", tileName);
            copyFileFromAssets(Constants.DIR+"/auto", entityName);
        }catch (IOException e){
            uiManager.popUpMessage("Game file corrupted, please re-install the game", () -> {});
        }
    }

    private void startGameFromPath(String path){
        try{
            GameState gs = (GameState)handler.getGame().getGameState();
            State.setStateAndInit(gs, path);
        }catch (Exception e){
            e.printStackTrace();
            uiManager.popUpMessage("Save document not found, please create a new save document", () -> {});
        }
    }

    /** Copies the tile and entity files from assets to save directory
     *
     * @param path the path of the target folder </type: String>
     */
    private void copyFileFromAssets(String path, String fileName) throws IOException {
        //copying all entity and tile files from assets folder
        //using a for loop and name files as tiles1, tile2,.etc?
        ArrayList<String> tiles = Utils.loadFileAsArrayList(fileName);
        File file = new File(path+"/"+fileName);
        if(file.exists()){
            file.delete();
            file.createNewFile();
        }
//        System.out.println(b);
        PrintWriter writer = new PrintWriter(file);
        for(String str: tiles)
            writer.println(str);
        writer.close();
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

}
