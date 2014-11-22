package com.google.ad.catalog.layouts;

import com.google.ad.catalog.R;
import com.google.ad.catalog.VideoExample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Menu system for different video ad types.
 */
public class Video extends Activity implements OnItemClickListener {
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.video);
    ListView adList = (ListView) findViewById(R.id.adList);
    ArrayAdapter<String> adListAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1,
        getResources().getStringArray(R.array.video_ad_list));
    adList.setAdapter(adListAdapter);
    adList.setOnItemClickListener(this);
  }

  /** Handles the click event for the items in the list. */
  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    Intent intent = new Intent(Video.this, VideoExample.class);   
    intent.putExtra("ad_tag_index", position);
    startActivity(intent);
  }
}
