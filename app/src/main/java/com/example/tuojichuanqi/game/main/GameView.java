package com.example.tuojichuanqi.game.main;

import android.content.Context;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.game.state.LogonState;
import com.example.tuojichuanqi.game.state.MenuState;
import com.example.tuojichuanqi.game.state.State;
import com.example.tuojichuanqi.framework.util.InputHandler;
import com.example.tuojichuanqi.game.state.LoadPlayState;
//首先SurfaceView也是一个View，它也有自己的生命周期。
//因为它需要另外一个线程来执行绘制操作，所以我们可以在它生命周期的初始化阶段开辟一个新线程
//然后开始执行绘制，当生命周期的结束阶段我们插入结束绘制线程的操作。
//这些是由其内部一个SurfaceHolder对象完成的。
public class GameView extends SurfaceView implements Runnable {
    private Bitmap gameImage;//代替java的Image类
    private Rect gameImageSrc;//Canvas绘制需要一个Rectangle对象来传递参数
    private Rect gameImageDst;
    private Canvas gameCanvas;//绘图的画布代替java的Graphics
    private Painter graphics;
    private volatile boolean running =false;
    private volatile State currentState;
    private InputHandler inputHandler;
    private Thread gameThread;
    private int left=0,top=0;
    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        gameImage=Bitmap.createBitmap(gameWidth,gameHeight, Bitmap.Config.RGB_565);//gameWidth,gameHeight改成mapWidth，mapHeight
        //使用Bitmap类的createBitmap方法来初始化gamelmage ,该方法接受一个图像的宽度、高度和配置。
        //我们将宽度和高度分别设置为与gameWidth和gameHeight变量相等,并且把图像配置为 RGB_565. gamelmage将覆盖整个屏幕,并且不需要是透明的。
        gameImageSrc=new Rect(left,top,gameImage.getWidth(),gameImage.getHeight());
        //Rect gamelmageSrc将用来指定gamelmage的哪一个区域·应该绘制到屏幕上。
        //在这个例子中,我们想要整个gamelmage都绘制,因此传入相应的参数。
        gameImageDst=new Rect();
        //Rect gamelmageDst将用于指定当gamelmage绘制到屏幕上的时候应该如何缩放。
        gameCanvas=new Canvas(gameImage);//先把图像绘制在gameImage自己的画布(Canvas)上
        graphics =new Painter(gameCanvas);//再绘制在离屏画布上
        SurfaceHolder holder=getHolder();
        holder.addCallback(new Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // TODO Auto-generated method stub
                //Log.d("GameView", "Surface Created");
                initInput();
                if(currentState==null) {
                    setCurrentState(new LogonState());
                }
                initGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // Log.d()方法用来调试消息打印到LogCat
                //Log.d("GameView", "Surface Destroyed");
                pauseGame();
            }
        });
    }
    public void setCurrentState(State newState) {
        // TODO Auto-generated method stub
        System.gc();
        newState.init();
        currentState=newState;
        inputHandler.setCurrentState(currentState);
        //System.out.println(currentState.getMapWidth()+"   "+currentState.getMapHeight());
        //gameImage.createScaledBitmap(gameImage,currentState.getMapWidth(),currentState.getMapHeight(),false);
        //gameImage=Bitmap.createBitmap(currentState.getMapWidth(),currentState.getMapHeight(), Bitmap.Config.RGB_565);//gameWidth,gameHeight改成mapWidth，mapHeight
    }
    private void initInput() {
        if(inputHandler==null) {
            inputHandler=new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }
    private void initGame() {
        running=true;
        gameThread=new Thread(this,"Game Thread");
        gameThread.start();

    }
    private void pauseGame() {
        running=false;
        while(gameThread.isAlive()) {
            try {
                gameThread.join();//Thread.join()当游戏准备暂停时应该停止执行
                break;
            }catch(InterruptedException e){	}
        }
    }
    private void updateAndRender(long delta) {
        currentState.update(delta/1000f);
        currentState.render(graphics);
        //left=currentState.getScreenOrigin(0);
        //top=currentState.getScreenOrigin(1);
        //System.out.println(left+"gameView  "+top);
        //gameImageSrc.set(left,top,left+GameMainActivity.GAME_WIDTH,top+GameMainActivity.GAME_HEIGHT);
        //向currentState请求x，y
        renderGameImage();
    }
    private void renderGameImage() {
        // getHolder().lockCanvas()锁定绘制,只允许一个线程绘制
        Canvas screen=getHolder().lockCanvas();
        if(screen!=null) {//验证screen不为空
            screen.getClipBounds(gameImageDst);//检查screen边界传入gameImageDst
            //gameImageDst的left,top,right,bottom值与之前的Rect对象一样
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
            //gameImageSrc获取整个gameImage,并且使用gameImageDst缩放
            getHolder().unlockCanvasAndPost(screen);
            //解锁,结束绘制
        }
    }
    @Override
    public void run() {
        long updateDurationMillis=0;
        long sleepDurationMillis=0;
        while(running) {
            long beforeUpdateRender=System.nanoTime();
            long deltaMillis=sleepDurationMillis+updateDurationMillis;
            updateAndRender(deltaMillis);
            updateDurationMillis=(System.nanoTime()-beforeUpdateRender)/1000000L;
            sleepDurationMillis=Math.max(2, 17-updateDurationMillis);
            try {
                Thread.sleep(sleepDurationMillis);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void onResume() {
        if(currentState!=null) {
            currentState.onResume();
        }
    }
    public void onPause() {
        if(currentState!=null)
            currentState.onPause();
    }

}
