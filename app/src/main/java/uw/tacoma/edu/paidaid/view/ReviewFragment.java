package uw.tacoma.edu.paidaid.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.model.Request;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
/** Review Fragment is from clicking on the messages button, then clicking on a username to review,
 * and then this fragment gives users an option to give a star rating (1 - 5) */
public class ReviewFragment extends Fragment {

    /** Request Selected Constant */
    public final static String REQUEST_SELECTED = "request_selected";


    /** Username text view for populating with the username of the request poster */
    private TextView mUsernameTextView;

    /** Rating bar */
    private RatingBar mStarRating;

    /** Request */
    private Request mRequest;

    /** Constructor */
    public ReviewFragment() {}

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
        }
    }

    /**
     * onCreateView
     * @param inflater instantiates the layout XML file into its corresponding View objects
     * @param container is a container for ViewGroup views
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        mUsernameTextView = (TextView) view.findViewById(R.id.username_text);

        return view;
    }



    /** OnFragmentInteractionListener */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
