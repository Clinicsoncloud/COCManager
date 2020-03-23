package com.coc.cocmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coc.cocmanager.Fragments.HomeFragment;
import com.coc.cocmanager.Fragments.InstallationHomeFragment;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.SharedPrefClass;

/**
 * created by ketan 23-03-2020
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    //region variables
    private Toolbar mToolbar;
    SharedPrefClass prefClass;
    private TextView tv_name;
    private ImageView iv_back;
    private ImageView ivToolbarIcon;
    private FragmentDrawer drawerFragment;
    RelativeLayout navHeaderContainer;

    private DrawerLayout drawerLayout;
    private static String TAG = MainActivity.class.getSimpleName();

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        setupEvents();
        initializeData();
    }

    private void initializeData() {

    }

    private void setupEvents() {

    }

    private void setupUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        navHeaderContainer = (RelativeLayout) findViewById(R.id.nav_header_container);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                fragment = new InstallationHomeFragment();
                // fragment = new BookingFragment();
                break;
            case 1:
                fragment = new HomeFragment();
                // fragment = new BookingFragment();
                break;
            case 2:
                fragment = new HomeFragment();
                // fragment = new BookingFragment();
                break;

            case 3:
                fragment = new HomeFragment();
                // fragment = new BookingFragment();
                break;


            case 4:
                fragment = new HomeFragment();
                // fragment = new BookingFragment();
                startActivity(new Intent(getApplicationContext(), Loginctivity.class));
                finish();
                int count = getSupportFragmentManager().getBackStackEntryCount();

                for (int i = 0; i < count; ++i) {
                    getFragmentManager().popBackStackImmediate();
                }
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                break;


            default:
                break;
        }

        // set the toolbar title
        getSupportActionBar().setTitle(title);

        this.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
                R.anim.slide_left_in, R.anim.slide_right_out).replace(R.id.container_body, fragment).addToBackStack(null).commit();
    }
}
