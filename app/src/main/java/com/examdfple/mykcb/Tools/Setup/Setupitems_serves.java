package com.examdfple.mykcb.Tools.Setup;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class Setupitems_serves {
    private String zaA = "00",zaR = "00",zaG = "00",zaB = "00";
    private Context content;
    private View lau = null;  // 展示布局
    private EditText ed ;
    private LinearLayout lsu ;
    public Setupitems_serves(Context content){
        this.content = content;
    }


    public void Xuanseqi(SeekBar s1, SeekBar s2, SeekBar s3, SeekBar s4, LinearLayout lay1, String M_color, EditText edt, View v){
        this.lau = v;
        this.ed = edt;
        this.lsu = lay1;
        // 设置默认颜色值
        SetM_Color(M_color,s1, s2, s3, s4,v);

        s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            zaA= getA(progress);
            Logcol();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
        s2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zaR= getA(progress);
                Logcol();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        s3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zaG=getA(progress);
                Logcol();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        s4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zaB= getA(progress);
                Logcol();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    public String getA(int a){
        if(a <16 ){
            return "0"+Integer.toHexString(a);
        }
        return Integer.toHexString(a);

    }
    public void Logcol(){
        // 设置背景色
        this.lau.setBackgroundColor(Color.parseColor(getColorAndroid()));
        this.lsu.setBackgroundColor(Color.parseColor(getColorAndroid()));
        this.ed.setText(getColor());
    }
    /**返回颜色16进制字符饭*/
    public String getColor(){
        return "#"+zaR+zaG+zaB +zaA;
    }
    /**给Android使用颜色值*/
    public String getColorAndroid(){
        return "#"+zaA+zaR+zaG+zaB;
    }
    public String StrColor(String M_c){
        String str = M_c.split("#")[1];
        return  "#" + str.substring(6,8) + str.substring(0,6);
    }
    /**设置默认颜色值*/
    public void SetM_Color(String M_c, SeekBar s1, SeekBar s2, SeekBar s3, SeekBar s4, View v){
        this.lau.setBackgroundColor(Color.parseColor(StrColor(M_c)));
        this.lsu.setBackgroundColor(Color.parseColor(StrColor(M_c)));
        String[] parts = (M_c.split("#"))[1].split("(?=\\\\D)");
        String colorParts = M_c.substring(1, M_c.length());
        String R = colorParts.substring(0, 2);
        String G = colorParts.substring(2, 4);
        String B = colorParts.substring(4, 6);
        String A;
       try{  A= colorParts.substring(6, 8);}catch (Exception e){ A = "00";}
        s1.setProgress(Integer.parseInt(A,16));
        s2.setProgress(Integer.parseInt(R,16));
        s3.setProgress(Integer.parseInt(G,16));
        s4.setProgress(Integer.parseInt(B,16));
        zaR = R;
        zaA = A;
        zaG = G;
        zaB = B;
        Logcol();
    }
}
