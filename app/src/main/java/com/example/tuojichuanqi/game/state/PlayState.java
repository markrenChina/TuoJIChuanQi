package com.example.tuojichuanqi.game.state;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import java.util.ArrayList;


import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.framework.util.UIButton;
import com.example.tuojichuanqi.game.model.monster;
import com.example.tuojichuanqi.game.model.Player;
import com.example.tuojichuanqi.game.main.PlayAssets;
import com.example.tuojichuanqi.game.main.GameMainActivity;
public class PlayState extends State {
    private static final int MAP001_WIDTH = 1024;
    private static final int MAP001_HEIGHT = 768;
    private Player player;
    public static ArrayList<monster>monsters;
    //private Cloud cloud ,cloud2;
    private int playerEXP=LoadPlayState.getEXP();

    private static final int PLAYER_WIDTH=48;
    private static final int PLAYER_HEIGHT=71;
    private float recentTouchY,recentTouchX;
    private boolean gamePaused=false,iskill=false;
    private String pausedString="游戏暂停  点击恢复";;
    private int[] ScreenOrigin={0,0};//画布原点存在负坐标的可能性
    public static float x=0,y=0;
    private double totalDuration = 0;
    private boolean isAI_Battle=false,isEquipment_OPEN=false,Equipment_description=false;
    private UIButton isAIBattleButton,equipmentButton,InterfaceShow,equipment_clothes_Show;
    @Override
    public void init() {
        int[] Player_Coo={15,10};//后期改为从存档读取
        int play_equip=LoadPlayState.getPlay_equip_clothes();//后期改为存档读取，人物装备编号
        int play_HP_now=50;//后期改为存档读取，人物实时血量
        int play_Mp_now=30;//后期改为存档读取，人物实时蓝量
        ScreenOrigin[0]=(Player_Coo[0]-8);
        ScreenOrigin[1]=(Player_Coo[1]-6);
        //System.out.println("PlayStateScreenOrign  第一次"+ScreenOrigin[0]+"   "+ScreenOrigin[1]);
        player=new Player(Player_Coo[0],Player_Coo[1],PLAYER_WIDTH,PLAYER_HEIGHT,play_equip,play_HP_now,play_Mp_now);
        monsters =new ArrayList<monster>();
        //cloud=new Cloud(100,100);
        //cloud2=new Cloud(500,50);
        for(int i=0;i<5;i++) {
            monster b=new monster(ScreenOrigin[0],ScreenOrigin[1]);
            monsters.add(b);
        }
        isAIBattleButton=new UIButton(636,450,721,471,PlayAssets.startAI_Battle,PlayAssets.StopAI_Battle);
        equipmentButton=new UIButton(639,406,669,436,PlayAssets.equipmentButton,PlayAssets.equipmentButton);
        //int[] target=way(monsters.get(MinIndex).getCoordinate(0),monsters.get(MinIndex).getCoordinate(1));
        //输出最小距离的索引,然后去数组monsters提取坐标,*/
    }
    public void onExit(){

    }
    public void onLoad() {

    }
    //way返回数组值:0号位表示移动动画选择,1号位表示次数
    //1表示向左站立,2表示向左上站立,3向上站立,4向右上,5向右,6向右下,7向下,8向坐下站立,移动速度0格
    //动画11表示向左走,12表示左上走,13向上走,14向右上走,15向右走,16向右下走,17向下走,18向左下走,走位移1格
    //动画21表示向左跑,22表示左上跑,23向上跑,24向右上跑,25向右跑,26向右下跑,27向下跑,28向左下跑,跑位移2格
    //对WhatDo取余是方向,10整除是判断跑步还是走路,图片编号10-20站立，110-190表示走路，210-290表示跑步
    @Override
    public void update(float delta) {
        if(gamePaused) {
            return;
        }
        if(!player.isAlive()) {
            setCurrentState(new GameOverState(playerEXP/100));
        }
        if (isAI_Battle) {
            AI_Battle(AIfind());
        }
        player.update(delta);
        PlayAssets.player_Ani.update(delta);
        //PlayAssets.player_Ani.update(delta);
        ScreenOrigin[0]=(player.getCoordinate(0)-8);
        ScreenOrigin[1]=(player.getCoordinate(1)-6);
        //更新屏幕画的原点坐标
        updateBlock(delta);

        //cloud.update(delta);
        //cloud2.update(delta);
    }
    private int AIfind (){
        double[] MonCoo= new double[monsters.size()];//5后期改成读取一个常量
        //System.out.println("[monsters.size():"+monsters.size());
        for (int i=0;i<monsters.size();i++) {
            int TempX = Math.abs(player.getCoordinate(0) - monsters.get(i).getCoordinate(0));
            int TempY = Math.abs(player.getCoordinate(1) - monsters.get(i).getCoordinate(1));
            MonCoo[i] = TempX * TempX + TempY * TempY;//记录每个怪的距离
        }
        return getMinIndex(MonCoo);//取得最近坐标索引
    }
    private void AI_Battle(int index){
        int TempX,TempY;
        //System.out.println("目标怪物的坐标："+monsters.get(index).getCoordinate(0)+"   "+monsters.get(index).getCoordinate(1));
        //System.out.println("角色的坐标："+player.getCoordinate(0)+"   "+player.getCoordinate(1));
        //System.out.println("屏幕原点的坐标："+ScreenOrigin[0]+"   "+ScreenOrigin[1]);

        TempX=monsters.get(index).getCoordinate(0)-ScreenOrigin[0];//屏幕点击坐标
        TempY=monsters.get(index).getCoordinate(1)-ScreenOrigin[1];//屏幕点击坐标
        System.out.println("目标怪屏幕坐标:"+TempX+"   "+TempY+isAttackable(TempX,TempY));
        if (isAttackable(TempX,TempY)){
            //攻击
            Attack(TempX,TempY);
        }else if (Math.abs(TempX-8)<=2&&Math.abs(TempY-6)<=2){
            //走路
            playerMove(TempX, TempY,1);//继续移动
        }else {
            playerMove(TempX,TempY,2);//跑步
        }
    }

    private void Attack(int TempX,int TempY) {
        player.Battle=true;
        player.setDoWhat(3);
        player.setDirection(Get_Direction(TempX,TempY));
        //输出攻击动画
    }

    private boolean isAttackable(int tempX, int tempY) {

        if(Math.abs(tempX-8)<=player.getMAX_BATTLE_SPACE()&&Math.abs(tempY-6)<=player.getMAX_BATTLE_SPACE()) {
            for (int i = 0; i < monsters.size(); i++) {
                if (tempX == (monsters.get(i).getCoordinate(0) - ScreenOrigin[0]) && tempY == (monsters.get(i).getCoordinate(1) - ScreenOrigin[1])) {
                    return true;
                }
            }
            return false;
        }else {
            return false;
        }
        /*if (Math.abs(tempX-8)<=player.getMAX_BATTLE_SPACE()&&Math.abs(tempY-6)<=player.getMAX_BATTLE_SPACE()){
            //先判断点击是否角色攻击范围,不是,移动,是判断点击坐标是否有怪,无移动,有攻击
            for (int i=0;i<monsters.size();i++){
                //System.out.println("TempX:"+tempX+"TempY:"+tempY+"怪物屏幕坐标"+(monsters.get(i).getCoordinate(1)-ScreenOrigin[0])+"   "+(monsters.get(i).getCoordinate(1)-ScreenOrigin[1]));
                if (tempX==(monsters.get(i).getCoordinate(0)-ScreenOrigin[0])&&tempY==(monsters.get(i).getCoordinate(1)-ScreenOrigin[1])){
                    //攻击
                    player.Battle=true;
                    player.setDoLock(false);
                    player.isNoPressed = true;//跑步关闭
                    int temp=player.getWhatDo();
                    if (temp/10==1){
                        player.setWhatDo(temp+20);
                    }else if(temp/10==2){
                        player.setWhatDo(temp+10);
                    }
                } else {
                    player.Battle=false;
                }
                System.out.println("WhatDo"+player.getWhatDo());
            }
        }*/
    }
    private int getMinIndex(double[] a) {
        /*if (a.length == 0) {
            return Integer.parseInt(null);
        }*/
        int minIndex = 0;
        for(int i=0;i<a.length-1;i++){
            if(a[minIndex]>a[i+1]){//if对比了i+1，所以循环长度要减1
                minIndex=i+1;
            }
        }
        return minIndex;
    }
    @Override
    public void onPause() {
        gamePaused=true;
    }

    private void updateBlock(float delta) {
        //for(Block b:blocks) {
        for(int i=0;i<monsters.size();i++)	{
            monster b=monsters.get(i);
            b.update(delta, ScreenOrigin[0],ScreenOrigin[1],x,y);
            /*if(b.isVisible()) {
                if(Rect.intersects(b.getRect(), player.getCoordinateRect())) {
                    b.onCollide(player);
                    //iskill=true;
                    //playerScore=playerScore+30;
                    //GameMainActivity.setHighScore(playerScore);
                    monsters.get(i).reset();
                }
            }*/
        }
        /*if (j==monsters.size()){
            j=0;
            for(int i=0;i<monsters.size();i++) {
                monster b=monsters.get(i);
                b.reset();
            }
        }*/
    }
    @Override
    public void render(Painter g) {
        //g.setColor(Color.rgb(208, 244, 247));
        //g.fillRect(0, 0, MAP001_WIDTH, MAP001_HEIGHT);//填充地图map001是1024*768
        readerMap(g);
        renderPlayer(g);

        renderBlocks(g);
        //renderSun(g);


        g.drawImage(PlayAssets.grass, 0, 349);//600-251=349，换算屏幕坐标{0,11}后面改跟坐标移动
        renderHP(g);
        renderScore(g);
        renderUIButton(g);
        if(gamePaused) {
            g.setColor(Color.argb(153, 0, 0, 0));
            g.fillRect(0, 0, GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_HEIGHT);
            g.drawString(pausedString, 235, 240);
        }
        if (iskill){
            renderInformation(g);
            iskill=false;
        }
    }

    private void renderUIButton(Painter g) {
        isAIBattleButton.render(g);
        equipmentButton.render(g);
        if (isEquipment_OPEN){
            InterfaceShow=new UIButton(563,3,795,365,PlayAssets.equipmentButtonShow,PlayAssets.equipmentButtonShow);
            InterfaceShow.render(g);
            PlayAssets.equipmentInterfaceShow(player.getPlay_equip());
            equipment_clothes_Show=new UIButton(644,119,724,247,PlayAssets.equipment_clothes,PlayAssets.equipment_clothes);
            equipment_clothes_Show.render(g);
            if (Equipment_description){
                g.setFont(Typeface.DEFAULT, 14);
                g.setColor(Color.WHITE);
                g.drawString("布衣(男),重量5,持久5/5", 600, 320);
                g.drawString("防御0-2,魔御0-1", 600, 335);
            }
            //g.drawImage(PlayAssets.equipment_clothes,644,119);
        }
        //System.out.println("readerUIButton");
    }

    private void renderHP(Painter g) {
        int tempHP=(int)(Player.player_HP_now/(float)Player.player_HP_total*30);
        int tempMP=(int)(Player.player_MP_now/(float)Player.player_MP_total*30);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT, 10);
        g.drawString(Player.player_HP_now+"/"+Player.player_HP_total,383,135);
        g.setColor(Color.rgb(238, 44, 44));
        g.fillRect(388, 140, tempHP, 4);
        g.setColor(Color.rgb(0,0,0));
        g.strokeRect(387,139,31,5);
        g.strokeRect(387,144,31,5);
        g.setColor(Color.rgb(30,144,255));
        g.fillRect(388,145,tempMP,4);

    }

    private void readerMap(Painter g) {
        for (int i=0;i<24;i++){
            for (int j=0;j<21;j++)
            g.drawImage(PlayAssets.FloorTile001,i*48+(int)x-96,j*32+(int)y-64);
        }//能看21列18行
    }

    /*private void renderClouds(Painter g) {
        g.drawImage(PlayAssets.cloud1,(int)cloud.getX(),(int)cloud.getY(),100,60);
        g.drawImage(PlayAssets.cloud2,(int)cloud.getX(),(int)cloud.getY(),100,60);

    }*/
    /*private void renderSun(Painter g) {
        g.setColor(Color.rgb(255, 165, 0));
        g.fillOval(715, -85, 170, 170);
        g.setColor(Color.YELLOW);
        g.fillOval(725, -75, 150, 150);
    }*/
    private void renderBlocks(Painter g) {
        //for(Block b:blocks) {
        for(int i=0;i<monsters.size();i++)	{
            monster b=monsters.get(i);
            if(b.isVisible()) {
                //System.out.println("第"+i+"个怪物像素位"+b.getX()+" : "+b.getY());
                g.drawImage(PlayAssets.block, (int)b.getX(),(int)b.getY());
                readermosterHP(g,b);
            }
        }

    }

    private void readermosterHP(Painter g, monster b) {
        int tempHP=(int)(b.getHP()/(float)b.getTotalHP()*30);
        g.setColor(Color.rgb(238, 44, 44));
        g.fillRect((int)b.getX()-4, (int) b.getY()-32, tempHP, 4);
        g.setColor(Color.rgb(0,0,0));
        g.strokeRect((int)b.getX()-5,(int)b.getY()-32,31,5);
    }

    private void renderPlayer(Painter g) {
        /*if(player.isGrounded()) {
            if(false) {
                g.drawImage(PlayAssets.duck, (int)player.getX(), (int)player.getY());
            }else {
                PlayAssets.runAnim.render(g, (int)player.getX(), (int)player.getY(), player.getWidth(), player.getHeight());
            }
        }else {
            g.drawImage(PlayAssets.jump, (int)player.getX(), (int)player.getY(),player.getWidth(),player.getHeight());
        }*/
        PlayAssets.player_Ani.render(g, (int)player.getX(), (int)player.getY());
        //PlayAssets.rightwalk.render();
    }
    private void renderScore(Painter g) {
        g.setFont(Typeface.DEFAULT, 15);
        g.setColor(Color.WHITE);
        g.drawString(""+playerEXP+"/9999999", 665, 553);//经验值显示
        g.drawString("测试地图："+player.getCoordinate(0)+"："+player.getCoordinate(1),30,575);//显示坐标

    }
    private void renderInformation(Painter g) {
        g.setFont(Typeface.DEFAULT, 20);
        g.setColor(Color.RED);
        g.drawString("获得经验值"+30, (GameMainActivity.GAME_WIDTH-PLAYER_WIDTH)/2-20,(GameMainActivity.GAME_HEIGHT-251-PLAYER_HEIGHT)/2-25);//头上显示获得经验
    }
    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        recentTouchX = scaledX;
        recentTouchY = scaledY;
        int Touch_SceenCoo_X = (int) (recentTouchX / 48);
        int Touch_SceenCoo_Y = (int) (recentTouchY / 32);
        if(e.getAction()==MotionEvent.ACTION_DOWN) {
            if (isAIBattleButton.getButtonRect().contains(scaledX,scaledY)){
                isAIBattleButton.onTouchDown(scaledX,scaledY);
                if (isAI_Battle){
                    isAI_Battle=false;
                    isAIBattleButton.cancel();
                }else {
                    isAI_Battle=true;
                }
            }else if (equipmentButton.getButtonRect().contains(scaledX,scaledY)){
                if (isEquipment_OPEN){
                    isEquipment_OPEN=false;
                }else {
                    isEquipment_OPEN=true;
                }
            }else if(isEquipment_OPEN&&equipment_clothes_Show.getButtonRect().contains(scaledX,scaledY)){
                //底下显示装备说明，动态读取数据
                Equipment_description=true;
                //后期添加一个方法，把存放装备说明的String改为衣服的
            }
            else if (isAttackable(Touch_SceenCoo_X,Touch_SceenCoo_Y)){
                Attack(Touch_SceenCoo_X,Touch_SceenCoo_Y);
            }else{
                playerMove(Touch_SceenCoo_X, Touch_SceenCoo_Y,1);
            }
        }else if (e.getAction()==MotionEvent.ACTION_MOVE){
            playerMove(Touch_SceenCoo_X,Touch_SceenCoo_Y,2);
        } else if(e.getAction()==MotionEvent.ACTION_UP) {
            if (gamePaused) {
                gamePaused = false;
                return true;
            }
            if (!isAI_Battle){
                player.setDoWhat(0);
            }
            //人物停止移动
            //player.setWhatDo(player.getWhatDo()%0);
        }
        return true;
    }

    private void playerMove(int Touch_SceenCoo_X, int Touch_SceenCoo_Y,int WalkOrRun) {
        player.setDoWhat(WalkOrRun);
        player.setDirection(Get_Direction(Touch_SceenCoo_X,Touch_SceenCoo_Y));
    }

    private int Get_Direction(int Touch_SceenCoo_X, int Touch_SceenCoo_Y) {
        if (Touch_SceenCoo_X == 7 && Touch_SceenCoo_Y == 5) {
            return 2;
            //左上走,12
        } else if (Touch_SceenCoo_X == 9 && Touch_SceenCoo_Y == 5) {
            return 4;
            //右上走,14
        } else if (Touch_SceenCoo_X == 7 && Touch_SceenCoo_Y == 7) {
            return 8;
            //左下走,18
        } else if (Touch_SceenCoo_X == 9 && Touch_SceenCoo_Y == 7) {
            return 6;
            //右下走,16
        } else if (Touch_SceenCoo_X >= 7 && Touch_SceenCoo_X <= 9&&Touch_SceenCoo_Y <=5) {
            //往上走,13
            return 3;
        }else if (Touch_SceenCoo_X >= 7 && Touch_SceenCoo_X <= 9&&Touch_SceenCoo_Y >=7) {
            //往下走,17
            return 7;
        } else if (Touch_SceenCoo_Y >= 5 && Touch_SceenCoo_Y <= 7&&Touch_SceenCoo_X <= 7) {
            //player.duck();
            //往左走,11
            return 1;
        } else if (Touch_SceenCoo_Y >= 5 && Touch_SceenCoo_Y <= 7&&Touch_SceenCoo_X >= 9) {
            //往右走,15
            return 5;
        } else if (Touch_SceenCoo_X <= 6&&Touch_SceenCoo_Y <= 4) {
            //往左上,12
           return 2;
        } else if (Touch_SceenCoo_X <= 6&&Touch_SceenCoo_Y >= 8) {
            //往左下,18
            return 8;
        } else if (Touch_SceenCoo_X >= 10&&Touch_SceenCoo_Y <= 4) {
            //往右上,14
            return 4;
        } else if (Touch_SceenCoo_X >= 10&&Touch_SceenCoo_Y >= 8) {
            //往右下,16
            return 6;
        }
        return 0;
    }

    public int getScreenOrigin(int index){
        return ScreenOrigin[index];
    }
    @Override
    public String toString() {
        return "PlayState";
    }

}
