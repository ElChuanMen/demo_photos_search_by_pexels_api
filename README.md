# Demo Android App Photo Search

An Android demo app to search for photos using the free Pexels API.

## Architecture and technical
- Language: **Android Kotlin**
- UI: **Jetpack Compose**
- Architecture: **Clean Architecture** ,**SOLID**
- Design pattern: **MVVM**
- Libraries: **Jetpack Compose, Dagger-Hilt, Retrofit, Coil, Gson, RoomDatabase**
- Supporter: **Google, Stackoverflow, Chat GPT, Grock**
## Deployment
- Download Apk demo : [Demo App Photo Search](https://github.com/ElChuanMen/demo_photos_search_by_pexels_api/blob/main/app/product/release)

- To build:

  Add your Pexels API_KEY to the __local.properties__ file:  __API_KEY__=[your_api_key]

  Or replace at line 94 (BuildConfig.API_KEY) in the (RetrofitClient.kt) file  with your key

**You should build a release or an APK file to test. Because Jetpack Compose is not smooth in debug mode.**
## Update in the future
### Feature
- Details Photo: Recalculate the network environment and network speed to choose the appropriate photo type to display (Large, 4G= large2x,Fast Wifi= original). If you want to show the photo with the best quality possible. But still ensure good performance.  You can rely on the screen parameters such as screen size, resolution, and internet connection type (Wifi, 4G). To come up with a suitable size from the photo's original size, for Example (Photo size = Screen Size * X)
- Refactor UI to, using tabs, HorizontalPager,BottomNavigationTab with main function (Search,Favorite,Download)
- Add animation to the scroll list action to expand and collapse the header layout
- Favorite Photo Feature : Add function [Favorite] in the details photo screen. Save the object photo in to RoomDatabase.
- Download Manager : Show list Downloaded Photos
- Share Photo
### Security
- Protect the API KEY: Combine C++ and Firebase config to protect local API KEY, or get the API KEY from Backend
## Support

For support,
- Email: doanvanquang146@gmail.com
- Telegram: @elchuanemn
- Phone/Zalo: +84949514503
