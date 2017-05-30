package uw.tacoma.edu.paidaid.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
/** Account Settings Activity is the activity launched when the user icon
    in the top right corner is clicked */
public class AccountSettingsActivity extends AppCompatActivity {

    /** Shared Preferences for user that is logged in. */
    private SharedPreferences mSharedPreferences;

    /** Logout button */
    private Button mLogoutButton;

    /** Username text box */
    private EditText mUsernameView;

    /** Email text box */
    private EditText mEmailView;

    /** Rating bar */
    private RatingBar mRatingBar;

    /**
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // get shared preferences file
        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);

        // set action bar toolbar to custom toolbar
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_no_logo);

        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mUsernameView = (EditText) findViewById(R.id.username_text);
        mEmailView = (EditText) findViewById(R.id.email_text);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);


        setUpSettingsFields();
        setUpOnClickListeners();
    }

    /**
     * Private helper method that sets up the onClick listeners for the buttons.
     */
    private void setUpOnClickListeners() {

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();

                Toast.makeText(getApplicationContext(), "Come back soon!"
                        , Toast.LENGTH_SHORT)
                        .show();
                finish();
            }
        });
    }

    /**
     * private helper method that sets up all the field for the settings
     */
    private void setUpSettingsFields() {

        String username = mSharedPreferences.getString(getString(R.string.USERNAME), "null");
        String email = mSharedPreferences.getString(getString(R.string.EMAIL), "null");
        float rating = mSharedPreferences.getFloat(getString(R.string.USER_RATING), 5f);
        mUsernameView.setText(username);
        mEmailView.setText(email);
        mRatingBar.setRating(rating);

    }

    /**
     * onDestroy finishes the activity
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

}
