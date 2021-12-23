package com.salim.medhelp;

import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salim.medhelp.crud.Signup_crud;
import com.salim.medhelp.pojo.Signup_pojo;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;

    Button login;
    TextView join;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        login = findViewById(R.id.loginbtn);
        join = findViewById(R.id.joinnow);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailinput = email.getText().toString();
                String passwwordinput = password.getText().toString();
                if (emailinput.isEmpty()) {
                    email.setError("Please enter your email address");
                    email.requestFocus();
                } else if (passwwordinput.isEmpty()) {
                    password.setError("Please enter your password ");
                    password.requestFocus();

                }
                else{
                    Signup_pojo pojo = new Signup_pojo();
                    pojo.setEmail(emailinput);
                    pojo.setPassword(passwwordinput);
                    Signup_crud crud = new Signup_crud(getApplicationContext());
                    Signup_pojo result = crud.getUser(pojo);
                    Log.i("result id ",+result.getId()+" ");

                    if(result.getMessage()=="Success"){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        SharedPreferences preferences =  getSharedPreferences("logindetails",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();


                        String fname = result.getFirstname();
                        String lname = result.getLastname();
                        String password2 = result.getPassword();
                        String name = result.getFirstname()+" "+result.getLastname();
                        String email2 = result.getEmail();
                        String dob = result.getDob();

                        Long id = result.getId();


                        editor.putString("fname",fname);
                        editor.putString("lname",lname);

                        editor.putString("password",password2);
                        editor.putString("name",name);
                        editor.putString("dob",dob);
                        editor.putString("email",email2);
                        editor.putLong("id",id);

                        editor.apply();

                        editor.commit();

                        SessionManager management = new SessionManager(getApplicationContext());
                        management.createLoginSession();

                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Invalid login detail",Toast.LENGTH_LONG).show();
                    }



                }

            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
