package com.mlsdev.authorizationsample;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;

import com.mlsdev.authorizationsample.model.nework.ApiClient;
import com.mlsdev.authorizationsample.view.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@LargeTest
public class TestAuthorizationActivity {
    private MockWebServer mockWebServer;
    private Context context;
    private String correctEmail = "test@test.com";
    private String incorrectEmail = "test@...";
    private String correctPassword = "12345678";
    private String incorrectPassword = "1";

    @Rule
    public IntentsTestRule<MainActivity> rule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws IOException {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        context = instrumentation.getTargetContext();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        ApiClient.setBaseUrl(mockWebServer.url("/").url().toString());
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testSignUserIn_WithIncorrectEmailAndCorrectPassword() {
        enterEmail(incorrectEmail);
        enterPassword(correctPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_incorrect)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn_WithCorrectEmailAndIncorrectPassword() {
        enterEmail(correctEmail);
        enterPassword(incorrectPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_too_short_password)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn_WithIncorrectEmailAndIncorrectPassword() {
        enterEmail(incorrectEmail);
        enterPassword(incorrectPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_too_short_password)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_incorrect)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn_WithEmptyPassword() {
        enterEmail(correctEmail);
        enterPassword("");
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_empty)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn_WithEmptyEmail() {
        enterEmail("");
        enterPassword(correctPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_empty)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn_WithEmptyEmailAndIncorrectPassword() {
        enterEmail("");
        enterPassword(incorrectPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_empty)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(context.getString(R.string.error_message_too_short_password)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignUserIn() {
        mockWebServer.enqueue(new MockResponse().setBody(AssetsUtil.getSignInResponseData(context)));

        enterEmail(correctEmail);
        enterPassword(correctPassword);
        performSignInButtonClick();

        Espresso.onView(ViewMatchers.withText("Welcome to")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void enterEmail(String email) {
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .perform(ViewActions.typeText(email))
                .perform(ViewActions.closeSoftKeyboard());
    }

    private void enterPassword(String password) {
        Espresso.onView(ViewMatchers.withId(R.id.et_password))
                .perform(ViewActions.typeText(password))
                .perform(ViewActions.closeSoftKeyboard());
    }

    private void performSignInButtonClick() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_in))
                .perform(ViewActions.click());
    }
}
