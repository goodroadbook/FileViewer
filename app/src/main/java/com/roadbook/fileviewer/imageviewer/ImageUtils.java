package com.roadbook.fileviewer.imageviewer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.roadbook.fileviewer.common.MediaData;
import com.roadbook.fileviewer.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by namjinha on 2016-01-22.
 */
public class ImageUtils
{

    public static Bitmap getVideoThumbnail(MediaData aMediaData)
    {
        Context context = aMediaData.imgview.getContext();
        ContentResolver thumbnailCR = context.getContentResolver();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap thumnailBm = MediaStore.Video.Thumbnails.getThumbnail(thumbnailCR,
                aMediaData.mediaid,
                MediaStore.Images.Thumbnails.MINI_KIND,
                options);
        Bitmap bitmap = getCenterBitmap(thumnailBm, 0);

        Drawable videoImg = null;
        if(Build.VERSION.SDK_INT >= 21)
        {
            videoImg = context.getDrawable(R.drawable.play_btn);
        }
        else
        {
            videoImg = context.getResources().getDrawable(R.drawable.play_btn);
        }
        Bitmap videoBitmap = ((BitmapDrawable) videoImg).getBitmap();
        return overlayBitmap(bitmap, videoBitmap);
    }

    private static Bitmap overlayBitmap(Bitmap aBmp1,
                                        Bitmap aBmp2)
    {
        Bitmap bmOverlay = Bitmap.createBitmap(aBmp1.getWidth(),
                aBmp1.getHeight(),
                aBmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(aBmp1, new Matrix(), null);
        canvas.drawBitmap(aBmp2,
                (aBmp1.getWidth() - aBmp2.getWidth())/2,
                (aBmp1.getHeight() - aBmp2.getHeight())/2,
                null);
        return bmOverlay;
    }


    public static Bitmap getImageThumbnail(MediaData aMediaData)
    {
        ContentResolver thumbnailCR = aMediaData.imgview.getContext().getContentResolver();
        Bitmap thumnailBm = MediaStore.Images.Thumbnails.getThumbnail(
                thumbnailCR,
                aMediaData.mediaid,
                MediaStore.Images.Thumbnails.MINI_KIND,
                null);
        return getCenterBitmap(thumnailBm, aMediaData.orientation);
    }

    public static Bitmap getAudioThumbnail(MediaData aMediaData)
    {
        /*final Uri albumartUri =  Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(albumartUri, aMediaData.mediaid);
        return uri;*/

        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(artworkUri, aMediaData.mediaid);

        ContentResolver cr = aMediaData.imgview.getContext().getContentResolver();
        InputStream in = null;
        try
        {
            in = cr.openInputStream(uri);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(in);
    }

    private static Bitmap getRotateBitmap(Bitmap aSrc,
                                          int aOrientation)
    {
        if(aSrc == null)
        {
            return null;
        }

        int width = aSrc.getWidth();
        int height = aSrc.getHeight();

        Matrix m = new Matrix();

        if(0 != aOrientation)
        {
            m.setRotate(aOrientation);
        }

        return Bitmap.createBitmap(aSrc,
                0,
                0,
                width,
                height,
                m,
                false);
    }

    private static Bitmap getCenterBitmap(Bitmap src,
                                          int aOrientation)
    {
        if(src == null)
        {
            return null;
        }

        Bitmap bitmap = null;
        int width = src.getWidth();
        int height = src.getHeight();

        Matrix m = new Matrix();

        if(0 != aOrientation)
        {
            m.setRotate(aOrientation);
        }

        if(width >= height)
        {
            bitmap = Bitmap.createBitmap(src,
                    width/2-height/2,
                    0,
                    height,
                    height,
                    m,
                    true);
        }
        else
        {
            bitmap = Bitmap.createBitmap(src,
                    0,
                    height/2-width/2,
                    width,
                    width,
                    m,
                    true);
        }

        return bitmap;
    }

    public static Bitmap getImageBitmap(String aImagePath, int aOrientation)
    {
        try
        {
            Bitmap displayImg = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            displayImg = BitmapFactory.decodeFile(aImagePath,
                    options);
            return getRotateBitmap(displayImg, aOrientation);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}


