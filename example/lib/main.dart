import 'dart:io' show Platform;

import 'package:flutter/material.dart';
import 'package:cobrowseio_flutter/cobrowseio_flutter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    initCobrowse();
  }

  void initCobrowse() async {
    // Init Cobrowse, pass licensekey and eventually custom data.
    await CobrowseIO.start("fX-geVPHMILBIg", {'username': 'Test'});

    // Retrieve a 6-digit code.
    print(await CobrowseIO.getCode());

    // Enable full access control on Android.
    if (Platform.isAndroid) {
      if (!await CobrowseIO.accessibilityServiceIsRunning()) {
        CobrowseIO.accessibilityServiceOpenSettings();
      }

      CobrowseIO.accessibilityServiceShowSetup();
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: Text('Co-browse.io Example'),
          ),
          body: Center(
              child: Text(
                  "First setup the correct license key, then find this device in your cobrowse.io dashboard to create a session.",
                  textAlign: TextAlign.center))),
    );
  }
}
