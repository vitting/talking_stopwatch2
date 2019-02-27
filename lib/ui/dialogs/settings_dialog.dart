import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_i18n/flutter_i18n.dart';
import 'package:material_design_icons_flutter/material_design_icons_flutter.dart';
import 'package:talking_stopwatch2/helpers/common_functions.dart';
import 'package:talking_stopwatch2/helpers/settings_data.dart';
import 'package:talking_stopwatch2/helpers/system_helpers.dart';
import 'package:talking_stopwatch2/ui/settings/settings_language_widget.dart';
import 'package:talking_stopwatch2/ui/settings/settings_switch_row_widget.dart';

class SettingsDialog extends StatelessWidget {
  final SettingsData settings;

  const SettingsDialog({Key key, this.settings}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return SimpleDialog(
      titlePadding: EdgeInsets.all(0),
      contentPadding: EdgeInsets.only(left: 10, right: 10, top: 20),
      backgroundColor: Colors.black38,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
      title: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: <Widget>[
          Text(FlutterI18n.translate(context, "settings.text1"),
              style: TextStyle(color: Colors.white)),
          IconButton(
            color: Colors.white,
            iconSize: 30,
            icon: Icon(Icons.close),
            onPressed: () {
              vibrateButton(settings.vibrate);
              Navigator.of(context).pop();
            },
          )
        ],
      ),
      children: <Widget>[
        Column(
          children: <Widget>[
            /// Language
            SettingsLanguageWidget(settings: settings),
            SizedBox(height: 20),

            /// Show notification
            Platform.isIOS
                ? Container()
                : Column(
                    children: <Widget>[
                      SettingsSwitchRowWidget(
                        icon: Icons.notifications,
                        text: FlutterI18n.translate(context, "settings.text10"),
                        vibrate: settings.vibrate,
                        value: settings.showNotification,
                        onChanged: (bool value) async {
                          await settings.updateShowNotification(value);
                        },
                      ),
                      SizedBox(height: 20)
                    ],
                  ),

            /// SpeakShort
            SettingsSwitchRowWidget(
              icon: MdiIcons.voice,
              text: FlutterI18n.translate(context, "settings.text9"),
              vibrate: settings.vibrate,
              value: settings.speakShort,
              onChanged: (bool value) async {
                await settings.updateSpeakShort(value);
              },
            ),
            SizedBox(height: 20),

            /// VibrateAtInterval
            SettingsSwitchRowWidget(
              icon: Icons.vibration,
              text: FlutterI18n.translate(context, "settings.text8"),
              vibrate: settings.vibrate,
              value: settings.vibrateAtInterval,
              onChanged: (bool value) async {
                await settings.updateVibrateAtInterval(value);
              },
            ),
            SizedBox(height: 20),

            /// KeepScreenOn
            SettingsSwitchRowWidget(
              icon: MdiIcons.cellphoneScreenshot,
              text: FlutterI18n.translate(context, "settings.text5"),
              vibrate: settings.vibrate,
              value: settings.keepScreenOn,
              onChanged: (bool value) async {
                await settings.updateKeepScreenOn(value);
                if (value) {
                  SystemHelpers.setScreenOn();
                } else {
                  SystemHelpers.setScreenOff();
                }
              },
            ),
            SizedBox(height: 20),

            /// Vibrate on keypress
            SettingsSwitchRowWidget(
              icon: Icons.vibration,
              text: FlutterI18n.translate(context, "settings.text6"),
              vibrate: settings.vibrate,
              value: settings.keepScreenOn,
              onChanged: (bool value) async {
                await settings.updateVibrate(value);
              },
            )
          ],
        )
      ],
    );
  }
}
