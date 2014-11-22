package com.google.ad.catalog;

import com.google.ad.catalog.TrackingVideoView.CompleteCallback;
import com.google.ads.interactivemedia.v3.api.AdDisplayContainer;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.AdsLoader;
import com.google.ads.interactivemedia.v3.api.AdsLoader.AdsLoadedListener;
import com.google.ads.interactivemedia.v3.api.AdsManager;
import com.google.ads.interactivemedia.v3.api.AdsManagerLoadedEvent;
import com.google.ads.interactivemedia.v3.api.AdsRequest;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.api.ImaSdkFactory;
import com.google.ads.interactivemedia.v3.api.ImaSdkSettings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity for the IMA SDK video example.
 */
public class VideoExample extends Activity implements CompleteCallback, 
    AdErrorListener, AdEventListener, AdsLoadedListener {
  
  private static final String CONTENT_URL =
      "http://rmcdn.2mdn.net/MotifFiles/html/1248596/" + "android_1330378998288.mp4";
  
  private int tagIndex;
  private String[] tags;
  private FrameLayout videoHolder;
  private VideoPlayer videoPlayer;
  private ViewGroup companionView;
  private ScrollView logScroll;
  private TextView log;
  private AdsLoader adsLoader;
  private ImaSdkFactory sdkFactory;
  private ImaSdkSettings sdkSettings;

  private boolean contentStarted;

  private boolean isAdStarted;

  private boolean isAdPlaying;

  private AdDisplayContainer container;

  private AdsManager adsManager;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.videoexample);
    tagIndex = getIntent().getIntExtra("ad_tag_index", 0);
    tags = getResources().getStringArray(R.array.video_ad_tags);
    initUi();
    sdkFactory = ImaSdkFactory.getInstance();
    createAdsLoader();
    requestAd();
  }

  protected void initUi() {
    videoHolder = (FrameLayout) findViewById(R.id.videoHolder);
    
    videoPlayer = new VideoPlayer(this);
    videoPlayer.setCompletionCallback(this);
      
    videoHolder.addView(videoPlayer);

    companionView = (ViewGroup) findViewById(R.id.companionFrame);
    logScroll = (ScrollView) findViewById(R.id.scroll);
    log = (TextView) findViewById(R.id.log);
  }
  
  protected void createAdsLoader() {
    adsLoader = sdkFactory.createAdsLoader(this, getImaSdkSettings());
    adsLoader.addAdErrorListener(this);
    adsLoader.addAdsLoadedListener(this);
  }

  protected ImaSdkSettings getImaSdkSettings() {
    if (sdkSettings == null) {
      sdkSettings = sdkFactory.createImaSdkSettings();
    }
    return sdkSettings;
  }

  protected void playVideo() {
    log("Playing video");
    videoPlayer.playContent(CONTENT_URL);
    contentStarted = true;
  }

  protected void pauseResumeAd() {
    if (isAdStarted) {
      if (isAdPlaying) {
        log("Pausing video");
        videoPlayer.pauseAd();
      } else {
        log("Resuming video");
        videoPlayer.resumeAd();
      }
    }
  }

  protected AdsRequest buildAdsRequest() {
    container = sdkFactory.createAdDisplayContainer();
    container.setPlayer(videoPlayer);
    container.setAdContainer(videoPlayer.getUiContainer());
    log("Requesting ads");
    AdsRequest request = sdkFactory.createAdsRequest();
    request.setAdTagUrl(tags[tagIndex]);

    ArrayList<CompanionAdSlot> companionAdSlots = new ArrayList<CompanionAdSlot>();

    CompanionAdSlot companionAdSlot = sdkFactory.createCompanionAdSlot();
    companionAdSlot.setContainer(companionView);
    companionAdSlot.setSize(300, 50);
    companionAdSlots.add(companionAdSlot);

    container.setCompanionSlots(companionAdSlots);
    request.setAdDisplayContainer(container);
    return request;
  }

  protected void requestAd() {
    adsLoader.requestAds(buildAdsRequest());
  }

  @Override
  public void onAdError(AdErrorEvent event) {
    log(event.getError().getMessage() + "\n");
  }

  @Override
  public void onAdsManagerLoaded(AdsManagerLoadedEvent event) {
    log("Ads loaded!");
    adsManager = event.getAdsManager();
    adsManager.addAdErrorListener(this);
    adsManager.addAdEventListener(this);
    log("Calling init.");
    adsManager.init();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (videoPlayer != null) {
      videoPlayer.savePosition();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (videoPlayer != null) {
      videoPlayer.restorePosition();
    }
  }

  @Override
  public void onComplete() {
    if (videoPlayer.isContentPlaying()) {
      adsLoader.contentComplete();
    }
  }

  @Override
  public void onAdEvent(AdEvent event) {
    log("Event:" + event.getType());

    switch (event.getType()) {
      case LOADED:
        log("Calling start.");
        adsManager.start();
        break;
      case CONTENT_PAUSE_REQUESTED:
        if (contentStarted) {
          videoPlayer.pauseContent();
        }
        break;
      case CONTENT_RESUME_REQUESTED:
        if (contentStarted) {
          videoPlayer.resumeContent();
        } else {
          playVideo();
        }
        break;
      case STARTED:
        isAdStarted = true;
        isAdPlaying = true;
        break;
      case COMPLETED:
        isAdStarted = false;
        isAdPlaying = false;
        break;
      case ALL_ADS_COMPLETED:
        isAdStarted = false;
        isAdPlaying = false;
        adsManager.destroy();
        break;
      case PAUSED:
        isAdPlaying = false;
        break;
      case RESUMED:
        isAdPlaying = true;
        break;
      default:
        break;
    }
  }

  protected void log(String message) {
    log.append(message + "\n");
    logScroll.post(new Runnable() {
      @Override
      public void run() {
        logScroll.fullScroll(View.FOCUS_DOWN);
      }
    });
  }
  
}
