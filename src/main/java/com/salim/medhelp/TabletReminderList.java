package com.salim.medhelp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Alarm_pojo;
import com.salim.medhelp.pojo.Rem_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabletReminderList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabletReminderList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabletReminderList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String trig_id = "_id";
    private static final String initial_trig = "inittrig";
    private static final String trig_alarm_id = "alarm_id";



    private OnFragmentInteractionListener mListener;
    ListView listView;
    TabletReminderListAdapter adapter;
    AlarmManager alarmManager;
    FloatingActionButton fab ;


    public TabletReminderList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabletReminderList.
     */
    // TODO: Rename and change types and number of parameters
    public static TabletReminderList newInstance(String param1, String param2) {
        TabletReminderList fragment = new TabletReminderList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tablet_reminder_list, container, false);

        fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager manager = activity.getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.frag_container, new TabletReminder()).addToBackStack(null).commit();
            }

        });

        fab.setVisibility(View.GONE);
        listView = view.findViewById(android.R.id.list);
        Signup_crud crud = new Signup_crud(getContext());
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        final long id = preferences.getLong("id", 1092L);
        final Signup_pojo pojo = new Signup_pojo();
        pojo.setId(id);

        Cursor cursor = crud.getRems(pojo);
        adapter = new TabletReminderListAdapter(getContext(),R.layout.tablet_reminder_item,cursor,0);
        listView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void swap(){
        Signup_crud crud = new Signup_crud(getContext());
        Signup_pojo spojo = new Signup_pojo();
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        long tid = preferences.getLong("id",1092L);
        spojo.setId(tid);

        adapter.swapCursor(crud.getRems(spojo));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, final long l) {
                PopupMenu menu = new PopupMenu(getContext(),view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.setalarmmenu:
                                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                                FragmentManager manager = activity.getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putLong("id",l);
                                TabletReminderForm remform = new TabletReminderForm();

                                remform.setArguments(bundle);

                                manager.beginTransaction().replace(R.id.frag_container,remform).addToBackStack(null).commit();
                                break;
                            case R.id.deleteremmenu:
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("MediHelper");
                                alert.setMessage("Do you want to delete this Reminder?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Signup_crud signup_crud = new Signup_crud(getActivity());
                                        Rem_pojo pojo = new Rem_pojo();
                                        pojo.setId(l);
                                        //get the all the alarm cursor that has this as their reminder id and delete and cancel all the alarms

                                        Cursor cursor = signup_crud.getRemAlarms(pojo);

                                        if(cursor!=null&&cursor.getCount()>0){
                                            while(cursor.moveToNext()){
                                            Alarm_pojo pojo1 = new Alarm_pojo();


                                            long id = cursor.getLong(cursor.getColumnIndex(trig_alarm_id));
                                            int theid = (int)cursor.getLong(cursor.getColumnIndex(initial_trig));


                                            Intent itActivity = new Intent();
                                            itActivity.setAction("com.salim.medhelp.rem");
                                            Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);


                                                //PendingIntent piBroadcast = PendingIntent.getBroadcast(getContext(), theid, itBroadcast, 0);
                                            PendingIntent piActivity = PendingIntent.getActivity(getContext(), theid, itActivity, 0);
                                            PendingIntent piService = PendingIntent.getService(getContext(), theid, itService, 0);

                                            alarmManager.cancel(piActivity);
                                            //alarmManager.cancel(piBroadcast);
                                            alarmManager.cancel(piService);

                                            pojo1.setId(id);


                                            signup_crud.delete_Alarm(pojo1);
                                            }
                                            cursor.close();

                                        }
                                        else{
                                            Toast.makeText(getContext(),"Cursor Empty",Toast.LENGTH_LONG).show();
                                            cursor.close();

                                        }
                                        cursor.close();


                                        signup_crud.delete_rem(pojo);
                                        swap();
                                        Toast.makeText(getContext(),"Reminder deleted",Toast.LENGTH_LONG).show();


                                    }

                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alert.show();
                                break;
                        }
                        return true;

                    }

                });
                menu.inflate(R.menu.rem_menu);
                menu.show();
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
