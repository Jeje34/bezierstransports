package com.bezierstransports.adapter;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.R;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;

import org.w3c.dom.Text;

public class AdapterStationSchedule extends BaseExpandableListAdapter {

    private Context context;
    private List<LineStation> listDataHeader;
    private HashMap<LineStation, List<Schedule>> listDataChild;

    public AdapterStationSchedule(Context context, List<LineStation> listDataHeader,
                                  HashMap<LineStation, List<Schedule>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
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
        ViewHolderChild viewHolder;
        final Schedule schedule = (Schedule) getChild(groupPosition, childPosition);

        viewHolder = new ViewHolderChild();
        LayoutInflater infalInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_item, null);

        viewHolder.nextDepartureLabel = (TextView) convertView.findViewById(R.id.next_departure_label);
        if (childPosition == 0) {
            viewHolder.nextDepartureLabel.setText(R.string.next_departure);
        }
        else if (childPosition == 1) {
            viewHolder.nextDepartureLabel.setText("                2-                ");
        }
        else if (childPosition == 2) {
            viewHolder.nextDepartureLabel.setText("                3-                ");
        }

        viewHolder.nextDepartureTime1 = (TextView) convertView.findViewById(R.id.next_departure_time1);
        viewHolder.nextDepartureTime1.setText(BeziersTransports.getScheduleFormat().format(schedule.getSchedule()));

        viewHolder.nextDepartureTimeLeft1 = (TextView) convertView.findViewById(R.id.next_departure_time_left1);
        // time left in minutes =  time schedule - time now
        long diff_ms = schedule.getSchedule().getTime() - BeziersTransports.getTimeNow().getTime(); // milliseconds
        long diff_min = diff_ms / (1000 * 60); // minutes
        viewHolder.nextDepartureTimeLeft1.setText("(" + diff_min + " min)");
        return convertView;
    }

    private class ViewHolderChild {
        TextView nextDepartureLabel;
        TextView nextDepartureTime1;
        TextView nextDepartureTimeLeft1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
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