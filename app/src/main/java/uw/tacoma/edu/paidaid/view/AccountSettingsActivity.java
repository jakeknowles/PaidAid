package uw.tacoma.edu.paidaid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
    }

}
