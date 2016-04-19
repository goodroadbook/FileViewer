package com.roadbook.fileviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.roadbook.fileviewer.videoviewer.VideoController;

/**
 * Created by namjinha on 2016-01-23.
 */
public class VideoPlayerActivity extends AppCompatActivity implements
        View.OnClickListener,
        SurfaceHolder.Callback
{
    private SurfaceView mSurfaceView = null;
    private SurfaceHolder mSurfaceholder	= null;
    private VideoController mVideoController = null;
    private String mVideoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vplayer_action_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        mVideoPath = i.getStringExtra("VIDEO_PATH");
        String title = i.getStringExtra("TITLE");
        actionBar.setTitle(title);

        mSurfaceView = (SurfaceView) findViewById(
                R.id.surfaceview);
        mSurfaceView.setOnClickListener(this);
        mSurfaceholder = mSurfaceView.getHolder();
        mSurfaceholder.addCallback(this);
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
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height)
    {
        ;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mVideoController = new VideoController();
        mVideoController.openVideo(mVideoPath, mSurfaceholder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        ;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.surfaceview:
                mVideoController.handleVideo();
                break;
            default:
                break;
        }
    }
}

