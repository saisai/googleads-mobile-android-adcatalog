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

import com.google.ad.catalog.layouts.AdvancedLayouts;
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
import android.widget.Button;
import android.widget.ToggleButton;

/**
 * This is the main activity that loads on when the app is first run.  The user
 * will be provided with a menu to view banners or interstitials.  If the user
 * has previously set the splash toggle button, an interstitial will be loaded
 * when this activity is created.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class AdCatalog extends Activity implements OnClickListener, AdListener {
  private InterstitialAd interstitial;
  private Button bannerButton;
  private Button interstitialButton;
  public static boolean isTestMode;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // Default to test mode on.
    isTestMode = true;
    ToggleButton testModeToggle = (ToggleButton) findViewById(R.id.toggleTestMode);
    testModeToggle.setChecked(isTestMode);

    // Load interstitial if splash interstitial preference was set.
    SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
    boolean loadSplashInterstitial = settings.getBoolean(Constants.PREFS_SPLASH_KEY, false);
    if (loadSplashInterstitial) {
      interstitial = new InterstitialAd(this, Constants.getAdmobId(this));
      interstitial.setAdListener(this);
      interstitial.loadAd(AdCatalogUtils.createAdRequest());
    }
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    Intent intent = null;
    switch (id) {
      // Banners button click - go to Banners Activity.
      case R.id.Banners:
        intent = new Intent(AdCatalog.this, Banners.class);
        break;
      // Interstitials button click - go to Interstitials Activity.
      case R.id.Interstitials:
        intent = new Intent(AdCatalog.this, Interstitials.class);
        break;
      // Advanced Layouts button click - go to Advanced Layouts Activity.
      case R.id.AdvancedLayouts:
        intent = new Intent(AdCatalog.this, AdvancedLayouts.class);
        break;
      // Test Ads toggle click - change Test Mode preference.
      case R.id.toggleTestMode:
        isTestMode = !isTestMode;
        Log.d("AdCatalog", "Test mode: " + isTestMode);
        break;
    }
    if (intent != null) {
      startActivity(intent);
    }
  }

  @Override
  public void onReceiveAd(Ad ad) {
    Log.d("AdExamples_Class", "I received an ad");
    interstitial.show();
  }

  @Override
  public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
    Log.d("AdExamples_Class", "I failed to receive an ad");
  }

  @Override
  public void onPresentScreen(Ad ad) {
    Log.d("AdExamples_Class", "Presenting screen");
    // Deactivate buttons so interstitial returns before they can be clicked.
    if (bannerButton != null) {
      bannerButton.setEnabled(false);
    }
    if (interstitialButton != null) {
      interstitialButton.setEnabled(false);
    }
  }

  @Override
  public void onDismissScreen(Ad ad) {
    Log.d("AdExamples_Class", "Dismissing screen");
    // Reactivate buttons after interstitial is dismissed.
    if (bannerButton != null) {
      bannerButton.setEnabled(true);
    }
    if (interstitialButton != null) {
      interstitialButton.setEnabled(true);
    }
  }

  @Override
  public void onLeaveApplication(Ad ad) {
    Log.d("AdExamples_Class", "Leaving application");
  }
}
