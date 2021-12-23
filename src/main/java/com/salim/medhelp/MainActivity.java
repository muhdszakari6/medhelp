package com.salim.medhelp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.card.MaterialCardView;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout ;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,AddpresForm.OnFragmentInteractionListener,
        ViewFinPress.OnFragmentInteractionListener,DocAppointment.OnFragmentInteractionListener,
        ViewPres.OnFragmentInteractionListener,TabletReminder.OnFragmentInteractionListener,
        MoreFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,CustomerFragment.OnFragmentInteractionListener,
        GotowebFragment.OnFragmentInteractionListener,FeedbackFragment.OnFragmentInteractionListener,
        UpdateaccountFragment.OnFragmentInteractionListener,ChangepasswordFragment.OnFragmentInteractionListener,
        PresDetails.OnFragmentInteractionListener,UpdatePres.OnFragmentInteractionListener,
        TabletReminderList.OnFragmentInteractionListener,TabletReminderForm.OnFragmentInteractionListener,
        BmiFragment.OnFragmentInteractionListener,BarcodescanFragment.OnFragmentInteractionListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private  androidx.appcompat.widget.Toolbar  toolbar;
    private BottomNavigationView bottomNavigationView;
    Fragment fragment;


    final HomeFragment homefragment = new HomeFragment();
    final ProfileFragment profileFragmentFragment = new ProfileFragment();
    final MoreFragment moreFragment = new MoreFragment();
    final CustomerFragment customerFragment = new CustomerFragment();
    final GotowebFragment gotowebFragment = new GotowebFragment();
    final FeedbackFragment feedbackFragment = new FeedbackFragment();


    private BottomNavigationView.OnNavigationItemSelectedListener onClickListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.homenavigation:{
                    FragmentManager fm = getSupportFragmentManager();
                    for(int i=0;i<fm.getBackStackEntryCount();++i){
                        fm.popBackStack();
                    }
                    fragment = homefragment;
                }
                    break;

                case R.id.morenav: {
                    FragmentManager fm = getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                    fragment = moreFragment;
                }
                    break;
                case R.id.remnav:{
                    FragmentManager fm = getSupportFragmentManager();
                    for(int i=0;i<fm.getBackStackEntryCount();++i){
                        fm.popBackStack();
                    }
                    fragment = new TabletReminderList();
                }

                    break;

                case R.id.profilenav: {
                    FragmentManager fm = getSupportFragmentManager();
                    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                        fm.popBackStack();
                    }
                    fragment = profileFragmentFragment;
                }
                    break;
            }
            return loadFragment(fragment);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (androidx.appcompat.widget.Toolbar ) findViewById(R.id.toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onClickListener);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        loadFragment(homefragment);
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);


        } else {


        }

        putActionBarToggle();

    }

    public void putActionBarToggle() {

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        if (drawerLayout == null) {
            drawerLayout.addDrawerListener(actionBarDrawerToggle);

        }
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawers();
        if (menuItem.getItemId() == R.id.logout) {
            SessionManager management = new SessionManager(this);
            management.logoutUser();
            finish();
        } else if (menuItem.getItemId() == R.id.drawer_web) {
            loadFragment(gotowebFragment);
        } else if (menuItem.getItemId() == R.id.launchscan) {
            IntentIntegrator scanner = new IntentIntegrator(this);
            scanner.initiateScan();

        } else if (menuItem.getItemId() == R.id.drawer_bmi) {
            loadFragment(new BmiFragment());
        } else if (menuItem.getItemId() == R.id.drawer_feeds) {
            loadFragment(feedbackFragment);
        } else if (menuItem.getItemId() == R.id.drawer_service) {
            loadFragment(customerFragment);
        }
        return true;
    }


    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commitAllowingStateLoss();
            return true;

        }
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode,intent);

        IntentResult scanresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanresult == null) {
            Toast.makeText(this, "No scanning data received", Toast.LENGTH_LONG).show();
        } else {

            String contents = scanresult.getContents();
            String format = scanresult.toString();
            BarcodescanFragment bfragment = new BarcodescanFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content", contents);
            bundle.putString("format", format);
            bfragment.setArguments(bundle);
            loadFragment(bfragment);

        }
    }*/
}



