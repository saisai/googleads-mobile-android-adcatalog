// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.ad.catalog;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ToggleButton;

/**
 * Menu system for different methods of displaying interstitials. Setting the splash
 * toggle will load an interstitial next time the application is launched.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class Interstitials extends Activity implements OnClickListener, AdListener {
  private InterstitialAd interstitial;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.interstitials);

    interstitial = new InterstitialAd(this, Constants.getAdmobId(this));
    interstitial.setAdListener(this);

    final ToggleButton splashToggleButton = (ToggleButton) findViewById(R.id.toggleSplash);
    if (splashToggleButton != null) {
      // Set the default toggle value from the preferences.
      SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
      splashToggleButton.setChecked(settings.getBoolean(Constants.PREFS_SPLASH_KEY, false));
    }
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    Intent intent;
    switch (id) {
      // Basic button click - load a basic interstitial.
      case R.id.basic:
        interstitial.loadAd(AdCatalogUtils.createAdRequest());
        break;
      // Game Levels button click - go to Game Levels Activity.
      case R.id.gameLevels:
        intent = new Intent(Interstitials.this, GameLevels.class);
        startActivity(intent);
        break;
      // Video Preroll button click - go to Video Preroll Activity.
      case R.id.videoPreroll:
        intent = new Intent(Interstitials.this, VideoPreroll.class);
        startActivity(intent);
        break;
      // Page Swipe button click - go to Page Swipe Activity.
      case R.id.pageSwipe:
        intent = new Intent(Interstitials.this, PageSwipe.class);
        startActivity(intent);
        break;
      // Splash button click - toggle preference to receive splash screen interstitial.
      case R.id.toggleSplash:
        ToggleButton splashToggleButton = (ToggleButton) view;
        boolean isChecked = splashToggleButton.isChecked();
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        settings.edit().putBoolean(Constants.PREFS_SPLASH_KEY, isChecked).commit();
        Log.i("Interstitials_Class", "Set splash preference to " + isChecked);
        break;
    }
  }

  @Override
  public void onReceiveAd(Ad ad) {
    Log.d("Interstitials_Class", "I received an ad");
    if (interstitial.isReady()) {
      interstitial.show();
    }
  }

  @Override
  public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
    Log.d("Interstitials_Class", "I failed to receive an ad");
  }

  @Override
  public void onPresentScreen(Ad ad) {
    Log.d("Interstitials_Class", "Presenting screen");
  }

  @Override
  public void onDismissScreen(Ad ad) {
    Log.d("Interstitials_Class", "Dismissing screen");
  }

  @Override
  public void onLeaveApplication(Ad ad) {
    Log.d("Interstitials_Class", "Leaving application");
  }
}
