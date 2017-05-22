package uw.tacoma.edu.paidaid;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by jake on 5/21/17.
 */

public class LoginFragmentTest {

    @Test
    public void testRegister() {

        onView(withId(R.id.username_text))
                .perform(typeText("test@uw.edu"));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("myPassword"));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button))
                .perform(click());
    }


    @Test
    public void testLoginNotRegistered() {
        onView(withId(R.id.username_text))
                .perform(typeText("notregisteredaccount@uw.edu."));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("test@@@@@@@@@@"));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.login_button))
                .perform(click());
    }

}
