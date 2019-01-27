package it.android.luca.movieapp

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import it.android.luca.movieapp.detail.ui.DetailActivity
import it.android.luca.movieapp.home.ui.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Rule
import it.android.luca.movieapp.home.ui.HomeMoviesAdapter
import it.android.luca.movieapp.utils.MockedInterceptor
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = IntentsTestRule(HomeActivity::class.java, false, false)

    @After
    fun teardown() {
        MockedInterceptor.mockedResponseMap.clear()
        MockedInterceptor.mockedErrorResponseMap.clear()
    }

    private fun startActivity(){
        activityRule.launchActivity(Intent())
    }

    @Test
    fun onErrorResponse_showErrorToast() {
        MockedInterceptor.mockedErrorResponseMap["/top_rated?page=1"] = "Error response"
        startActivity()
        onView(ViewMatchers.withText("HTTP 404 Error response")).inRoot(
            RootMatchers.withDecorView(
                CoreMatchers.not(
                    `is`(activityRule.activity.window.decorView)
                )
            )
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onStart_showFirst20Movies() {
        MockedInterceptor.mockedResponseMap["/top_rated?page=1"] = "/top_rated_1.json"
        startActivity()
        onView(withId(R.id.movie_list)).check(RecyclerViewItemCountAssertion(20))
    }

    @Test
    fun onScroll_showSecondPage() {
        MockedInterceptor.mockedResponseMap["/top_rated?page=1"] = "/top_rated_1.json"
        MockedInterceptor.mockedResponseMap["/top_rated?page=2"] = "/top_rated_2.json"
        startActivity()
        onView(withId(R.id.movie_list)).perform(scrollToPosition<HomeMoviesAdapter.MovieHolder>(17))
        onView(withId(R.id.movie_list)).check(RecyclerViewItemCountAssertion(40))
    }

    @Test
    fun onClick_openDetailPage() {
        MockedInterceptor.mockedResponseMap["/top_rated?page=1"] = "/top_rated_1.json"
        startActivity()
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
