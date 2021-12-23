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

import com.google.android.material.card.MaterialCardView;import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Signup_pojo;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateaccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateaccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateaccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    EditText fname,lname,email,password,dob;
    Button update;

    public UpdateaccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UptdateaccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateaccountFragment newInstance(String param1, String param2) {
        UpdateaccountFragment fragment = new UpdateaccountFragment();
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
        final View view = inflater.inflate(R.layout.fragment_updateaccount, container, false);
        fname = view.findViewById(R.id.updatefirstname);
        lname = view.findViewById(R.id.updatelastname);
        email = view.findViewById(R.id.updateemail);
        password = view.findViewById(R.id.updatepassword);
        dob = view.findViewById(R.id.updatedob);
        update = view.findViewById(R.id.updateprofile);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);
        final String fname1 = preferences.getString("fname","NO");
        final String lname2 = preferences.getString("lname","NO");
        final String email2 = preferences.getString("email","NO");
        final String password2 = preferences.getString("password","NO");
        final String dob2 = preferences.getString("dob","NO");

        fname.setText(fname1);
        lname.setText(lname2);
        email.setText(email2);
        password.setText(password2);
        dob.setText(dob2);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname1.isEmpty()){
                    fname.setError("Please Enter first name");
                }
                else if(lname2.isEmpty()){
                    lname.setError("Please Enter last name");
                }
                else if(email2.isEmpty()|| email2.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(email2.toString()).matches()){
                    email.setError("Please Enter a valid email address");
                }
                else if(dob2.isEmpty()){
                    dob.setError("Please Enter your State");
                }
                else if(password2.isEmpty()){
                    password.setError("Please enter a valid password");
                    password.requestFocus();
                }
                else {
                    Signup_pojo sp = new Signup_pojo();
                    sp.setId(id);
                    sp.setFirstname(fname.getText().toString());
                    sp.setLastname((lname.getText().toString()));
                    sp.setEmail(email.getText().toString());
                    sp.setDob(dob.getText().toString());
                    sp.setPassword(password.getText().toString());
                    Signup_pojo pojo = new Signup_pojo();
                    Signup_crud crud = new Signup_crud(getContext());
                    SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

                    final long id = preferences.getLong("id", 1092L);


                    pojo.setId(id);

                    byte[] propic = crud.getImage(pojo);
                    sp.setImage(propic);

                    Signup_crud scrud = new Signup_crud(getContext());
                    int okay = scrud.update(sp);
                    if(okay!=0){
                        Toast.makeText(getContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                        SessionManager manager = new SessionManager(getContext());
                        manager.logoutUser();
                        getActivity().finish();

                    }
                    else {
                        Toast.makeText(getContext(),"Something went wrong.Try Again",Toast.LENGTH_LONG).show();

                    }
                }
            }});

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
