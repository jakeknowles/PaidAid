package uw.tacoma.edu.paidaid.coreFeatures;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import uw.tacoma.edu.paidaid.tasks.GeocodeAsyncTask;
import uw.tacoma.edu.paidaid.view.DatePickerFragment;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/11/17
 *
 * Add Button Fragment from clicking "Add" on home screen. */
public class AddRequestFragment extends DialogFragment implements View.OnClickListener {

    /**
     * The url to add a new request
     */
    private static final String ADD_REQUEST_URL =
            "http://paidaid.x10host.com/addRequest.php?cmd=requests";


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
     * Method that validates the zipcode and if invalid zipcode
     * asks the user to enter a valid zipcode
     * @param zipCode the zipcode to validate
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
     * Helper method to run the google api geocode task
     * @param theZipCode the zipcode of location
     * @return the task
     */
    private GeocodeAsyncTask getLatandLong(String theZipCode) {

        GeocodeAsyncTask task = new GeocodeAsyncTask(getActivity());
        return (GeocodeAsyncTask) task.execute(theZipCode);

    }



    /**
     * Get the users input and insert into database
     */
    private void getUserInput() {

        double tipAmount = 0;
        String zipCode = (String) mZipCode.getText().toString();

        if (mTip.getText().toString().length() != 0)
            tipAmount = Double.parseDouble(mTip.getText().toString());


        // validate the zipcode to make sure it is valid
        // otherwise return
        if (validateZipCode(zipCode) == -1)
            return;

        // get the lat and long coordinates using google api call
        GeocodeAsyncTask task = getLatandLong(zipCode);
        Double lat = task.getLatitude();
        Double lng = task.getLongitude();

        // if task didn't return the right results
        if (lat == null || lng == null) {
            return;
        }


        final String storeName = mStoreName.getText().toString();
        final String itemsComments = mItemsComments.getText().toString();

        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("tip", tipAmount);
            post_dict.put("zipcode", zipCode);
            post_dict.put("storename", storeName);
            post_dict.put("items_comments", itemsComments);
            post_dict.put("userid", 1);
            post_dict.put("lat", lat);
            post_dict.put("lng", lng);
        } catch (JSONException e) {
            Log.e("ERROR ADD REQUEST JSON", e.toString());
        }

        Log.e("JSON", String.valueOf(post_dict));

        // execute httppost request, insert into data base
        if (post_dict.length() > 0) {
            new AddRequestsTask().execute(String.valueOf(post_dict));
        }

    }

    private void launchCalendar() {

        DatePickerFragment frag = new DatePickerFragment();
        frag.show(getFragmentManager(), "launch");

    }

    /** OnFragmentInteractionListener */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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


    /**
     * A task to download the requests from the database.
     */
    private class AddRequestsTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            BufferedReader inBuffer = null;
            String url = ADD_REQUEST_URL;
            String jsonObject = params[0];
            String result = "";
            HttpURLConnection urlConnection = null;
            try {

                URL urlObject = new URL(url);
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


            } catch(Exception e) {
                result = "Unable to add new request: ";
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
            return  result;
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
                Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_LONG)
                        .show();
                return;
            }


            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");


                if(status.equals("success")) {

                    Fragment fragHome = getActivity().getSupportFragmentManager()
                            .findFragmentByTag(getString(R.string.home_tag));

                    if (fragHome == null)
                        getActivity(). getSupportFragmentManager().beginTransaction()
                                .add(R.id.activity_main, fragHome)
                                .commit();
                    else
                        getActivity(). getSupportFragmentManager().beginTransaction()
                                .detach(fragHome)
                                .attach(fragHome)
                                .commit();

                    Toast.makeText(getActivity().getApplicationContext(), "Your Request was successfully posted",
                            Toast.LENGTH_LONG).show();

                } else {

                    String err = (String) jsonObject.get("error");
                    Toast.makeText(getActivity().getApplicationContext(), "Error! Request not posted " + err,
                            Toast.LENGTH_LONG).show();

                }


            } catch (JSONException e) {

                Toast.makeText(getActivity().getApplicationContext(), "Something wrong new Request " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
