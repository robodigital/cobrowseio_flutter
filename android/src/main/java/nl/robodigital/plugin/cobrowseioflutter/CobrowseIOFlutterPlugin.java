package nl.robodigital.plugin.cobrowseioflutter;

import android.app.Activity;
import android.os.Handler;
import android.content.Context;
import android.content.Intent;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import io.cobrowse.CobrowseIO;
import io.cobrowse.CobrowseAccessibilityService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;


/**
 * CobrowseIOFlutterPlugin
 */
public class CobrowseIOFlutterPlugin 
  implements MethodCallHandler, FlutterPlugin, ActivityAware {

  private static final String CHANNEL = "cobrowseio_plugin";

  private static CobrowseIOFlutterPlugin instance;
  private static MethodChannel channel;

  private FlutterPluginBinding pluginBinding;
  private Activity activity;

  private final Object initializationLock = new Object();

  public static void registerWith(Registrar registrar) {
    if (instance == null) {
      instance = new CobrowseIOFlutterPlugin();
    }

    if (registrar.activity() != null) {
      instance.onAttachedToEngine(registrar.messenger());
      instance.onAttachedToActivity(registrar.activity());
    }
  }

  @Override
  public void onAttachedToEngine(FlutterPluginBinding binding) {
      this.pluginBinding = binding;
      onAttachedToEngine(binding.getBinaryMessenger());
  }

  private void onAttachedToEngine(BinaryMessenger messenger) {
    synchronized (initializationLock) {
      if (channel != null) {
        return;
      }

      channel = new MethodChannel(messenger, CHANNEL);
      channel.setMethodCallHandler(this);
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    channel = null;
  }

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    onAttachedToActivity(binding.getActivity());
  }

  private void onAttachedToActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    onAttachedToActivity(binding);
  }

  @Override
  public void onDetachedFromActivity() {
      channel.setMethodCallHandler(null);
      channel = null;
      activity = null;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    try {
      switch (call.method) {
        case "start":
          handlerStart(call, result);
          break;
        case "getCode":
          handlerGetCode(call, result);
          break;
        case "accessibilityServiceIsRunning":
          handlerAccessibilityServiceIsRunning(call, result);
          break;
        case "accessibilityServiceOpenSettings":
          handlerAccessibilityServiceOpenSettings(call, result);
          break;
        case "accessibilityServiceShowSetup":
          handlerAccessibilityServiceShowSetup(call, result);
          break;
        default:
          result.notImplemented();
          break;
      }
    } catch (Exception e) {
      result.error("Error", e.getMessage(), e.getStackTrace());
    }
  }

  public void handlerStart(MethodCall call, Result result) throws Exception {
    try {
      String licenseKey = call.argument("licenseKey").toString();
      HashMap<String, Object> customData = call.argument("customData");
      CobrowseIO.instance().license(licenseKey);
      CobrowseIO.instance().customData(customData);
      CobrowseIO.instance().start(this.activity);
      result.success("Started!");
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }

  public void handlerGetCode(MethodCall call, Result result) throws Exception {
    try {
      CobrowseIO.instance().createSession((err, session) -> {
          if (err == null && session != null) {
            result.success(session.code());
          } else {
            result.error("Error", "Failed to create code", null);
          }
      });
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }

  public void handlerAccessibilityServiceIsRunning(MethodCall call, Result result) throws Exception {
    try {
      boolean isRunning = CobrowseAccessibilityService.isRunning(this.activity);
      result.success(isRunning);
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }

  public void handlerAccessibilityServiceOpenSettings(MethodCall call, Result result) throws Exception {
    try {
      Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      this.pluginBinding.getApplicationContext().startActivity(intent);
      result.success("Setting opened");
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }

  public void handlerAccessibilityServiceShowSetup(MethodCall call, Result result) throws Exception {
    try {
      CobrowseAccessibilityService.showSetup(this.activity);
      result.success("Setup shown");
    } catch (Exception ex) {
      result.error("Error", ex.getMessage(), ex.getStackTrace());
    }
  }
}
