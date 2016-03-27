package com.roadbook.fileviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.roadbook.fileviewer.imageviewer.ImageUtils;

/**
 * Created by namjinha on 2016-01-22.
 */

public class ImageViewerActivity extends AppCompatActivity
{
    private ActionBar mActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String imagePath = i.getStringExtra("IMAGE_PATH");
        int orientation = i.getIntExtra("ORIENTATION", 0);
        String title = i.getStringExtra("TITLE");
        mActionBar.setTitle(title);

        showImageView(imagePath, orientation);
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

    private void showImageView(String aImagePath,
                               int aOrientation)
    {
        Bitmap bmp = ImageUtils.getImageBitmap(aImagePath, aOrientation);

        ImageView iView = (ImageView)findViewById(R.id.showimage);
        iView.setImageBitmap(bmp);
    }
}


