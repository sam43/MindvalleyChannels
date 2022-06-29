# MindvalleyChannels
Mindvalley assignment for recruitment process

- MVVM & Architecture Components (ViewModel, StateFlow, Flow) are used in this project.
- Programming language: Kotlin, designed with .XML.
- A simple splash screen added in the start with application theme.
- Shimmer layout added for loading view while loading data from server
- Kotlin Coroutines is used for background operations with different dispatcher.
- Dagger Hilt is used for dependency injection.
- Navigation UI component was used for loading starting fragment and is scalable in future.
- Retrofit is used for networking.
- Gson and GsonConverterFactory is used for JSON parsing with retrofit.
- Retrofit cache interceptor is used for cache mechanism and offline support. Reason behind not using room is, for the given scenario it will not be an efficient way. 
  However, Room should be responsible for storing more frequent data from the user unlike the Preferences/ DataStore
- Glide is used for image loading and caching.
- A custom designed parent-child adapter was implemented for the recyclerview as it can handle multiple type of layout managers (depending upon the type).
- Recyclerview was initiated with Custom Snaphelper for smooth UI experience.
- JUnit4 Framework is used for Unit. Mockito for mocking purpose and Espresso Framework is used for UI testing

### What parts of the test did you find challenging and why?

After worked the network testing and repository testing, when I moved to the UI testing I found it challenging enough. I have tried with 
less complex UI than the current one. To resolve this, I also tried mocking the FragmentBinding to access the child view ID but I couldn't succeed but I can 
check the immediate view hierarchy and test it. I needed some more research on it but as I have some deliverable form my office, I couldn't.

### What feature would you like to add in the future to improve the project?

Adding gamification to the lessons so that people become more engaged to the course they are enrolling into.
I have gone through some course's details page where both video player and information like course outline situated but when ever user scrolls down or go back from current page,
the video gts hidden and stopped respectively but as of my perspective I would like watch what I was watching while traversing the whole application. From
this idea a PiP (Picture in Picture) Mode will be a very good choice of features to improve Midnvalley app overall.


