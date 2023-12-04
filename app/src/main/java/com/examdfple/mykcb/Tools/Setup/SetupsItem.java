package com.examdfple.mykcb.Tools.Setup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.examdfple.mykcb.R;
import com.examdfple.mykcb.setFile;

import org.json.JSONObject;

public class SetupsItem {
    private Context content;
    public SetupsItem(Context content) {
        this.content = content;
    }

    /**
     * 修改颜色值
     *
     * @param alert   弹唱对象
     * @param M_color 默认颜色值
     * @param jso
     * @param v
     */
    public boolean Modify_Color(AlertDialog.Builder alert, String M_color, JSONObject jso, View v) {
        // 添加视图
        View vise = View.inflate(content,R.layout.setitems__color_selection,null);
        SeekBar SeekBar1s = vise.findViewById(R.id.seekBar);
        SeekBar SeekBar2s = vise.findViewById(R.id.seekBar2);
        SeekBar SeekBar3s = vise.findViewById(R.id.seekBar3);
        SeekBar SeekBar4s = vise.findViewById(R.id.seekBar4);
        LinearLayout lay1 = vise.findViewById(R.id.backcolorsdf);
        EditText edt = vise.findViewById(R.id.editTextTextEmailAddress);

        // 创建弹窗
        EditText editText = new EditText(content);
        editText.setText(M_color);
        // 选择颜色值
        Setupitems_serves sers = new Setupitems_serves(content);
        sers.Xuanseqi(SeekBar1s,SeekBar2s,SeekBar3s,SeekBar4s,lay1,M_color,edt,v);
        alert.setTitle("修改标题颜色")
                .setView(vise)
                // .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 保存颜色值
                        try {
                            jso.put("DatenavigationColor",sers.getColor());
                            setFile sdf = new setFile(content);
                            // 保存文件
                            sdf.write("Datpack.json",jso.toString());
                             // 重启后生效
//                            announcement();
//                            new Setup().SetSet_it();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();

        return true;
    }
}
