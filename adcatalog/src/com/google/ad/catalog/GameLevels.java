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

import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

/**
 * This activity shows an example of how to display an interstitial in between 
 * game levels.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class GameLevels extends Activity implements OnClickListener {
  private InterstitialAd interstitial;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.gamelevels);

    // Start loading the interstitial.
    interstitial = new InterstitialAd(this);
    interstitial.setAdUnitId(Constants.getAdmobId(this));
    interstitial.setAdListener(new LogAndToastAdListener(this) {
      @Override
      public void onAdClosed() {
        super.onAdClosed();
        final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.levelFlipper);
        viewFlipper.showNext();
      }
    });
    interstitial.loadAd(AdCatalogUtils.createAdRequest());
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    switch (id) {
      case R.id.gameNextButton:
        if (interstitial.isLoaded()) {
          interstitial.show();
        } else {
          interstitial.loadAd(AdCatalogUtils.createAdRequest());
          final ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.levelFlipper);
          viewFlipper.showNext();
        }
        break;
    }
  }
}
