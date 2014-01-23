Google Mobile Ads SDK for Android
=============================
The Google Mobile Ads SDK is the latest generation in Google mobile advertising featuring refined ad formats and streamlined APIs for access to mobile ad networks and advertising solutions. The SDK enables mobile app developers to maximize their monetization in native mobile apps.

This repository contains the source for the AdCatalog project, which shows a variety of ways that AdMob can be integrated into your app.

Features
---------
The is the Ad Catalog for Android.  The catalog was written and tested with the
Google Admob Ads SDK version 6.0.1, and supports Android 1.6 or higher.

The catalog provides:
* A simple banner example featuring a standard banner and smart banner on
  phones and additional formats on tablets.
* Four banner layout examples:
  1. TabbedView - a banner ad is persisted across tabs.
  2. ListView - a banner ad is embedded into a list every k list items.
  3. OpenGLView - a banner ad is docked to the bottom of the screen and sits
                  outside an OpenGLView.
  4. ScrollView - a banner ad is docked to the top of the screen and sits
                  outside of a ScrollView.
* Five interstitial examples:
  1. Basic - an interstitial ads loads and displays when ready.
  2. Game Levels - an interstitial is displayed before moving to level 2.
  3. Video Preroll - an interstitial is displayed prior to viewing a video.
  4. Page Swipe - an interstitial is displayed when swiping between screens.
  5. Splash - if this option is set, an interstitial will be displayed the next
              time the app loads.
* Test mode toggle - determines whether to request test ads.
* Support for tablets.

Requirements:
-------------
* Android SDK 3.2 or higher
* Google AdMob Ads SDK for Android
* AdMob publisher ID
* Eclipse (recommended)

Getting started with the Ad Catalog with Eclipse:
--------------------------------------------------
1. Create an Android project from existing source.
   * Create a new Android Project in Eclipse, and select "Create project from
     existing source."
   * Browse to the base directory of the source code.  The Project Name and
     Target name should be populated.  Click Finish.
2. Add the Google AdMob Ads Jar to your Eclipse Project.
   * Right click on your app project in Eclipse and select "Properties.
   * Select "Java Build Path" and the "Libraries" tab. Then click
     "Add External JARs..." to add the Google AdMob Ads Jar.
3. Add the android-support-v4.jar to your Eclipse Project.
   * The android-support-v4.jar is included in the Android Compatibility
     package.  For instructions on how to download this package, visit
     http://developer.android.com/sdk/compatibility-library.html#Downloading.
   * Include this jar the same way as the Google AdMob Ads jar.
4. Insert your AdMob publisher ID into the application.
   * In the admob_id string in res/values/strings.xml, replace the
     "INSERT_YOUR_ADMOB_ID_HERE" text with your publisher ID.

Your application should run properly.

Note:
-( Test mode currently only gets test ads in the emulator.  To get test ads in
  test mode on your device, call adRequest.addTestDevice("YOUR_DEVICE_ID")
  method before calling the loadAd method.  Your device ID can be found in the
  LogCat output when loading an ad on your device.

Downloads
-----------
Please check out our [releases](https://github.com/googleads/googleads-mobile-android-adcatalog/releases) for the latest downloads for the different sample apps.

Documentation
--------------
Check out our [developers site](https://developers.google.com/mobile-ads-sdk/) for documentation on using the SDK, and join the developer community on [our forum](https://groups.google.com/forum/#!forum/google-admob-ads-sdk).

Suggesting improvements
-------------------------
To file bugs, make feature requests, or to suggest other improvements, please use [github's issue tracker](https://github.com/googleads/googleads-mobile-android-adcatalog/issues).

License
--------
[Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html)

Contributing
-------------
Pull requests are welcome! Please sign [this Google Code contributor agreement](https://developers.google.com/open-source/cla/individual?csw=1) before submitting.
