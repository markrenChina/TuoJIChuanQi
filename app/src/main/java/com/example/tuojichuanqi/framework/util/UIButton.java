package com.example.tuojichuanqi.framework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class UIButton {
    private Rect buttonRect;
    private boolean buttonDown=false;
    private Bitmap buttonImage, buttonDownImage;
    public  UIButton(int left, int top, int right, int bottom, Bitmap buttonImage,Bitmap buttonPressedImage) {
        buttonRect=new Rect(left,top,right,bottom);
        this.buttonImage=buttonImage;
        this.buttonDownImage=buttonPressedImage;
    }
    public  UIButton(int left, int top, int right, int bottom) {
        buttonRect=new Rect(left,top,right,bottom);
    }
    public void render(Painter g) {
        if (buttonImage!=null) {
            Bitmap currentButtonImage = buttonDown ? buttonDownImage : buttonImage;
            //三元运算法例如a=b?c:d 如果b为true则a=c，false a=d
            g.drawImage(currentButtonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
        }
    }
    public void onTouchDown(int touchX,int touchY) {
        if(buttonRect.contains(touchX, touchY)) {
            buttonDown=true;
        }else {
            buttonDown=false;
        }
    }
    public void cancel() {
        buttonDown=false;
    }
    public void on(){
        buttonDown=true;
    }
    public boolean isPressed(int touchX,int touchY ) {
        return buttonDown&&buttonRect.contains(touchX, touchY);
    }

    public Rect getButtonRect() {
        return buttonRect;
    }
}
