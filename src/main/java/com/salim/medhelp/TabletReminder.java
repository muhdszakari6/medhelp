package com.salim.medhelp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Rem_pojo;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabletReminder.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabletReminder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabletReminder extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    EditText tabname,tabtreatment;
    Button addrem;

    public TabletReminder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabletReminder.
     */
    // TODO: Rename and change types and number of parameters
    public static TabletReminder newInstance(String param1, String param2) {
        TabletReminder fragment = new TabletReminder();
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
        View view = inflater.inflate(R.layout.fragment_tablet_reminder, container, false);

        tabname = view.findViewById(R.id.remtabletname);
        tabtreatment = view.findViewById(R.id.remtablettreatment);
        addrem = view.findViewById(R.id.savetabletrem);
        addrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,treat;
                name = tabname.getText().toString();
                treat = tabtreatment.getText().toString();
                if(name.isEmpty()){
                    tabname.setError("Enter the tablet name ");

                }
                else if (treat.isEmpty()){
                    tabtreatment.setError("Ejnter the tablet's treatment");

                }
                else{
                    Rem_pojo rpojo = new Rem_pojo();
                    rpojo.setTabletname(tabname.getText().toString());
                    rpojo.setTablettreatment(tabtreatment.getText().toString());


                    SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);
                    final long id = preferences.getLong("id", 1092L);
                    final Signup_pojo pojo = new Signup_pojo();
                    pojo.setId(id);
                    Signup_crud crud = new Signup_crud(getContext());
                    Rem_pojo result = crud.addRem(rpojo,pojo);

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frag_container, new TabletReminderList()).addToBackStack(null).commit();


                }
            }
        });


        return view;
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
