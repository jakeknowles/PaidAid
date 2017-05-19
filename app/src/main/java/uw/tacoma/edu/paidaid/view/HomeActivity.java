package uw.tacoma.edu.paidaid.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.authenticate.LoginActivity;
import uw.tacoma.edu.paidaid.model.Request;
import uw.tacoma.edu.paidaid.pager.AddRequestButtonFragment;
import uw.tacoma.edu.paidaid.pager.MessagesButtonFragment;
import uw.tacoma.edu.paidaid.pager.RequestFragment;
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

            this.mBottomNavigationMenuBar = (BottomNavigationView) findViewById(R.id.layout_navigation);



            /** Listener for handling events on bottom menu navigation buttons. */
            mBottomNavigationMenuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.home_button:
                            replaceFragmentNoBackStack(getSupportFragmentManager()
                                    .findFragmentByTag(getString(R.string.home_tag)));
                            break;
                        case R.id.add_button:
                            Fragment fragAdd = (Fragment) getSupportFragmentManager()
                                    .findFragmentByTag(getString(R.string.add_tag));

                            if (fragAdd == null)
                                addFragmentNoBackStack(new AddRequestButtonFragment(), getString(R.string.add_tag));
                            else
                                replaceFragmentNoBackStack(fragAdd);
                            break;
                        case R.id.messages_button:
                            Fragment fragMes = (Fragment) getSupportFragmentManager()
                                    .findFragmentByTag(getString(R.string.messages_tag));

                            if (fragMes == null)
                                addFragmentNoBackStack(new MessagesButtonFragment(), getString(R.string.messages_tag));
                            else
                                replaceFragmentNoBackStack(fragMes);
                            break;
                        case R.id.requests_button:
                            Fragment fragReq = (Fragment) getSupportFragmentManager()
                                    .findFragmentByTag(getString(R.string.myRequests_tag));

                            if (fragReq == null)
                                addFragmentNoBackStack(new RequestsButtonFragment(), getString(R.string.myRequests_tag));
                            else
                                replaceFragmentNoBackStack(fragReq);
                            break;
                    }
                    return true;
                }
            });


            // add the request fragment to populate the grid of requests
            if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {

                RequestFragment requestFragment = new RequestFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_main, requestFragment, getString(R.string.home_tag))
                        .commit();
            }
        }


    /**
     * Method that replaces a fragment without adding it to the backstack
     * @param theFragment the fragment to replace with
     */
    public void replaceFragmentNoBackStack(Fragment theFragment) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main, theFragment)
                    .commit();
        }

    /**
     * Method that adds a new fragment without adding it to the backstack
     * @param theFragment the new fragment to add
     * @param theFragmentTag the fragment tag name
     */
    public void addFragmentNoBackStack(Fragment theFragment, String theFragmentTag) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, theFragment, theFragmentTag)
                    .commit();
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




