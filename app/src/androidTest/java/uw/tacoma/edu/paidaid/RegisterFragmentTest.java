package uw.tacoma.edu.paidaid;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import uw.tacoma.edu.paidaid.authenticate.LoginActivity;
import uw.tacoma.edu.paidaid.view.HomeActivity;

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
 * @Author: Jake Knowles
 * @Author  Dmitriy Onishchenko
 * @version 5/29/2017
 */

/** Instrumented Test for Register Fragment -- Testing for valid username, email, and password & small
 *  login tests */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterFragmentTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule2 = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void testRegisterPass() {

        Random random = new Random();

        // Generate a random email address
        String email = "email" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Generate a random username
        String username = "username" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        // Generate a random password
        String password = "password" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        onView(withId(R.id.sign_up_now_button))
                .perform(click());


        onView(withId(R.id.username_text))
                .perform(typeText(username));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText(password));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.email_text))
                .perform(typeText(email));
        onView(withId(R.id.email_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.confirm_button))
                .perform(click());

        onView(withText("Welcome to PaidAid!"))
                .inRoot(withDecorView(not(is(
                        mActivityRule2.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterInvalidEmail() {

        Random random = new Random();

        // Generate a random username
        String username = "username" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        // Generate a random password
        String password = "password" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        onView(withId(R.id.sign_up_now_button))
                .perform(click());


        onView(withId(R.id.username_text))
                .perform(typeText(username));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText(password));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.email_text))
                .perform(typeText("abcefghijklmnopqrstuvwxyz"));
        onView(withId(R.id.email_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.confirm_button))
                .perform(click());

        onView(withText("Failed to Register: Please enter a valid email."))
                .inRoot(withDecorView(not(is(
                        mActivityRule2.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterInvalidUsername() {

        Random random = new Random();

        // Generate a random email address
        String email = "email" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Generate a random password
        String password = "password" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        onView(withId(R.id.sign_up_now_button))
                .perform(click());


        onView(withId(R.id.username_text))
                .perform(typeText("abc"));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText(password));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.email_text))
                .perform(typeText("abcefghijklmnopqrstuvwxyz"));
        onView(withId(R.id.email_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.confirm_button))
                .perform(click());

        onView(withText("Failed to Register: Please enter a valid email."))
                .inRoot(withDecorView(not(is(
                        mActivityRule2.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterInvalidPassword() {

        Random random = new Random();

        // Generate a random email address
        String email = "email" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1)
                + "@uw.edu";

        // Generate a random username
        String username = "username" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        onView(withId(R.id.sign_up_now_button))
                .perform(click());


        onView(withId(R.id.username_text))
                .perform(typeText("abc"));
        onView(withId(R.id.username_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("{bad}"));
        onView(withId(R.id.password_text)).perform(closeSoftKeyboard());
        onView(withId(R.id.email_text))
                .perform(typeText("abcefghijklmnopqrstuvwxyz"));
        onView(withId(R.id.email_text)).perform(closeSoftKeyboard());

        onView(withId(R.id.confirm_button))
                .perform(click());

        onView(withText("Failed to Register: Please enter a valid email."))
                .inRoot(withDecorView(not(is(
                        mActivityRule2.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));
    }

    public void testLoginPass() {

        onView(withId(R.id.username_text))
                .perform(typeText("jake")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("password")).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button))
                .perform(click());
    }

    public void testLoginFail() {

        onView(withId(R.id.username_text))
                .perform(typeText("fail")).perform(closeSoftKeyboard());
        onView(withId(R.id.password_text))
                .perform(typeText("password")).perform(closeSoftKeyboard());
        onView(withId(R.id.login_button))
                .perform(click());
    }
}