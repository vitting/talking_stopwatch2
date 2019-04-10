import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:talking_stopwatch2/helpers/common_functions.dart';
import 'package:talking_stopwatch2/helpers/settings_data.dart';
import 'package:talking_stopwatch2/helpers/timer_values.dart';
import 'package:talking_stopwatch2/ui/dialogs/settings_dialog.dart';

class ShortcutSettings extends StatelessWidget {
  final SettingsData settings;
  final StreamController controller;

  const ShortcutSettings({Key key, this.settings, this.controller})
      : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Positioned(
      right: 20,
      top: 40,
      child: IconButton(
        tooltip: FlutterI18n.translate(context, "shortcutSettings.text1"),
        highlightColor: Colors.blue[800],
        iconSize: 30,
        icon: Icon(Icons.settings),
        color: Colors.white,
        onPressed: () async {
          vibrateButton(settings.vibrate);
          await _showSettings(context);
          await FlutterI18n.refresh(context, Locale(settings.language));
          controller.add(getTimeValues(TimerState.updateValue));
        },
      ),
    );
  }

  Future<void> _showSettings(BuildContext context) async {
    return showDialog<void>(
        context: context,
        builder: (BuildContext dialogContext) =>
            SettingsDialog(settings: settings));
  }
}
