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
import com.salim.medhelp.pojo.Pres_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewPres.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewPres#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPres extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listView;
    ViewPresAdapter adapter;
    AlarmManager alarmManager;


    public ViewPres() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPres.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewPres newInstance(String param1, String param2) {
        ViewPres fragment = new ViewPres();
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
        View view = inflater.inflate(R.layout.fragment_view_pres, container, false);

        listView = view.findViewById(android.R.id.list);

        Signup_pojo pojo = new Signup_pojo();
        Signup_crud crud = new Signup_crud(getContext());

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);
        pojo.setId(id);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Cursor cursor = crud.getPress(pojo);
        adapter = new ViewPresAdapter(getContext(),R.layout.presitem,cursor,1);
        listView.setAdapter(adapter);

        return view;
    }

    public void swap(){
        Signup_crud crud = new Signup_crud(getContext());
        Signup_pojo spojo = new Signup_pojo();
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        final long tid = preferences.getLong("id",1092L);
        spojo.setId(tid);

        adapter.swapCursor(crud.getPress(spojo));

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
                            case R.id.viewdetailedpresitem:
                                final AppCompatActivity activity = (AppCompatActivity)view.getContext();
                                FragmentManager manager = activity.getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putLong("id",l);
                                PresDetails presDetails = new PresDetails();

                                presDetails.setArguments(bundle);

                                manager.beginTransaction().replace(R.id.frag_container,presDetails).addToBackStack(null).commit();
                                break;
                            case R.id.deletepresitem:
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setTitle("MediHelper");
                                alert.setMessage("Do you want to delete this prescription?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Signup_crud signup_crud = new Signup_crud(getActivity());
                                        Pres_pojo pojo = new Pres_pojo();

                                        pojo.setId(l);
                                        Pres_pojo result = signup_crud.getPres(pojo);
                                        long trig = result.getTrig();



                                        Intent itActivity = new Intent();
                                        Intent itService = new Intent(getContext(), com.salim.medhelp.SnoozeEvent.class);

                                        itActivity.setAction("com.salim.medhelp.alarm");

                                        PendingIntent piActivity = PendingIntent.getActivity(getContext(), (int) trig, itActivity, 0);
                                        PendingIntent piService = PendingIntent.getService(getContext(), (int)trig, itService,  0);

                                        alarmManager.cancel(piActivity);
                                        alarmManager.cancel(piService);

                                        signup_crud.delete_pres(pojo);

                                        Toast.makeText(getContext(),"Prescription deleted",Toast.LENGTH_LONG).show();
                                        swap();


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
                menu.inflate(R.menu.presmenu);
                menu.show();
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
