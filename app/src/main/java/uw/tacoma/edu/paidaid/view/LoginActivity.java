package uw.tacoma.edu.paidaid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uw.tacoma.edu.paidaid.R;

/** Activity for logging in with username & password */
public class LoginActivity extends AppCompatActivity {

    /**
     * onCreate loads the activity login layout & then creates a new login fragment
     * @param savedInstanceState is a reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.login_fragment_container, new LoginFragment())
            .commit();
    }

}
