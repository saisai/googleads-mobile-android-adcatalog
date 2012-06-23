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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * This activity features interstitial ads before going to a youtube link.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class VideoPreroll extends Activity implements OnClickListener {
  private InterstitialAd interstitial;
  private VideoHandler videoHandler;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.videopreroll);

    videoHandler = new VideoHandler();
    interstitial = new InterstitialAd(this, Constants.getAdmobId(this));
    interstitial.setAdListener(videoHandler);
    interstitial.loadAd(AdCatalogUtils.createAdRequest());
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    switch (id) {
      case R.id.earthquake:
        videoHandler.setLink(Constants.EARTHQUAKE_LINK);
        break; 
      case R.id.fillInTheBlanks:
        videoHandler.setLink(Constants.FILL_IN_THE_BLANKS_LINK);
        break;
      case R.id.packageTracking:
        videoHandler.setLink(Constants.PACKAGE_TRACKING_LINK);
        break;
    }
    if (interstitial.isReady()) {
      interstitial.show();
    } else {
      interstitial.loadAd(AdCatalogUtils.createAdRequest());
      videoHandler.playVideo();
    }
  }

  /**
   * This handler supports playing a video after an interstitial is displayed.
   * 
   * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
   */
  class VideoHandler implements AdListener {
    private String youtubeLink;

    /** Instantiates a new VideoHandler object. */
    public VideoHandler() {
      youtubeLink = "";
    }

    /** Sets the YouTube link. */
    public void setLink(String link) {
      youtubeLink = link;
    }

    /** Plays the video. */
    public void playVideo() {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink)));
    }

    @Override
    public void onReceiveAd(Ad ad) {
      Log.d("Video_Handler", "I received an ad");
    }

    @Override
    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
      Log.d("Video_Handler", "I failed to receive an ad");
    }

    @Override
    public void onPresentScreen(Ad ad) {
      Log.d("Video_Handler", "Presenting screen");
    }

    @Override
    public void onDismissScreen(Ad ad) {
      Log.d("Video_Handler", "Dismissing screen");
      playVideo();
    }

    @Override
    public void onLeaveApplication(Ad ad) {
      Log.d("Video_Handler", "Leaving application");
    }
  }
}
