package com.softmedialtda.softmediaphotoapp.util;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softmedialtda.softmediaphotoapp.R;
import com.softmedialtda.softmediaphotoapp.models.Notification;

import java.util.ArrayList;

/**
 * Created by Agustin on 5/4/2018.
 */

public class NotificationListAdapter extends BaseAdapter {
    private ArrayList<Notification> listData;
    private LayoutInflater layoutInflater;

    public NotificationListAdapter(Context context, ArrayList<Notification> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row_notification,null);
            holder = new ViewHolder();
            holder.subjectTextView = (TextView) convertView.findViewById(R.id.subjectTextView);
            holder.senderTextView = (TextView) convertView.findViewById(R.id.senderTextView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.subjectTextView.setText(listData.get(position).getSubject());
        holder.senderTextView.setText(listData.get(position).getSender());
        holder.dateTextView.setText(listData.get(position).getDate());

        return convertView;
    }

    static class ViewHolder{
        TextView subjectTextView;
        TextView senderTextView;
        TextView dateTextView;

    }
}
