package it.android.luca.movieapp

import android.content.Intent
import android.support.test.InstrumentationRegistry.*
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import it.android.luca.movieapp.detail.ui.DetailActivity
import it.android.luca.movieapp.detail.ui.DetailActivity.Companion.MOVIE_ID
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule
import org.hamcrest.CoreMatchers.`is`
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*




@RunWith(AndroidJUnit4::class)
class DetailActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(DetailActivity::class.java, false, false)


    private fun startActivity(id: String){

        val intent = Intent(getTargetContext(), DetailActivity::class.java)
        intent.putExtra(MOVIE_ID, id)
        activityRule.launchActivity(intent)
    }

    @Test
    fun onNonExistingMovie_showErrorToast() {

        startActivity("1")
        onView(withText("HTTP 404 Movie not found")).inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onExistingMovie_showDetail() {
        startActivity("372058")
        onView(withId(R.id.poster)).check(matches(isDisplayed()))
        onView(withId(R.id.description)).check(matches(isDisplayed()))
        onView(withId(R.id.release_date)).check(matches(withText("26-08-2016")))
    }

    @Test
    fun onBackArrow_CloseActivity() {
        startActivity("372058")
        onView(withContentDescription("Navigate up")).perform(click())
        assertTrue(activityRule.activity.isFinishing)

    }
}