package uw.tacoma.edu.paidaid.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;


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
import uw.tacoma.edu.paidaid.coreFeatures.RequestFragment;

/**
 * @Author: Jake Knowles
 * @Author  Dmitriy Onishchenko
 * @version 5/29/2017
 *
/**
 * A task to download the requests from the database.
 */
public class AddRequestsTask extends AsyncTask<String, String, String> {

    /**
     * The url to add a new request
     */
    private static final String ADD_REQUEST_URL =
            "http://paidaid.x10host.com/addRequest.php?";

    /**
     * The Activity using this task.
     */
    private AppCompatActivity mActivity;


    /**
     * Constructor
     * @param theActivity theActivity
     */
    public AddRequestsTask(Activity theActivity) {
        mActivity = (AppCompatActivity) theActivity;
    }


    /**
     * doInbackground JSON
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

            URL urlObject = new URL(ADD_REQUEST_URL);
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

            // Get the response
            InputStream content = urlConnection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                result += s;
            }


        } catch (Exception e) {
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
            Toast.makeText(mActivity, result, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            String status = (String) jsonObject.get("result");


            if (status.equals("success")) {

                Fragment fragHome = mActivity.getSupportFragmentManager()
                        .findFragmentByTag(mActivity.getString(R.string.home_tag));

                if (fragHome == null)
                    mActivity.getSupportFragmentManager().beginTransaction()
                            .add(R.id.activity_main, new RequestFragment())
                            .commit();
                else
                    mActivity.getSupportFragmentManager().beginTransaction()
                            .detach(fragHome)
                            .attach(fragHome)
                            .commit();

                Toast.makeText(mActivity, "Your Request was successfully posted",
                        Toast.LENGTH_LONG).show();

            } else {

                String err = (String) jsonObject.get("error");
                Toast.makeText(mActivity, "Error! Request not posted " + err,
                        Toast.LENGTH_LONG).show();

            }


        } catch (JSONException e) {

            Toast.makeText(mActivity, "Something wrong new Request " +
                    e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}