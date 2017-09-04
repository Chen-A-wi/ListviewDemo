package com.example.a_wi.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by A-WI on 2017/8/21.
 */

public class StoreAdapter extends BaseAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<StroeData>mlistItem;
    private ViewHolder holder;

    public View.OnClickListener mButtonOnClickListen;

    static class ViewHolder {
        TextView mTitle,mText;
        ImageView mIcon;
        Button mButton;
    }

    public StoreAdapter(Context context, ArrayList<StroeData> listItem,
                        View.OnClickListener onClick) {
        mInflater = LayoutInflater.from(context);
        mButtonOnClickListen = onClick;
        this.mlistItem = listItem;
    }

    @Override
    public int getCount() {
        return mlistItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mlistItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);

            holder = new ViewHolder();
            holder.mTitle = (TextView)convertView.findViewById(R.id.item_title);
            holder.mText = (TextView)convertView.findViewById(R.id.item_text);
            holder.mIcon = (ImageView)convertView.findViewById(R.id.item_imageView);
            holder.mButton = (Button)convertView.findViewById(R.id.button);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        StroeData mStroeData = mlistItem.get(position);
        holder.mTitle.setText(mStroeData.getTitle());
        holder.mText.setText(mStroeData.getText());
        holder.mIcon.setImageResource(mStroeData.getIcon());

        holder.mButton.setTag(mlistItem.get(position));
        if(mButtonOnClickListen != null)
            holder.mButton.setOnClickListener(mButtonOnClickListen);

        return convertView;
    }
}
