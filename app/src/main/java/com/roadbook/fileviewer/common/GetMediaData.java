package com.roadbook.fileviewer.common;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.roadbook.fileviewer.MainActivity;
import com.roadbook.fileviewer.common.MediaData;

import java.util.ArrayList;

/**
 * Created by namjinha on 2016-01-22.
 */
public class GetMediaData
{
    public static void getImageArrayData(Context aContext,
                                         ArrayList<MediaData> aMediaData)
    {
        Cursor imageCursor = null;

        String[] imagecolumns = {
                MediaStore.Images.Media._ID,            // 미디어 ID
                MediaStore.Images.Media.DATA,          // 이미지 경로
                MediaStore.Images.Media.ORIENTATION,  // 이미지 방향
                MediaStore.Images.Media.TITLE
        };

        //컨텐트 리졸브의 query() 함수와 URI를 이용하여 이미지 정보를 가져온다.
        imageCursor = aContext.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imagecolumns,
                null,
                null,
                null);

        // 가져온 이미지 정보는 Cursor를 통해 확인할 수 있다.
        if(imageCursor != null && imageCursor.moveToFirst())
        {
            int imageid = 0;
            String imagePath = null;
            int orientation = 0;
            String title = null;

            int imageIdColumnIndex = imageCursor.
                    getColumnIndex(MediaStore.Images.Media._ID);
            int imagePathColumnIndex = imageCursor.
                    getColumnIndex(MediaStore.Images.Media.DATA);
            int imageOrientationColumnIndex = imageCursor.
                    getColumnIndex(MediaStore.Images.Media.ORIENTATION);
            int imageTitleColumnIndex = imageCursor.
                    getColumnIndex(MediaStore.Images.Media.TITLE);
            do
            {
                imageid = imageCursor.getInt(
                        imageIdColumnIndex);
                imagePath = imageCursor.getString(
                        imagePathColumnIndex);
                orientation = imageCursor.getInt(
                        imageOrientationColumnIndex);
                title =  imageCursor.getString(
                        imageTitleColumnIndex);

                // 이미지 정보 중에 필요한 미디어 ID, 이미지 경로, 이미지 방향을 
                // MediaData 클래스와 ArrayList로 관리한다.
                MediaData info = new MediaData();
                info.type = FWValue.TYPE_IMAGE;
                info.mediaid = imageid;
                info.mediapath = imagePath;
                info.orientation = orientation;
                info.mediatitle = title;
                aMediaData.add(info);
            }
            while(imageCursor.moveToNext());
        }
    }

    public static void getVideoArrayData(Context aContext,
                                         ArrayList<MediaData> aMediaData)
    {
        Cursor videoCursor = null;

        String[] videocolumns = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE
        };


        videoCursor = aContext.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videocolumns,
                null,
                null,
                null);

        if(videoCursor != null && videoCursor.moveToFirst())
        {
            int videoId = 0;
            String videoPath = null;
            String title = null;

            int videoIdColumnIndex = videoCursor.
                    getColumnIndex(MediaStore.Video.Media._ID);
            int videoPathColumnIndex = videoCursor.
                    getColumnIndex(MediaStore.Video.Media.DATA);
            int videoTitleColumnIndex = videoCursor.
                    getColumnIndex(MediaStore.Video.Media.TITLE);

            do
            {
                videoId = videoCursor.getInt(videoIdColumnIndex);
                videoPath = videoCursor.getString(
                        videoPathColumnIndex);
                title = videoCursor.getString(
                        videoTitleColumnIndex);

                MediaData info = new MediaData();
                info.type = FWValue.TYPE_VIDEO;
                info.mediaid = videoId;
                info.mediapath = videoPath;
                info.mediatitle = title;
                aMediaData.add(info);
            }
            while(videoCursor.moveToNext());
        }
    }

    public static void getAudioArrayData(Context aContext, ArrayList<MediaData> aMediaData)
    {
        Cursor audioCursor = null;

        String[] audiocolumns = {
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA
        };

        audioCursor = aContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                audiocolumns,
                null,
                null,
                null);

        if(audioCursor != null && audioCursor.moveToFirst())
        {
            int audioId = 0;
            String audioPath = null;
            String audioTitle = null;

            int audioColumnIndex = audioCursor.
                    getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int audioPathColumnIndex = audioCursor.
                    getColumnIndex(MediaStore.Audio.Media.DATA);
            int audioTitleColumnIndex = audioCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do
            {
                audioId = audioCursor.getInt(audioColumnIndex);
                audioPath = audioCursor.getString(audioPathColumnIndex);
                audioTitle = audioCursor.getString(audioTitleColumnIndex);

                MediaData info = new MediaData();
                info.type = FWValue.TYPE_AUDIO;
                info.mediaid = audioId;
                info.mediatitle = audioTitle;
                info.mediapath = audioPath;
                aMediaData.add(info);
            }
            while(audioCursor.moveToNext());
        }
    }
}
