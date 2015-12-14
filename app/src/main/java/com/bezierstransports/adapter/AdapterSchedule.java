package com.bezierstransports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.Schedule;

import java.util.List;

/**
 * Created by Jérémy Pastor on 13/12/2015.
 */
public class AdapterSchedule extends ArrayAdapter<Schedule> {

    public AdapterSchedule (Context context, int textViewResourceId, List<Schedule> objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Schedule schedule = getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout. simple_list_item_1, null);
            viewHolder.schedule = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.schedule.setText(BeziersTransports.getScheduleFormat().format(schedule.getSchedule()));
        return convertView;
    }

    private class ViewHolder {
        TextView schedule;
    }
}
