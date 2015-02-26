package cn.edu.ustc.appseed.clubseed.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.edu.ustc.appseed.clubseed.R;

/**
 * Created by Hengruo on 2015/2/26.
 */
public class DrawerListViewAdapter extends BaseAdapter {
    private String[] data;
    private Activity activity;
    private LayoutInflater inflater;

    public DrawerListViewAdapter(Activity activity, String[] data){
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[1];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null){
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null)
            convertView = inflater.inflate(R.layout.drawer_list, null);
        TextView textViewItem = (TextView)convertView.findViewById(R.id.drawer_listitem_textview);
        textViewItem.setText(data[position]);
        return convertView;
    }
}
