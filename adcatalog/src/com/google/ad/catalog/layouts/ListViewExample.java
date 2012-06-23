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

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Example of a ListView with embedded AdMob ads.
 *
 * @author api.rajpara@gmail.com (Raj Parameswaran)
 */
public class ListViewExample extends ListActivity {
  public static final String[] LIST_ITEMS = {
    "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7",
    "Item 8", "Item 9", "Item 10", "Item 11", "Item 12", "Item 13", "Item 14",
    "Item 15", "Item 16", "Item 17", "Item 18", "Item 19", "Item 20", "Item 21"
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Set list adapter to be the custom ListViewExampleListAdapter.
    ListViewExampleListAdapter adapter = new ListViewExampleListAdapter(
        this, new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, LIST_ITEMS));
    setListAdapter(adapter);
  }

  @Override
  protected void onListItemClick(ListView listView, View view, int position, long id) {
    String item = (String) getListAdapter().getItem(position);
    // Clicking an item should bring up toast, different from clicking ad.
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
  }
}
