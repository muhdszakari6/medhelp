

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
 * {@link ChangepasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangepasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangepasswordFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    EditText oldpassword,newpasswprd,conpass;
    Button changePassword;

    public ChangepasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangepasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangepasswordFragment newInstance(String param1, String param2) {
        ChangepasswordFragment fragment = new ChangepasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);

        oldpassword = view.findViewById(R.id.oldpassword);
        newpasswprd = view.findViewById(R.id.newpassword);
        conpass = view.findViewById(R.id.conpassword);
        changePassword = view.findViewById(R.id.changepassword);
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);
        final String fname1 = preferences.getString("fname","NO");
        final String lname2 = preferences.getString("lname","NO");
        final String email2 = preferences.getString("email","NO");
        final String password2 = preferences.getString("password","NO");
        final String dob2 = preferences.getString("dob","NO");



        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String old = oldpassword.getText().toString();
                final String newg = newpasswprd.getText().toString();
                final String cong = conpass.getText().toString();


                if(old.isEmpty()){
                    oldpassword.setError("Enter your old password");
                }
                else if(newg.isEmpty()){
                    newpasswprd.setError("Enter your new password");
                }
                else if(cong.isEmpty()){
                    conpass.setError("Enter Confirmation password");
                }
                else{
                    if(!oldpassword.getText().toString().equals(password2)){
                        Toast.makeText(getContext(),"Your old password is incorrect",Toast.LENGTH_LONG).show();


                    }else if(!newpasswprd.getText().toString().equals(conpass.getText().toString())){
                        Toast.makeText(getContext(),"Your new password does not match with your confirmation password.",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Signup_pojo sp = new Signup_pojo();
                        sp.setId(id);
                        sp.setFirstname(fname1);
                        sp.setLastname(lname2);
                        sp.setEmail(email2);
                        sp.setDob(dob2);


                        Signup_pojo pojo = new Signup_pojo();
                        Signup_crud crud = new Signup_crud(getContext());
                        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

                        final long id = preferences.getLong("id", 1092L);


                        pojo.setId(id);

                        byte[] propic = crud.getImage(pojo);
                        sp.setImage(propic);
                        sp.setPassword(newpasswprd.getText().toString());
                        Signup_crud scrud = new Signup_crud(getContext());
                        int okay = scrud.update(sp);
                        if(okay!=0){
                            Toast.makeText(getContext(),"Password Changed",Toast.LENGTH_LONG).show();
                            SessionManager manager = new SessionManager(getContext());
                            manager.logoutUser();
                            getActivity().finish();

                        }
                        else {
                            Toast.makeText(getContext(),"Something went wrong.Try Again",Toast.LENGTH_LONG).show();

                        }
                    }
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
