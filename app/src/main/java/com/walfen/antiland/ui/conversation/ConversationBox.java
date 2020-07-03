package com.walfen.antiland.ui.conversation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.walfen.antiland.Constants;
import com.walfen.antiland.ui.ClickListener;
import com.walfen.antiland.ui.UIManager;
import com.walfen.antiland.ui.UIObject;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;

public class ConversationBox extends UIObject {

    private ArrayList<Conversation> conversationList;
    private int currentConversation;
    private ClickListener finalClicker;
    private UIManager manager;

    public ConversationBox(UIManager manager) {
        super(0, Constants.SCREEN_HEIGHT-Constants.DEFAULT_SIZE*3, Constants.SCREEN_WIDTH, Constants.DEFAULT_SIZE*3);
        currentConversation = 0;
        this.manager = manager;
        active = false;
    }

    @Override
    public void onTouchEvent(MotionEvent event) {
        if(!active || currentConversation >= conversationList.size())
            return;
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int pointerIndex = event.findPointerIndex(event.getPointerId(event.getActionIndex()));
            if(event.getY(pointerIndex) >= y)
                currentConversation ++;
            if(currentConversation >= conversationList.size()) {
                finalClicker.onClick();
                active = false;
                manager.showUI();
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        if(!active || currentConversation >= conversationList.size())
            return;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(x, y, x+width, y+height, paint);
        Conversation c = conversationList.get(currentConversation);
        int left = c.isReply()?Constants.SCREEN_WIDTH-256:128;
        int top = (int)(y+64);
        canvas.drawBitmap(c.getCharacter(), null, new Rect(left, top, left+128, top+128), Constants.getRenderPaint());
        paint.setColor(Color.WHITE);
        paint.setTextSize(36);
        Rect r = new Rect();
        ArrayList<String> text = Utils.splitString(c.getText(), 60);
        paint.getTextBounds(text.get(0), 0, text.get(0).length(), r);
        for(int i = 0; i < text.size(); i++){
            canvas.drawText(text.get(i), 128+256, top+32+(r.height()+1)*i, paint);
        }
    }

    public void setConversationList(ArrayList<Conversation> conversationList, ClickListener clicker){
        this.conversationList = conversationList;
        currentConversation = 0;
        finalClicker = clicker;
    }

}
