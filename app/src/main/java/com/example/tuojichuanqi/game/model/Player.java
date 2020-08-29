package com.example.tuojichuanqi.game.model;
import com.example.tuojichuanqi.framework.util.DamageCalculation;
import com.example.tuojichuanqi.game.main.PlayAssets;
import com.example.tuojichuanqi.game.state.PlayState;

import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Player {
    private float x=384, y;
    private int width, height, velY,EXP;
    private Rect rect, CoordinateRect, ground;
    private boolean isAlive,isDoLock=false;//isDoLock,锁定WhatDo
    private float duckDuration =.6f;
    private static final int JUMP_VELOCITY=-600;
    private static final int ACCEL_GRAVITY = 1800;
    private static final int PLAYER_COO_WIDTH=48;
    private static final int PLAYER_COO_HEIGHT=32;
    private int[] coordinate={0,0};
    private int playSpeed_X=80,playSpeed_Y=53;
    private boolean rightWaik=false;
    private int WhatDo=7;
    private double totalDuration = 0;
    private int play_equip_clothes;//装备编号，在图片序号*1000
    public static boolean isNoPressed=true;//持续按压0.6秒后转跑步
    public static int player_HP_now,player_MP_now,player_HP_total=100,player_MP_total=100;//后面总血量应该改为等级加上装备实时算出来
    private int MAX_BATTLE_SPACE=1;//最大攻击距离，可以有技能冷却刷新
    public boolean Battle=false;
    private int DoWhat,direction,MIN_Attack=3,MAX_Attack=5;
    //两个常量JUMP VELOCITY和ACCEL GRAVITY将用来确定玩家能够跳多高,以及玩家将回落的多快。
    //ACCEL GRAVITY是 JUMP VELOCITY将会在每一秒中增加的量。值-600和1800都是通过试验来确定的。
    public Player(int x, int y, int width, int height,int play_equip_C,int HP_now,int MP_now) {
        this.coordinate[0]=x;
        this.coordinate[1]=y;
        player_HP_now=HP_now;
        player_MP_now=MP_now;
        this.play_equip_clothes=play_equip_C;
        PlayAssets.player_Ani_update(WhatDo,play_equip_clothes);
        //System.out.println("初始化主角时y="+y);
        this.width = width;
        this.height = height;
        ground = new Rect(0, 600, 0+800, 600+251);//ground表示草地边界矩形宽800,高45
        rect = new Rect();//rect表示主边界矩形，用于绘图
        CoordinateRect = new Rect();//CoordinateRect表示坐标边界矩形，用于检测
        isAlive = true;//表示player还活着
        //isDucked = false;//表示player站立  isDucked boolean变量和duckDuration变量一起使用。
        //当玩家按下向下键的时候, isDucked将会变为true ,并且duckDuration "(值为6秒)将会开始在每-帧递减1秒。
        //一旦6秒时间到了(当 duckDuration为0的时候) ,我们将duckDuration重新设置为6 ,并且将isDucked设置为false ,让玩家站起来。
        updateRects(); // This will give an error.}
    }
    public void update(float delta) {

        /*if(duckDuration>0&&isDucked) {
            duckDuration-=delta;
        }else {
            isDucked=false;
            duckDuration =.6f;
        }
        if(!isGrounded()) {
            velY+=ACCEL_GRAVITY*delta;
        }else {
            //y=406-height;
            velY=0;
        }
        y+=velY*delta;*/
        int temp = WhatDo / 10;
        if (totalDuration>0) {
            totalDuration -= delta;

            if (temp == 0&&DoWhat!=0) {
                setWhatDo();
                totalDuration = .6f;
                //System.out.println("Player.update被执行");
            } else if (temp == 1 || temp == 2) {
                //控制坐标改变和像素改变
                //x=x+playSpeed*delta;
                switch (WhatDo % 10) {
                    case 2: PlayState.y = PlayState.y + playSpeed_Y * delta * temp;//1+3
                    case 1: PlayState.x = PlayState.x + playSpeed_X * delta * temp;break;
                    case 4: PlayState.x = PlayState.x - playSpeed_X * delta * temp;//3+5
                    case 3: PlayState.y = PlayState.y + playSpeed_Y * delta * temp;break;
                    case 6: PlayState.y = PlayState.y - playSpeed_Y * delta * temp;//5+7
                    case 5: PlayState.x = PlayState.x - playSpeed_X * delta * temp;break;
                    case 8: PlayState.x = PlayState.x + playSpeed_X * delta * temp;//1+7
                    case 7: PlayState.y = PlayState.y - playSpeed_Y * delta * temp;break;
                    //计算屏幕原点的像素位置
                }
                //System.out.println("PlayState.x1:"+PlayState.x+"   PlayState.y1:"+PlayState.y);
                if (PlayState.x>=48){
                    PlayState.x-=48;
                    coordinate[0]-=1;
                }else if (PlayState.x<=-48){
                    PlayState.x+=48;
                    coordinate[0]+=1;
                }
                //System.out.println("PlayState.x2:"+PlayState.x);
                if (PlayState.y>=32){
                    PlayState.y-=32;
                    coordinate[1]-=1;

                }else if (PlayState.y<=-32){
                    PlayState.y+=32;
                    coordinate[1]+=1;
                }
                //System.out.println("PlayState.y2:"+PlayState.y);
            }else if (temp == 3) {
                //战斗状态
            }
        } else {
                if (temp == 0) {
                } else if (temp == 1 || temp == 2) {
                    PlayState.x = 0;
                    PlayState.y = 0;
                    /*
                    switch (WhatDo % 10) {
                        case 1: coordinate[0] -= temp;break;
                        case 2: coordinate[0] -= temp;coordinate[1] -= temp;break;
                        case 3: coordinate[1] -= temp;break;
                        case 4: coordinate[0] += temp;coordinate[1] -= temp;break;
                        case 5: coordinate[0] += temp;break;
                        case 6: coordinate[0] += temp;coordinate[1] += temp;break;
                        case 7: coordinate[1] += temp;break;
                        case 8: coordinate[0] -= temp;coordinate[1] += temp;break;
                    }*/
                }
                if (temp==3) {
                    System.out.println("伤害被执行");
                    //伤害计算
                    //计算被攻击的对象，技能可能是群体攻击
                    //通过攻击方向(必须是WhatDo,因为Direction在0.6秒期间可能被重置)取得攻击目标坐标,通过坐标查出怪物对象
                    int AttackTempX=coordinate[0],AttackTempY=coordinate[1];//攻击的核心目标坐标，技能可以由OnTouch传入，先阶段讨论物理攻击
                    switch (WhatDo % 10) {
                        case 2: AttackTempY=coordinate[1]-1;
                        case 1: AttackTempX=coordinate[0]-1;break;
                        case 4: AttackTempX=coordinate[0]+1;
                        case 3: AttackTempY=coordinate[1]-1;break;
                        case 6: AttackTempY=coordinate[1]+1;
                        case 5: AttackTempX=coordinate[0]+1;break;
                        case 8: AttackTempX=coordinate[0]-1;
                        case 7: AttackTempY=coordinate[1]+1;break;
                    }
                     ArrayList<monster> Attackmonsters;
                    Attackmonsters =new ArrayList<monster>();
                    for (int i = 0; i < PlayState.monsters.size(); i++) {
                        if (AttackTempX == PlayState.monsters.get(i).getCoordinate(0)  &&
                                AttackTempY == PlayState.monsters.get(i).getCoordinate(1)) {
                                monster b=PlayState.monsters.get(i);
                                Attackmonsters.add(b);
                        }
                    }
                    System.out.println("");
                    //角色最小攻击，最大攻击，准确   等          不定数量的被攻击伤害对象，通过对象读取防御
                    DamageCalculation.implementDamageCalculation(MIN_Attack,MAX_Attack,Attackmonsters);//执行伤害
                }
                setWhatDo();
                totalDuration = .6f;
            }
        updateRects();
        }
        //先检查是否下蹲,再检查是否跳跃,并且改变
    public void updateRects() {
        y =224-height;
        rect.set((int)x,(int)y,(int)x+width,(int)y+height);
        //System.out.println("updateRects时y="+y);
        CoordinateRect.set(384,192, (int)x+PLAYER_COO_WIDTH, (int)y+PLAYER_COO_HEIGHT);
    }
    /*public void jump() {
        if(isGrounded()) {
            //PlayAssets.playSound(PlayAssets.onJumpID);
            isDucked=false;
            duckDuration=.6f;
            y-=10;
            velY=JUMP_VELOCITY;
            updateRects();
        }
    }*/
    //跳跃时重置duckDuration表示没有下蹲
    /*public void duck() {
        if(isGrounded()) {
            isDucked=true;
        }//检查是否在地面上,如果不在为跳跃告诉playState绘制
    }*/
    public void pushBack(int dX) {
        //PlayAssets.playSound(PlayAssets.hitID);
        //x-=dX;
        //if(x<-width/2) {
        //    isAlive=false;
        //}
        //rect.set((int)x, (int)y,(int)x+ width,(int)y+ height);
    }
    //碰撞后退,如果出图一般GAMEOVER.
    public boolean isGrounded() {
        //return Rect.intersects(rect,ground);
        return true;
    }//判断是否在地上
    public int getWidth() { return width;}
    public int getHeight() { return height;}
    public int getVelY() { return velY;}
    public Rect getRect() { return rect;}
    public Rect getCoordinateRect() {return CoordinateRect;}
    public Rect getGround() { return ground;}
    public boolean isAlive() { return isAlive;}
    public float getDuckDuration() { return duckDuration;}

    public int getCoordinate(int i) {
        return coordinate[i];
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }
    public float getX() { return x;}
    public float getY() { return y;}

    public int getWhatDo() {
        return WhatDo;
    }

    private void setWhatDo() {
        if (direction==0){
            direction=WhatDo%10;
        }
        this.WhatDo=DoWhat*10+direction;
        //System.out.println( "setWhatDo:WhatDo="+WhatDo+"   dieection="+direction);
        PlayAssets.player_Ani_update(WhatDo,play_equip_clothes);
    }


    public int getPlay_equip() {
        return play_equip_clothes;
    }

    public int getMAX_BATTLE_SPACE() {
        return MAX_BATTLE_SPACE;
    }

    public void setDoWhat(int doWhat) {
        DoWhat = doWhat;
    }

    public void setDirection(int direction) {
        if (direction==0) return;
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
