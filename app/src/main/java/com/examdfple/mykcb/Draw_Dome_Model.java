package com.examdfple.mykcb;
public class Draw_Dome_Model {
    private String item_name;
    private int item_icon;
    private Class itemActivity;
    public Draw_Dome_Model(String item_name, int item_icon, Class itemActivity) {
        this.item_name = item_name;
        this.item_icon = item_icon;
        this.itemActivity = itemActivity;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int  getItem_icon() {
        return item_icon;
    }

    public void setItem_icon(int item_icon) {
        this.item_icon = item_icon;
    }

    public Class getItemActivity() {
        return itemActivity;
    }

    public void setItemActivity(Class itemActivity) {
        this.itemActivity = itemActivity;
    }
}
