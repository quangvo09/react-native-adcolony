
package com.smartapp.rnadcolony;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class RNRnAdcolonyModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNRnAdcolonyModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNAdcolony";
  }
}