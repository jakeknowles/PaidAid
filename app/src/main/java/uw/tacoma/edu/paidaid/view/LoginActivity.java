package uw.tacoma.edu.paidaid.view;




import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;

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
