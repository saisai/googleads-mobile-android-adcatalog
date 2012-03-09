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
import com.google.ad.catalog.Constants;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Custom list adapter to embed AdMob ads in a ListView at some specified
 * frequency.
 *
 * @author api.rajpara@gmail.com (Raj Parameswaran)
 */
public class ListViewExampleListAdapter extends BaseAdapter implements AdListener {
  private static final String LOGTAG = "ListViewExampleListAdapter";

  // Frequency of embedded AdMob ads within the ListView (every n list items).
  private static final int FREQUENCY_ADS = 10;
  private final Activity activity;
  private final BaseAdapter delegate;

  public ListViewExampleListAdapter(Activity activity, BaseAdapter delegate) {
    this.activity = activity;
    this.delegate = delegate;
  }

  @Override
  public int getCount() {
    // Total count includes list items and ads.
    return (int) (delegate.getCount() + Math.ceil(delegate.getCount() / FREQUENCY_ADS) + 1);
  }

  @Override
  public Object getItem(int position) {
    return delegate.getItem(getOffsetPosition(position));
  }

  @Override
  public long getItemId(int position) {
    return delegate.getItemId(getOffsetPosition(position));
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if ((position % FREQUENCY_ADS) == 0) {
      if (convertView instanceof AdView) {
        Log.d(LOGTAG, "I am reusing an ad");
        return convertView;
      } else {
        Log.d(LOGTAG, "I am creating a new ad");
        AdView adView = new AdView(activity, AdSize.BANNER, Constants.getAdmobId(activity));

        // Disable focus for AdView and its subviews so you can't get to it
        // with the trackpad.
        for (int i = 0; i < adView.getChildCount(); i++) {
          adView.getChildAt(i).setFocusable(false);
        }
        adView.setFocusable(false);

        // Convert the default layout parameters so that they play nice with
        // ListView.
        float density = activity.getResources().getDisplayMetrics().density;
        int height = Math.round(AdSize.BANNER.getHeight() * density);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
            AbsListView.LayoutParams.FILL_PARENT,
            height);
        adView.setLayoutParams(params);

        AdRequest adRequest = new AdRequest();
        if (AdCatalog.isTestMode) {
          adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        }
        adView.loadAd(adRequest);
        return adView;
      }
    } else {
      return delegate.getView(getOffsetPosition(position), convertView, parent);
    }
  }

  @Override
  public int getViewTypeCount() {
    return delegate.getViewTypeCount() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if ((position % FREQUENCY_ADS) == 0) {
      return delegate.getViewTypeCount();
    } else {
      return delegate.getItemViewType(getOffsetPosition(position));
    }
  }

  @Override
  public boolean areAllItemsEnabled() {
    return false;
  }

  @Override
  public boolean isEnabled(int position) {
      return ((position % FREQUENCY_ADS) != 0) &&
               delegate.isEnabled(getOffsetPosition(position));
  }

  @Override
  public void onDismissScreen(Ad arg0) {
    Log.d(LOGTAG, "Dismissing screen");
  }

  @Override
  public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
    Log.d(LOGTAG, "I failed to receive an ad");
  }

  @Override
  public void onLeaveApplication(Ad arg0) {
    Log.d(LOGTAG, "Leaving application");
  }

  @Override
  public void onPresentScreen(Ad arg0) {
    Log.d(LOGTAG, "Presenting screen");
  }

  @Override
  public void onReceiveAd(Ad arg0) {
    Log.d(LOGTAG, "I received an ad");
  }

  private int getOffsetPosition(int position) {
    return (position - (int) Math.ceil(position / FREQUENCY_ADS) - 1);
  }
}
