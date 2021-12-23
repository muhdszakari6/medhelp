package com.salim.medhelp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TabRemAdapter extends ResourceCursorAdapter{
    private static final String alarm_time = "time";
    private static final String alarm_date = "date";
    private static final String alarm_dose = "dose";
    private static final String alarm_freq = "freq";


    private static final class TabRemTag{
        TextView time,date,dose,freq;


    }

    public TabRemAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        TabRemTag tag = new TabRemTag();
        tag.time = view.findViewById(R.id.alarmitemtime);
        tag.date = view.findViewById(R.id.alarmitemdate);
        tag.dose = view.findViewById(R.id.alarmitemdose);
        tag.freq = view.findViewById(R.id.freqitem);
        view.setTag(tag);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TabRemTag tag = (TabRemTag)(view.getTag());
        tag.date.setText(cursor.getString(cursor.getColumnIndex(alarm_date)));
        tag.time.setText(cursor.getString(cursor.getColumnIndex(alarm_time)));
        tag.dose.setText(cursor.getString(cursor.getColumnIndex(alarm_dose)));
        tag.freq.setText(cursor.getString(cursor.getColumnIndex(alarm_freq)));



    }
}
