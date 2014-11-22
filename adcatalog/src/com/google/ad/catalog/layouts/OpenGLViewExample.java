// Copyright 2012, Google Inc. All Rights Reserved.
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

import com.google.ad.catalog.AdCatalogUtils;
import com.google.ad.catalog.R;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

/**
 * Example of an OpenGL view with an AdMob banner.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class OpenGLViewExample extends Activity {
  private GLSurfaceView glSurfaceView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.openglviewexample);

    // Load the AdView with a request.
    AdView adView = (AdView) findViewById(R.id.openGLAdView);
    adView.loadAd(AdCatalogUtils.createAdRequest());

    // Initialize the GLSurfaceView and add it to the layout above the AdView.
    this.glSurfaceView = new GLSurfaceView(this);
    this.glSurfaceView.setRenderer(new CubeRenderer(true));
    RelativeLayout layout = (RelativeLayout) findViewById(R.id.openGLLayout);
    RelativeLayout.LayoutParams params =
        new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                        LayoutParams.MATCH_PARENT);
    params.addRule(RelativeLayout.ABOVE, R.id.openGLAdView);
    layout.addView(this.glSurfaceView, params);
  }

  @Override
  protected void onResume() {
    // Ideally a game should implement onResume() and onPause()
    // to take appropriate action when the activity looses focus.
    super.onResume();
    this.glSurfaceView.onResume();
  }

  @Override
  protected void onPause() {
    // Ideally a game should implement onResume() and onPause()
    // to take appropriate action when the activity looses focus.
    super.onPause();
    this.glSurfaceView.onPause();
  }
}
