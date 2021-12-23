package com.salim.medhelp;

import android.content.Context;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class SettingsAdapter extends ArrayAdapter<String> {
    private java.lang.String[] values;
    private Context context;
    public SettingsAdapter(@NonNull Context context, java.lang.String[] values) {
        super(context, R.layout.more_item, values);
        this.context = context;
        this.values = values;

    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View settingsView = inflater.inflate(R.layout.settingsitem,parent,false);
        ImageView imageView = settingsView.findViewById(R.id.settingsicon);
        TextView textView = settingsView.findViewById(R.id.settingsname);
        textView.setText(values[position]);
        java.lang.String value = values[position];
        if(value.equals("Update Profile"))
            imageView.setImageResource(R.drawable.ic_person_add_black_24dp);
        else if(value.equals("Change Password"))
            imageView.setImageResource(R.drawable.ic_vpn_key_green_24dp);
        else if(value.equals("Tell a Friend"))
            imageView.setImageResource(R.drawable.ic_help_outline_green_24dp);

        return settingsView;
    }
}
