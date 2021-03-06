package com.bezierstransports.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.R;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterStationSchedule extends BaseExpandableListAdapter {

    private Context context;
    private List<LineStation> listDataHeader;
    private HashMap<LineStation, List<Schedule>> listDataChild;

    public AdapterStationSchedule(Context context, List<LineStation> listDataHeader,
                                  HashMap<LineStation, List<Schedule>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;

        /* add a empty schedule at the beginning of the value list, to still
         call getChildView() in the case there are no children */
        for (Map.Entry<LineStation,List<Schedule>> entry : listChildData.entrySet()) {
            List<Schedule> value = entry.getValue();
            value.add(0, new Schedule());
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolderChild viewHolder = new ViewHolderChild();
        final Schedule schedule = (Schedule) getChild(groupPosition, childPosition);

        LayoutInflater infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_item, null);
        viewHolder.nextDepartureLabel = (TextView) convertView.findViewById(R.id.next_departure_label);

        // for the first child (an empty Schedule)...
        if (schedule.getSchedule() == null && childPosition == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FA5858"));
            if (this.getChildrenCount(groupPosition) == 1) {
                // ...if no other children: display a message
                viewHolder.nextDepartureLabel.setText(R.string.next_departure_tomorrow);
            } else {
                // ...if other children: don't display the empty Schedule
                convertView = infalInflater.inflate(R.layout.list_item_empty, null);
            }

        } else {
            convertView.setBackgroundColor(Color.parseColor(schedule.getLineStation().getLine().getColor()));
            // for other children, display time and time left
            if (childPosition == 1) {
                viewHolder.nextDepartureLabel.setText(R.string.next_departure);
            } else if (childPosition == 2) {
                viewHolder.nextDepartureLabel.setText("                2-                ");
            } else if (childPosition == 3) {
                viewHolder.nextDepartureLabel.setText("                3-                ");
            }

            viewHolder.nextDepartureTime1 = (TextView) convertView.findViewById(R.id.next_departure_time1);
            viewHolder.nextDepartureTime1.setText(BeziersTransports.getScheduleFormat().format(schedule.getSchedule()));

            viewHolder.nextDepartureTimeLeft1 = (TextView) convertView.findViewById(R.id.next_departure_time_left1);
            // time left in minutes =  time schedule - time now
            long diff_ms = schedule.getSchedule().getTime() - BeziersTransports.getTimeNow().getTime(); // milliseconds
            long diff_min = diff_ms / (1000 * 60); // minutes
            viewHolder.nextDepartureTimeLeft1.setText("(" + diff_min + " min)");
        }

        return convertView;
    }

    private class ViewHolderChild {
        TextView nextDepartureLabel;
        TextView nextDepartureTime1;
        TextView nextDepartureTimeLeft1;
    }

    private class ViewHolderEmptyChild {  }

    // return 1 if no children because of the empty Schedule (see constructor) in the aim to call getView
    public int getChildrenCount(int groupPosition) {
        if(this.listDataChild.get(this.listDataHeader.get(groupPosition)).size() != 0){
            return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        }
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolderGroup viewHolder;
        LineStation lineStation = (LineStation) getGroup(position);
        if (convertView == null) {
            viewHolder = new ViewHolderGroup();
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderGroup) convertView.getTag();
        }

        viewHolder.stationName = (TextView) convertView.findViewById(R.id.stationName);
        viewHolder.stationName.setTypeface(null, Typeface.BOLD);
        viewHolder.stationName.setText(lineStation.getStation().getStationName());
        viewHolder.stationName.setTextColor(Color.parseColor(lineStation.getLine().getColor()));

        viewHolder.cityName = (TextView) convertView.findViewById(R.id.cityName);
        viewHolder.cityName.setText(lineStation.getStation().getCity().getCityName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolderGroup {
        TextView stationName;
        TextView cityName;
    }
}