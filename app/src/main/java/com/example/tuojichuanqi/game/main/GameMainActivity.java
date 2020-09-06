package com.example.tuojichuanqi.game.main;

import android.app.Activity;
import android.os.Bundle;
import android.content.res.AssetManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.WindowManager;
import com.example.tuojichuanqi.R;

public class GameMainActivity extends Activity {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static GameView sGame;
    public static AssetManager assets;
    public static SharedPreferences UN_AND_PSW,PlayerFile;
    //public static boolean finish;
    //private static final String EXPKey="EXPKey",Equip_Clothes_Key="E_C";
    //private static int EXP,play_equip_clothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UN_AND_PSW=getSharedPreferences("UsenameAndPassword",Activity.MODE_PRIVATE);

        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //getWindow().addFlags已过时
    }
    /*private int retrieveEXP() {
        return prefs.getInt(EXPKey, 0);
    }//读取文件最高分改成经验值
    private int retrieve_P_E_C() {
        return prefs.getInt(Equip_Clothes_Key, 1);
    }//读取装备衣服的编号
    public static void setHighScore(int nowEXP) {
        GameMainActivity.EXP=nowEXP;
        Editor editor=prefs.edit();
        editor.putInt(EXPKey, EXP);
        editor.commit();//editor.commit()提交到磁盘,editor.apply()提交到内存
    }//修改最高分改成修改经验值

    public static int getEXP() {
        return EXP;
    }

    public static int getPlay_equip_clothes() {
        return play_equip_clothes;
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        PlayAssets.onResume();
        sGame.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        PlayAssets.onPause();
        sGame.onPause();
    }
    /*@Override
    public void finish(){
        //sGame.finish();
        if (finish){
            super.finish();
        }
        //PlayAssets.onPause();

    }*/
}