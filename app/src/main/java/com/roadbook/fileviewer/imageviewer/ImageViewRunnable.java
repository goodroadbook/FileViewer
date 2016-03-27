package com.roadbook.fileviewer.imageviewer;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.roadbook.fileviewer.MainActivity;
import com.roadbook.fileviewer.common.FWValue;
import com.roadbook.fileviewer.common.MediaData;
import com.roadbook.fileviewer.R;

import java.util.HashMap;

/**
 * Created by namjinha on 2016-01-22.
 */
public class ImageViewRunnable implements Runnable
{
    private Bitmap mBitmap = null;
    private MediaData mMediaData = null;
    private HashMap<ImageView, String> mImageViewMap = null;

    public ImageViewRunnable(
                                Bitmap aBitmap,
                                MediaData aMediaData,
                                HashMap<ImageView, String> aImageViewMap)
    {
        mBitmap = aBitmap;
        mMediaData = aMediaData;
        mImageViewMap = aImageViewMap;
    }

    public void run()
    {
        if(false == isImageViewValid())
        {
            return;
        }

        int defaulticon = FWValue.IMAGE_RES_ID;

        switch(mMediaData.type)
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

        if(mBitmap != null)
        {
            mMediaData.imgview.setImageBitmap(mBitmap);
        }
        else
        {
            mMediaData.imgview.setImageResource(defaulticon);
        }
    }

    private boolean isImageViewValid()
    {
        String mediaPath = mImageViewMap.get(mMediaData.imgview);
        if(mediaPath == null ||
                false == mediaPath.equals(mMediaData.mediapath))
        {
            return false;
        }

        return true;
    }
}

