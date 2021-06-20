package com.sharkaboi.mediahub.common.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context

class NotifyAlarmManager(context: Context) {

    init {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    }
}