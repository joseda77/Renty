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
class MainActivityTest{

    @Rule @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
            MainActivity::class.java
    )

    @Test
    fun clickSearchButton() {
        onView(withId(R.id.search_button)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(ViewAssertions.matches(isEnabled()))
        onView(withId(R.id.search_button)).perform(click())
    }

    @Test
    fun clickHideShowButton() {
        onView(withId(R.id.show_hide_button)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.show_hide_button)).check(ViewAssertions.matches(isEnabled()))
        onView(withId(R.id.show_hide_button)).perform(click())
    }

    @Test
    fun clickTypeSpinner() {
        onView(withId(R.id.type)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.type)).perform(click())
    }

    @Test
    fun clickFromSpinner() {
        onView(withId(R.id.from)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.from)).perform(click())
    }

    @Test
    fun clickToSpinner() {
        onView(withId(R.id.to)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.to)).perform(click())
    }

}