package com.example.tuojichuanqi.game.state;
import com.example.tuojichuanqi.framework.util.Painter;
import android.view.MotionEvent;
import com.example.tuojichuanqi.game.main.GameMainActivity;
public abstract class State {
    private int[] ScreenOrigin={0,0};
    public void setCurrentState(State newState) {
        GameMainActivity.sGame.setCurrentState(newState);
    }
    public abstract void init();
    //当我们转移到一个新的游戏状态的时候，将会调用init()方法。
    //这是初始化任何将要在整个游戏状态中使用的游戏对象的好地方。
    public void onExit(){

    }
    //调用assets的unload()释放bitmap的内存
    public void onLoad() {

    }
    //载入该状态特有的bitmap，assets载入公有bitmap。
    public abstract void update(float delta);
    //游戏循环将在每一帧上调用当前状态的update()方法。
    //我们使用它在游戏状态中更新每个游戏对象。
    public abstract void render(Painter g);
    //游戏循环将在每一帧上调用当前状态的render()方法。
    //我们使用它来把游戏图形渲染到屏幕上。
    public abstract boolean onTouch(MotionEvent e,int scaledX,int scaledY);
    //当玩家长按键盘按键的时候，将会调用当前状态的onKeyPress()方法。
    //它接受和键盘事件相关的信息作为参数，
    public void onResume() {}
    public void onPause() {}
    //当玩家释放键盘按键的时候，将会调用当前状态的onKeyRelease()方法。
    //它接受和键盘事件相关的信息作为参数
    public abstract String toString();
    //要求每个状态类实现一个toString()方法，该方法将以字符串格式提供关于该状态的一些信息。
    public int getScreenOrigin(int index){
        return ScreenOrigin[index];
    }
    //public abstract  int getMapWidth() ;
    //public abstract  int getMapHeight() ;
}
