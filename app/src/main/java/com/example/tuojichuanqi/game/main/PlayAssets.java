package com.example.tuojichuanqi.game.main;

import java.io.InputStream;

import com.example.tuojichuanqi.framework.animation.Animation;
import com.example.tuojichuanqi.framework.animation.Frame;

import android.media.MediaPlayer;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.SoundPool;

public class PlayAssets {
    private static SoundPool soundPool;
    //soundPool对象加载短声音
    public static Bitmap  block, grass,FloorTile001,startAI_Battle,StopAI_Battle,equipmentButton,equipmentButtonShow,equipment_clothes;
    public static Animation player_Ani;
    public static int hitID,onJumpID;
    public static MediaPlayer mediaPlayer;
    public static final int PLAY_STAND_ANI_NUM=4,PLAY_MOVE_ANI_BATTLE_NUM=6;
    public static void load() {
        block = LogonAssets.loadBitmap("弓箭手.png", false);
        grass = LogonAssets.loadBitmap("UI001.png", false);
        StopAI_Battle = LogonAssets.loadBitmap("AIBattle-close.png", false);
        startAI_Battle = LogonAssets.loadBitmap("AIBattle-open.png", false);
        FloorTile001= LogonAssets.loadBitmap("地砖1.png", false);
        equipmentButton=LogonAssets.loadBitmap("装备界面开关.png",false);
        equipmentButtonShow=LogonAssets.loadBitmap("装备界面.png",false);
        //hitID = loadSound("hit.wav");
        //onJumpID = loadSound("onjump.wav");
    }

    /*private static int loadSound(String filename) {
        int soundID=0;
        if(soundPool==null) {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            soundID=soundPool.load(GameMainActivity.assets.openFd(filename),1 );
        }catch(IOException e) {
            e.printStackTrace();
        }
        return soundID;
    }
    public static void playSound(int soundID) {
        if(soundPool!=null) {
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
    }*/
    public static void onResume() {
        //hitID = loadSound("hit.wav");
        //onJumpID = loadSound("onjump.wav");
        //playMusic("TheInternationale.mp3",true);
    }
    public static void onPause() {
        if(soundPool!=null) {
            soundPool.release();
            soundPool=null;
        }
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    public static Animation player_Ani_update(int whatDo,int play_equip_clothes) {
        //System.out.println("whatDo"+whatDo);
        int temp=whatDo/10;
        String play_equip_num=""+play_equip_clothes;
        if (play_equip_num.length()==1){
            play_equip_num="00"+play_equip_num;
        }else if (play_equip_num.length()==2){
            play_equip_num="0"+play_equip_num;
        }
        //System.out.println("WHATDO :"+player_WhatDo+"   /10= "+player_WhatDo/10+"   play_equip_NUM:"+play_equip_num);
        //if(temp>某值)出错
        //System.out.println("temp"+temp);
        if (temp==0){
            update_play_ani(play_equip_num,temp,PLAY_STAND_ANI_NUM,whatDo%10);
        }else if (temp==1||temp==2||temp==3){
            update_play_ani(play_equip_num,temp,PLAY_MOVE_ANI_BATTLE_NUM,whatDo%10);
        }
        return player_Ani;
    }

    private static void update_play_ani(String play_equip_num,int temp, int num,int player_WhatDo) {
        Bitmap[] playAni = new Bitmap[num];
        Frame[] f=new Frame[num];
        for (int i=0;i<num;i++){
            String Temp=""+temp;
            //System.out.println("Temp"+Temp);
            //System.out.println("player_WhatDo"+player_WhatDo);
            //System.out.println(play_equip_num+Temp+(player_WhatDo*10+i)+".png");
            playAni[i]= LogonAssets.loadBitmap(play_equip_num+Temp+(player_WhatDo*10+i)+".png", true);
            f[i]= new Frame(playAni[i], .1f);
        }
        player_Ani = new Animation(f);
    }
    public static void equipmentInterfaceShow(int play_equip_clothes){
        //读取全身装备图片,后期改动态读取
        equipment_clothes=LogonAssets.loadBitmap("E0010.png", false);
    }
    /*public static void playMusic(String filename, boolean looping) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }try {
            AssetFileDescriptor afd = GameMainActivity.assets.openFd(filename);
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType (AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
