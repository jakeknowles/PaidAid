package uw.tacoma.edu.paidaid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;


/**
 * @Author: Jake Knowles
 * @Author  Dmitriy Onishchenko
 * @version 5/29/2017
 */


public class AccountSettingsRatingActivity extends AppCompatActivity {

    /** Request Selected Constant */
    public final static String REQUEST_SELECTED = "request_selected";


    /** Username text view for populating with the username of the request poster */
    private TextView mUsernameTextView;

    /** Email text view */
    private TextView mEmailTextView;

    /** Rating bar */
    private RatingBar mStarRating;

    /** Request */
    private Request mRequest;

    /**
     * onStart updates the request view
     */
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        Bundle args = getIntent().getExtras();
//        if (args != null) {
//            mRequest = (Request) args.getSerializable(REQUEST_USER_EMAIL);
////            mRequest = (Request) args.getSerializable(REQUEST_USERNAME);
//            updateView(mRequest);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_rating);

        // set action bar toolbar to custom toolbar
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar_no_logo);


        mUsernameTextView = (TextView) findViewById(R.id.username_account_fill);
        mEmailTextView = (TextView) findViewById(R.id.email_text);
        mStarRating = (RatingBar) findViewById(R.id.ratingbar);


    }

    /**
     * updateView uses getter methods to update request view for the user to see the contents of the request
     * @param request request is the request needed to be picked up
     */
    public void updateView(Request request) {
        if (request != null) {
            mUsernameTextView.setText(request.getmUsername());
            mEmailTextView.setText(request.getmEmail());
            mStarRating.setRating((float) request.getmStarRating());

        }
    }


}
