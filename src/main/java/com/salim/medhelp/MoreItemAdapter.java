package com.salim.medhelp;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreItemAdapter extends ArrayAdapter<String> {
    private String[] values;
    private Context context;
    public MoreItemAdapter(@NonNull Context context, String[] values) {
        super(context, R.layout.more_item, values);
        this.context = context;
        this.values = values;

    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View moreView = inflater.inflate(R.layout.more_item,parent,false);
        ImageView imageView = moreView.findViewById(R.id.moreicon);
        TextView textView = moreView.findViewById(R.id.moretxt);
        textView.setText(values[position]);
        String value = values[position];
        if(value.equals("Settings"))
            imageView.setImageResource(R.drawable.ic_settings_black_24dp);
        else if(value.equals("Go to Website"))
            imageView.setImageResource(R.drawable.ic_web_green_24dp);
        else if(value.equals("Customer Service"))
            imageView.setImageResource(R.drawable.ic_help_outline_green_24dp);
        else if(value.equals("Give App Feedback"))
            imageView.setImageResource(R.drawable.ic_phone_android_blue_24dp);
        return moreView;
    }
}