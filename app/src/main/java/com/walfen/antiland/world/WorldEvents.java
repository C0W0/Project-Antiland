package com.walfen.antiland.world;

import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.ChangeEvent;

public class WorldEvents {

    public static ChangeEvent[][] allWorldEvents = new ChangeEvent[16][32];

    public static void initEvents(Handler handler){
        allWorldEvents[1][0] = () -> handler.getPlayer().getMapManager().getMaps()[0].triggerMapEvent(0);
        allWorldEvents[1][1] = () -> {
            for(int x = 40; x < 48; x++){
                handler.getWorld().setTile(x, 9, 89);
            }
            for(int y = 10; y < 12; y++){
                for(int x = 40; x < 48; x++)
                    handler.getWorld().setTile(x, y, 79);
            }
            for(int y = 12; y < 14; y++){
                for(int x = 40; x < 48; x++)
                    handler.getWorld().setTile(x, y, 87);
            }
            handler.getPlayer().getMapManager().getMaps()[0].triggerMapEvent(1);
        };
    }


}
