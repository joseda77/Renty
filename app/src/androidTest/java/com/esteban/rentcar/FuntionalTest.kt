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

//https://code.tutsplus.com/es/tutorials/testing-android-user-interfaces-with-espresso--cms-31687
@RunWith(AndroidJUnit4::class)
class FuntionalTest {

    @Rule @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
            MainActivity::class.java
    )



}