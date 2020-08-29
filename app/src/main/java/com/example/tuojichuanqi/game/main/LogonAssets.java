package com.example.tuojichuanqi.game.main;

import com.example.tuojichuanqi.framework.animation.Animation;
import com.example.tuojichuanqi.framework.animation.Frame;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
public class LogonAssets {
    public static Bitmap Logon,register,ok,password_error;
    public static Bitmap[] writing;
    public static void load() {
        writing=new Bitmap[67];
        //Logon = loadBitmap("Logon.png", false);
        //register=loadBitmap("register.png",false);
        ok=loadBitmap("ok.png",false);
        //password_error=loadBitmap("passworderror.png",false);
        for (int i=0;i<67;i++){
            if (i<=9) {
                //System.out.println("Button-" + i + ".png");
                writing[i] = loadBitmap("Button-" + i + ".png", false);
            }else if (i<=35){
                //A-Z 65-90
                //System.out.println("Button-" + (char)(i+55) + ".png");
                writing[i] = loadBitmap("Button-" + (char)(i+55) + ".png", false);
            }else if (i<=61){
                //a-z 97-122
                writing[i] = loadBitmap("Button-s-" +(char)(i+61) + ".png", false);
            }else if (i==62){
                writing[i]=loadBitmap("Button-delete.png",false);
            }else if (i==63){
                writing[i]=loadBitmap("Button-big-no.png",false);
            }else if (i==64){
                writing[i]=loadBitmap("Button-big-yes.png",false);
            }else if (i==65){
                writing[i]=loadBitmap("Button-back.png",false);
            }else {
                writing[i]=loadBitmap("Button-Enter.png",false);
            }
        }
    }
    public static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream=null;
        //创建一个inputStream对象用于从文件中读取数据
        try {
            inputStream=GameMainActivity.assets.open(filename);}
        catch(IOException e) {
            e.printStackTrace();
        }
        Options options=new Options();
        //创建一个options对象指定如何存储到内存
        if(transparency) {
            options.inPreferredConfig=Config.ARGB_8888;//ARGB_8888透明图,占用内存较大
        }else {
            options.inPreferredConfig=Config.RGB_565;	//RGB_565无透明度,占用内存较少
        }
        Bitmap bitmap=BitmapFactory.decodeStream(inputStream, null,  options);
        //使用BitmapFactory类创建一个新的bitmap传入inputStream和Options对象作为参数
        return bitmap;
    }
    public static void unloadBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }
}
