package com.roadbook.fileviewer.imageviewer;

import android.content.Context;
import android.widget.ImageView;

import com.roadbook.fileviewer.MainActivity;
import com.roadbook.fileviewer.R;
import com.roadbook.fileviewer.common.FWValue;
import com.roadbook.fileviewer.common.MediaData;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by namjinha on 2016-01-22.
 */

public class ImageLoader
{
    private final int D_RES_ID = R.drawable.default_img;
    private ExecutorService mExecutorService = null;
    private HashMap<ImageView, String> mImageViewMap = null;

    public ImageLoader(Context aContext)
    {
        mImageViewMap = new HashMap<ImageView, String>();
        mExecutorService = Executors.newFixedThreadPool(10);
    }

    public void displayImage(MediaData mediaData)
    {
        if(null == mImageViewMap)
        {
            return;
        }

        if(null == mediaData)
        {
            return;
        }

        mImageViewMap.put(mediaData.imgview, mediaData.mediapath);
        mExecutorService.submit(new ImageRunnable(mediaData, mImageViewMap));

        int defaulticon = FWValue.IMAGE_RES_ID;

        switch(mediaData.type)
        {
            case FWValue.TYPE_IMAGE:
                defaulticon = FWValue.IMAGE_RES_ID;
                break;
            case FWValue.TYPE_VIDEO:
                defaulticon = FWValue.VIDEO_RES_ID;
                break;
            case FWValue.TYPE_AUDIO:
                defaulticon = FWValue.AUDIO_RES_ID;
                break;
            default:
                defaulticon = FWValue.IMAGE_RES_ID;
                break;
        }

        mediaData.imgview.setImageResource(defaulticon);
    }
}

