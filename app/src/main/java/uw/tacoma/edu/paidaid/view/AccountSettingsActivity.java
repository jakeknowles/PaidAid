package uw.tacoma.edu.paidaid.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
/** Account Settings Activity is the activity launched when the user icon
 * in the top right corner is clicked */
public class AccountSettingsActivity extends AppCompatActivity {


    /**
     * Shared Preferences for user that is logged in.
     */
    private SharedPreferences mSharedPrefernces;

    /**
     * Logout button
     */
    private Button mLogoutButton;

    private EditText mUsernameView;

    /**
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // get shared preferences file
        mSharedPrefernces = getSharedPreferences(getString(R.string.LOGIN_PREFS),
                Context.MODE_PRIVATE);

        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mUsernameView = (EditText) findViewById(R.id.username_text);



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
                mSharedPrefernces.edit().putBoolean(getString(R.string.LOGGEDIN), false).commit();

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

        String username = mSharedPrefernces.getString(getString(R.string.USERNAME), "null");
        mUsernameView.setText(username);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

}
