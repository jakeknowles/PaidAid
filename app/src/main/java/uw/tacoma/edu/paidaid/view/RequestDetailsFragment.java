package uw.tacoma.edu.paidaid.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/26/17
 *

/** When Request is clicked on home screen, this fragment shows all the details regarding the request */
public class RequestDetailsFragment extends Fragment {

    /** Listener */
    private OnFragmentInteractionListener mListener;

    /** Request Item Selected Constant */
    public final static String REQUEST_ITEM_SELECTED = "request_selected";

    /** Request Selected Constant */
    public final static String REQUEST_SELECTED = "request_selected";

    /** Username text view for populating with the username of the request poster */
    private TextView mUsernameTextView;

    /** Tip text view for populating with the tip from the request */
    private TextView mTipTextView;

    /** Distance text view for populating with the distance of how far away the request is */
    private TextView mDistanceAwayTextView;

    /** Store name text view for populating with the store name of where the request items should be bought */
    private TextView mStoreNameTextView;

    /** Items & Comments text view for populating with the items and comments from the posted request */
    private TextView mItemsAndCommentsTextView;

    /** Rating Bar for populating with the star rating of the user */
    private RatingBar mStarRating;

    /** Request */
    private Request mRequest;

    /** Constructor */
    public RequestDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * onStart updates the request view
     */
    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {

            // Set article based on argument passed in
            mRequest = (Request) args.getSerializable(REQUEST_SELECTED);
            updateView(mRequest);
        }
    }

    /**
     * updateView uses getter methods to update request view for the user to see the contents of the request
     * @param request request is the request needed to be picked up
     */
    public void updateView(Request request) {
        if (request != null) {
            mUsernameTextView.setText(request.getmUsername());
            mTipTextView.setText(Request.MONEY_SIGN + String.format("%.2f", request.getmTipAmount()));
            mDistanceAwayTextView.setText(Double.toString(request.getmDistanceAway()) + Request.MILES_UNITS);
            mStoreNameTextView.setText(request.getmStoreName());
            mItemsAndCommentsTextView.setText(request.getmItemsAndComments());
            mStarRating.setRating((float) request.getmStarRating());
        }
    }

    /**
     * onCreateView assigns text views with XML layouts and assigns a listener to the "pick up" button
     * @param inflater inflater
     * @param container container
     * @param savedInstanceState savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_request, container, false);

        mUsernameTextView = (TextView) view.findViewById(R.id.username_account_fill);
        mUsernameTextView.setFocusable(false); // Sets text view to not editable
        mUsernameTextView.setClickable(true);  // Sets text view to clickable / scrollable

        mTipTextView = (TextView) view.findViewById(R.id.tip_amount_text);
        mDistanceAwayTextView = (TextView) view.findViewById(R.id.location);
        mStoreNameTextView = (TextView) view.findViewById(R.id.store_name);

        mItemsAndCommentsTextView = (TextView) view.findViewById(R.id.items_comments);
        mItemsAndCommentsTextView.setFocusable(false); // Sets text view to not editable
        mItemsAndCommentsTextView.setClickable(true);  // Sets text view to clickable / scrollable

        mStarRating = (RatingBar) view.findViewById(R.id.ratingbar);

        Button button = (Button) view.findViewById(R.id.pickup_request_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.pickup_request_button:
                        launchEmail();
                        break;
                    default:
                        break;
                }
            }
        });

        mUsernameTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.username_account_fill:
                        launchReview();
                        break;
                    default:
                        break;
                }
            }
        });

        return view;
    }


    /** Launches Date Picker Calendar fragment */
    private void launchEmail() {
        Intent i = new Intent(this.getActivity(), EmailActivity.class);
        startActivity(i);

    }

    /** Launches Review fragment */
    private void launchReview() {

        Fragment frag = new ReviewFragment();
        Bundle args = new Bundle();

        args.putString("REQUEST_USERNAME", mRequest.getmUsername());
        args.putInt("REQUEST_USERID", mRequest.getmUserID());
        frag.setArguments(args);

        getFragmentManager().beginTransaction()
                .add(R.id.activity_main, frag)
                .addToBackStack(null).commit();

    }

    /**
     * onDetach assigns listener to null
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
