import 'dart:async';
import 'dart:collection';
import 'dart:io';

import 'package:flutter/services.dart';

class CobrowseIO {
  static const MethodChannel _channel =
      const MethodChannel('cobrowseio_plugin');

  static Future<String> start(String licenseKey, Map customData) async {
    final Map<String, dynamic> params = <String, dynamic>{
      "licenseKey": licenseKey,
      "customData": customData
    };

    return await _channel.invokeMethod('start', params);
  }

  static Future<String> getCode() async {
    return await _channel.invokeMethod('getCode');
  }
}
