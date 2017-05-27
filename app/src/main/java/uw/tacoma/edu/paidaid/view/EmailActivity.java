package uw.tacoma.edu.paidaid.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/26/17
 *

/**
 * Email Activity is an activity launched when the user is able to pick up a request,
 * prompting the user with an email template ready to fill in.
 */
public class EmailActivity extends Activity {

    /** Send email button */
    Button sendEmail;

    /** Subject of email */
    EditText emailSubject;

    /** Message content of email */
    EditText emailMessage;

    /**
     * onCreate loads up XML layout for Email Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pick_up_request_message);


        /** Text edits where text will be typed in from user and a button to submit text */
        emailSubject = (EditText) findViewById(R.id.subject);
        emailMessage = (EditText) findViewById(R.id.message);
        sendEmail = (Button) findViewById(R.id.send_button);

        /** Listener on button for sending email */
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = emailSubject.getText().toString();
                String message = emailMessage.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("text/plain");
                startActivity(Intent.createChooser(email, "Send Email Via"));

            }
        });
    }
}
