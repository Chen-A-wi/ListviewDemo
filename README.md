# ListView（活動列表）

ListView有很多種也是製作專案很常用到的東西，今天要講解的部分是稍微複雜的利用BaseAdapter來達成客製化的ListView。

## 定義Item物件

首先呢先在專案中創一個.xml想怎麼取名都可以，範例這邊呢就先叫item.xml好了。

那創立這xml主要是要定義這listView裡面每一列中有什麼物件以及物件位置，後續listView可藉由這xml來填入不同的資料。

接著我們在item.xml加入了imageView、textView、Button以及Guideline總共7個物件。

```java
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="vertical">


<ImageView
android:id="@+id/item_imageView"
android:layout_width="0dp"
android:layout_height="0dp"
android:layout_marginBottom="32dp"
android:layout_marginLeft="32dp"
android:layout_marginRight="16dp"
android:layout_marginTop="16dp"
android:contentDescription=""
app:layout_constraintBottom_toTopOf="@+id/guideline"
app:layout_constraintLeft_toLeftOf="parent"
app:layout_constraintRight_toLeftOf="@+id/guideline2"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.0"
app:srcCompat="@mipmap/ic_launcher"
tools:ignore="ContentDescription,RtlHardcoded" />

<android.support.constraint.Guideline
android:id="@+id/guideline"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="horizontal"
app:layout_constraintGuide_begin="107dp" />

<TextView
android:id="@+id/item_title"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginLeft="32dp"
android:layout_marginTop="16dp"
android:text="TextView"
android:textSize="16sp"
android:textStyle="bold"
app:layout_constraintLeft_toLeftOf="@+id/guideline2"
app:layout_constraintTop_toTopOf="parent"
tools:ignore="HardcodedText,RtlHardcoded" />

<TextView
android:id="@+id/item_text"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginLeft="32dp"
android:layout_marginTop="16dp"
android:text="TextView"
app:layout_constraintLeft_toLeftOf="@+id/guideline2"
app:layout_constraintTop_toBottomOf="@+id/item_title"
tools:ignore="HardcodedText,RtlHardcoded" />

<Button
android:id="@+id/button"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:layout_marginBottom="8dp"
android:layout_marginRight="24dp"
android:layout_marginTop="8dp"
android:focusable="false"
android:text="Button"
app:layout_constraintBottom_toTopOf="@+id/guideline"
app:layout_constraintRight_toRightOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layout_constraintVertical_bias="0.511"
tools:ignore="HardcodedText,RtlHardcoded" />

<android.support.constraint.Guideline
android:id="@+id/guideline2"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:orientation="vertical"
app:layout_constraintGuide_end="277dp" />
</android.support.constraint.ConstraintLayout>
```

## \[注意\]

那這邊先提一個特別需要注意的地方是Button或是imageButton都會與OnItemClickListener搶焦點，你可能會發現怎麼點擊這Item都沒反應，這時就在物件內加入下面這行程式碼即可。

```java
android:focusable="false"
android:clickable="true"
```

或者你也可以在code中setFocusable\(false\)，像是下面這樣。

```java
button.setFocusable(false);
```

## 建立Data

這裡先建立處理資料的class，處理像是Title，text，image等要置換的資料。

將資料從MainActivity傳進StoreData內藉由StoreData傳至Adapter內，這裡先有個初步概念。

這裡這設計會傳入標題、內文及圖示，那如果有不同狀況可以依據進行調整。

```java
public class StroeData
{
private String mTitle,mText;
private int mIcon;

public StroeData(String title, String text, int icon) {
setTitle(title);
setText(text);
setIcon(icon);
}

//region ================================ GetMethod ============================================
public String getTitle() {
return mTitle;
}

public String getText() {
return mText;
}

public int getIcon() {
return mIcon;
}
//endregion

//region ================================ SetMethod ============================================
public void setTitle(String title) {
this.mTitle = title;
}

public void setText(String text) {
this.mText = text;
}

public void setIcon(int icon) {
this.mIcon = icon;
}
//endregion
}
```

## 建立Adapter

Adapter算是整個ListView的核心，可以使ListView實作它所制定的方法，如getCount方法可取得清單中的數量、getView方法可得到特定展示元件等。

BaseAdapter與CursorAdapter是抽象類別，其他ArrayAdapter、SimpleAdapter與SimpleCursorAdapter都是一般類別，可以直接建構出物件的類別。

1. ArrayAdapter：
當資料來源是陣列或List集合時，可使用ArrayAdapter。

2. SimpleCursorAdapter：
當資料來源是由資料庫\(SQLite\)查詢的Cursor結果時，可使用SimpleCursorAdapter。

3. SimpleAdapter：
資料來源是類似表格有列與欄的時候，可使用Map集合儲存列，再使用List將每一列收集後，使用SimpleAdapter。

4. BaseAdapter：
當有客製化需求時，可繼承BaseAdapter後再自行實作對應的方法。

```java
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

//將ButtonOnClick事件藉由Adapter傳回ＭainAcivity
public StoreAdapter(Context context, ArrayList<StroeData> listItem,
View.OnClickListener onClick) {
mInflater = LayoutInflater.from(context);
mButtonOnClickListen = onClick;
this.mlistItem = listItem;
}

@Override
public int getCount() {
return mlistItem.size();//取得資料總筆數
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
holder = (ViewHolder)convertView.getTag();//進入可視畫面時，取得超出可視畫面Item的佈局
}

//填入佈局內所定義的資料
StroeData mStroeData = mlistItem.get(position);
holder.mTitle.setText(mStroeData.getTitle());
holder.mText.setText(mStroeData.getText());
holder.mIcon.setImageResource(mStroeData.getIcon());

holder.mButton.setTag(mlistItem.get(position));//取得被點擊Button位於第幾個Item
if(mButtonOnClickListen != null)
holder.mButton.setOnClickListener(mButtonOnClickListen);

return convertView;
}
}
```

## 實作MainActivity

在MainActivity內就是單純餵入資料，初始化物件以及處理事件被觸發時該做的事情。

```java
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
```

更多內容可以參考我的[GitBook](https://www.gitbook.com/book/taiwan-a-wi/-sofware-engineer-survival-guide/details)

參考資料：[**綠豆湯**]( https://litotom.com/2016/03/26/%E6%B8%85%E5%96%AE%E5%85%83%E4%BB%B6%E4%BB%8B%E7%B4%B9listview-adapter/l)
