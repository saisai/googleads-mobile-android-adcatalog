// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.ad.catalog;

import com.google.ad.catalog.TrackingVideoView.CompleteCallback;
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

/**
 * An example video player that implements VideoAdPlayer.
 */
public class VideoPlayer extends RelativeLayout implements VideoAdPlayer {
  private TrackingVideoView video;
  private FrameLayout adUiContainer;
  private MediaController mediaController;

  /**
   * Used to store the content URL during mid-rolls.
   */
  private String savedContentUrl;
  
  /**
   * Used to store the content position during mid-rolls.
   */
  private int savedContentPosition = 0;
  
  /**
   * Used to store the position of the video player when the parent activity is
   * paused, and seek to the correct position when the activity is resumed.
   */
  private int savedPosition = 0;
  
  private boolean contentPlaying;

  public VideoPlayer(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public VideoPlayer(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public VideoPlayer(Context context) {
    super(context);
    init();
  }

  private void init() {
    mediaController = new MediaController(getContext());
    mediaController.setAnchorView(this);

    // Center the video in the parent layout (when video ratio doesn't match the
    // layout size it will by default position to the left).
    LayoutParams videoLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                                                     LayoutParams.MATCH_PARENT);
    videoLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
    videoLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
    video = new TrackingVideoView(getContext());
    // Adding the touch listener to allow pause/resume during an ad. This is necessary because we
    // remove the MediaController during ad playback to prevent seeking.
    video.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View unusedView, MotionEvent unusedEvent) {
        if (!contentPlaying) {
          // Only applies when ad is playing
          video.togglePlayback();
        }
        return false;
      }
    });

    addView(video, videoLayoutParams);
    adUiContainer = new FrameLayout(getContext());
    addView(adUiContainer, LayoutParams.MATCH_PARENT);
  }

  public ViewGroup getUiContainer() {
    return adUiContainer;
  }

  public void setCompletionCallback(CompleteCallback callback) {
    video.setCompleteCallback(callback);
  }

  /**
   * Play whatever is already in the video view.
   */
  public void play() {
    video.start();
  }

  public void playContent(String contentUrl) {
    contentPlaying = true;
    savedContentUrl = contentUrl;
    video.setVideoPath(contentUrl);
    video.setMediaController(mediaController);
    play();
  }

  public void pauseContent() {
    savedContentPosition = video.getCurrentPosition();
    video.stopPlayback();
    video.setMediaController(null); // Disables seeking during ad playback.
  }

  public void resumeContent() {
    contentPlaying = true;
    video.setVideoPath(savedContentUrl);
    video.seekTo(savedContentPosition);
    video.setMediaController(mediaController);
    play();
  }

  public boolean isContentPlaying() {
    return contentPlaying;
  }

  public void savePosition() {
    savedPosition = video.getCurrentPosition();
  }

  public void restorePosition() {
    video.seekTo(savedPosition);
  }

  // Methods implementing VideoAdPlayer interface.

  @Override
  public void playAd() {
    contentPlaying = false;
    video.start();
  }

  @Override
  public void stopAd() {
    video.stopPlayback();
  }

  @Override
  public void loadAd(String url) {
    video.setVideoPath(url);
  }

  @Override
  public void pauseAd() {
    video.pause();
  }

  @Override
  public void resumeAd() {
    video.start();
  }

  @Override
  public void addCallback(VideoAdPlayerCallback callback) {
    video.addCallback(callback);
  }

  @Override
  public void removeCallback(VideoAdPlayerCallback callback) {
    video.removeCallback(callback);
  }

  @Override
  public VideoProgressUpdate getProgress() {
    int durationMs = video.getDuration();

    if (durationMs <= 0) {
      return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
    }
    VideoProgressUpdate vpu = new VideoProgressUpdate(video.getCurrentPosition(), durationMs);
    Log.i("PLAYER", vpu.toString());
    return vpu;
  }
}
