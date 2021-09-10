import 'dart:collection';
import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:cobrowseio_flutter/cobrowseio_flutter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState(){
    super.initState();
    CobrowseIO.start("YOUR_LICENSE_KEY", {
        'username': 'Test'
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: Text('Co-browse.io Example'),
          ),
          body: Center(
            child: Text("First setup the correct license key, then find this device in your cobrowse.io dashboard to create a session.", textAlign: TextAlign.center)
          )
      ),
    );
  }
}
