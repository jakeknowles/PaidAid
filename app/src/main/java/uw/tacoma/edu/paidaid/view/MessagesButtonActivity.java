package uw.tacoma.edu.paidaid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import uw.tacoma.edu.paidaid.R;

public class MessagesButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation_buttons, menu);
        return true;
    }
}
