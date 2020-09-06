package com.example.tuojichuanqi.framework.util;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;


public class UIEditText  {
    Writing Writing;
    private Rect InputTextRect;
    private boolean WritingOpen=false;
    protected  StringBuilder InputBuilder;
    private boolean visible;
    public UIEditText(int left, int top, int right, int bottom,boolean visible) {
        InputTextRect=new Rect(left,top,right,bottom);
        InputBuilder=new StringBuilder();
        this.visible=visible;
        //System.out.println(InPut_Text.isEnabled()+"");
    }

    /*public UIEditText(int left, int top, int right, int bottom){
        EditTextRect=new Rect(left,top,right,bottom);
    }*/
    public void render(Painter g) {
        g.setColor(Color.BLACK);
        g.fillRect(InputTextRect.left,InputTextRect.top,InputTextRect.width(),InputTextRect.height());
        if (InputBuilder.length()>0) {
            //显示获取到的文本
            g.setColor(Color.WHITE);
            g.setFont(Typeface.DEFAULT, 16);
            if (visible){
                g.drawString(InputBuilder.toString(),InputTextRect.left,InputTextRect.bottom);
            }else {
                StringBuilder password=new StringBuilder();
                for (int i=1;i<=InputBuilder.length();i++){
                    password.append('*');
                }
                g.drawString(password.toString(),InputTextRect.left,InputTextRect.bottom);
            }
        }
        if (WritingOpen){
            Writing.render(g);
        }
    }
    public void onTouchDown(int touchX, int touchY, UIEditText editText) {
        if (editText.InputTextRect.contains(touchX,touchY)){
            //弹出输入法
            //System.out.println("点击点击点击");
            //InPut_Text.setFocusable(true);
            //InPut_Text.setFocusableInTouchMode(true);
            //InPut_Text.requestFocus();
            //inputText.showSoftInput(GameMainActivity.sGame,0);
            if (editText.WritingOpen){
                editText.writingClose();
            }else {
                editText.Writing = new Writing();
                editText.WritingOpen = true;
            }
            //System.out.println("完成完成");
            //inputText.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        }
        if (editText.WritingOpen) {
            editText.Writing.onTouchDown(touchX, touchY,editText);
        }
        /*获取输入法按下回车
        if(){
            //保存内容
        }*/
    }

    public void writingClose(){
        WritingOpen=false;
    }

    public Rect getInputTextRect() {
        return InputTextRect;
    }

    public boolean isWritingOpen() {
        return WritingOpen;
    }

    public StringBuilder getInputBuilder() {
        return InputBuilder;
    }
}
