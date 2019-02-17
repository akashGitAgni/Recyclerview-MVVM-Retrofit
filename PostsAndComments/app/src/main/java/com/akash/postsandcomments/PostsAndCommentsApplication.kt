package com.akash.postsandcomments

import android.app.Application
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class PostsAndCommentsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(dataSourceModule, viewmodelModule))
    }
}