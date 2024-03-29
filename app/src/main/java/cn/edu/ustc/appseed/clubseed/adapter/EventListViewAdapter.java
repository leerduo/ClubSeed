package cn.edu.ustc.appseed.clubseed.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.LinkedList;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.data.ListPhp;
import cn.edu.ustc.appseed.clubseed.data.Event;

/**
 * Created by gdshen on 2/6/15.
 */
public class EventListViewAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private LinkedList<Event> mEvents;
    public static final int red = 0xfffa3746;

    public EventListViewAdapter(Activity activity, ListPhp listPhp) {
        this.activity = activity;
        this.mEvents = listPhp.getData();
    }


    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return mEvents.get(position);
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


        TextView textViewClubName = (TextView) convertView.findViewById(R.id.textViewClubName);
        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        TextView textViewPlace = (TextView) convertView.findViewById(R.id.textViewPlace);
        TextView textViewTime = (TextView) convertView.findViewById(R.id.textViewTime);
        TextView textViewSummary = (TextView) convertView.findViewById(R.id.textViewSummary);

        ImageView imageViewThumbnail = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);

        textViewClubName.setText(mEvents.get(position).getClubname());
        textViewTitle.setText(mEvents.get(position).getTitle());
        textViewPlace.setText(mEvents.get(position).getPlace());
        textViewTime.setText(mEvents.get(position).getTime());
        textViewSummary.setText(mEvents.get(position).getSummary());

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(mEvents.get(position).getClubname().substring(0, 1), red);
        imageViewThumbnail.setImageDrawable(drawable);

        return convertView;
    }
}
