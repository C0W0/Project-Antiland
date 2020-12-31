package com.walfen.antiland;

import android.media.MediaPlayer;

public class MusicController {

    private MediaPlayer player;
    private Handler handler;
    private int queueMusic;

    public MusicController(Handler handler){
        this.handler = handler;
        queueMusic = -1;
    }

    public void update(){
        if(player == null)
            return;
        try{
            if(!player.isPlaying() && queueMusic != -1){
                playMusic(queueMusic);
                player.setLooping(true);
                queueMusic = -1;
            }
        }catch (IllegalStateException ignored){}
    }

    public void playMusic(int resource){
        stopMusic();
        player = MediaPlayer.create(handler.getGame().getContext(), resource);
        player.setLooping(true);
        player.start();
    }

    public void changeMusic(int resource){
        if(player == null){
            playMusic(resource);
            return;
        }
        queueMusic = resource;
        player.setLooping(false);
    }

    public void stopMusic(){
        if(player != null)
            player.release();
        player = null;
    }

    public boolean isMusicPlaying(){
        return player != null && player.isPlaying();
    }


}
