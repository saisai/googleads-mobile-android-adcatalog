// Copyright 2011, Google Inc. All Rights Reserved.
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
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Showcases a standard banner ad and a medium rectangle ad.
 * 
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class Banners extends Activity implements OnClickListener, AdListener {
  private AdView adViewBanner;
  private AdView adViewRect;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.banners);

    // Get the adView banner.
    adViewBanner = (AdView) findViewById(R.id.adViewBanner);
    AdRequest adRequestBanner = new AdRequest();
    // Set testing according to our preference.
    if (AdCatalog.isTestMode) {
      adRequestBanner.addTestDevice(AdRequest.TEST_EMULATOR);
    }
    adViewBanner.setAdListener(this);
    // Initiate a generic request to load it with an ad.
    adViewBanner.loadAd(adRequestBanner);

    // Do the same for the medium rectangle ad.
    adViewRect = (AdView) findViewById(R.id.adViewRect);
    AdRequest adRequestRect = new AdRequest();
    if (AdCatalog.isTestMode) {
      adRequestRect.addTestDevice(AdRequest.TEST_EMULATOR);
    }
    adViewRect.setAdListener(this);
    adViewRect.loadAd(adRequestRect);
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    switch (id) {
      // Make the standard banner visible on click.
      case R.id.standardBanner:
        adViewRect.setVisibility(View.GONE);
        adViewBanner.setVisibility(View.VISIBLE);
        break;
      // Make the medium rectangle visible on click.
      case R.id.mediumRectangle:
        adViewBanner.setVisibility(View.GONE);
        adViewRect.setVisibility(View.VISIBLE);
        break;
    }
  }

  /** Overwrite the onDestroy() method to dispose of banners first. */
  @Override
  public void onDestroy() {
    if (adViewBanner != null) {
      adViewBanner.destroy();
    }
    if (adViewRect != null) {
      adViewRect.destroy();
    }
    super.onDestroy();
  }

  @Override
  public void onReceiveAd(Ad ad) {
    Log.d("Banners_class", "I received an ad");
  }

  @Override
  public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
    Log.d("Banners_class", "I failed to receive an ad");
  }

  @Override
  public void onPresentScreen(Ad ad) {
    Log.d("Banners_class", "Presenting screen");
  }

  @Override
  public void onDismissScreen(Ad ad) {
    Log.d("Banners_class", "Dismissing screen");
  }

  @Override
  public void onLeaveApplication(Ad ad) {
    Log.d("Banners_class", "Leaving application");
  }
}
