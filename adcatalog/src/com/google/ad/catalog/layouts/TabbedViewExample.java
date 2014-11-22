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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

import java.util.HashMap;

/**
 * Example of including AdMob ads into a Tabbed View, which references the v4
 * support library for the fragment API instead of the deprecated TabActivity
 * class.  These library classes are included in android-support-v4.jar, which
 * is a part of the Android Compatibility Package found in the Extras section
 * of the Android SDK Manager.
 *
 * @author api.eleichtenschl@gmail.com (Eric Leichtenschlag)
 */
public class TabbedViewExample extends FragmentActivity {
  private TabHost tabHost;
  private TabManager tabManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tabviewexample);
    this.tabHost = (TabHost) findViewById(android.R.id.tabhost);
    this.tabHost.setup();

    // Initialize the tabs.
    this.tabManager = new TabManager(this, this.tabHost, R.id.realtabcontent);
    Bundle args = new Bundle();
    args.putInt("layoutResource", R.layout.tabblue);
    this.tabManager.addTab(this.tabHost.newTabSpec("Tab1").setIndicator("Tab 1"),
        TabFragment.class, args);
    args = new Bundle();
    args.putInt("layoutResource", R.layout.tabred);
    this.tabManager.addTab(this.tabHost.newTabSpec("Tab2").setIndicator("Tab 2"),
        TabFragment.class, args);
    args = new Bundle();
    args.putInt("layoutResource", R.layout.tabgreen);
    this.tabManager.addTab(this.tabHost.newTabSpec("Tab3").setIndicator("Tab 3"),
        TabFragment.class, args);
    // Set the current tab if one exists.
    if (savedInstanceState != null) {
      this.tabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
    }
    AdCatalogUtils.createAdRequest();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("tab", this.tabHost.getCurrentTabTag());
  }

  /**
   * This is a helper class that implements a generic mechanism for
   * associating fragments with the tabs in a tab host.  It relies on a
   * trick.  Normally a tab host has a simple API for supplying a View or
   * Intent that each tab will show.  This is not sufficient for switching
   * between fragments.  So instead we make the content part of the tab host
   * 0dp high (it is not shown) and the TabManager supplies its own dummy
   * view to show as the tab content.  It listens to changes in tabs, and takes
   * care of switch to the correct fragment shown in a separate content area
   * whenever the selected tab changes.
   */
  public static class TabManager implements OnTabChangeListener {
    private final FragmentActivity activity;
    private final TabHost tabHost;
    private final int containerId;
    private final HashMap<String, TabInfo> tabInfoMap = new HashMap<String, TabInfo>();
    private TabInfo currentTab;

    /** Represents the information of a tab. */
    static final class TabInfo {
      private final String tag;
      private final Class<?> clss;
      private final Bundle args;
      private Fragment fragment;

      TabInfo(String tag, Class<?> clss, Bundle args) {
        this.tag = tag;
        this.clss = clss;
        this.args = args;
      }
    }

    /** Creates an empty tab. */
    static class DummyTabFactory implements TabContentFactory {
      private final Context context;

      public DummyTabFactory(Context context) {
        this.context = context;
      }

      @Override
      public View createTabContent(String tag) {
        View view = new View(this.context);
        view.setMinimumWidth(0);
        view.setMinimumHeight(0);
        return view;
      }
    }

    public TabManager(FragmentActivity activity, TabHost tabHost, int containerId) {
      this.activity = activity;
      this.tabHost = tabHost;
      this.containerId = containerId;
      this.tabHost.setOnTabChangedListener(this);
    }

    public void addTab(TabSpec tabSpec, Class<?> clss, Bundle args) {
      tabSpec.setContent(new DummyTabFactory(this.activity));
      String tag = tabSpec.getTag();
      TabInfo tabInfo = new TabInfo(tag, clss, args);

      // Check to see if there is already a fragment for this tab.  If so,
      // deactivate it, because the initial state is that a tab isn't shown.
      tabInfo.fragment = this.activity.getSupportFragmentManager().findFragmentByTag(tag);
      if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
        FragmentTransaction transaction =
            this.activity.getSupportFragmentManager().beginTransaction();
        transaction.detach(tabInfo.fragment).commit();
      }
      this.tabInfoMap.put(tag, tabInfo);
      this.tabHost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String tabId) {
      TabInfo newTab = this.tabInfoMap.get(tabId);
      if (this.currentTab != newTab) {
        FragmentTransaction transaction =
            this.activity.getSupportFragmentManager().beginTransaction();
        if (this.currentTab != null && this.currentTab.fragment != null) {
          transaction.detach(this.currentTab.fragment);
        }
        if (newTab != null) {
          if (newTab.fragment == null) {
            newTab.fragment = Fragment.instantiate(this.activity,
                newTab.clss.getName(), newTab.args);
            transaction.add(this.containerId, newTab.fragment, newTab.tag);
          } else {
            transaction.attach(newTab.fragment);
          }
        }
        this.currentTab = newTab;
        transaction.commit();
        this.activity.getSupportFragmentManager().executePendingTransactions();
      }
    }
  }

  /** Fragment for a tab in the Tab View example. */
  public static class TabFragment extends Fragment {
    private int layoutResource;

    static TabFragment newInstance(int resource) {
      TabFragment fragment = new TabFragment();
      // Supply the layout resource input as an argument.
      Bundle args = new Bundle();
      args.putInt("layoutResource", resource);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      // Default to blue tab, if no resource is provided.
      this.layoutResource = R.layout.tabblue;
      Bundle args = getArguments();
      if (args != null && args.getInt("layoutResource") != 0) {
        this.layoutResource = args.getInt("layoutResource");
      }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
      return inflater.inflate(this.layoutResource, container, false);
    }
  }
}
