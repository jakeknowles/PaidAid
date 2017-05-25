package uw.tacoma.edu.paidaid.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uw.tacoma.edu.paidaid.R;

/**
 * Created by dmitriy on 5/25/17.
 */

public class GeocodeAsyncTask extends AsyncTask<String, String, String> {

    /**
     * The fragment using this Async task
     */
    private Activity mActivity;

    private String mLatitude;

    private String mLongitude;



    /**
     * The url to get lat and long from zipcode
     */
    private static final String LAT_LONG_URL =
            "http://maps.googleapis.com/maps/api/geocode/json?address=";


    public GeocodeAsyncTask(Activity theActivity) {
        mActivity = theActivity;
    }

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

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = (String) jsonObject.get("status");

            if (status.equals("ZERO_RESULTS")) {

                Toast.makeText(mActivity.getApplicationContext(), "Invalid ZipCode."
                        , Toast.LENGTH_SHORT)
                        .show();
            } else if (status.equals("OK")) {





                Log.e("THIS IS THE JSON", jsonObject.get("results").toString());

                Toast.makeText(mActivity, result.toString(), Toast.LENGTH_SHORT).show();

            }
        }
        catch (JSONException e) {
            Toast.makeText(mActivity.getApplicationContext(), "Something wrong with the ZipCode " +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }






    }
}
