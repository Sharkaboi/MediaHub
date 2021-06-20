package com.sharkaboi.mediahub.di

import android.content.Context
import com.sharkaboi.mediahub.common.alarm_manager.NotifyAlarmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AlarmManagerModule {

    @Provides
    @Singleton
    fun getNotifyAlarmManager(@ApplicationContext context: Context): NotifyAlarmManager =
        NotifyAlarmManager(context)
}