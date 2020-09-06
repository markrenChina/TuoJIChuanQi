package com.example.tuojichuanqi.game.state;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.example.tuojichuanqi.GameApplication;
import com.example.tuojichuanqi.framework.util.Painter;
import com.example.tuojichuanqi.framework.util.UIButton;
import com.example.tuojichuanqi.framework.util.UIEditText;
import com.example.tuojichuanqi.game.main.GameMainActivity;
import com.example.tuojichuanqi.game.main.LogonAssets;

public class LogonState extends State {
    private UIButton LogonButton,registerButton,register_OK_Button,ok_Button,finish_Botton;
    private UIEditText usename,password,register_usename,register_PSW;
    private boolean usename_writingOpen=false,password_writingOpen=false,registerOpen=false,register_UN_OPEN=false,register_PSW_OPEN=false,password_ERROR=false;
    private Bitmap Logon,register,ok,password_error;
    @Override
    public void init() {
        LogonAssets.load();
        onLoad();
        LogonButton=new UIButton(420,310,520,340);
        registerButton=new UIButton(290,310,382,339);
        finish_Botton=new UIButton(500,140,530,170);
        register_OK_Button=new UIButton(480,320,580,352,LogonAssets.ok,LogonAssets.ok);
        usename=new UIEditText(371,230,520,250,true);
        password=new UIEditText(371,265,520,290,false);
        register_usename=new UIEditText(459,150,580,180,true);
        register_PSW=new UIEditText(459,190,580,220,true);
    }
    @Override
    public void onLoad(){
        Logon = LogonAssets.loadBitmap("Logon.png", false);
        register=LogonAssets.loadBitmap("register.png",false);
        ok=LogonAssets.loadBitmap("ok.png",false);
        password_error=LogonAssets.loadBitmap("passworderror.png",false);
    }
    @Override
    public void onExit() {
        LogonAssets.unloadBitmap(Logon);
        LogonAssets.unloadBitmap(register);
        LogonAssets.unloadBitmap(ok);
        LogonAssets.unloadBitmap(password_error);
    }
    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Logon,0,0);
        usename.render(g);
        password.render(g);
        if (registerOpen){
            g.drawImage(register,200,100);
            register_usename.render(g);
            register_PSW.render(g);
            System.out.println("registerOpen-1");
            register_OK_Button.render(g);
        }else if (password_ERROR){
            g.drawImage(password_error,180,200);
        }
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (LogonButton.getButtonRect().contains(scaledX,scaledY)){
                String psw = GameMainActivity.UN_AND_PSW.getString(usename.getInputBuilder().toString(), null);
                if (null != psw && psw.equals(password.getInputBuilder().toString())) {
                    GameMainActivity.PlayerFile= GameApplication.getContext().getSharedPreferences(usename.getInputBuilder().toString(), Activity.MODE_PRIVATE);
                    setCurrentState(new LoadState(this,new MenuState()));
                }else {
                    //显示账号密码不正确
                    password_ERROR=true;
                    ok_Button=new UIButton(500,320,580,360);
                }
                return true;
            }else if (password_ERROR&&ok_Button.getButtonRect().contains(scaledX,scaledY)){
                password_ERROR=false;
            }else if (registerButton.getButtonRect().contains(scaledX,scaledY)){
                registerOpen=true;
                usename_writingOpen=false;
                usename.writingClose();
                password_writingOpen=false;
                password.writingClose();
            }else if (registerOpen&&register_usename.getInputTextRect().contains(scaledX,scaledY)){
                register_UN_OPEN=true;
                register_PSW_OPEN=false;
                register_PSW.writingClose();
            }else if (registerOpen&&register_PSW.getInputTextRect().contains(scaledX,scaledY)){
                register_UN_OPEN=false;
                register_PSW_OPEN=true;
                register_usename.writingClose();
            }else if (registerOpen&&register_OK_Button.getButtonRect().contains(scaledX,scaledY)){
                registerOpen=false;
                register_PSW_OPEN=false;
                register_PSW.writingClose();
                register_UN_OPEN=false;
                register_usename.writingClose();
                if (register_usename.getInputBuilder().length()==0||register_PSW.getInputBuilder().length()==0){
                    //Toast.makeText(GameMainActivity.,"+++++",Toast.LENGTH_LONG).show();
                    return true;
                }else {
                    //写入存档
                    SharedPreferences.Editor editor=GameMainActivity.UN_AND_PSW.edit();
                    editor.putString(register_usename.getInputBuilder().toString(),register_PSW.getInputBuilder().toString());
                    editor.apply();
                    GameMainActivity.PlayerFile = GameApplication.getContext().getSharedPreferences(register_usename.getInputBuilder().toString(),0);
                }
            }
            else if (!registerOpen&&usename.getInputTextRect().contains(scaledX,scaledY)){
                usename_writingOpen=true;
                password_writingOpen=false;
                password.writingClose();
            }else if (!registerOpen&&password.getInputTextRect().contains(scaledX,scaledY)){
                usename_writingOpen=false;
                password_writingOpen=true;
                usename.writingClose();
            }else if (finish_Botton.getButtonRect().contains(scaledX,scaledY)){
                //
                System.exit(0);
                //GameMainActivity.finish=true;
            }
            if (usename_writingOpen){
                usename.onTouchDown(scaledX,scaledY,usename);
            }
            if (password_writingOpen) {
                password.onTouchDown(scaledX, scaledY, password);
            }
            if (register_UN_OPEN){
                register_usename.onTouchDown(scaledX,scaledY,register_usename);
            }
            if (register_PSW_OPEN){
                register_PSW.onTouchDown(scaledX,scaledY,register_PSW);
            }
            //scoreButton.onTouchDown(scaledX, scaledY);
        }
        return true;
    }

    @Override
    public String toString() {
        return "LogonState";
    }
}
