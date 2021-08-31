# quiz-app
A work-in-progress quiz app I started developing for a client but got paused.

## Background
This app was intended to be a trivia app where users answers questions to compete with their friends. Most of the components (buttons, shadows, cards, animations, etc.) were custom and created from stratch. After a month of part-time work, the business owners decided not to prioritize this particular mobile app and paused the project until a second notice. The project is completely written by me (Safa Orhan) and architected in a way that it's highly testable and scalable.

## Libraries Used
- Amplify AWS: Authentication system
- Retrofit & OkHttp: API consumption
- Hilt: Dependency Injection
- Lottie: Animations
- Jetpack Navigation: Navigation system
- ViewModel, Kotlin Coroutines, Kotlin Flow, Kotlin StateFlow, Jetpack DataStore, Data Binding Library, ...

## Test Libraries Used
- JUnit4
- AssertJ: Fluent assertions
- Mockito Kotlin: Fluent mocking
- Turbine: To write fluent Kotlin Flow tests
- MockWebServer: E2E testing networking and business logic together

## Architectural Decisions

### Multi Module App
This app is a **multi-module** app. It means every feature and tooling is packed in a separate gradle module to reduce build times and allow module-level reusability among other future projects. The challenge with creating a multi-module app is you need to consider gradle module dependency tree at and Hilt dependency tree at the same time. It needs a good knowledge of how gradle works.

### Automated Unit Tests
This app has automated unit tests which run in the JVM level and automated by GitHub actions. With a good GitHub configuration we can ensure there are no failing tests in the master / main branch.

Also, with the help of MockWebServer we could test the app using realistic tests against saved backend responses. See the following test:

![carbon (27)](https://user-images.githubusercontent.com/4990386/131467622-46b7c275-c465-4b37-b3f3-f8c02550d891.png)

### MVVM with Coroutines + Flow
This app deals with asynchronity using Coroutines and create reactive streams with the help of Flow and StateFlow. In general the app follows the officially endorsed MVVM architecture pattern by Google.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)
