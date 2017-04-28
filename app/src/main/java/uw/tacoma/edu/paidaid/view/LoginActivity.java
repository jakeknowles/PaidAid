package uw.tacoma.edu.paidaid.view;



import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uw.tacoma.edu.paidaid.R;

public class LoginActivity extends AppCompatActivity {

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
