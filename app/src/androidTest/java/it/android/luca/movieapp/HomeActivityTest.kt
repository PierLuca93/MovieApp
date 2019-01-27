package it.android.luca.movieapp

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import it.android.luca.movieapp.detail.ui.DetailActivity
import it.android.luca.movieapp.home.ui.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import it.android.luca.movieapp.home.ui.HomeMoviesAdapter
import org.hamcrest.CoreMatchers.`is`


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = IntentsTestRule(HomeActivity::class.java, false, false)

    @Before
    fun setUp(){
        activityRule.launchActivity(Intent())
    }

    @Test fun onStart_showFirst20Movies() {
        onView(withId(R.id.movie_list)).check(RecyclerViewItemCountAssertion(20))
    }

    @Test fun onScroll_showSecondPage() {
        onView(withId(R.id.movie_list)).perform(scrollToPosition<HomeMoviesAdapter.MovieHolder>(18))
        Thread.sleep(2000)
        onView(withId(R.id.movie_list)).check(RecyclerViewItemCountAssertion(40))
    }

    @Test fun onClick_openDetailPage() {
        onView(withId(R.id.movie_list)).perform(actionOnItemAtPosition<HomeMoviesAdapter.MovieHolder>(4, click()))
        intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
    }
}


class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}
