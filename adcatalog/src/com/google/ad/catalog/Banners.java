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
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Showcases the different banner formats on both phones and tablets.  Phones
 * will show a standard banner, a medium rectangle, and a smart banner.  Tablets
 * will show full size banners and leaderboard ads in addition to the three
 * formats above.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class Banners extends Activity implements OnClickListener, AdListener {
  private AdView adViewBanner;
  private AdView adViewRect;
  private AdView adViewSmartBanner;
  private AdView adViewFullSizeBanner;
  private AdView adViewLeaderboard;
  /** Used to hide the last AdView that was selected. */
  private AdView lastVisibleAdView = null;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.banners);

    // Set the ad listener and load an load for each AdView.
    AdRequest adRequest = AdCatalogUtils.createAdRequest();
    adViewBanner = (AdView) findViewById(R.id.adViewBanner);
    adViewBanner.setAdListener(this);
    adViewBanner.loadAd(adRequest);
    adViewSmartBanner = (AdView) findViewById(R.id.adViewSmart);
    adViewSmartBanner.setAdListener(this);
    adViewSmartBanner.loadAd(adRequest);

    if (AdCatalogUtils.isExtraLargeScreen(this)) {
      adViewRect = (AdView) findViewById(R.id.adViewRect);
      adViewRect.setAdListener(this);
      adViewRect.loadAd(adRequest);
      adViewFullSizeBanner = (AdView) findViewById(R.id.adViewFullSize);
      adViewFullSizeBanner.setAdListener(this);
      adViewFullSizeBanner.loadAd(adRequest);
      adViewLeaderboard = (AdView) findViewById(R.id.adViewLeaderboard);
      adViewLeaderboard.setAdListener(this);
      adViewLeaderboard.loadAd(adRequest);
    }
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    if (lastVisibleAdView != null) {
      lastVisibleAdView.setVisibility(View.GONE);
    }
    switch (view.getId()) {
      case R.id.standardBanner:
        adViewBanner.setVisibility(View.VISIBLE);
        lastVisibleAdView = adViewBanner;
        break;
      case R.id.mediumRectangle:
        adViewRect.setVisibility(View.VISIBLE);
        lastVisibleAdView = adViewRect;
        break;
      case R.id.smartBanner:
        adViewSmartBanner.setVisibility(View.VISIBLE);
        lastVisibleAdView = adViewSmartBanner;
        break;
      case R.id.fullSize:
        adViewFullSizeBanner.setVisibility(View.VISIBLE);
        lastVisibleAdView = adViewFullSizeBanner;
        break;
      case R.id.leaderboard:
        adViewLeaderboard.setVisibility(View.VISIBLE);
        lastVisibleAdView = adViewLeaderboard;
        break;
    }
  }

  /** Overwrite the onDestroy() method to dispose of the banners first. */
  @Override
  public void onDestroy() {
    if (adViewBanner != null) {
      adViewBanner.destroy();
    }
    if (adViewRect != null) {
      adViewRect.destroy();
    }
    if (adViewSmartBanner != null) {
      adViewSmartBanner.destroy();
    }
    if (adViewFullSizeBanner != null) {
      adViewFullSizeBanner.destroy();
    }
    if (adViewLeaderboard != null) {
      adViewLeaderboard.destroy();
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
