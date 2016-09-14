package com.example.veb.jsondemo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by VEB on 2016/9/5.
 */
public class ListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ClickListener clickListener;
    private List<Pictures> mList;
    private ImageLoader mLoader;
    public static String URLs[];

    public ListAdapter(Context context, List<Pictures> data, ListView listView, ClickListener clickListener){
        this.clickListener = clickListener;
        mInflater = LayoutInflater.from(context);
        this.mList = data;
        mLoader = new ImageLoader(listView,context);
        URLs = new String[data.size()];
        for(int i= 0; i < data.size(); i++ ){
            URLs[i] = data.get(i).getIconUrl();
        }
        mLoader.loadImage(data.size());
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
    public View getView(int position, View convertView, final ViewGroup parent) {

        Holder holder = null;
        if(convertView ==null){
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new Holder();
            holder.icon = (ImageView) convertView.findViewById(R.id.image1);
            holder.text = (Button) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }
        holder.text.setText((position+1) + ".  " + mList.get(position).getTexts());
        String url = mList.get(position).getIconUrl();
        holder.icon.setTag(url);//
        mLoader.showImages(holder.icon, url);

        final View v = convertView;
        final int p = position;
        final int idt = holder.text.getId();//获取需要点击的组件ID
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClicks(v, parent, p, idt);
            }
        });

        return convertView;
    }

    class Holder{
        public ImageView icon;
        public TextView text;
    }
}
