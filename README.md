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

### Plugins

| Plugin | Links |
| ------ | ------ |
| Cobrowse.io iOS | https://github.com/cobrowseio/cobrowse-sdk-ios-binary |
| Cobrowse.io Android | https://github.com/cobrowseio/cobrowse-sdk-android-binary |

