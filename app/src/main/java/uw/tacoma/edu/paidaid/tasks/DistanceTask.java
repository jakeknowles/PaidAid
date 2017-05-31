package uw.tacoma.edu.paidaid.tasks;

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
import java.util.List;

import uw.tacoma.edu.paidaid.model.Request;

/**
 * Created by dmitriy on 5/30/17.
 */

public class DistanceTask extends  AsyncTask<List<Request>, Void, List<Request>> {

    /**
     * URL to calculate the distance.. need to add additional parameters see buildDistanceURL()
     */
    private static final String DISTANCE_URL =
            "http://maps.googleapis.com/maps/api/directions/json?origin=";

    Double mLatitude;
    Double mLongitude;


    public DistanceTask(Double lat, Double lng) {

        mLatitude = lat;
        mLongitude = lng;
    }

    List<Request> mRequests;
    @Override
    protected List<Request> doInBackground(List<Request>... params) {

        mRequests = params[0];

//        for (Request req:  mRequests) {
        for (int i = 20; i < 21; i++) {
            Request req = mRequests.get(i);

                String url = buildDistanceURL(req.getLatitude(), req.getLongitude());

            Log.e("URL ", url);
                HttpURLConnection urlConnection = null;
                StringBuilder result = new StringBuilder(10000);

                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != "" || s != null) {
//                        Log.i("line not null", s.trim());
                        System.out.println(s);
                        result.append(s);
                    }

                    if(buffer.readLine() == null) {
                        System.out.println("what the heck" + result.toString());


                    }



//                    parseResultSetDistance(result.toString(), req);



                } catch (Exception e) {
                    Log.e("Distance Task ", "Unable to get distance");
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }


        return mRequests;
    }



    private void parseResultSetDistance(String result, Request theRequests) {

//        Log.e("THIS IS THE RESULT", result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = (String) jsonObject.get("status");

            if (status.equals("ZERO_RESULTS")) {

                Log.i("ZER0 RESULTS", "Distance Task");
                theRequests.setDistanceAway(0);

            } else if (status.equals("OK")) {

                // get the location json to get the lat and longitude
                JSONArray routes = (JSONArray) jsonObject.get("routes");
                JSONObject route = routes.getJSONObject(0);
                JSONArray legs = (JSONArray) route.get("legs");
                JSONObject distance = (JSONObject) legs.get(0);




                Log.e("this is the distance", distance.getString("text"));


                Log.e("this is lat in geocode", Double.toString(mLatitude));

            }
        }
        catch (JSONException e) {
//            Toast.makeText(mActivity.getApplicationContext(), "Something wrong with the ZipCode API call" +
//                    e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("got here error", "distance taskparseresults");
        }



    }


    private String buildDistanceURL(double lat, double lng) {

        StringBuilder url = new StringBuilder(DISTANCE_URL);
        url.append(mLatitude);
        url.append(",");
        url.append(mLongitude);
        url.append("&destination=");
        url.append(lat);
        url.append(",");
        url.append(lng);

        return url.toString();
    }







}
