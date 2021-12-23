package com.salim.medhelp;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Signup_pojo;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ImageView uimage;
    TextView uname, dob, uemail,bmi;
    Button deleteAccount;
    TextView numberofPres, numberofAps, numberofFinpres;


    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURe_PHOTO = 2;


    Bitmap thumbnail;
    Signup_pojo myuser;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        deleteAccount = (Button) view.findViewById(R.id.deleteprofile);
        numberofAps = view.findViewById(R.id.numberofaps);
        numberofFinpres = view.findViewById(R.id.numberoffinpres);
        numberofPres = view.findViewById(R.id.numberofpres);
        uemail = view.findViewById(R.id.proemail);

        dob = view.findViewById(R.id.prodob);

        uname = view.findViewById(R.id.name);
        uimage = view.findViewById(R.id.propic);


        Signup_pojo pojo = new Signup_pojo();
        Signup_crud crud = new Signup_crud(getContext());
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails", Context.MODE_PRIVATE);

        final long id = preferences.getLong("id", 1092L);


        pojo.setId(id);

        byte[] propic = crud.getImage(pojo);
        if (propic != null) {


            Bitmap propic2 = BitmapFactory.decodeByteArray(propic, 0, propic.length);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),propic2);
            roundedBitmapDrawable.setCircular(true);

            uimage.setImageDrawable(roundedBitmapDrawable);

        }
        int presscount = crud.getPressCount(pojo);
        int finpresscount = crud.getFinPressCount(pojo);
        int apcount = crud.getApCount(pojo);
        numberofPres.setText(presscount+"");
        numberofFinpres.setText(finpresscount+"");
        numberofAps.setText(apcount+"");

        uname.setText(preferences.getString("name", "NO"));
        dob.setText(preferences.getString("dob", "NO"));
        uemail.setText(preferences.getString("email", "NO"));
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Application");
                alert.setMessage("Do you want to delete your account?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Signup_pojo deletepojo = new Signup_pojo();
                        deletepojo.setId(id);
                        Signup_crud crud = new Signup_crud(getContext());

                        crud.delete_user(deletepojo);
                        Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_LONG).show();
                        SessionManager manager = new SessionManager(getContext());
                        manager.logoutUser();
                        getActivity().finish();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }

        });



        uimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(getActivity(), view);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.choosepicfrmgallery:
                                Intent photopicker = new Intent(Intent.ACTION_PICK);
                                photopicker.setType("image/*");
                                startActivityForResult(photopicker,SELECT_PHOTO);
                                break;

                            case R.id.capturepropic:
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,CAPTURe_PHOTO);

                                break;
                            case R.id.removepropic:
                                uimage.setImageResource(R.drawable.ic_person_white_24dp);
                                Signup_crud ucrud = new Signup_crud(getContext());

                                myuser = new Signup_pojo();
                                SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
                                final long id = preferences.getLong("id",1092L);
                                String fname = preferences.getString("fname","NO");
                                String lname = preferences.getString("lname","NO");
                                String useremail = preferences.getString("email","NO");
                                String userpassword = preferences.getString("password","NO");
                                String dob = preferences.getString("dob","NO");


                                myuser.setId(id);
                                myuser.setFirstname(fname);
                                myuser.setLastname(lname);
                                myuser.setEmail(useremail);
                                myuser.setPassword(userpassword);
                                myuser.setDob(dob);
                                myuser.setImage(null);

                                ucrud.update(myuser);
                                break;


                        }
                        return true;
                    }
                });

                menu.inflate (R.menu.propic );
                menu.show();

            }
        });



        return view;
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==SELECT_PHOTO){
            if(resultCode== RESULT_OK){
                try {
                    final Uri uri = data.getData();
                    final InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                    final Bitmap theimage = BitmapFactory.decodeStream(inputStream);
                    RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),theimage);
                    roundedBitmapDrawable.setCircular(true);

                    uimage.setImageDrawable(roundedBitmapDrawable);

                    byte[] dbimg = getBitmapAsByteArray(theimage);
                    int size = dbimg.length;
                    if((size/1024)<1050){
                        addtodb(dbimg);

                    }
                    else{
                        dbimg = null;
                        addtodb(dbimg);

                        uimage.setImageResource(R.drawable.ic_person_white_24dp);

                        Toast.makeText(getContext(),"The selected image is too large",Toast.LENGTH_LONG).show();

                    }






                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==CAPTURe_PHOTO){
            if(resultCode==RESULT_OK){
                onCaptureImageResult(data);

            }
        }

    }
    public void onCaptureImageResult(Intent data){
        thumbnail = (Bitmap)data.getExtras().get("data");
        uimage.setMaxWidth(120);
        uimage.setMaxHeight(120);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),thumbnail);
        roundedBitmapDrawable.setCircular(true);

        uimage.setImageDrawable(roundedBitmapDrawable);
        byte[] dbimg = getBitmapAsByteArray(thumbnail);
        addtodb(dbimg);
    }

    public void addtodb(byte[] image){
        Signup_crud ucrud = new Signup_crud(getContext());

        myuser = new Signup_pojo();
        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("logindetails",Context.MODE_PRIVATE);
        final long id = preferences.getLong("id",1092L);
        String fname = preferences.getString("fname","NO");
        String lname = preferences.getString("lname","NO");
        String useremail = preferences.getString("email","NO");
        String userpassword = preferences.getString("password","NO");
        String dob = preferences.getString("dob","NO");


        myuser.setId(id);
        myuser.setFirstname(fname);
        myuser.setLastname(lname);
        myuser.setEmail(useremail);
        myuser.setPassword(userpassword);
        myuser.setDob(dob);
        myuser.setImage(image);

        ucrud.update(myuser);

    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
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
