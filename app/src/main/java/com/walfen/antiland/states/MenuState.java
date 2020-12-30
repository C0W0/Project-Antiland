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
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.ui.buttons.TextButton;
import com.walfen.antiland.ui.buttons.UIImageButton;
import com.walfen.antiland.ui.decorative.UIDecoration;
import com.walfen.antiland.untils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class MenuState extends State {

    private UIManager uiManager;
    private UIObject[] menuObjects;
    private Bitmap splashScreen, splashBackground;
    private int xDispute, yDispute;
    private boolean isMenuOn = false;

    public MenuState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        splashScreen = ImageEditor.scaleBitmap(Assets.splashScreen, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        splashBackground = ImageEditor.scaleBitmapForced(Assets.splashBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        xDispute = Constants.SCREEN_WIDTH/2 - splashScreen.getWidth()/2;
        yDispute = Constants.SCREEN_HEIGHT/2 - splashScreen.getHeight()/2;
        menuObjects = new UIObject[7];
        menuObjects[0] = new UIDecoration(Constants.SCREEN_WIDTH/2.f-300, Constants.SCREEN_HEIGHT/2.f-400+150, 600, 650, Assets.menuFrame);
        menuObjects[1] = new UIImageButton(Constants.SCREEN_WIDTH/2.f-226, Constants.SCREEN_HEIGHT/2.f-332+150, 256, 128,
                Assets.newGameButton, () -> uiManager.popUpMessage("This will wipe out all existing save files, " +
                "are you sure you want to proceed?", this::createNewSaveDocument));
        menuObjects[2] = new UIImageButton(Constants.SCREEN_WIDTH/2.f-226, Constants.SCREEN_HEIGHT/2.f-164+150, 384, 128,
                Assets.startFromSave, () -> startGameFromPath(Constants.DIR+"/main"));
        menuObjects[3] = new UIImageButton(Constants.SCREEN_WIDTH/2.f-226, Constants.SCREEN_HEIGHT/2.f+36+150, 512, 128,
                Assets.startFromAutoSave, () -> startGameFromPath(Constants.DIR+"/auto"));
        menuObjects[4] = new UIImageButton(8, Constants.SCREEN_HEIGHT-8-128, 128, 128, Assets.goBack, this::switchMenu);
        for(int i = 0; i < 5; i++)
            menuObjects[i].setActive(false);
        menuObjects[5] = new UIImageButton(Constants.SCREEN_WIDTH/2.f-428, Constants.SCREEN_HEIGHT/2.f+236, 256, 128, Assets.playButton, this::switchMenu);
        menuObjects[6] = new UIImageButton(Constants.SCREEN_WIDTH/2.f+172, Constants.SCREEN_HEIGHT/2.f+236, 256, 128, Assets.creditsButton,
                () -> {handler.getGame().getCreditsState().init(); State.setState(handler.getGame().getCreditsState());});
        uiManager.addUIObject(menuObjects);
    }

    //for debugging purpose
    private void test(){

    }

    @Override
    public void update(){
        uiManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
//        canvas.drawRect(new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), paint); // placeholder for background
        canvas.drawBitmap(splashBackground, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), Constants.getRenderPaint());
        canvas.drawBitmap(splashScreen, null, new Rect(xDispute, yDispute, xDispute+splashScreen.getWidth(), yDispute+splashScreen.getHeight()), Constants.getRenderPaint());

        paint.setColor(Color.WHITE);
//        paint.setAlpha(180);
//        canvas.drawRect(new Rect(Constants.SCREEN_WIDTH/2-300, Constants.SCREEN_HEIGHT/2-400,
//                Constants.SCREEN_WIDTH/2+300, Constants.SCREEN_HEIGHT/2+300), paint); // placeholder for loading
//        paint.setAlpha(255);
        Rect r = new Rect();
        paint.setTextSize(30);
        paint.getTextBounds(Constants.GAME_VERSION_DISPLAY, 0, Constants.GAME_VERSION_DISPLAY.length(), r);
        canvas.drawText(Constants.GAME_VERSION_DISPLAY, Constants.SCREEN_WIDTH-r.width()-100, Constants.SCREEN_HEIGHT-10, paint);
        paint.setColor(Color.BLACK);
        uiManager.draw(canvas);
        if(isMenuOn){
            try{
                String date = Utils.loadFileAsArrayList(new FileInputStream(new File(Constants.DIR+"/main/save.wld"))).get(0);
                paint.setTextSize(25);
                paint.getTextBounds(date, 0, date.length(), r);
                canvas.drawText(date, Constants.SCREEN_WIDTH/2.f-r.width()/2.f+20, Constants.SCREEN_HEIGHT/2.f-80+r.height()+140, paint);
            }catch (IOException ignored){ }
            try {
                String date = Utils.loadFileAsArrayList(new FileInputStream(new File(Constants.DIR+"/auto/save.wld"))).get(0);
                paint.getTextBounds(date, 0, date.length(), r);
                canvas.drawText(date, Constants.SCREEN_WIDTH/2.f-r.width()/2.f+20, Constants.SCREEN_HEIGHT/2.f+120+r.height()+140, paint);
            }catch (IOException ignored){ }
            uiManager.postDraw(canvas);
        }
    }

    private void createNewSaveDocument(){
        String tileNameGeneral = "tiles", entityNameGeneral = "entity",
                playerName = "player.wld", inventoryName = "inventory.wld",
        missionName = "missions.wld", skillName = "skills.wld", statusName = "effects.wld",
        mapNameGeneral = "map", globalMap = "globalMap.wld";
        try {
            Utils.deleteDirectoryFiles(new File(Constants.DIR+"/main"));
            Utils.deleteDirectoryFiles(new File(Constants.DIR+"/auto"));
            new File(Constants.DIR+"/auto/player").mkdirs();
            new File(Constants.DIR+"/main/player").mkdirs();
            for(int i = 0; i < 3; i++){
                new File(Constants.DIR+"/auto/world"+i).mkdirs();
                new File(Constants.DIR+"/main/world"+i).mkdirs();
            }
            String date = Calendar.getInstance().getTime().toString();
            File saveFile = new File(Constants.DIR+"/main/save.wld");
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            PrintWriter saveWriter = new PrintWriter(saveFile);
            saveWriter.println(date);
            saveWriter.println("0");
            saveWriter.close();
            saveFile = new File(Constants.DIR+"/auto/save.wld");
            if(saveFile.exists())
                saveFile.delete();
            saveFile.createNewFile();
            saveWriter = new PrintWriter(saveFile);
            saveWriter.println(date);
            saveWriter.println("0");
            saveWriter.close();
            copyFileSeriesFromAssets(Constants.DIR+"/main/world", tileNameGeneral, 0, 2);
            copyFileSeriesFromAssets(Constants.DIR+"/main/world", entityNameGeneral, 0, 2);
            createFileSeries(Constants.DIR+"/main/world", mapNameGeneral, 0, 2);
            copyFileFromAssets(Constants.DIR+"/main/player", playerName);
            copyFileFromAssets(Constants.DIR+"/main/player", inventoryName);
            copyFileFromAssets(Constants.DIR+"/main/player", missionName);
            copyFileFromAssets(Constants.DIR+"/main/player", skillName);
            copyFileFromAssets(Constants.DIR+"/main/player", statusName);
            copyFileFromAssets(Constants.DIR+"/main", globalMap);
            copyFileSeriesFromAssets(Constants.DIR+"/auto/world", tileNameGeneral, 0, 2);
            copyFileSeriesFromAssets(Constants.DIR+"/auto/world", entityNameGeneral, 0, 2);
            createFileSeries(Constants.DIR+"/auto/world", mapNameGeneral, 0, 2);
            copyFileFromAssets(Constants.DIR+"/auto/player", playerName);
            copyFileFromAssets(Constants.DIR+"/auto/player", inventoryName);
            copyFileFromAssets(Constants.DIR+"/auto/player", missionName);
            copyFileFromAssets(Constants.DIR+"/auto/player", skillName);
            copyFileFromAssets(Constants.DIR+"/auto/player", statusName);
            copyFileFromAssets(Constants.DIR+"/auto", globalMap);
        }catch (IOException e){
//            uiManager.popUpMessage("Game file corrupted, please re-install the game");
            e.printStackTrace();
            return;
        }
        startGameFromPath(Constants.DIR+"/main");
    }

    private void startGameFromPath(String path){
        try{
            State.setStateAndInit(handler.getGame().getGameState(), path);
        }catch (Exception e){
//            uiManager.popUpMessage("Save document not found, please create a new save document");
            e.printStackTrace();
        }
    }

    /** Copies the tile and entity files from assets to save directory
     *
     * @param path the path of the target folder </type: String>
     */
    private void copyFileFromAssets(String path, String fileName) throws IOException {
        //copying all entity and tile files from assets folder
        ArrayList<String> lines = Utils.loadFileAsArrayList(fileName);
        File file = new File(path+"/"+fileName);
        if(file.exists()){
            file.delete();
        }
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);
        for(String str: lines)
            writer.println(str);
        writer.close();
    }

    private void copyFileSeriesFromAssets(String path, String generalFileName, int start, int end) throws IOException{
        for(int i = start; i < end+1; i++){
            copyFileFromAssets(path+i, generalFileName+i+".wld");
        }
    }

    private void createFileSeries(String path, String generalFileName, int start, int end) throws IOException{
        for(int i = start; i < end+1; i++){
            File file = new File(path+i+"/"+generalFileName+i+".wld");
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
        }
    }

    private void switchMenu(){
        isMenuOn = !isMenuOn;
        for(UIObject o: menuObjects)
            o.setActive();
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
