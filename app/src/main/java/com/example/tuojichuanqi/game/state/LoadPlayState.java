package com.example.tuojichuanqi.game.state;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.game.main.GameMainActivity;
import com.example.tuojichuanqi.game.main.PlayAssets;

/**
 * @author mark
 */
public class LoadPlayState extends State {
    private static final String EXPKey="EXPKey",Equip_Clothes_Key="E_C";
    private static  String play_Name="";
    private static int EXP,play_equip_clothes;
    @Override
    public void init() {
        EXP=retrieveEXP();
        play_equip_clothes=retrieve_P_E_C();
        PlayAssets.load();
    }
    @Override
    public void onExit(){

    }
    @Override
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
        return GameMainActivity.PlayerFile.getInt(EXPKey, 0);
    }//读取文件最高分改成经验值
    private int retrieve_P_E_C() {
        return GameMainActivity.PlayerFile.getInt(Equip_Clothes_Key, 1);
    }//读取装备衣服的编号

    public static void setEXP(int nowEXP) {
        EXP=nowEXP;
        SharedPreferences.Editor editor=GameMainActivity.PlayerFile.edit();
        editor.putInt(EXPKey, EXP);
        editor.apply();
    }

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
