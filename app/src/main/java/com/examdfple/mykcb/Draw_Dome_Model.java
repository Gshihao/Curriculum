package com.examdfple.mykcb;

import android.net.Uri;

public class Draw_Dome_Model {
    private String item_name;
    private int item_icon;
    public Draw_Dome_Model(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Uri getItem_icon() {
        return Uri.parse(String.valueOf(item_icon));
    }

    public void setItem_icon(int item_icon) {
        this.item_icon = item_icon;
    }
}
