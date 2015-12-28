package com.bezierstransports.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bezierstransports.R;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_line, null);
            viewHolder.bigLineNumber = (TextView) convertView.findViewById(R.id.bigLineNumber);
            viewHolder.lineNumber = (TextView) convertView.findViewById(R.id.lineNumber);
            viewHolder.lineName = (TextView) convertView.findViewById(R.id.lineName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (line.getLineNumber().length() == 1) {
            viewHolder.bigLineNumber.setText(line.getLineNumber());
        } else {
            viewHolder.bigLineNumber.setText(line.getLineNumber());
        }

        viewHolder.bigLineNumber.setBackgroundColor(Color.parseColor(line.getColor()));
        viewHolder.bigLineNumber.setTextColor(Color.WHITE);
        viewHolder.lineNumber.setText("Ligne " + line.getLineNumber());
        viewHolder.lineNumber.setTextColor(Color.parseColor(line.getColor()));
        viewHolder.lineName.setText(line.getLineName());
        return convertView;
    }

    private class ViewHolder {
        TextView bigLineNumber;
        TextView lineNumber;
        TextView lineName;
    }
}
