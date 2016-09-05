package com.example.veb.jsondemo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView;

import java.util.List;

/**
 * Created by VEB on 2016/9/5.
 */
public class ListAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private LayoutInflater mInflater;
    private List<Pictures> mList;
    private int mStart, mEnd;
    private boolean mFirstIn;
    private ImageLoader mloader;
    public static String URLs[];

    public ListAdapter(Context context, List<Pictures> data, ListView listView){
        mInflater = LayoutInflater.from(context);
        this.mList = data;
        mloader = new ImageLoader(listView);
        URLs = new String[data.size()];
        for(int i= 0; i < data.size(); i++ ){
            URLs[i] = data.get(i).getIconUrl();
        }
        listView.setOnScrollListener(this);
        mFirstIn = true;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        if(convertView ==null){
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new Holder();
            holder.icon = (ImageView) convertView.findViewById(R.id.image1);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        String url = mList.get(position).getIconUrl();
        holder.icon.setTag(url);
        mloader.showImage(holder.icon, url);
        return convertView;
    }

    class Holder{
        public ImageView icon;
    }

   public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCon, int toCount){
        mStart = firstVisibleItem;
       mEnd = mStart + visibleItemCon;
       if(mFirstIn && visibleItemCon > 0){
           mloader.loadImage(mStart, mEnd);
           mFirstIn = false;
       }
   }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            mloader.loadImage(mStart, mEnd);

        }else {
            mloader.canceAllTasks();
        }
    }

}
