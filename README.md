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
- Download Apk demo : [Demo App Photo Search](https://github.com/ElChuanMen/demo_photos_search_by_pexels_api/blob/main/app/product/release/Demo_Photo_Search_build_1.0_090525_1006-product-release.apk)

- To build:
 
  Add your Pexels API_KEY to the __local.properties__ file:  __API_KEY__=[your_api_key]
  
  Or replace at line 94 (BuildConfig.API_KEY) in the (RetrofitClient.kt) file  with your key
 
**You should build a release or an APK file to test. Because Jetpack Compose is not smooth in debug mode.**
## Update in the future
### Feature
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
