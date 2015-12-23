package com.bezierstransports.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bezierstransports.model.Line;

import java.util.List;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class AdapterLine extends ArrayAdapter<Line> {

    public AdapterLine (Context context, int textViewResourceId, List<Line> objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Line line = getItem(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout. simple_list_item_2, null);
            viewHolder.lineNumber = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.lineName = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.lineNumber.setText("Ligne " + line.getLineNumber());
        viewHolder.lineName.setText(line.getLineName());
        return convertView;
    }

    private class ViewHolder {
        TextView lineNumber;
        TextView lineName;
    }
}
