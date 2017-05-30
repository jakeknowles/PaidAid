package uw.tacoma.edu.paidaid.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.authenticate.LoginActivity;
import uw.tacoma.edu.paidaid.coreFeatures.RequestFragment;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
/** Review Fragment is from clicking on the messages button, then clicking on a username to review,
 * and then this fragment gives users an option to give a star rating (1 - 5) */
public class ReviewFragment extends Fragment {


    /**
     * The url to submit a review
     */
    private static final String SUBMIT_REVIEW_URL =
            "http://paidaid.x10host.com/submitReview.php";

    /**
     * Username text view for populating with the username of the request poster
     */
    private EditText mUsernameTextView;

    /**
     * Rating Bar
     */
    private RatingBar mStarRating;

    /**
     * Submit button
     */
    private Button mSubmitButton;

    /**
     * The useres id
     */
    private int mUserId;

    /**
     * The activity that launched this fragment
     */
    private Activity mHomeActivity;

    /**
     * Constructor
     */
    public ReviewFragment() {
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
     * onCreateView
     * @param inflater instantiates the layout XML file into its corresponding View objects
     * @param container is a container for ViewGroup views
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        String username = "";
        Bundle args = getArguments();
        if (args != null) {
            username = args.getString("REQUEST_USERNAME");
            mUserId = args.getInt("REQUEST_USERID");
        }

        mSubmitButton = (Button) view.findViewById(R.id.submit_rating);
        mStarRating = (RatingBar) view.findViewById(R.id.ratingBar);


        mUsernameTextView = (EditText) view.findViewById(R.id.username_text);
        mUsernameTextView.setText(username);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkIfReviewingYourself()) {
                    Toast.makeText(getActivity(), "Cannot submit review for yourself, nice try!", Toast.LENGTH_SHORT).show();
                    return;
                }


                final JSONObject review_JSON = new JSONObject();

                try {
                    review_JSON.put("userid", mUserId);
                    review_JSON.put("rating", mStarRating.getRating());
                } catch (JSONException e) {
                    Log.e("ERROR ADD REQUEST JSON", e.toString());
                }

                // submit the review
                new SubmitReviewTask().execute(review_JSON.toString());

            }
        });

        return view;
    }


    /**
     * Make sure they are not trying to subit a review for themselves
     * @return true if the user is the same for request as logged in, false otherwise
     */
    private boolean checkIfReviewingYourself() {

        SharedPreferences sp = getActivity()
                .getSharedPreferences(getString(R.string.LOGIN_PREFS), Context.MODE_PRIVATE);

        if (sp.getString(getString(R.string.USERNAME), "").equals(mUsernameTextView.getText().toString()))
            return true;

        return false;
    }



    /**
     * onAttach for LoginActivity
     * @param context is the data for the activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof HomeActivity) {
            mHomeActivity = (HomeActivity) context;
        }
    }

    /**
     * OnFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    /**
     * @version 5/29/2017
     *          <p>
     *          /**
     *          A task to download the requests from the database.
     * @Author: Jake Knowles
     * @Author Dmitriy Onishchenko
     */
    private class SubmitReviewTask extends AsyncTask<String, String, String> {


        /**
         * doInbackground JSON
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(String... params) {

            BufferedReader inBuffer = null;
            String jsonObject = params[0];
            String result = "";
            HttpURLConnection urlConnection = null;
            try {

                URL urlObject = new URL(SUBMIT_REVIEW_URL);
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                DataOutputStream localDataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
                localDataOutputStream.writeBytes(jsonObject);
                localDataOutputStream.flush();
                localDataOutputStream.close();

                // get the response
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    result += s;
                }


            } catch (Exception e) {
                result = "Unable to Submit Review: ";
                result += e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();

                if (inBuffer != null) {
                    try {
                        inBuffer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        /**
         * It checks to see if there was a problem with the URL(Network), if not tries to parse the
         * json object, if successful sets the adapter to the RecyclerViewAdapter, if not successful
         * shows an error message toast.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {

            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");

                if (status.equals("success")) {

                    Log.e("THIS IS ACTIVITY", getActivity().toString());

                    Fragment fragHome = getActivity().getSupportFragmentManager()
                            .findFragmentByTag(getActivity().getString(R.string.home_tag));

                    if (fragHome == null) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.activity_main, new RequestFragment())
                                .commit();
                    }
                    else {
                        // go back to details fragment
                        getActivity().getSupportFragmentManager().popBackStack();
                    }

                    Toast.makeText(getActivity(), "Your review has been submitted",
                            Toast.LENGTH_SHORT).show();

                } else {

                    String err = (String) jsonObject.get("error");
                    Toast.makeText(getActivity(), "Error! Review not submitted " + err,
                            Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                Toast.makeText(getActivity(), "Something wrong submit review: " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}