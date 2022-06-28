package com.sam43.mindvalleychannels.helper

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import com.sam43.mindvalleychannels.ui.MainActivityTest
import kotlinx.coroutines.runBlocking


inline fun <reified T : Fragment> launchFragmentFromContainerX(
    fragmentArgs: Bundle? = null,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivityTest::class.java
        )
    )

    ActivityScenario.launch<MainActivityTest>(startActivityIntent).onActivity { activity ->
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        // Create a mock NavController
        //val mockNavController = mock(NavController::class.java)

        // Create a graphical FragmentScenario for the MainFragment
        val fragmentScenario = launchFragmentInContainer<T>()

        // Set the NavController property on the fragment
        fragmentScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }
}

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    navHostController: NavHostController? = null,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: T.() -> Unit = {}

): ActivityScenario<MainActivityTest> {

    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            MainActivityTest::class.java
        )
    )

    return ActivityScenario.launch<MainActivityTest>(startActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        fragment.arguments = fragmentArgs

        runBlocking {
            navHostController?.let {
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        Navigation.setViewNavController(fragment.requireView(), it)
                    }
                }
            }
        }

        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        (fragment as T).action()
    }
}

fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
    return RecyclerViewMatcher(recyclerViewId)
}