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

    //宣告內容物
    static class ViewHolder {
        TextView mTitle,mText;
        ImageView mIcon;
        Button mButton;
    }

    //利用Adapter回傳按鈕監聽事件
    public StoreAdapter(Context context, ArrayList<StroeData> listItem,
                        View.OnClickListener onClick) {
        mInflater = LayoutInflater.from(context);
        mButtonOnClickListen = onClick;
        this.mlistItem = listItem;
    }

    @Override
    public int getCount() {
        return mlistItem.size();//回傳ArrayList有幾個
    }

    @Override
    public Object getItem(int position) {
        return mlistItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //1.item到哪個位置 2.item所使用的view 3.item的parent
    /**Holder是回收機制的精華*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) //判斷View是否被初始化過
        {
            convertView = mInflater.inflate(R.layout.item, null);

            holder = new ViewHolder();
            holder.mTitle = (TextView)convertView.findViewById(R.id.item_title);
            holder.mText = (TextView)convertView.findViewById(R.id.item_text);
            holder.mIcon = (ImageView)convertView.findViewById(R.id.item_imageView);
            holder.mButton = (Button)convertView.findViewById(R.id.button);

            convertView.setTag(holder);//將item建立好的物件存入holder中
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
