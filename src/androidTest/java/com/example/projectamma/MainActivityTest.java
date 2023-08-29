package com.example.projectamma;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.projectamma.UI.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // Launch the MainActivity
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testLoginButtonVisibility() {
        // Check if the login button is displayed
        Espresso.onView(ViewMatchers.withId(R.id.loginButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateUserButtonVisibility() {
        // Check if the create user button is displayed
        Espresso.onView(ViewMatchers.withId(R.id.createUserButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateUserButtonAction() {
        Espresso.onView(ViewMatchers.withId(R.id.createUserButton))
                .perform(ViewActions.click());
    }
}
