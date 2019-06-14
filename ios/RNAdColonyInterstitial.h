#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventEmitter.h"
#endif

#import <AdColony/AdColony.h>

@interface RNAdColonyInterstitial : RCTEventEmitter <RCTBridgeModule>
@end
