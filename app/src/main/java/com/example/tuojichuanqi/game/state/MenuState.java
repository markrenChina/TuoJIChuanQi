package com.example.tuojichuanqi.game.state;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.util.Log;

import com.example.tuojichuanqi.framework.animation.Animation;
import com.example.tuojichuanqi.framework.animation.Frame;
import com.example.tuojichuanqi.framework.util.Painter;

import com.example.tuojichuanqi.framework.util.UIButton;
import com.example.tuojichuanqi.game.main.LogonAssets;
//import com.example.tuojichuanqi.game.main.MenuAssets;
import com.example.tuojichuanqi.game.main.PlayAssets;
import com.example.tuojichuanqi.game.main.GameMainActivity;
import com.example.tuojichuanqi.game.model.Player;

public class MenuState extends State {
    private static Bitmap welcome, scoreDown, score, startDown, start,ChooseSoldier_1,ChooseSoldier_2,ChooseSoldier_3;
    private static Animation ChooseSoldier;
    public static int hitID,onJumpID;
    public static MediaPlayer mediaPlayer;
    private UIButton playButton,scoreButton;
    @Override
    public void init() {
        //初始化按钮
        //MenuAssets.load();
        playButton=new UIButton(375,455,443,486);

        //scoreButton =new UIButton(316,300,484,359,PlayAssets.score,PlayAssets.scoreDown);

    }
    @Override
    public void onLoad(){
        welcome = LogonAssets.loadBitmap("角色选择.png", false);
        //scoreDown = loadBitmap("score_button_down.png", true);
        //score = loadBitmap("score_button.png", true);
        //startDown = loadBitmap("start_button_down.png", true);
        //start = loadBitmap("startbutton.png", true);
        ChooseSoldier_1= LogonAssets.loadBitmap("战士角选1.png", true);
        ChooseSoldier_2= LogonAssets.loadBitmap("战士角选2.png", true);
        ChooseSoldier_3= LogonAssets.loadBitmap("战士角选3.png", true);

        Frame ChooseSoldier1 = new Frame(ChooseSoldier_1, .2f);
        Frame ChooseSoldier2 = new Frame(ChooseSoldier_2, .2f);
        Frame ChooseSoldier3 = new Frame(ChooseSoldier_3, .2f);
        ChooseSoldier=new Animation(ChooseSoldier1, ChooseSoldier2, ChooseSoldier3);
    }
    @Override
    public void onExit() {
        LogonAssets.unloadBitmap(welcome);
        ChooseSoldier=null;
        LogonAssets.unloadBitmap(ChooseSoldier_1);
        LogonAssets.unloadBitmap(ChooseSoldier_2);
        LogonAssets.unloadBitmap(ChooseSoldier_3);
    }
    @Override
    public void update(float delta) {
        //Thread.currentThread().sleep(1000);
        ChooseSoldier.update(delta);
    }

    @Override
    public void render(Painter g) {
        g.drawImage(welcome, 0, 0);
        //playButton.render(g);//动态输出按钮图片，按下与不按下图片不一样
        ChooseSoldier.render(g,57,60);
        //scoreButton.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if(e.getAction()==MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            //scoreButton.onTouchDown(scaledX, scaledY);
        }
        if(e.getAction()==MotionEvent.ACTION_UP) {
            if(playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                //	Log.d("11111", "22222");
                setCurrentState(new LoadState(this,new LoadPlayState()));
            }//else if(scoreButton.isPressed(scaledX, scaledY)){
                //scoreButton.cancel();
                //setCurrentState(new ScoreState());
                //	Log.d("MenuState", "Score Button Pressed!");
            //}
            else {
                playButton.cancel();
                //scoreButton.cancel();
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return "MenuState";
    }
}
