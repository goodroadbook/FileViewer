package com.roadbook.fileviewer.common;

import android.graphics.drawable.Drawable;

/**
 * Created by namjinha on 2015-12-27.
 */
public class GridItem
{
    private String mAppName = null;
    private Drawable mAppIcon = null;

    public GridItem(String aAppName, Drawable aAppIcon)
    {
        this.mAppName = aAppName;
        this.mAppIcon = aAppIcon;
    }

    public String getAppName()
    {
        return this.mAppName;
    }

    public Drawable getAppIcon()
    {
        return this.mAppIcon;
    }
}
