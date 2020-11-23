package io.github.dzulfikar68.exampleongkirapp

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val retrofit = module { factory {} }

        startKoin {
            modules(
                    retrofit
            )
        }
    }
}