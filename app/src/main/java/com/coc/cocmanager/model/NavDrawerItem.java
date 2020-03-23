package com.coc.cocmanager.model;

/**
 * Created by ketan 23-3-2020
 */
public class NavDrawerItem {
    private boolean showNotify;
    private int icon;
    private String title;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify,int icon, String title) {
        this.icon = icon;
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
