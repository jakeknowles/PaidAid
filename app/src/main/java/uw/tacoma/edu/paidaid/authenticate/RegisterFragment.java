package uw.tacoma.edu.paidaid.authenticate;

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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import uw.tacoma.edu.paidaid.R;


/**
 * @Author Dmitriy Onishchenko
 * @Author Jake Knowles
 * @version 5/5/2017
 *
 * A Registration fragment that requires a user to input a username
 * email and password, and allows them to register with PaidAid
 */
public class RegisterFragment extends Fragment {

    /**
     * Registration URL to add new user to database
     */
    private static final String REGISTER_USER_URL
             = "http://paidaid.x10host.com/register.php?";

    /**
     * The username edit text view.
     */
    private EditText mUsernameEditText;

    /**
     * The email edit text view.
     */
    private EditText mEmailEditText;

    /**
     * The password text view.
     */
    private EditText mPasswordEditText;

    /**
     * The activity that launched this fragment.
     */
    private LoginActivity mActivity;

    /**
     * The shared preferences to keep track of user logged in status
     */
    SharedPreferences mSharedPreferences;


    /**
     * Constructor Initialize fields.
     */
    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * onCreate gets shared preferences
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);


    }

    /**
     * Loads XML layout and creates text edits, assigns click listener to button
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mUsernameEditText = (EditText) view.findViewById(R.id.username_text);
        mEmailEditText = (EditText) view.findViewById(R.id.email_text);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_text);

        // Attach on click listener
        Button confirm = (Button) view.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildRegisterUrl(v);

                RegisterTask task = new RegisterTask();
                task.execute(url);

            }

        });

        return view;
    }

    /**
     * LoginActivity - instanceof
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof LoginActivity) {
            mActivity = (LoginActivity) context;
        }
    }


    /**
     * Grabs input from the user1 and build a url.
     *
     * @param v view
     * @return a url to execute
     */
    private String buildRegisterUrl(View v) {

        StringBuilder sb = new StringBuilder(REGISTER_USER_URL);

        try {

            String username = mUsernameEditText.getText().toString();
            sb.append("username=");
            sb.append(username);

            String email = mEmailEditText.getText().toString();
            sb.append("&email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            String password = mPasswordEditText.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            Log.i("RegisterFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();

    }


    /**
     * The register task which will take a url and execute the web service call to our server.
     */
    private class RegisterTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to Register Reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }

        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.

            Log.e("REGISTER RESULT", result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");


                if (status.equals("success")) {

                    int userid = (int)  jsonObject.get("userid");
                    String ratingS = jsonObject.getString("rating");
                    float rating = Float.valueOf(ratingS);

                    // save user information and logged in status
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .putString(getString(R.string.USERNAME), mUsernameEditText.getText().toString())
                            .putString(getString(R.string.EMAIL), mEmailEditText.getText().toString())
                            .putInt(getString(R.string.USERID), userid)
                            .putFloat(getString(R.string.USER_RATING), rating)
                            .commit();


                    Toast.makeText(mActivity.getApplicationContext(), "Welcome to PaidAid!"
                            , Toast.LENGTH_LONG)
                            .show();

                    // go to home screen
                    getActivity().finish();

                } else {
                    Toast.makeText(mActivity.getApplicationContext(), "Failed to Register: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(mActivity.getApplicationContext(), "Something wrong with the Register " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Interface must be implemented by activities that contain this
     * fragment to allow interaction.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}
