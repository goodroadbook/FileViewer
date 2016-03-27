package com.roadbook.fileviewer.common;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

/**
 * Created by namjinha on 2016-03-21.
 */
public class MemorySize
{
    public static String FormatSize(long size)
    {
        String unit = null;
        String[] unitstr = {"KB", "MB", "GB"};
        for(int i=0; i<unitstr.length; i++)
        {
            if (size >= 1024)
            {
                unit = unitstr[i];
                size /= 1024;
            }
            else
            {
                break;
            }
        }
        return String.valueOf(size) + unit;
    }

    public static long getTotalMemorySize(File aFile)
    {
        if(null == aFile)
        {
            return 0;
        }

        Log.d("namjinha", "getTotalMemorySize = " + aFile.getAbsolutePath());

        StatFs stat = new StatFs(aFile.getPath());
        long blockSize = 0;
        if(Build.VERSION.SDK_INT >= 18)
        {
            blockSize = stat.getBlockSizeLong();
        }
        else
        {
            blockSize = stat.getBlockSize();
        }

        long totalBlocks = 0;
        if(Build.VERSION.SDK_INT >= 18)
        {
            totalBlocks = stat.getBlockCountLong();
        }
        else
        {
            totalBlocks = stat.getBlockCount();
        }

        Log.d("namjinha", "totalBlocks * blockSize = " + totalBlocks * blockSize);

        return totalBlocks * blockSize;
    }


    public static long getAvailableMemorySize(File aFile)
    {
        if(null == aFile)
        {
            return 0;
        }

        Log.d("namjinha", "getAvailableMemorySize = " + aFile.getAbsolutePath());

        StatFs stat = new StatFs(aFile.getPath());
        long blockSize = 0;
        if(Build.VERSION.SDK_INT >= 18)
        {
            blockSize = stat.getBlockSizeLong();
        }
        else
        {
            blockSize = stat.getBlockSize();
        }

        long availableBlocks = 0;
        if(Build.VERSION.SDK_INT >= 18)
        {
            availableBlocks = stat.getAvailableBlocksLong();
        }
        else
        {
            availableBlocks = stat.getAvailableBlocks();
        }

        Log.d("namjinha", "availableBlocks * blockSize = " + availableBlocks * blockSize);

        return availableBlocks * blockSize;
    }
}
