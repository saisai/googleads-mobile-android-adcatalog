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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * This activity demonstrates displaying an interstitial in between page
 * swipes.  Use case could be displaying a gallery of photos.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class PageSwipe extends Activity implements OnTouchListener {
  private InterstitialAd interstitial;
  private float downXValue;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pageswipe);

    LinearLayout layout = (LinearLayout) findViewById(R.id.swipe_main);
    layout.setOnTouchListener(this);

    interstitial = new InterstitialAd(this);
    interstitial.setAdUnitId(Constants.getAdmobId(this));
    interstitial.setAdListener(new LogAndToastAdListener(this));
    interstitial.loadAd(AdCatalogUtils.createAdRequest());
  }

  /** Used to detect which direction a user swiped.  */
  @Override
  public boolean onTouch(View view, MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        downXValue = event.getX();
        break;
      case MotionEvent.ACTION_UP:
        float currentX = event.getX();
        ViewFlipper viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        if (downXValue < currentX) {
          viewFlipper.setInAnimation(view.getContext(), R.anim.push_right_in);
          viewFlipper.setOutAnimation(view.getContext(), R.anim.push_right_out);
          viewFlipper.showPrevious();
          showInterstitial();
        } else if (downXValue > currentX) {
          viewFlipper.setInAnimation(view.getContext(), R.anim.push_left_in);
          viewFlipper.setOutAnimation(view.getContext(), R.anim.push_left_out);
          viewFlipper.showNext();
          showInterstitial();
        }
        break;
    }
    return true;
  }

  /** Shows the interstitial, or loads a new interstitial if one is not ready. */
  public void showInterstitial() {
    if (interstitial.isLoaded()) {
      interstitial.show();
    } else {
      interstitial.loadAd(AdCatalogUtils.createAdRequest());
    }
  }

}
