package com.roadbook.fileviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.roadbook.fileviewer.common.FWValue;
import com.roadbook.fileviewer.fileviewer.FileAdapter;
import com.roadbook.fileviewer.fileviewer.FileItem;

import java.io.File;
import java.util.ArrayList;

public class FileGridActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ArrayList<FileItem> mFileHistory = null;
    private ArrayList<FileItem> mFileList = null;
    private FileAdapter mFileAdapter = null;
    private ActionBar mActionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_grid);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mFileHistory = new ArrayList<>();
        mFileList = new ArrayList<>();

        File fi = Environment.getExternalStorageDirectory();
        createFileList(fi);
        mActionBar.setTitle("Storage");

        GridView gv = (GridView)findViewById(R.id.gridview);
        gv.setEmptyView((TextView)findViewById(R.id.empty));
        mFileAdapter = new FileAdapter(this);
        mFileAdapter.initData(mFileList);
        gv.setAdapter(mFileAdapter);
        gv.setOnItemClickListener(this);

        showDirName();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                handleBackKeyPressed();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        FileItem fileItem = mFileList.get(position);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        String type = null;
        switch (fileItem.fileType)
        {
            case FWValue.FILE_TYPE_FOLDER:
                mActionBar.setTitle(fileItem.fileName);
                mFileHistory.add(fileItem);
                createFileList(new File(fileItem.filePath));
                mFileAdapter.initData(mFileList);
                mFileAdapter.notifyDataSetChanged();
                showDirName();
                break;
            case FWValue.FILE_TYPE_IMAGE:
                type = "image/*";
                break;
            case FWValue.FILE_TYPE_VIDEO:
                type = "video/*";
                break;
            case FWValue.FILE_TYPE_AUDIO:
                break;
            case FWValue.FILE_TYPE_ACROBAT:
                type = "application/pdf";
                break;
            case FWValue.FILE_TYPE_POWERP:
                type = "application/vnd.ms-powerpoint";
                break;
            case FWValue.FILE_TYPE_EXCEL:
                type = "application/vnd.ms-excel";
                break;
            case FWValue.FILE_TYPE_WORD:
                type = "application/msword";
                break;
            case FWValue.FILE_TYPE_TEXT:
                type = "text/*";
                break;
            case FWValue.FILE_TYPE_HTML:
                type = "text/html";
                break;
            case FWValue.FILE_TYPE_ZIP:
                type = "application/zip";
                break;
            case FWValue.FILE_TYPE_APK:
                type = "application/vnd.android.package-archive";
                break;
            case FWValue.FILE_TYPE_UNKNOWN:
                type = "application/*";
                break;
            default:
                break;
        }

        if(null == type)
        {
            return;
        }

        intent.setDataAndType(Uri.fromFile(new File(fileItem.filePath)), type);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        handleBackKeyPressed();
    }

    private void handleBackKeyPressed()
    {
        if(null == mFileHistory || mFileHistory.size() == 0)
        {
            finish();
            return;
        }

        File file = null;
        int index = mFileHistory.size()-1;
        mFileHistory.remove(index);

        int size = mFileHistory.size();
        if(size == 0)
        {
            file = Environment.getExternalStorageDirectory();
            mActionBar.setTitle("Storge");
        }
        else
        {
            file = new File(mFileHistory.get(size-1).filePath);
            mActionBar.setTitle(file.getName());
        }

        createFileList(file);
        mFileAdapter.initData(mFileList);
        mFileAdapter.notifyDataSetChanged();

        showDirName();
    }

    private void showDirName()
    {
        if(null == mFileHistory)
        {
            return;
        }

        String path = "Storage > ";
        int size = mFileHistory.size();
        for(int i=0; i<size; i++)
        {
            path = path + mFileHistory.get(i).fileName + " > ";
        }

        TextView pathdir = (TextView)findViewById(R.id.historytxt);
        pathdir.setText(path);
    }

    private void createFileList(File aFile)
    {
        if(null == aFile)
        {
            return;
        }

        mFileList.clear();

        String extname = null;
        File[] files = aFile.listFiles();
        for(File file : files)
        {
            FileItem fitem = new FileItem();
            fitem.fileName = file.getName();
            fitem.filePath = file.getAbsolutePath();
            if(false == file.isDirectory())
            {
                extname = fitem.fileName.substring(fitem.fileName.lastIndexOf(".") + 1);
                fitem.fileiconRes = getIconResId(fitem, extname);
                fitem.fileExtName = extname;
            }
            else
            {
                fitem.fileType = FWValue.FILE_TYPE_FOLDER;
                fitem.fileiconRes = R.drawable.icon_folder_normal;
            }

            mFileList.add(fitem);
        }
    }

    private int getIconResId(FileItem aFitem, String aExtname)
    {
        int[] resId = {
                R.drawable.icon_picture_normal,
                R.drawable.icon_video_normal,
                R.drawable.icon_music_normal,
                R.drawable.icon_acrobat_normal,
                R.drawable.icon_html_normal,
                R.drawable.icon_powerpoint_normal,
                R.drawable.icon_excel_normal,
                R.drawable.icon_word_normal,
                R.drawable.icon_txt_normal,
                R.drawable.icon_zip_normal,
                R.drawable.icon_android_normal,
                R.drawable.icon_unknown_normal
        };

        String[][] extlist =
                {
                        {"png", "jpg"},
                        {"avi", "mp4"},
                        {"mp3"},
                        {"pdf"},
                        {"html","htm"},
                        {"ppt", "pptx"},
                        {"xlsx", "xls"},
                        {"docx"},
                        {"txt", "text"},
                        {"zip"},
                        {"apk"}
                };

        for(int i=0; i<extlist.length; i++)
        {
           for(int j=0; j<extlist[i].length; j++)
           {
               if(true == extlist[i][j].equals(aExtname))
               {
                   aFitem.fileType = i;
                   break;
               }
           }
        }

        return resId[aFitem.fileType];
    }
}