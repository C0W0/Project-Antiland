package com.walfen.antiland.ui.slider;


import android.graphics.Bitmap;

import com.walfen.antiland.ui.buttons.UIImageButton;

public class SliderAdjuster extends UIImageButton {

    private Slider slider;
    private int change;

    public SliderAdjuster(float x, float y, int width, int height, int change, Bitmap image, Slider slider) {
        super(x, y, width, height, image, null);
        this.slider = slider;
        this.change = change;
    }

    @Override
    public void onClick() {
        if(change > 0 && slider.getValue() >= slider.getMax()-change){
            slider.setValue(slider.getMax());
        } else if(change < 0 && slider.getValue() <= slider.getMin()-change){
            slider.setValue(slider.getMin());
        } else{
            slider.setValue(slider.getValue()+change);
        }
    }
}
