package com.roadbook.fileviewer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.roadbook.fileviewer.common.FWValue;
import com.roadbook.fileviewer.common.GridAdapter;
import com.roadbook.fileviewer.common.GridItem;
import com.roadbook.fileviewer.common.MemorySize;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        AdapterView.OnItemClickListener
{
    private final int MY_PERMISSION_REQUEST_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        initMainLayout();

        openSplashActivity();

        getFileSize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case FWValue.REQ_CODE_SPLASH:
                checkPermission();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            openSettingActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.nav_guide:
                openGuideActivity();
                break;
            case R.id.nav_setting:
                openSettingActivity();
                break;
            case R.id.nav_image:
                openImageViewer();
                break;
            case R.id.nav_video:
                openVideoViewer();
                break;
            case R.id.nav_music:
                openMusicViewer();
                break;
            case R.id.nav_file:
                openFileViewer();
                break;
            case R.id.nav_share:
                appshare();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                }
                break;
        }
    }

    private void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);
            }
        }
    }

    private void initMainLayout()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(R.drawable.home_top_bi);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView ptxt = (TextView)navigationView.getHeaderView(0).findViewById(R.id.pvertxt);
        try
        {
            ptxt.setText(this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        createMenu();
    }

    private void createMenu()
    {
        String[] mununame = {   getString(R.string.title_image),
                                getString(R.string.title_video),
                                getString(R.string.title_audio),
                                getString(R.string.title_file)};

        Integer[] menuresId = { R.drawable.home_image_normal,
                                R.drawable.home_videos_normal,
                                R.drawable.home_music_normal,
                                R.drawable.home_file_normal};

        ArrayList<GridItem> appItem = new ArrayList<>();

        for(int i=0; i<mununame.length; i++)
        {
            GridItem gitem;
            if(Build.VERSION.SDK_INT >= 21)
            {
                gitem = new GridItem(mununame[i], getDrawable(menuresId[i]));
            }
            else
            {
                gitem = new GridItem(mununame[i], getResources().getDrawable(menuresId[i]));
            }

            appItem.add(gitem);
        }

        GridView gridView = (GridView)findViewById(R.id.grid_main);
        GridAdapter adapter = new GridAdapter(this, R.layout.grid_item_layout, appItem);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void openSplashActivity()
    {
        Intent i = new Intent(this, SplashActivity.class);
        startActivityForResult(i, FWValue.REQ_CODE_SPLASH);
    }

    private void getFileSize()
    {
        int progress_totalsize = 0;
        int progress_availablesize = 0;

        long totalsize = MemorySize.getTotalMemorySize(Environment.getExternalStorageDirectory());
        String internalTotalMemSize = MemorySize.FormatSize(totalsize);

        long availablesize = MemorySize.getAvailableMemorySize(Environment.getExternalStorageDirectory());
        String internalAvailableMemSize = MemorySize.FormatSize(availablesize);

        String internal_str = String.format("%s / %s", internalAvailableMemSize, internalTotalMemSize);

        TextView internalText = (TextView)findViewById(R.id.internal_str);

        int color = 0;
        if(Build.VERSION.SDK_INT < 23)
        {
            color = getResources().getColor(R.color.blue);
        }
        else
        {
            color = getColor(R.color.blue);
        }
        Spannable textSpan = new SpannableString(internal_str);
        textSpan.setSpan(new ForegroundColorSpan(color),
                0,
                internalAvailableMemSize.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        internalText.setText(textSpan);

        progress_totalsize = Integer.valueOf(internalTotalMemSize.replaceAll("[^0-9]", ""));
        progress_availablesize = progress_totalsize - Integer.valueOf(internalAvailableMemSize.replaceAll("[^0-9]", ""));

        ProgressBar internalProgressbar = (ProgressBar)findViewById(R.id.progressbarinternal);
        internalProgressbar.setMax(progress_totalsize);
        internalProgressbar.setProgress(progress_availablesize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        switch(position)
        {
            case 0:
                openImageViewer();
                break;
            case 1:
                openVideoViewer();
                break;
            case 2:
                openMusicViewer();
                break;
            case 3:
                openFileViewer();
                break;
            default:
                break;
        }
    }

    private void openGuideActivity()
    {
        Intent i = new Intent(this, GuideActivity.class);
        startActivity(i);
    }

    private void openSettingActivity()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    private void openImageViewer()
    {
        Intent i = new Intent(this, MediaGridActivity.class);
        i.putExtra(FWValue.TYPE, FWValue.TYPE_IMAGE);
        startActivity(i);
    }

    private void openVideoViewer()
    {
        Intent i = new Intent(this, MediaGridActivity.class);
        i.putExtra(FWValue.TYPE, FWValue.TYPE_VIDEO);
        startActivity(i);
    }

    private void openMusicViewer()
    {
        Intent i = new Intent(this, MediaGridActivity.class);
        i.putExtra(FWValue.TYPE, FWValue.TYPE_AUDIO);
        startActivity(i);
    }

    private void openFileViewer()
    {
        Intent i = new Intent(this, FileGridActivity.class);
        startActivity(i);
    }

    private void appshare()
    {
        Intent msg = new Intent(Intent.ACTION_SEND);
        msg.addCategory(Intent.CATEGORY_DEFAULT);
        msg.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        msg.putExtra(Intent.EXTRA_TEXT, "market://details?id=com.roadbook.fileviewer");
        msg.putExtra(Intent.EXTRA_TITLE, getString(R.string.app_name));
        msg.setType("text/plain");
        startActivity(Intent.createChooser(msg, getString(R.string.title_share_intent)));
    }
}
