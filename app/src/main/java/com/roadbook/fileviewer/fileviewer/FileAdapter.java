package com.roadbook.fileviewer.fileviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roadbook.fileviewer.R;
import com.roadbook.fileviewer.imageviewer.ImageLoader;

import java.util.ArrayList;

/**
 * Created by namjinha on 2016-01-22.
 */

public class FileAdapter extends BaseAdapter
{
    class ViewHolder
    {
        public ImageView imageView	= null;
        public TextView textView	= null;
    }

    private LayoutInflater mInflater = null;
    private Context mContext = null;
    private ImageLoader mImageLoader	= null;

    private ArrayList<FileItem> mFileList	= null;

    public FileAdapter(Context aContext)
    {
        mContext = aContext;
        mInflater = LayoutInflater.from(mContext);
        mImageLoader = new ImageLoader(mContext.getApplicationContext());
        mFileList = new ArrayList<FileItem>();
    }

    public void initData(ArrayList<FileItem> aFileList)
    {
        mFileList = aFileList;
    }

    public int getCount()
    {
        if(null == mFileList)
        {
            return 0;
        }

        return mFileList.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView,
                        ViewGroup parent)
    {
        FileItem fileItem = null;
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.file_item_layout, null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.menuicon);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.menuname);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(null == mFileList || mFileList.size() == 0)
        {
            return convertView;
        }

        int resIconId = mFileList.get(position).fileiconRes;
        viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(resIconId));
        viewHolder.textView.setText(mFileList.get(position).fileName);

        return convertView;
    }
}

