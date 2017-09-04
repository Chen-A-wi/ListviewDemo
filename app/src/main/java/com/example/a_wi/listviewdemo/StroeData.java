package com.example.a_wi.listviewdemo;

import android.widget.ImageView;

/**
 * Created by A-WI on 2017/8/5.
 */

public class StroeData
{
    private String mTitle,mText;
    private int mIcon;

    public StroeData(String title, String text, int icon) {
        setTitle(title);
        setText(text);
        setIcon(icon);
    }

    //region ================================ GetMethod ============================================
    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public int getIcon() {
        return mIcon;
    }
    //endregion

    //region ================================ SetMethod ============================================
    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }
    //endregion
}
