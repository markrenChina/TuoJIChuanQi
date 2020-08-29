package com.example.tuojichuanqi.framework.animation;
import android.graphics.Bitmap;
public class Frame {
    private Bitmap image;
    private double duration;

    public Frame(Bitmap image,double duration) {
        this.image=image;
        this.duration=duration;
        //System.out.println(duration);
    }
    public double getDuration() {
        return duration;
    }
    public Bitmap getImage() {
        return image;
    }
}
