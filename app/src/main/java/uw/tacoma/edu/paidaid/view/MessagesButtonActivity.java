package uw.tacoma.edu.paidaid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import uw.tacoma.edu.paidaid.R;

public class MessagesButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_button);

        getSupportActionBar().setDisplayShowHomeEnabled(true); //sets icon on top
        getSupportActionBar().setLogo(R.drawable.topbarpaidaid);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setupNavigationView();
    }


    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {

            // Select first menu item by default and show activity accordingly.
            Menu menu = bottomNavigationView.getMenu();
            chooseActivity(menu.getItem(0));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            chooseActivity(item);
                            return false;
                        }
                    });
        }
    }

    /**
     * Perform action when any item is selected.
     *
     * @param item Item that is selected.
     */
    protected void chooseActivity(MenuItem item) {
        Intent intent;
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.home_button:
                break;
            case R.id.add_button:
                intent = new Intent(this, AddRequestButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.messages_button:
                intent = new Intent(this, MessagesButtonActivity.class);
                startActivity(intent);
                break;
            case R.id.requests_button:
                intent = new Intent(this, RequestsButtonActivity.class);
                startActivity(intent);
                break;
        }
    }
}
