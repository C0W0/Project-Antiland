package com.walfen.antiland;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.gfx.GameCamera;
import com.walfen.antiland.states.GameState;
import com.walfen.antiland.states.State;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.world.World;

public class Handler {

    private GamePanel game;
    private World world;

    public Handler(GamePanel game){
        this.game = game;
    }

    public GamePanel getGame() {
        return game;
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }

    public UIManager getUIManager(){
        return State.getCurrentState().getUiManager();
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    public void setGameWorld(int world, int playerX, int playerY){
        getGame().getGameState().changePlayerRegion(world, playerX, playerY);
        setWorld(getGame().getGameState().getWorlds().get(world));
    }

    public String getCurrentRunningPath(){
        return getGame().getGameState().getCurrentPath();
    }

    public Player getPlayer(){
        return getGame().getGameState().getPlayer();
    }

    public int getGameWorldIndex(){
        return getGame().getGameState().getWorldIndex();
    }

}
