#import "CobrowseIOFlutterPlugin.h"
@import CobrowseIO;

@implementation CobrowseIOFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"cobrowseio_plugin"
            binaryMessenger:[registrar messenger]];
  CobrowseIOFlutterPlugin* instance = [[CobrowseIOFlutterPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  NSDictionary *arguments = call.arguments;
  if ([@"start" isEqualToString:call.method]) {
    [self start:call result:result args:arguments];
  }
  else {
    result(FlutterMethodNotImplemented);
  }
}

- (void)start:(FlutterMethodCall*)call result:(FlutterResult)result args:(NSDictionary*)args {
  NSString *licenseKey = [args objectForKey:@"licenseKey"];
  NSDictionary *customData = [args objectForKey:@"customData"];
  if(licenseKey != nil ){
    CobrowseIO.instance.license = licenseKey;
    CobrowseIO.instance.customData = customData;
    [CobrowseIO.instance start];
  }else{
    result([NSString stringWithFormat:@"[ start ERROR ] :: licenseKey is required"]);
  }
}

@end
