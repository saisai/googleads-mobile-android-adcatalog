package com.google.ad.catalog;

import static com.google.ad.catalog.AdCatalogUtils.LOG_TAG;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * This creates a common AdListener that can be used or extended
 * for different uses. Uses logging and toasting for each action.
 */
public class LogAndToastAdListener extends AdListener {
  
  Activity activity;
  
  public LogAndToastAdListener(Activity activity) {
    this.activity = activity;
  }
  
  /** Called when an ad is clicked and about to return to the application. */
  @Override
  public void onAdClosed() {
    Log.d(LOG_TAG, "onAdClosed");
    Toast.makeText(activity, "onAdClosed", Toast.LENGTH_SHORT).show();
  }

  /** Called when an ad failed to load. */
  @Override
  public void onAdFailedToLoad(int error) {
    String message = "onAdFailedToLoad: " + getErrorReason(error);
    Log.d(LOG_TAG, message);
    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
  }

  /**
   * Called when an ad is clicked and going to start a new Activity that will
   * leave the application (e.g. breaking out to the Browser or Maps
   * application).
   */
  @Override
  public void onAdLeftApplication() {
    Log.d(LOG_TAG, "onAdLeftApplication");
    Toast.makeText(activity, "onAdLeftApplication", Toast.LENGTH_SHORT).show();
  }

  /**
   * Called when an Activity is created in front of the app (e.g. an
   * interstitial is shown, or an ad is clicked and launches a new Activity).
   */
  @Override
  public void onAdOpened() {
    Log.d(LOG_TAG, "onAdOpened");
    Toast.makeText(activity, "onAdOpened", Toast.LENGTH_SHORT).show();
  }

  /** Called when an ad is loaded. */
  @Override
  public void onAdLoaded() {
    Log.d(LOG_TAG, "onAdLoaded");
    Toast.makeText(activity, "onAdLoaded", Toast.LENGTH_SHORT).show();
  }

  private String getErrorReason(int errorCode) {
    String errorReason = "";
    switch(errorCode) {
      case AdRequest.ERROR_CODE_INTERNAL_ERROR:
        errorReason = "Internal error";
        break;
      case AdRequest.ERROR_CODE_INVALID_REQUEST:
        errorReason = "Invalid request";
        break;
      case AdRequest.ERROR_CODE_NETWORK_ERROR:
        errorReason = "Network Error";
        break;
      case AdRequest.ERROR_CODE_NO_FILL:
        errorReason = "No fill";
        break;
    }
    return errorReason;
  }
  
}
