import 'package:talking_stopwatch2/helpers/system_helpers.dart';
import 'package:talking_stopwatch2/helpers/timer_values.dart';

TimerValues getTimeValues(TimerState state, [int time]) {
    return TimerValues(
        timerState: state,
        time: time);
  }

  void vibrateButton(bool vibrate) {
    if (vibrate) {
      SystemHelpers.vibrate30();
    }
  }