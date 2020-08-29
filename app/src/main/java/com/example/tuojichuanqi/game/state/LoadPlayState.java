package com.example.tuojichuanqi.game.state;
import com.example.tuojichuanqi.game.main.GameMainActivity;
import com.example.tuojichuanqi.game.main.PlayAssets;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.game.model.Player;

public class LoadPlayState extends State {
    private static final String EXPKey="EXPKey",Equip_Clothes_Key="E_C";
    private static  String play_Name="";
    private static int EXP,play_equip_clothes;
    @Override
    public void init() {
        // TODO Auto-generated method stub
        System.out.println("LoadPlayInit-EXP");
        EXP=retrieveEXP();
        System.out.println("LoadPlayInit-equip_clothes");
        play_equip_clothes=retrieve_P_E_C();
        System.out.println("LoadPlayInit-PlayAssets.");
        PlayAssets.load();
        System.out.println("LoadPlayInit-finish");
    }
    public void onExit(){

    }
    public void onLoad() {

    }
    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
        System.out.println("LoadPlayState1");
        setCurrentState(new LoadState(this,new PlayState()));
        System.out.println("LoadPlayState2");
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    private int retrieveEXP() {
        //PlayFile为空
        return GameMainActivity.PlayerFile.getInt(EXPKey, 0);
    }//读取文件最高分改成经验值
    private int retrieve_P_E_C() {
        return GameMainActivity.PlayerFile.getInt(Equip_Clothes_Key, 1);
    }//读取装备衣服的编号
    public static void setEXP(int nowEXP) {
        EXP=nowEXP;
        SharedPreferences.Editor editor=GameMainActivity.PlayerFile.edit();
        editor.putInt(EXPKey, EXP);
        editor.commit();//editor.commit()提交到磁盘,editor.apply()提交到内存
    }//修改最高分改成修改经验值

    public static int getEXP() {
        return EXP;
    }

    public static int getPlay_equip_clothes() {
        return play_equip_clothes;
    }
    @Override
    public String toString() {
        return "LoadPlayState";
    }
}
