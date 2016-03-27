package com.roadbook.fileviewer.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roadbook.fileviewer.R;

import java.util.ArrayList;

/**
 * Created by namjinha on 2015-12-27.
 */
public class GridAdapter extends ArrayAdapter<GridItem>
{
    class ViewHolder
    {
        public ImageView imageView	= null;
        public TextView textView	= null;
    }

    private LayoutInflater mInflater			= null;

    public GridAdapter(Context context, int resource, ArrayList<GridItem> aGridItem)
    {
        super(context, resource, aGridItem);

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return super.getCount();
    }

    @Override
    public GridItem getItem(int position)
    {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;

        ViewHolder viewHolder = new ViewHolder();
        if(null == view)
        {
            view = mInflater.inflate(R.layout.grid_item_layout, null);
            viewHolder.imageView = (ImageView)view.findViewById(R.id.menuicon);
            viewHolder.textView = (TextView)view.findViewById(R.id.menuname);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)view.getTag();
        }

        GridItem gridItem = getItem(position);
        viewHolder.imageView.setImageDrawable(gridItem.getAppIcon());
        viewHolder.textView.setText(gridItem.getAppName());

        return view;
    }
}

