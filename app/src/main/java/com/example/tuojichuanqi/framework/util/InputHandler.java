package com.example.tuojichuanqi.framework.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.tuojichuanqi.game.state.State;
import com.example.tuojichuanqi.game.main.GameMainActivity;

public class InputHandler implements View.OnTouchListener {
    //InputMethodManager inputText=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    private State currentState;
    public void setCurrentState(State currentState) {
        this.currentState=currentState;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int scaledX=(int)((event.getX()/v.getWidth())*GameMainActivity.GAME_WIDTH);
        int scaledY=(int)((event.getY()/v.getHeight())*GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);
    }
}
