package com.coc.cocmanager.Activity;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coc.cocmanager.Fragments.InventoryFragment;
import com.coc.cocmanager.Fragments.SummaryFragment;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.SharedPrefClass;
import com.coc.cocmanager.Fragments.HomeFragment;
import com.coc.cocmanager.Fragments.InstallationHomeFragment;

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

        if (savedInstanceState == null) {
            Fragment fragment= new SummaryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }

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
                fragment = new SummaryFragment();
                break;
            case 1:
                fragment = new InstallationHomeFragment();
                break;
            case 2:
                fragment = new InventoryFragment();
                break;

            case 3:
                fragment = new HomeFragment();
                break;


            case 4:
                fragment = new HomeFragment();
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
