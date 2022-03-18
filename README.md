# cobrowseio_flutter

A Flutter plugin to make use of cobrowse.io. View your app running remotely on customer devices. Guide the user, or take remote control.

#### Supported devices

  - Android
  - iOS

## Getting Started

### Usage
To use this plugin, add cobrowseio_flutter as a dependency in your pubspec.yaml file.

### init

`import 'package:cobrowseio_flutter/cobrowseio_flutter.dart';`

```dart
    String result = await CobrowseIO.start("YOUR_LICENSE_KEY", {
        'custom_data_field': 'Test'
    });
```

### Get a 6-digit code
After doing the init above, call:

```dart
    String code = await CobrowseIO.getCode();
```

### Add full device remote control (Android only)
If you want to have access to the full device on android, add this to your `AndroidManifest.xml` file:

```xml
    <application>
        <service
            android:name="io.cobrowse.CobrowseAccessibilityService"
            android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE" />
    </application>
```

In your code, call:

```dart
    if (Platform.isAndroid) {

      // Or: Check if we have access to full remote control.
      if (!await CobrowseIO.accessibilityServiceIsRunning()) {

        // If not, open the accesibility service settings to let the user give access.
        CobrowseIO.accessibilityServiceOpenSettings();
      }

      // Or: Show the default setup screen.
      CobrowseIO.accessibilityServiceShowSetup();
    }
```

### Plugins

| Plugin | Links |
| ------ | ------ |
| Cobrowse.io iOS | https://github.com/cobrowseio/cobrowse-sdk-ios-binary |
| Cobrowse.io Android | https://github.com/cobrowseio/cobrowse-sdk-android-binary |

