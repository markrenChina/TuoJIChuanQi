package com.example.tuojichuanqi.game.model;

import android.graphics.Rect;
import com.example.tuojichuanqi.framework.util.RandomNumberGenerator;
public class monster {
    private float x, y;
    private int width, height,EXP;
    private Rect rect;//站立的矩阵
    private boolean visible;
    private static final int UPPER_Y = 275;
    private static final int LOWER_Y = 355;
    private static final int BLOCK_HEIGHT=77;
    private static final int BLOCK_WIDTH=68;
    private int[] coordinate={0,0};
    private  int HP,totalHP=50;
    //private boolean Battle;

    public monster(int S_O_C_X,int S_O_C_Y) {
        coordinate[0]=RandomNumberGenerator.getRandInt(21);
        coordinate[1]=RandomNumberGenerator.getRandInt(19);
        System.out.println("怪物坐标："+coordinate[0]+"     "+coordinate[1]);
        this.width = BLOCK_WIDTH;
        this.height = BLOCK_HEIGHT;
        HP=50;
        x=(coordinate[0]-S_O_C_X)*48;
        y=(coordinate[1]-S_O_C_Y+1)*32-BLOCK_HEIGHT;
        rect = new Rect((int)x , (int)y, (int)x + 48, (int)y+ 32);
        visible = true;
    }

    public void update(float delta, int S_O_C_X, int S_O_C_Y,float changeX,float changeY) {
        //x += velX * delta;
        if (HP<=0){
            reset();
        }
        x=(coordinate[0]-S_O_C_X)*48+changeX;
        y=(coordinate[1]-S_O_C_Y+1)*32-BLOCK_HEIGHT+changeY;
        updateRect();
    }

    public void updateRect() {
        rect.set((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public void reset() {
        //visible = true;
        // 1 in 3 chance of becoming an Upper Block
        /*if (RandomNumberGenerator.getRandInt(3) == 0) {
            y = UPPER_Y;
        } else {
            y = LOWER_Y;
        }*/
       // x += 1000;
        HP=totalHP;
        coordinate[0]=RandomNumberGenerator.getRandInt(21);
        coordinate[1]=RandomNumberGenerator.getRandInt(19);
        //updateRect();
    }

    public void onCollide(Player p) {
        //把角色弹回原坐标
        visible = false;
        /*Battle=true;
        p.setDoLock(false);
        System.out.println("WhatDo1: "+p.getWhatDo());
        if (p.getWhatDo()/10==1){
            p.setWhatDo(p.getWhatDo()+20);
        }else if(p.getWhatDo()/10==2){
            p.setWhatDo(p.getWhatDo()+10);
        }

        System.out.println("WhatDo2: "+p.getWhatDo());*/

        //改变WhatDo进入攻击状态
        //p.pushBack(30);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }
    /*public boolean isBattle() {
        return Battle;
    }*/
    public Rect getRect() {
        return rect;
    }
    public int getEXP() {
        return EXP;
    }
    public int getBlockHeight(){ return BLOCK_HEIGHT;}
    public int getBlockWidth() {return BLOCK_WIDTH;}

    public int getCoordinate(int i) {
        return coordinate[i];
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getTotalHP() {
        return totalHP;
    }
}
