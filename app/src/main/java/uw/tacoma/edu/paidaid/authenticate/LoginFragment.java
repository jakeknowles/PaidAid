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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Dmitriy Onishchenko
 * @Author Jake Knowles
 * @version 5/5/2017
 *
 * Login Fragment requiring a username and password
 * */
public class LoginFragment extends Fragment implements View.OnClickListener {


    /**
     * Shared preferences used to keep track if user is logged in or not
     */
    private SharedPreferences mSharedPreferences;

    /**
     * URL for our login.php file
     * */
    private final static String LOGIN_URL
            = "http://paidaid.x10host.com/login.php?";

    /**
     * Login Activity member variable
     * */
    private LoginActivity mLoginActivity;

    /**
     * Edit text member variable for username
     * */
    private EditText mUsernameEditText;

    /**
     * Edit Text member variable for password
     * */
    private EditText mPasswordEditText;


    /**
     * Constructor
     * */
    public LoginFragment() {}


    /**
     * onCreate
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);

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

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        /** Load layouts for username & password */
        mUsernameEditText = (EditText) view.findViewById(R.id.username_text);
        mPasswordEditText = (EditText) view.findViewById(R.id.password_text);

        /** Attach on click listener */
        Button login = (Button) view.findViewById(R.id.login_button);
        login.setOnClickListener(this);

        /** Attach on click listener */
        Button signUp = (Button) view.findViewById(R.id.sign_up_now_button);
        signUp.setOnClickListener(this);

        return view;
    }

    /**
     * onAttach for LoginActivity
     * @param context is the data for the activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof LoginActivity) {
            mLoginActivity = (LoginActivity) context;
        }
    }


    /**
     * Gets the text entered into the username and password fields and builds
     * login url to be used with the AsyncTask
     * @param v the view
     * @return url to be used to with AsyncTask
     */
    private String buildLoginURL(View v) {

        StringBuilder sb = new StringBuilder(LOGIN_URL);

        try {

            String username = mUsernameEditText.getText().toString();
            sb.append("username=");
            sb.append(username);


            String password = mPasswordEditText.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            Log.i("LoginFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }


    /**
     * Helper method to launch new fragment if the user1 want to sign up.
     */
    private void launchSignUpScreen() {

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.login_fragment_container, new RegisterFragment())
                .addToBackStack(null)
                .commit();

    }


    /**
     * Helper method to create url and try logging in
     * @param view the view
     */
    private void tryLogin(View view) {

        String url = buildLoginURL(view);

        LoginTask task = new LoginTask();
        task.execute(url);


    }

    /**
     * Onclick method part of the onClick listener.
     * @param v the view
     */
    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.login_button:
                tryLogin(v);
                break;
            case R.id.sign_up_now_button:
                launchSignUpScreen();
                break;
            default:
                break;
        }
    }


    /**
     * Inner LoginTask class dealing with database
     * */
    private class LoginTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to Login Reason: "
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
         * exception is caught. It tries to call the parse method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.

            try {
                Log.e("Result", result);

                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");

                if (status.equals("success")) {

                    String email = (String) jsonObject.get("email");
                    int userid = (int)  jsonObject.get("userid");
                    String ratingS = jsonObject.getString("rating");
                    float rating = Float.valueOf(ratingS);

                    // save user information and logged in status
                    mSharedPreferences
                            .edit()
                            .putBoolean(getString(R.string.LOGGEDIN), true)
                            .putString(getString(R.string.USERNAME), mUsernameEditText.getText().toString())
                            .putString(getString(R.string.EMAIL), email)
                            .putInt(getString(R.string.USERID), userid)
                            .putFloat(getString(R.string.USER_RATING), rating)
                            .commit();

                    Toast.makeText(mLoginActivity.getApplicationContext(), "Welcome Back!"
                            , Toast.LENGTH_LONG)
                            .show();

                    // Go to home screen upon successful login
                    // do this my finishing the loginActivity
                    getActivity().finish();

                } else {
                    Toast.makeText(mLoginActivity.getApplicationContext(), "Failed to Login: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(mLoginActivity.getApplicationContext(), "Something wrong with the Login " +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * OnFragmentInteractionListener, activities must implment this interface to
     * allow for interaction.
     * */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
