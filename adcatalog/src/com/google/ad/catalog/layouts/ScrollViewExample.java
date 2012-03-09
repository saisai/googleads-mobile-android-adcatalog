// Copyright 2012 Google Inc. All Rights Reserved.
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

package com.google.ad.catalog.layouts;

import com.google.ad.catalog.AdCatalog;
import com.google.ad.catalog.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Example of a ScrollView with an AdMob banner.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class ScrollViewExample extends Activity implements AdListener {
  private static final String LOGTAG = "ScrollViewExample";
  private AdView adView;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.scrollviewexample);

    adView = (AdView) findViewById(R.id.adView);
    AdRequest adRequestBanner = new AdRequest();
    // Set testing according to our preference.
    if (AdCatalog.isTestMode) {
      adRequestBanner.addTestDevice(AdRequest.TEST_EMULATOR);
    }
    adView.setAdListener(this);
    adView.loadAd(adRequestBanner);
  }

  /** Overwrite the onDestroy() method to dispose of banners first. */
  @Override
  public void onDestroy() {
    if (adView != null) {
      adView.destroy();
    }
    super.onDestroy();
  }

  @Override
  public void onReceiveAd(Ad ad) {
    Log.d(LOGTAG, "I received an ad");
  }

  @Override
  public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
    Log.d(LOGTAG, "I failed to receive an ad");
  }

  @Override
  public void onPresentScreen(Ad ad) {
    Log.d(LOGTAG, "Presenting screen");
  }

  @Override
  public void onDismissScreen(Ad ad) {
    Log.d(LOGTAG, "Dismissing screen");
  }

  @Override
  public void onLeaveApplication(Ad ad) {
    Log.d(LOGTAG, "Leaving application");
  }
}
