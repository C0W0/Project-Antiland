package com.walfen.antiland.ui.conversation;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.ImageEditor;

public class Conversation {

    private String text;
    private Bitmap character;
    private boolean reply; // if true, the text and character is displayed on the right side

    public Conversation(String text, Bitmap character, boolean reply){
        this.text = text;
        this.character = ImageEditor.scaleBitmap(character, 128);
        this.reply = reply;
    }

    public String getText() {
        return text;
    }

    public Bitmap getCharacter() {
        return character;
    }

    public boolean isReply() {
        return reply;
    }
}
