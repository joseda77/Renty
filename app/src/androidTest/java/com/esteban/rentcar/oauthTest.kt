package com.esteban.rentcar

import android.support.test.espresso.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo

@RunWith(AndroidJUnit4::class)
class oauthTest{

    @Rule @JvmField
    var activityRule = ActivityTestRule<oauth>(
            oauth::class.java
    )

    @Test
    fun clickLoginButton() {
        onView(withId(R.id.sign_in_button)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.sign_in_button)).check(ViewAssertions.matches(isEnabled()))
        onView(withId(R.id.sign_in_button)).perform(click())
    }

}