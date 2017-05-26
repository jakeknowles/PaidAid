package uw.tacoma.edu.paidaid;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uw.tacoma.edu.paidaid.authenticate.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by jake on 5/26/17.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterFragmentTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testRegister() {

        // Type text and then press the button.
        onView(withId(R.id.username_text))
                .perform(typeText("test@uw.edu"));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("myPassword"));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button))
                .perform(click());

        onView(withText("success"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }




}
