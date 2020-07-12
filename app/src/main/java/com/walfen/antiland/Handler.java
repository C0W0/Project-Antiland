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

    public Player getPlayer(){
        GameState gs = (GameState)(getGame().getGameState());
        return gs.getPlayer();
    }

}
