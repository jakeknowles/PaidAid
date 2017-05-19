package uw.tacoma.edu.paidaid.view;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.authenticate.LoginActivity;
import uw.tacoma.edu.paidaid.model.Request;
import uw.tacoma.edu.paidaid.pager.AddRequestButtonFragment;
import uw.tacoma.edu.paidaid.pager.HomeButtonFragment;
import uw.tacoma.edu.paidaid.pager.MessagesButtonFragment;
import uw.tacoma.edu.paidaid.pager.RequestsButtonFragment;


/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
 *  Home Screen Activity - Consists of Bottom Navigation Bar Buttons,
 *  Account Button, and the Request Feed. */
public class HomeActivity extends AppCompatActivity implements RequestFragment.OnListFragmentInteractionListener {

        /** Navigation bar */
        private BottomNavigationView mBottomNavigationMenuBar;

        /** View pager variable for each fragment screen */
        private ViewPager mScreen;

        /** Array to hold fragments */
        private ArrayList<Fragment> mMenuBarArray = new ArrayList<>();

        /**
         * Shared preferences used to keep track of who's logged in.
         */
        private SharedPreferences mSharedPreferences;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            // instantiate shared preferences
            mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                    , Context.MODE_PRIVATE);


            /** Displays paid aid logo on top action bar */
            getSupportActionBar().setDisplayShowHomeEnabled(true); //sets icon on top
            getSupportActionBar().setLogo(R.drawable.topbarpaidaid);
            getSupportActionBar().setDisplayUseLogoEnabled(true);

            /** Finds and assigns screen and navigation bar layout */
            this.mScreen = (ViewPager) findViewById(R.id.pager);
            this.mBottomNavigationMenuBar = (BottomNavigationView) findViewById(R.id.layout_navigation);


            mMenuBarArray.add(new HomeButtonFragment()); /** Home Button */
            mMenuBarArray.add(new AddRequestButtonFragment()); /** Add Button */
            mMenuBarArray.add(new MessagesButtonFragment()); /** Messages Button */
            mMenuBarArray.add(new RequestsButtonFragment()); /** Requests Button */


            /** Creates an adapter that handles fragments so the user can return back. */
//            mScreen.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//
//                /** Method needed for adapter */
//                @Override
//                public Fragment getItem(int position) {
//                    return mMenuBarArray.get(position);
//                }
//
//                /** Method needed for adapter */
//                @Override
//                public int getCount() {
//                    return mMenuBarArray.size();
//                }
//            });

            /** Listener for handling events on bottom menu navigation buttons. */
            mBottomNavigationMenuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.home_button:
                            mScreen.setCurrentItem(0); // Set to Index 1 ( Home )
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.activity_main, new HomeButtonFragment())
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case R.id.add_button:
                            mScreen.setCurrentItem(1); // Set to Index 2 ( Add )
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.activity_main, new AddRequestButtonFragment())
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case R.id.messages_button:
                            mScreen.setCurrentItem(2); // Set to Index 3 ( Messages )
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.activity_main, new MessagesButtonFragment())
                                    .addToBackStack(null)
                                    .commit();
                            break;
                        case R.id.requests_button:
                            mScreen.setCurrentItem(3); // Set to Index 4 ( Requests )
                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.activity_main, new RequestsButtonFragment())
                                    .addToBackStack(null)
                                    .commit();
                            break;
                    }
                    return true;
                }
            });

            /** New Request Fragment */
            if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {
                RequestFragment requestFragment = new RequestFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_main, requestFragment)
                        .commit();
            }
//
//            /** New AddRequestButtonFragment */
//            if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.addRequestFragment) == null) {
//                AddRequestButtonFragment addRequestButtonFragment = new AddRequestButtonFragment();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.activity_main, addRequestButtonFragment)
//                        .commit();
//            }

        }


    /** Creates account settings user button on top right of home screen */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.account_settings, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.user_account:
                // launch login or setting activity
                userSettingsLogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    /**
     * Private helper method.
     * Launches either a login activity if user is not currently logged in
     * or the user settings activity if they are logged in.
     */
    private void userSettingsLogin() {

        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);

        } else {
            Intent i = new Intent(this, AccountSettingsActivity.class);
            startActivity(i);
        }
    }


    /** Need for future use */
    @Override
    public void onListFragmentInteraction(Request request) {

//        CourseDetailFragment courseDetailFragment = new CourseDetailFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(CourseDetailFragment.COURSE_ITEM_SELECTED, item);
//        courseDetailFragment.setArguments(args);
//
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, courseDetailFragment)
//                .addToBackStack(null)
//                .commit();
    }
}




