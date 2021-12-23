package com.salim.medhelp;

import android.app.DatePickerDialog;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Signup_pojo;

public class SignupActivity extends AppCompatActivity {
    EditText fname,lname,email,age,password;
    String firstname,lastname,emailadd,userage,userpassword;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        DatePickerDialog.OnDateSetListener dlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1=i1+1;
                age.setText(i2 + "/" + i1 + "/" + i);

            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,0, dlistener, 2018, 11, 29);

        fname = (EditText)findViewById(R.id.firstnamesignup);
        lname = (EditText)findViewById(R.id.lastnamesignup);
        email = (EditText)findViewById(R.id.emailsignup);
        age = (EditText)findViewById(R.id.dobsignup);
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        password = (EditText)findViewById(R.id.passwordsignup);
        signup = (Button)findViewById(R.id.signupbtn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstname = fname.getText().toString();
                lastname = lname.getText().toString();
                emailadd = email.getText().toString();
                userage = age.getText().toString();
                userpassword = password.getText().toString();
                if (firstname.isEmpty()) {
                    fname.setError("Please Enter first name");
                } else if (lastname.isEmpty()) {
                    lname.setError("Please Enter last name");
                } else if (emailadd.isEmpty() || emailadd.length() > 50 || !Patterns.EMAIL_ADDRESS.matcher(emailadd.toString()).matches()) {
                    email.setError("Please Enter a valid email address");
                } else if (userage.isEmpty()) {
                    age.setError("Please Enter your State");
                } else if (userpassword.isEmpty()) {
                    password.setError("Please enter a valid password");
                    password.requestFocus();
                } else {
                    Signup_pojo sp = new Signup_pojo();
                    sp.setFirstname(fname.getText().toString());
                    sp.setLastname((lname.getText().toString()));
                    sp.setEmail(email.getText().toString());
                    sp.setDob(age.getText().toString());
                    sp.setPassword(password.getText().toString());
                    Signup_pojo result;
                    Signup_crud scrud = new Signup_crud(getApplicationContext());
                    result = scrud.add_new_user(sp);
                    if(result.getId()!=-1){
                        Log.i("id is",result.getId()+"");
                        Toast.makeText(getApplicationContext(),"Sign up Successful "+result.getId(), Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Sign up Failed ",Snackbar.LENGTH_LONG).show();
                        return;
                    }


                }
            }
        });
    }

}
