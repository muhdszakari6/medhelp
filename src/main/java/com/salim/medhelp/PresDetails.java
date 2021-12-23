package com.salim.medhelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Pres_pojo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PresDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PresDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PresDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    TextView namedt,treatmentdt,numofdrugdt,doseperdaydt,firstdosetimedt,firstdosedatedt;
    ImageView picdt;
    Button update;

    public PresDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PresDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static PresDetails newInstance(String param1, String param2) {
        PresDetails fragment = new PresDetails();
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
        View view = inflater.inflate(R.layout.fragment_pres_details, container, false);

        namedt = view.findViewById(R.id.presdetailtabletname);
        treatmentdt = view.findViewById(R.id.presdetailtreatment);
        numofdrugdt = view.findViewById(R.id.presdetailnumoftablets);
        doseperdaydt = view.findViewById(R.id.presdetaildoseperday);
        firstdosedatedt = view.findViewById(R.id.presdetailfirstdosedate);
        firstdosetimedt = view.findViewById(R.id.presdetailfirstdosetime);
        picdt = view.findViewById(R.id.presdetailpishure);
        update = view.findViewById(R.id.updatepresdetails);
        final Bundle bundle = this.getArguments();
        if(bundle!=null){
        long id = bundle.getLong("id");
            Signup_crud crud = new Signup_crud(getContext());
            Pres_pojo pojo = new Pres_pojo();
            pojo.setId(id);
            final Pres_pojo result = crud.getPres(pojo);
            namedt.setText(result.getName());
            treatmentdt.setText(result.getTreatment());
            numofdrugdt.setText(String.valueOf(result.getNumber()));
            doseperdaydt.setText(result.getDose());
            firstdosetimedt.setText(result.getFirstdosetime());
            firstdosedatedt.setText(result.getFirstdosedate());

            byte[] drugpic = result.getImage();
            if(drugpic!=null){

            Bitmap pic = BitmapFactory.decodeByteArray(drugpic, 0, drugpic.length);


            picdt.setImageBitmap(pic);

            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UpdatePres updatePres = new UpdatePres();
                    Bundle bundle1 = new Bundle();
                    bundle1.putLong("id",result.getId());
                    bundle1.putString("tabname",result.getName());
                    bundle1.putString("tabtreat",result.getTreatment());
                    bundle1.putInt("number",result.getNumber());
                    bundle1.putString("dose",result.getDose());
                    bundle1.putString("time",result.getFirstdosetime());
                    bundle1.putString("date",result.getFirstdosedate());
                    bundle1.putLong("trig",result.getTrig());
                    bundle1.putLong("user",result.getUserid());
                    updatePres.setArguments(bundle1);


                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager manager = activity.getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.frag_container, updatePres).addToBackStack(null).commit();

                }
            });

        }


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
