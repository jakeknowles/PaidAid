package uw.tacoma.edu.paidaid.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dmitriy on 5/25/17.
 */

public class GeocodeAsyncTask extends AsyncTask<String, String, String> {

    /**
     * The fragment using this Async task
     */
    private Activity mActivity;

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
     * Constructor
     * @param theActivity
     */
    public GeocodeAsyncTask(Activity theActivity) {
        mActivity = theActivity;
    }

    /**
     * Latitude Getter
     * @return mLatitude
     */
    public Double getLatitude() {
        return mLatitude;
    }

    /**
     * Longitude getter
     * @return mLongitude
     */
    public Double getLongitude() {
        return mLongitude;
    }

    /**
     * doInBackground JSON
     * @param params
     * @return
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
     * onPostExecute JSON
     * @param result
     */
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

                // get the location json to get the lat and longitude
                JSONArray res = (JSONArray) jsonObject.get("results");
                JSONObject location = res.getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location");

                Log.e("this is loc", location.toString());
                mLatitude = location.getDouble("lat");
                mLongitude = location.getDouble("lng");

                Log.e("this is lat in geocode", Double.toString(mLatitude));

            }
        }
        catch (JSONException e) {
            Toast.makeText(mActivity.getApplicationContext(), "Something wrong with the ZipCode API call" +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
