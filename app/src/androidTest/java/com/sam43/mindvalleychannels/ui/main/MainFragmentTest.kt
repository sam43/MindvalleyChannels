package com.sam43.mindvalleychannels.ui.main

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.di.AppModule
import com.sam43.mindvalleychannels.di.BindingModule
import com.sam43.mindvalleychannels.di.DispatcherProvider
import com.sam43.mindvalleychannels.helper.launchFragmentInHiltContainer
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.repository.MainRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTestOnTestScope
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import javax.inject.Inject


@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class, BindingModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //private lateinit var testNavHostController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Inject
    lateinit var retrofit: Retrofit
    @Inject
    lateinit var api: Api

    @Inject
    lateinit var repository: MainRepository

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @Test
    fun fragmentIsLaunched() {
        val testNavHostController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        testNavHostController.setGraph(R.navigation.app_navigation)
        testNavHostController.setCurrentDestination(R.id.mainFragment)

        launchFragmentInHiltContainer<MainFragment>(navHostController = testNavHostController)
    //        Assert.assertTrue(scenario.)
//        onView(withId(R.id.rvEpisodes))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvChannels))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvCategories))
//            .check(matches(isDisplayed()))
    }
}