package com.salim.medhelp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class ViewPresAdapter extends ResourceCursorAdapter{

    private static final String pres_name = "name";
    private static final String pres_treat = "treatment";
    private static final String pres_dosedate ="dosedate";


    static final class ViewPresTag{
        TextView tabname,tabttreatment,tabletdate;

    }

    public ViewPresAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, layout, c, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = super.newView(context, cursor, parent);
        ViewPresTag tag = new ViewPresTag();
        tag.tabname = view.findViewById(R.id.prestabletname);
        tag.tabttreatment = view.findViewById(R.id.presitemtreatment);
        tag.tabletdate = view.findViewById(R.id.presitemdate);
        view.setTag(tag);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewPresTag tag = (ViewPresTag) view.getTag();
        tag.tabname.setText(cursor.getString(cursor.getColumnIndex(pres_name)));
        tag.tabttreatment.setText(cursor.getString(cursor.getColumnIndex(pres_treat)));
        tag.tabletdate.setText(cursor.getString(cursor.getColumnIndex(pres_dosedate)));

    }
}
