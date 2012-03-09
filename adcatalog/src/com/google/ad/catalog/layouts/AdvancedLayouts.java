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

import com.google.ad.catalog.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Menu system for different ad layouts.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class AdvancedLayouts extends Activity implements OnClickListener {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.advancedlayouts);
  }

  /** Handles the on click events for each button. */
  @Override
  public void onClick(View view) {
    final int id = view.getId();
    Intent intent = null;
    switch (id) {
      // Uncomment corresponding intent when implementing an example.
      case R.id.tabbedView:
        intent = new Intent(AdvancedLayouts.this, TabbedViewExample.class);
        break;
      case R.id.listView:
        intent = new Intent(AdvancedLayouts.this, ListViewExample.class);
        break;
      case R.id.openGLView:
        intent = new Intent(AdvancedLayouts.this, OpenGLViewExample.class);
        break;
      case R.id.scrollView:
        intent = new Intent(AdvancedLayouts.this, ScrollViewExample.class);
        break;
    }
    if (intent != null) {
      startActivity(intent);
    }
  }
}
