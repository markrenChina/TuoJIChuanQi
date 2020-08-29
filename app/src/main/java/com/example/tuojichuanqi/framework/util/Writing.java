package com.example.tuojichuanqi.framework.util;



import com.example.tuojichuanqi.game.main.LogonAssets;

public class Writing {
    private boolean big_Open;
    private UIButton[] buttonKey;
    public  Writing(){
        buttonKey=new UIButton[76];
        for (int i=0;i<40;i++){
            int temp1=i%9,temp2=i/9;
            if (i<=9) {
                buttonKey[i] = new UIButton(150 + temp1 * 50, 400 + temp2 * 50, 180 + temp1 * 50, 430 + temp2 * 50, LogonAssets.writing[i], LogonAssets.writing[i]);
            }else if (i<=35){
                buttonKey[i]=new UIButton(150 + temp1 * 50, 400 + temp2 * 50, 180 + temp1 * 50, 430 + temp2 * 50, LogonAssets.writing[i+26], LogonAssets.writing[i]);
            }else if (i==36){
                //删除键
                buttonKey[i]= new UIButton(650, 400 , 680, 430, LogonAssets.writing[62], LogonAssets.writing[62]);
            }else if (i==37){
                //大小写
                buttonKey[i]=new UIButton(650 , 450, 680, 480, LogonAssets.writing[63], LogonAssets.writing[64]);
            }else if (i==38){
                //隐藏键盘
                buttonKey[i]=new UIButton(650 , 500, 680, 530, LogonAssets.writing[65], LogonAssets.writing[65]);
            }else{
                //回车
                buttonKey[i]=new UIButton(650 , 550, 680, 580, LogonAssets.writing[66], LogonAssets.writing[66]);
            }
        }
    }
    public void render(Painter g) {
        //System.out.println("Writing read ok");
        for (int i=0;i<40;i++){
            //System.out.println("Writing read ok"+i);
                buttonKey[i].render(g);
        }
    }
    public void onTouchDown(int touchX, int touchY, UIEditText editText) {
        for (int i=0;i<40;i++) {
            if (buttonKey[i].getButtonRect().contains(touchX, touchY)) {
                if (i <= 9) {
                    //System.out.println("点击点击");
                    editText.InputBuilder.append(i);
                } else if (i <= 35 ) {
                    if (big_Open){
                        editText.InputBuilder.append((char)( i+55));
                    }else {
                        editText.InputBuilder.append((char)( i+87));
                    }
                } else if (i == 36) {
                    //删除键
                    if (editText.InputBuilder.length()!=0){
                    editText.InputBuilder.deleteCharAt(editText.InputBuilder.length()-1);
                    }
                } else if (i == 37) {
                    //大小写
                    if (big_Open){
                        big_Open=false;
                        for (int j=10;j<36;j++){
                            buttonKey[j].cancel();
                        }
                        buttonKey[i].cancel();
                    }else {
                        big_Open=true;
                        for (int j=10;j<36;j++){
                            buttonKey[j].on();
                        }
                        buttonKey[i].on();
                    }
                } else if (i == 38) {
                    //隐藏键盘
                    editText.writingClose();
                } else {
                    //回车
                    //editText.WritingOpen=false;
                    editText.writingClose();
                }
            }
        }
    }
}
