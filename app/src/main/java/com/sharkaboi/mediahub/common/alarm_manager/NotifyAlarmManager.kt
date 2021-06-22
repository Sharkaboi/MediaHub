package com.sharkaboi.mediahub.common.alarm_manager

import android.app.AlarmManager
import android.content.Context

class NotifyAlarmManager(context: Context) {

    init {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }
}