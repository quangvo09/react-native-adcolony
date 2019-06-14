package com.smartapp.rnadcolony;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyUserMetadata;
import com.adcolony.sdk.AdColonyZone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RNAdColonyInterstitialAdModule extends ReactContextBaseJavaModule implements AdColonyInterstitialListener {

    public static final String REACT_CLASS = "RNAdColonyInterstitial";

    public static final String EVENT_AD_LOADED = "interstitialAdLoaded";
    public static final String EVENT_AD_FAILED_TO_LOAD = "interstitialAdFailedToLoad";
    public static final String EVENT_AD_OPENED = "interstitialAdOpened";
    public static final String EVENT_AD_CLOSED = "interstitialAdClosed";
    public static final String EVENT_AD_LEFT_APPLICATION = "interstitialAdLeftApplication";

    private String _adUnitID;
    private String _zoneID;

    private AdColonyInterstitial mInterstitialAd;
    private AdColonyInterstitialListener listener;

    private Promise mRequestAdPromise;
    private boolean isReady; 

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RNAdColonyInterstitialAdModule(ReactApplicationContext reactContext) {
        super(reactContext);

        listener = this;
        mInterstitialAd = new InterstitialAd(reactContext);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @ReactMethod
    public void setAdUnitID(String adUnitID) {
        _adUnitID = adUnitID;
    }

    @ReactMethod
    public void setZoneID(String zoneID) {
        _zoneID = zoneID;
    }

    @ReactMethod
    public void requestAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run () {
              isReady = false;
              mRequestAdPromise = promise;
              AdColony.requestInterstitial(_zoneID, listener);
            }
        });
    }

    @ReactMethod
    public void showAd(final Promise promise) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run () {
                if (isReady) {
                    mInterstitialAd.show();
                    promise.resolve(null);
                } else {
                    promise.reject("E_AD_NOT_READY", "Ad is not ready.");
                }
            }
        });
    }

    @ReactMethod
    public void isReady(final Callback callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run () {
                callback.invoke(isReady);
            }
        });
    }

    @Override
    public void onRequestFilled(AdColonyInterstitial ad) {
      sendEvent(EVENT_AD_LOADED, null);
      isReady = true;
      if (mRequestAdPromise != null) {
        mRequestAdPromise.resolve(null);
      }
    }
 
    @Override
    public void onRequestNotFilled(AdColonyZone zone) {
      WritableMap event = Arguments.createMap();
      WritableMap error = Arguments.createMap();
      String errorMessage = "Ad request was not filled";
      event.putString("message", errorMessage);
      sendEvent(EVENT_AD_FAILED_TO_LOAD, event);

      if (mRequestAdPromise != null) {
        mRequestAdPromise.reject(errorString, errorMessage);
      }
    }

    @Override
    public void onOpened(AdColonyInterstitial ad) {
        sendEvent(EVENT_AD_OPENED, null);
    }

    @Override
    public void onClosed(AdColonyInterstitial ad) {
        sendEvent(EVENT_AD_CLOSED, null);
    }

    @Override
    public void onExpiring(AdColonyInterstitial ad) {
        isReady = false;
    }
}
