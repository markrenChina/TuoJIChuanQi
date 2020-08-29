package com.example.tuojichuanqi.game.state;

import android.view.MotionEvent;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.game.main.GameMainActivity;
public class ScoreState extends State {
    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Painter g) {
        // TODO Auto-generated method stub
        g.setColor(Color.rgb(53, 156, 253));
        g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString("The ALL-Time High Score", 120, 175);
        g.setFont(Typeface.DEFAULT_BOLD, 70);
        g.drawString(""+LoadPlayState.getEXP(), 370, 260);
        g.setFont(Typeface.DEFAULT_BOLD, 50);
        g.drawString("Touch the screen", 220, 350);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getAction()==MotionEvent.ACTION_UP) {
            //setCurrentState(new MenuState());
        }
        return true;
    }
    @Override
    public String toString() {
        return "ScoreState";
    }

}
