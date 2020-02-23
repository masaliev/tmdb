package com.github.masaliev.tmdb.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.github.masaliev.tmdb.data.local.db.AppDatabase
import com.github.masaliev.tmdb.data.local.db.AppDatabaseHelper
import com.github.masaliev.tmdb.data.local.db.DatabaseHelper
import com.github.masaliev.tmdb.data.local.db.MockDatabaseHelper
import com.github.masaliev.tmdb.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "tmdb_database")
            .build()
    }

    @Provides
    @Singleton
    internal fun provideDatabaseHelper(appDatabase: AppDatabase): DatabaseHelper =
        AppDatabaseHelper(appDatabase)
}