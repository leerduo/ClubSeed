package cn.edu.ustc.appseed.clubseed.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.zip.Inflater;

import cn.edu.ustc.appseed.clubseed.R;

/**
 * Created by Hengruo on 2015/2/19.
 */
public class DrawerAdapter extends BaseAdapter {

    private String[] texts;
    private int[] images;
    private LayoutInflater inflater;
    private Activity activity;

    public DrawerAdapter(String[] texts, int[] images, Activity activity){
        this.texts=texts;
        this.images=images;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return texts.length;
    }

    @Override
    public Object getItem(int position) {
        return texts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_events, null);


        TextView textViewDrawer = (TextView) convertView.findViewById(R.id.textViewDrawer);
        ImageView imageViewDrawer = (ImageView) convertView.findViewById(R.id.imgDrawer);

        textViewDrawer.setText(texts[position]);
        imageViewDrawer.setImageDrawable(activity.getResources().getDrawable(images[position]));
        return convertView;
    }
}
