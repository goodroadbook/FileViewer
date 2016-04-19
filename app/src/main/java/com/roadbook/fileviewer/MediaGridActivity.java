package com.roadbook.fileviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.roadbook.fileviewer.common.FWValue;
import com.roadbook.fileviewer.common.GetMediaData;
import com.roadbook.fileviewer.imageviewer.ImageAdapter;
import com.roadbook.fileviewer.common.MediaData;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by namjinha on 2016-01-22.
 */
public class MediaGridActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private int mType = FWValue.TYPE_IMAGE;
    private ArrayList<MediaData> mMediaList = null;
    private ActionBar mActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_action_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        //mActionBar.setDisplayShowCustomEnabled(true);

        //View customView = LayoutInflater.from(this).inflate(R.layout.custom_action, null);
        //mActionBar.setCustomView(customView);

        Intent i = this.getIntent();
        mType = i.getIntExtra(FWValue.TYPE, FWValue.TYPE_IMAGE);

        mMediaList = new ArrayList<MediaData>();

        switch(mType)
        {
            case FWValue.TYPE_IMAGE:
                mActionBar.setTitle(getString(R.string.title_image));
                GetMediaData.getImageArrayData(this, mMediaList);
                break;
            case FWValue.TYPE_VIDEO:
                mActionBar.setTitle(getString(R.string.title_video));
                GetMediaData.getVideoArrayData(this, mMediaList);
                break;
            case FWValue.TYPE_AUDIO:
                mActionBar.setTitle(getString(R.string.title_audio));
                GetMediaData.getAudioArrayData(this, mMediaList);
                break;
            default:
                break;
        }

        GridView gv = (GridView)findViewById(R.id.gridview);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.initData(mMediaList);
        gv.setAdapter(imageAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_bi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View aView,
                            int aPos, long aId)
    {
        switch(mMediaList.get(aPos).type)
        {
            case FWValue.TYPE_IMAGE:
                showOriImage(aPos);
                break;
            case FWValue.TYPE_VIDEO:
                showVideoPlayer(mMediaList.get(aPos).mediapath,
                        mMediaList.get(aPos).mediatitle);
                break;
            case FWValue.TYPE_AUDIO:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(new File(mMediaList.get(aPos).mediapath)), "audio/*");
                startActivity(i);
                break;
            default:
                break;
        }
    }

    private void showOriImage(int aPos)
    {
        Intent i = new Intent(this, ImageViewerActivity.class);
        i.putExtra("IMAGE_PATH", mMediaList.get(aPos).mediapath);
        i.putExtra("ORIENTATION", mMediaList.get(aPos).orientation);
        i.putExtra("TITLE", mMediaList.get(aPos).mediatitle);
        startActivity(i);
    }

    private void showVideoPlayer(String aVideoPath, String aVideoTitle)
    {
        Intent i = new Intent(this, VideoPlayerActivity.class);
        i.putExtra("VIDEO_PATH", aVideoPath);
        i.putExtra("TITLE", aVideoTitle);
        startActivity(i);
    }
}



