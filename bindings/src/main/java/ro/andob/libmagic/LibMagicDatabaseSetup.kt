package ro.andob.libmagic

import android.app.Application
import java.io.File

internal class LibMagicDatabaseSetup
private constructor
(
    applicationContext : Application,
    val databaseFile : File,
)
{
    constructor(applicationContext : Application) : this(
        applicationContext = applicationContext,
        databaseFile = applicationContext.getFileStreamPath("magic.mgc"),
    )
    init
    {
        if (databaseFile.exists() && HashUtils.md5Checksum(databaseFile) !=
            HashUtils.md5Checksum { applicationContext.assets.open(databaseFile.name) })
        {
            //database was altered
            databaseFile.delete()
        }

        if (!databaseFile.exists())
        {
            applicationContext.assets.open(databaseFile.name).buffered().use { inputStream ->
                databaseFile.outputStream().buffered().use { outputStream ->
                    inputStream.copyTo(outputStream)
                    outputStream.flush()
                }
            }
        }
    }
}
