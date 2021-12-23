package com.salim.medhelp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class TabletReminderListAdapter extends ResourceCursorAdapter {

    private static final String rem_name = "name";
    private static final String rem_treat = "treatment";
    static final class TabTag{
        TextView tabname;
        TextView tabtreat;

    }

    public TabletReminderListAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        TabTag tag = new TabTag();
        tag.tabname = view.findViewById(R.id.remitemtabletname);
        tag.tabtreat = view.findViewById(R.id.remitemtreatment);
        view.setTag(tag);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TabTag tag = (TabTag)(view.getTag());
        tag.tabname.setText(cursor.getString(cursor.getColumnIndex(rem_name)));
        tag.tabtreat.setText(cursor.getString(cursor.getColumnIndex(rem_treat)));

    }
}


