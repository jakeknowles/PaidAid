package uw.tacoma.edu.paidaid.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
/** Review Fragment is from clicking on the messages button, then clicking on a username to review,
 * and then this fragment gives users an option to give a star rating (1 - 5) */
public class ReviewFragment extends Fragment {

    /** Username text view for populating with the username of the request poster */
    private EditText mUsernameTextView;

    /** Rating Bar */
    private RatingBar mStarRating;

    private Button mSubmitButton;

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
     * onCreateView
     * @param inflater instantiates the layout XML file into its corresponding View objects
     * @param container is a container for ViewGroup views
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        String value = getArguments().getString("REQUEST_USERNAME");

        mSubmitButton = (Button) view.findViewById(R.id.submit_rating);
        mStarRating = (RatingBar) view.findViewById(R.id.ratingBar);


        mUsernameTextView = (EditText) view.findViewById(R.id.username_text);
        mUsernameTextView.setText(value);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    /** OnFragmentInteractionListener */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
