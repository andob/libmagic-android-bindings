package ro.andob.libmagic_binding

import android.app.Application
import ro.andob.libmagic.LibMagic

class SampleApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        val uncaughtExceptionHandlerDelegate = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            ex.printStackTrace()
            uncaughtExceptionHandlerDelegate?.uncaughtException(thread, ex)
        }

        LibMagic.initialize(this)
    }
}
