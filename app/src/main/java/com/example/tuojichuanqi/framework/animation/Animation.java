package com.example.tuojichuanqi.framework.animation;

import com.example.tuojichuanqi.framework.util.Painter;

public class Animation {
    private Frame[] frames;
    private double[] frameEndTimes;
    private int currentFrameIndex=0;
    private double totalDuration=0;
    private double currentTime=0;

    public Animation(Frame...frames) {
        this.frames=frames;
        frameEndTimes=new double[frames.length];
        for(int i=0;i<frames.length;i++) {
            Frame f=frames[i];
            totalDuration+=f.getDuration();
            frameEndTimes[i]=totalDuration;
            //frameEndTimes每一幅图的结束时间
        }
    }
    public synchronized void update(float increment) {
        currentTime+=increment;
        if(currentTime>totalDuration) {
            wrapAnimation();
            //如果时间已经大于总动画时间,wrapAnimation()重置索引0,现在时间对总时间取余
        }
        while(currentTime>frameEndTimes[currentFrameIndex]) {
            currentFrameIndex++;
        }//找到要输出的图
    }
    private synchronized void wrapAnimation() {
        currentFrameIndex=0;
        currentTime%=totalDuration;
    }
    public synchronized void render(Painter g,int x,int y) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y);
    }
    public synchronized void render(Painter g,int x,int y,int width,int height) {
        g.drawImage(frames[currentFrameIndex].getImage(), x, y, width, height);
    }
}
