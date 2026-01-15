# News app
## Dev Comment
This project is my first Android application written in Kotlin. While I have prior experience developing in Flutter, this was my first time working with Kotlin and Jetpack Compose.

Due to time constraints, I was not able to implement two requested features:

- **Protocol Buffers serialization** – I attempted to generate and use .proto files, but ran into integration issues that I could not resolve in time.
- **Unit tests** – while I understand their importance for verifying functionality, I was unable to write them before the submission deadline.

Despite these missing components, I decided to submit the project because a substantial portion of the app has been implemented, including:
- Fetching and displaying news articles from the News API
- Infinite scroll with paging support
- Responsive grid layout for portrait and landscape
-  Navigation between a list of articles and individual article details

I have commented some external sources in the code and have mentioned others here in the readme under used sources.

## Setup
### API Key
1. Get an API key at this [link](https://newsapi.org/docs/get-started).
2. Add the line
```
NEWS_API_KEY=your_key
```
to the gradle.properties file and replace "your_key" with your API key.
## Used Sources
- [Using retrofit](https://medium.com/@desiappdev24/fetching-data-using-retrofit-in-jetpack-compose-a-complete-guide-97f4c2101cb7)
- [Navigating from newsScreen to Article](https://dilipp817.medium.com/navigation-in-jetpack-compose-a-complete-guide-f15020f1b07d)
- [store api key in android](https://medium.com/@darayve/how-to-safely-store-api-keys-in-android-project-a-straightforward-guide-22c7fffd95e7)
- [use api key interceptor](https://dnmtechs.com/adding-api_key-into-interceptor-using-okhttp-in-kotlin/)
- [viewmodels with jetpack compose](https://medium.com/appcent/how-to-use-viewmodel-with-jetpack-compose-dc543f10bf6f)