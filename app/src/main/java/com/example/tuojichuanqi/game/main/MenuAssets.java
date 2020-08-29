package com.example.tuojichuanqi.game.main;

import java.io.InputStream;

import com.example.tuojichuanqi.framework.animation.Animation;
import com.example.tuojichuanqi.framework.animation.Frame;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
/*
public class MenuAssets {
    private static SoundPool soundPool;
    //soundPool对象加载短声音
    public static Bitmap welcome, scoreDown, score, startDown, start,ChooseSoldier_1,ChooseSoldier_2,ChooseSoldier_3;
    public static Animation ChooseSoldier;
    public static int hitID,onJumpID;
    public static MediaPlayer mediaPlayer;
    public static void load() {
        //welcome = LogonAssets.loadBitmap("角色选择.png", false);
        //scoreDown = loadBitmap("score_button_down.png", true);
        //score = loadBitmap("score_button.png", true);
        //startDown = loadBitmap("start_button_down.png", true);
        //start = loadBitmap("startbutton.png", true);
        //ChooseSoldier_1= LogonAssets.loadBitmap("战士角选1.png", true);
        //ChooseSoldier_2= LogonAssets.loadBitmap("战士角选2.png", true);
        //ChooseSoldier_3= LogonAssets.loadBitmap("战士角选3.png", true);

        //Frame ChooseSoldier1 = new Frame(ChooseSoldier_1, .2f);
        //Frame ChooseSoldier2 = new Frame(ChooseSoldier_2, .2f);
        //Frame ChooseSoldier3 = new Frame(ChooseSoldier_3, .2f);
        //ChooseSoldier=new Animation(ChooseSoldier1, ChooseSoldier2, ChooseSoldier3);
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
    }
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
    }
}
*/