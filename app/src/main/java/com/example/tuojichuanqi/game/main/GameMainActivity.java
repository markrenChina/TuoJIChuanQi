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
        UN_AND_PSW=getSharedPreferences("UsenameAndPassword",Activity.MODE_WORLD_READABLE);
        //EXP=retrieveEXP();
        //play_equip_clothes=retrieve_P_E_C();
        PlayerFile=getSharedPreferences(GameMainActivity.UN_AND_PSW.getString("usename", null),Activity.MODE_PRIVATE);
        //临时解决PlayFile为空
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
/*
使用 SharedPreferences 的步骤如下：
1) 获取Preferences
每个 Activity 默认都有一个 SharedPreferences 对象，获取 SharedPreferences 对象的方法有两种：

1）SharedPreferences getSharedPreferences(String name, int mode)。

使用该方法获取 name 指定的 SharedPreferences 对象，并获取对该 SharedPreferences 对象的读写控制权。

当应用程序中可能使用到多个 SharedPreferences 时使用该方法。

2）SharedPreferences getPreferences(int mode)。

当应用程序中仅需要一个SharedPreferences对象时，使用该方法获取当前 Activity 对应的 SharedPreferences，而不需要指定 SharedPreferences 的名字。

其中，参数 mode 有 4 种取值，分别是：

    MODE_PRIVATE：默认方式，只能被创建的应用程序或者与创建的应用程序具有相同用户 ID 的应用程序访问。
    MODE_WORLD_READABLE：允许其他应用程序对该 SharedPreferences 文件进行读操作。
    MODE_WORLD_WRITEABLE：允许其他应用程序对该 SharedPreferences 文件进行写操作。
    MODE_MULTI_PROCESS：在多进程应用程序中，当多个进程都对同一个 SharedPreferences 进行访问时，该文件的每次修改都会被重新核对。

2) 获取 SharedPreferences.Editor
调用 edit() 方法获取 SharedPreferences.Editor，SharedPreferences 通过该接口对其内容进行更新。
3) 更新 SharedPreferences
通过 SharedPreferences.Editor 接口提供的 put 方法对 SharedPreferences 进行更新。

例如使用 putBoolean(String key, boolean value)、putFloat(String key, float value) 等方法将相应数据类型的数据与其 key 对应起来。
4) 提交
调用 SharedPreferences.Editor 的 commit() 方法将更新提交到 SharedPreferences 中。
 */