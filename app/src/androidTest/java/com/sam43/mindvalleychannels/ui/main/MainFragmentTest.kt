package com.sam43.mindvalleychannels.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.PerformException
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sam43.mindvalleychannels.R
import com.sam43.mindvalleychannels.databinding.MainFragmentBinding
import com.sam43.mindvalleychannels.di.AppModule
import com.sam43.mindvalleychannels.di.BindingModule
import com.sam43.mindvalleychannels.di.DispatcherProvider
import com.sam43.mindvalleychannels.helper.launchFragmentInHiltContainer
import com.sam43.mindvalleychannels.helper.withRecyclerView
import com.sam43.mindvalleychannels.network.Api
import com.sam43.mindvalleychannels.repository.MainRepository
import com.sam43.mindvalleychannels.ui.adapters.ParentAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import javax.inject.Inject


@ExperimentalCoroutinesApi
@UninstallModules(AppModule::class, BindingModule::class)
@HiltAndroidTest
@SmallTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var vm: MainViewModel

    @Mock
    lateinit var binding: MainFragmentBinding
    private lateinit var inflater: LayoutInflater

    @Mock
    private lateinit var rootView: SwipeRefreshLayout // must be the type of the root view in the layout
    @Mock
    private lateinit var groupView: ViewGroup

    //private lateinit var testNavHostController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        //val context = ApplicationProvider.getApplicationContext<HiltTestApplication>()
        vm = MainViewModel(repository, dispatcherProvider)
        //inflater = LayoutInflater.from(context)
    }

    @Test
    fun testBindingSuccess() {
        //binding = MainFragmentBinding.inflate(inflater, null, false)
        //Assert.assertTrue(binding.root == rootView)
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
    fun testLaunchFragmentInHiltContainer() {
        launchFragmentInHiltContainer<MainFragment> {
            vm.consumeRemoteChannels()
            this.onResume()
            Assert.assertTrue(this.isVisible)
            Assert.assertTrue(this.isResumed)
        }
        onView(withId(R.id.tvTitleHome))
            .check(matches(isDisplayed()))
        onView(withId(R.id.flChannels))
            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvChannels))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvEpisodes))
//            .check(matches(isDisplayed()))
//        onView(withId(R.id.rvCategories))
//            .check(matches(isDisplayed()))
    }

    @Test(expected = PerformException::class)
    fun check_if_recyclerview_if_some_data_does_not_exist() {
        launchFragmentInHiltContainer<MainFragment> {
            this.activity
            this.onResume()
        }
        // Attempt to scroll to an item that contains the special text.
        onView(allOf(isDescendantOfA(withId(R.id.flChannels)),
            isDescendantOfA(withRecyclerView(R.id.rvChannels).atPosition(0)),
            isDescendantOfA(withRecyclerView(R.id.nestedRecyclerView).atPosition(0)),
            withId(R.id.nestedTitleTextView)))
            .perform(
                RecyclerViewActions.scrollTo<ParentAdapter.VH>(
                    hasDescendant(withText("not in the list"))
                ))
    }
    @Test
    fun check_if_recyclerview_if_some_data_exist() {
        launchFragmentInHiltContainer<MainFragment> {
            this.activity
            this.onResume()
        }
        // Attempt to scroll to an item that contains the special text.
        onView(withId(R.id.nestedTitleTextView))
            .check(matches(withText("Mindvalley Mentoring")))
    }

}