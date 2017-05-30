package uw.tacoma.edu.paidaid.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.authenticate.LoginActivity;
import uw.tacoma.edu.paidaid.coreFeatures.AddRequestFragment;
import uw.tacoma.edu.paidaid.coreFeatures.MyMessagesFragment;
import uw.tacoma.edu.paidaid.coreFeatures.MyRequestsFragment;
import uw.tacoma.edu.paidaid.coreFeatures.RequestFragment;
import uw.tacoma.edu.paidaid.model.Request;


/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
 *  Home Screen Activity - Consists of Bottom Navigation Bar Buttons,
 *  Account Button, and the Request Feed. */
public class HomeActivity extends AppCompatActivity implements
        RequestFragment.OnListFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
         {

    /**
     * Checking for permissions
     */
    private static final int MY_PERMISSIONS_LOCATIONS = 0;

    /** Navigation bar */
    private BottomNavigationView mBottomNavigationMenuBar;

    /**
     * Flag to keep track of when a user clicks on features and is not logged in
     */
    private boolean mClickedFlag = false;


    /**
     * The Google api client used for locations
     */
    private GoogleApiClient mGoogleApiClient;


    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    /*

     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;



    /**
     * Shared preferences used to keep track of who's logged in.
     */
    private static SharedPreferences mSharedPreferences;

    /**
     * Loads layout and Navigation bar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set overlay to make the action bar hide on scroll
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instantiate shared preferences
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);

        // Set action bar toolbar to custom toolbar
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        //getSupportActionBar().setHideOnContentScrollEnabled(true);


        /** Finds and assigns screen and navigation bar layout */
        this.mBottomNavigationMenuBar = (BottomNavigationView) findViewById(R.id.layout_navigation);

        // Hide bottom navigation bar when keyboard is visible
        keyboardListener();


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }



        createLocationRequest();


//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        // check for permissions to access location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_LOCATIONS);
        } else {

            // On click listener to bottom nav bar
            addListenerToNavBar();

            // Add the request fragment to populate the grid of requests
            if (savedInstanceState == null || getSupportFragmentManager().findFragmentById(R.id.list) == null) {

                RequestFragment requestFragment = new RequestFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.activity_main, requestFragment, getString(R.string.home_tag))
                        .commit();

            }
        }

    }



//    /**
//     * Requests location updates from the FusedLocationApi.
//     */
//    protected void startLocationUpdates() {
//        // The final argument to {@code requestLocationUpdates()} is a LocationListener
//        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(
//                    mGoogleApiClient, mLocationRequest, this);
//        }
//    }

//    /**
//     * Removes location updates from the FusedLocationApi.
//     */
//    protected void stopLocationUpdates() {
//        // It is a good practice to remove location requests when the activity is in a paused or
//        // stopped state. Doing so helps battery performance and is especially
//        // recommended in applications that request frequent location updates.
//
//        // The final argument to {@code requestLocationUpdates()} is a LocationListener
//        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStart() {

        Log.e("ON START ", "METHOD CALLED");

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
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

    /**
     * If account settings icon is selected, launch userSettingsLogin()
     * @param item item
     * @return boolean
     */
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
     * Get the current location
     * @return current Location
     */
    public Location getCurrentLocation() {
        return mCurrentLocation;
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

    /**
     * Method that listens for keyboard and hides the navigation bar if the keyboard is up
     *
     */
    private void keyboardListener() {

        final View activityRootView = findViewById(R.id.activity_main);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);

                int screenHeight = activityRootView.getRootView().getHeight();
                int heightDiff = screenHeight - (r.bottom - r.top);
                boolean visible = heightDiff > screenHeight / 3;
                if (visible) {
                    mBottomNavigationMenuBar.setVisibility(View.INVISIBLE);
                } else {
                    mBottomNavigationMenuBar.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    /**
     * Helper method that adds on click listener to bottom navigation bar
     */
    private void addListenerToNavBar() {

        /** Listener for handling events on bottom menu navigation buttons. */
        mBottomNavigationMenuBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home_button:
                        Fragment fragHome = getSupportFragmentManager()
                                .findFragmentByTag(getString(R.string.home_tag));
                        changeScreen(fragHome, new RequestFragment());
                        break;

                    case R.id.add_button:
                        if (!isUserLoggedIn()) break;

                        Fragment fragAdd = getSupportFragmentManager()
                                .findFragmentByTag(getString(R.string.add_tag));
                        changeScreen(fragAdd, new AddRequestFragment());
                        break;

                    case R.id.messages_button:
                        if (!isUserLoggedIn()) break;

                        Fragment fragMes = getSupportFragmentManager()
                                .findFragmentByTag(getString(R.string.messages_tag));
                        changeScreen(fragMes, new MyMessagesFragment());
                        break;

                    case R.id.requests_button:
                        if (!isUserLoggedIn()) break;

                        Fragment fragReq = getSupportFragmentManager()
                                .findFragmentByTag(getString(R.string.myRequests_tag));
                        changeScreen(fragReq, new MyRequestsFragment());
                        break;

                }
                return true;
            }
        });
    }



    /**
     * Helper method launches the correct fragment
     * @param theFragment
     */
    private void changeScreen(Fragment theFragment, Fragment newFragmentToLaunch) {

        if (theFragment == null)
            addFragmentNoBackStack(newFragmentToLaunch, getString(R.string.messages_tag));
        else
            replaceFragmentNoBackStack(theFragment);
    }


    /**
     * Checks if user is logged in or not
     * if they are not display a toast message and take them to login activity
     * @return true if logged in false otherwise
     */
    private boolean isUserLoggedIn() {

        boolean loggedIn = mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false);

        // if flag is set return false prevents repeated attempts
        if(mClickedFlag) return false;

        if (!loggedIn && !mClickedFlag) {

            Toast mes = Toast.makeText(this.getApplicationContext(),
                    "You must be logged in to access PaidAid features",
                    Toast.LENGTH_LONG);
            mes.show();

            // set clicked flag to true when not logged in
            mClickedFlag = true;


            // launch login screen after delay
            final Activity activity = this;

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mClickedFlag = false;
                    Intent i = new Intent(activity, LoginActivity.class);
                    startActivity(i);

                }
            }, 2000);

            return false;
        }
        return true;
    }


    /**
     * onListFragmentInteraction makes sure user is logged in to access requests
     * @param request request
     */
    @Override
    public void onListFragmentInteraction(Request request) {

        if (isUserLoggedIn()) {

            RequestDetailsFragment requestDetailsFragment = new RequestDetailsFragment();
            Bundle args = new Bundle();
            args.putSerializable(RequestDetailsFragment.REQUEST_ITEM_SELECTED, request);
            requestDetailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main, requestDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        };
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permissions granted
                    setUpWhenPermissionsGranted();

                } else {

                    findViewById(R.id.user_account).setEnabled(false);
                    Toast.makeText(this, "You need Location Services enabled to use PaidAid",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    /**
     * Helper method that is called when user accepts location services.
     */
    private void setUpWhenPermissionsGranted() {

        // On click listener to bottom nav bar
        addListenerToNavBar();

        // Add the request fragment to populate the grid of requests
        if (getSupportFragmentManager().findFragmentByTag(getString(R.string.home_tag)) == null) {

            RequestFragment requestFragment = new RequestFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, requestFragment, getString(R.string.home_tag))
                    .commit();

            Toast.makeText(this, "Welcome to PaidAid!",
                    Toast.LENGTH_LONG).show();

        }

    }

             private static int UPDATE_INTERVAL = 1; // 10 sec
             private static int FATEST_INTERVAL = 5; // 5 sec
             private static int DISPLACEMENT = 10; // 10 meters

             private void createLocationRequest() {
                 mLocationRequest = new LocationRequest();
                 mLocationRequest.setInterval(UPDATE_INTERVAL);
                 mLocationRequest.setFastestInterval(FATEST_INTERVAL);
                 mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                 mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
             }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e("HOME ACTIVITY", "CONNECTED");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mCurrentLocation != null) {
                    Log.i("THIS IS THE LOCATION", mCurrentLocation.toString());
                } else {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
//                    Toast.makeText(this, "Unable to get locations please restart App", Toast.LENGTH_LONG).show();
                }
            }
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.e("HOME ACTIVITY", "Connection suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.e("HOME ACTIVITY", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());


    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Log.i("LOCATION CHANGED", mCurrentLocation.toString());


    }

}




