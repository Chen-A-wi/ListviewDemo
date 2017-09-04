package com.example.a_wi.listviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private StoreAdapter StoreAdpter;
    private ListView mListView;
    private ArrayList<StroeData> mListItem;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObj();
        initAdapter();
    }

    private void initObj() {
        mListView = (ListView)findViewById(R.id.listView);

        enterData();
    }

    private void enterData() {
        StroeData itemData1 = new StroeData("大安店","好讚",R.drawable.cat_icon);
        StroeData itemData2 = new StroeData("龍潭店","好讚",R.drawable.cat_icon);

        mListItem = new ArrayList<StroeData>();
        for (int i = 0; i < 20; i++) {
            mListItem.add(itemData1);
            mListItem.add(itemData2);
        }
    }

    private void initAdapter() {
        StoreAdpter = new StoreAdapter(this, mListItem, mButtonOnClickListen);//透過Adapter回傳按鈕監聽事件
        mListView.setAdapter((ListAdapter) StoreAdpter);

        //Item觸發監聽
        mListView.setOnItemClickListener(new itemOnClickListen());
    }

    private View.OnClickListener mButtonOnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button evtBtn = (Button)view;
            StroeData tag = (StroeData)evtBtn.getTag();
            Toast.makeText(MainActivity.this,"你按"+ String.valueOf(tag.getTitle()) +"按鈕是嗎？",Toast.LENGTH_LONG).show();
        }
    };

    private class itemOnClickListen implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }
}
