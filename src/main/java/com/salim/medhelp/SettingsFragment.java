package com.salim.medhelp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;import com.google.android.material.snackbar.Snackbar;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView listView;



    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        listView = view.findViewById(android.R.id.list);
        String[] values = {"Update Profile","Change Password","Tell a Friend"};
        SettingsAdapter morelistadapter = new SettingsAdapter(getActivity(),values);

        listView.setAdapter(morelistadapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();

                    manager.beginTransaction().replace(R.id.frag_container, new UpdateaccountFragment()).addToBackStack(null).commit();


                } else if (i == 1) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frag_container, new ChangepasswordFragment()).addToBackStack(null).commit();


                } else if (i == 2) {
                    final PopupMenu menu = new PopupMenu(getActivity(), view);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        final String message = "Download our MediHelper Application on Play Store created by Muhammad Salim Zakari";

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();
                            switch (id) {
                                case R.id.tellviaemail:
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("message/html");
                                    intent.putExtra(Intent.EXTRA_SUBJECT,"Download Application");
                                    intent.putExtra(Intent.EXTRA_TEXT,message);
                                    try{
                                        startActivity(intent.createChooser(intent,"Tell a friend"));
                                    }catch (ActivityNotFoundException ex){
                                        Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_LONG).show();
                                    }


                                    break;
                                case R.id.tellviamessage:
                                    Intent sendsms = new Intent(Intent.ACTION_VIEW);
                                    sendsms.setData(Uri.parse("sms:"));

                                    sendsms.putExtra("sms_body",message);
                                    startActivity(sendsms);

                                    break;
                            }
                            return true;
                        }
                    });

                    menu.inflate (R.menu.menu);
                    menu.show();



                }
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
