package uw.tacoma.edu.paidaid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import uw.tacoma.edu.paidaid.R;

/** Home Activity for all activities*/
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        getSupportActionBar().setDisplayShowHomeEnabled(true); //sets icon on top
//        getSupportActionBar().setLogo(R.drawable.paidaid);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setupNavigationView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_navigation_buttons, menu);
        return true;
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = bottomNavigationView.getMenu();
            selectActivity(menu.getItem(0));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectActivity(item);
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
    protected void selectActivity(MenuItem item) {

        item.setChecked(true);
        Intent intent;
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
