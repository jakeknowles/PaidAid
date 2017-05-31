package uw.tacoma.edu.paidaid.coreFeatures;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uw.tacoma.edu.paidaid.R;
import uw.tacoma.edu.paidaid.data.UserDB;
import uw.tacoma.edu.paidaid.tasks.AddRequestsTask;
import uw.tacoma.edu.paidaid.view.DatePickerFragment;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
 * Add Button Fragment from clicking "Add" on home screen. */
public class AddRequestFragment extends DialogFragment implements View.OnClickListener {

    /**
     * The tip amount.
     */
    private EditText mTip;

    /**
     * The zipcode that user entered
     */
    private EditText mZipCode;

    /**
     * The store name(s) that user entered
     */
    private EditText mStoreName;

    /**
     * The items and additional comments
     */
    private EditText mItemsComments;

    /**
     * Add new request button
     */
    private Button mAddButton;


    /** Constructor */
    public AddRequestFragment() {}


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
        View view = inflater.inflate(R.layout.fragment_add_request, container, false);

        /** Attach on click listener */
        Button date = (Button) view.findViewById(R.id.date_picker_button);
        date.setOnClickListener(this);

        // retrieve the text views for the input from user
        mTip = (EditText) view.findViewById(R.id.tip_amount_text);
        mZipCode = (EditText) view.findViewById(R.id.zipcode);
        mStoreName = (EditText) view.findViewById(R.id.store_name);
        mItemsComments = (EditText) view.findViewById(R.id.items_comments);
        mAddButton = (Button) view.findViewById(R.id.post_request_button);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUserInput();

            }
        });

        return view;
    }


    /**
     * Method that validates the zip code and if invalid zipcode
     * asks the user to enter a valid zip code
     * @param zipCode the zip code to validate
     */
    private int validateZipCode(String zipCode) {

        if (zipCode.length() > 5 || zipCode.length() < 5) {
            Toast.makeText(getActivity().getApplicationContext(), "ZipCode must be 5 numbers long", Toast.LENGTH_LONG)
                    .show();
            return -1;
        }

        else return 0;
    }


    /**
     * Get the users input and insert into database
     */
    private void getUserInput() {

        String zipCode = (String) mZipCode.getText().toString();

        // Validate the zip code to make sure it is valid - otherwise return
        if (validateZipCode(zipCode) == -1)
            return;


        // Get the lat and long coordinates using google api call and post
        GeocodeAsyncTask task = new GeocodeAsyncTask();
        task.execute(zipCode);

    }


    /**
     * getUserId gets the users ID
     *
     * @return USERID
     */
     private int getUserId() {

//         SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS)
//                , Context.MODE_PRIVATE);
//         sharedPref.getInt(getString(R.string.USERID), -1);

         UserDB db = new UserDB(getActivity());

         return db.getmUserId();

     }

    /**
     * Onclick method part of the onClick listener.
     * @param v the view
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.date_picker_button:
                launchCalendar();
                break;
            default:
                break;
        }
    }

    /** Launches Date Picker Calendar fragment */
    private void launchCalendar() {

        DatePickerFragment frag = new DatePickerFragment();
        frag.show(getFragmentManager(), "launch");

    }


    /**
     * Google api call to zecode a zipcode and get its latitude and longitude
     * coordinates, after that post the new requests
     */
    private class GeocodeAsyncTask extends AsyncTask<String, String, String> {

        /**
         * The latitude coordinate of location
         */
        private Double mLatitude;

        /**
         * The longitude coordinate of location
         */
        private Double mLongitude;


        /**
         * The url to get lat and long from zipcode
         */
        private static final String LAT_LONG_URL =
                "http://maps.googleapis.com/maps/api/geocode/json?address=";

        /**
         * doInBackground works with location stuff
         * @param params
         * @return String
         */
        @Override
        protected String doInBackground(String... params) {

            String zipCode = params[0];
            String url = LAT_LONG_URL + zipCode;
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL urlObject = new URL(url);
                urlConnection = (HttpURLConnection) urlObject.openConnection();

                InputStream content = urlConnection.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    result += s;
                }

            } catch (Exception e) {
                result = "Unable to Get LAT and LONG: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return result;

        }

        /**
         * onPostExecute get JSON data
         * @param result result
         */
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("status");

                if (status.equals("ZERO_RESULTS")) {

                    Toast.makeText(getActivity(), "Invalid ZipCode."
                            , Toast.LENGTH_SHORT)
                            .show();
                } else if (status.equals("OK")) {

                    // Get the location json to get the lat and longitude
                    JSONArray res = (JSONArray) jsonObject.get("results");
                    JSONObject location = res.getJSONObject(0)
                            .getJSONObject("geometry")
                            .getJSONObject("location");

                    mLatitude = location.getDouble("lat");
                    mLongitude = location.getDouble("lng");

                    double tipAmount = 0;
                    if (mTip.getText().length() !=0) tipAmount = Double.parseDouble(mTip.getText().toString());
                    final String storeName = mStoreName.getText().toString();
                    final String itemsComments = mItemsComments.getText().toString();
                    final String zipcode = mZipCode.getText().toString();
                    final JSONObject post_dict = new JSONObject();

                    try {
                        post_dict.put("tip", tipAmount);
                        post_dict.put("zipcode", zipcode);
                        post_dict.put("storename", storeName);
                        post_dict.put("items_comments", itemsComments);
                        post_dict.put("userid", getUserId());
                        post_dict.put("lat", mLatitude);
                        post_dict.put("lng", mLongitude);
                    } catch (JSONException e) {
                        Log.e("ERROR ADD REQUEST JSON", e.toString());
                    }

                    // Execute http post request, insert into data base using async task
                    if (post_dict.length() > 0) {
                        new AddRequestsTask(getActivity()).execute(String.valueOf(post_dict));
                    }

                }
            } catch (JSONException e) {
                Toast.makeText(getActivity(), "Something wrong with the ZipCode API call" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

    /** OnFragmentInteractionListener */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
