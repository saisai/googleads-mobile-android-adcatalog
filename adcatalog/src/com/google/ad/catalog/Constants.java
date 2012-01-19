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

import android.content.Context;

/**
 * This class holds constant values for publisher ID and preferences keys.
 * 
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public final class Constants {
  /** The AdMob publisher ID. */
  private static String admobId = null;

  /** Preferences keys. */
  public static final String PREFS_NAME = "AdExamplesPrefs";
  public static final String PREFS_SPLASH_KEY = "loadSplashInterstitial";

  /** Video links. */
  public static final String EARTHQUAKE_LINK = "http://www.youtube.com/watch?v=SZ-ZRhxAINA";
  public static final String FILL_IN_THE_BLANKS_LINK = 
      "http://www.youtube.com/watch?v=24Ri7yZhRwM&amp;NR=1";
  public static final String PACKAGE_TRACKING_LINK = "http://www.youtube.com/watch?v=jb5crXH2Cb8";

  /** Private constructor so that the class cannot be instantiated. */
  private Constants() {
    // This will never be called.
  }
  
  /** Gets the AdMob Id from the context resources. */
  public static String getAdmobId(Context context) {
    if (admobId == null) {
      admobId = context.getResources().getString(R.string.admob_id);
    }
    return admobId;
  }
}
