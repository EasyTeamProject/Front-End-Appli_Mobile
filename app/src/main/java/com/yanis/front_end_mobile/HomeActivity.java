package com.yanis.front_end_mobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import com.onesignal.OneSignal;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private AddEventFragment addEventFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mMainFrame=(FrameLayout)findViewById(R.id.main_frame);
        mMainNav=(BottomNavigationView)findViewById(R.id.main_nav);

        homeFragment= new HomeFragment();
        addEventFragment = new AddEventFragment();
        notificationFragment = new NotificationFragment();
        accountFragment = new AccountFragment();

        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.nav_home :
                                setFragment(homeFragment);
                                return true;
                            case R.id.nav_add_event:
                                setFragment(addEventFragment);
                                return true;
                            case R.id.nav_notif :
                                setFragment(notificationFragment);
                                return true;
                            case R.id.nav_profil :
                                setFragment(accountFragment);
                                return true;


                            default:return false;
                        }


                    }
                }
        );
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
