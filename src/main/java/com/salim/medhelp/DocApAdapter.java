package com.salim.medhelp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class DocApAdapter extends ResourceCursorAdapter{
    private static final String ap_loc = "location";
    private static final String ap_comp = "complain";
    private static final String ap_date = "date";
    private static final String ap_time = "time";

    static final class DocApTag{
        TextView location,complainn,apdate,aptime;
    }

    public DocApAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        return view;

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        DocApAdapter.DocApTag tag = new DocApAdapter.DocApTag();
        tag.location = view.findViewById(R.id.hositemlocation);
        tag.complainn = view.findViewById(R.id.hositemcomplain);
        tag.apdate = view.findViewById(R.id.hositemdate);
        tag.aptime = view.findViewById(R.id.hositemtime);

        view.setTag(tag);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        DocApAdapter.DocApTag tag = (DocApAdapter.DocApTag)view.getTag();

        tag.location.setText(cursor.getString(cursor.getColumnIndex(ap_loc)));
        tag.complainn.setText(cursor.getString(cursor.getColumnIndex(ap_comp)));
        tag.apdate.setText(cursor.getString(cursor.getColumnIndex(ap_date)));
        tag.aptime.setText(cursor.getString(cursor.getColumnIndex(ap_time)));


    }

}
