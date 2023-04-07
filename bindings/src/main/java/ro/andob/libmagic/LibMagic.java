package ro.andob.libmagic;

import android.app.Application;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public final class LibMagic
{
    static { System.loadLibrary("magic"); }

    private static Application applicationContext;

    private static native @Nullable String getFileMimeType(String mgcFilePath, String filePath);

    public static void initialize(@NotNull Application applicationContext)
    {
        LibMagic.applicationContext = applicationContext;
    }

    public static synchronized @NotNull String getFileMimeType(File file)
    {
        Objects.requireNonNull(file);

        File databaseFile = Objects.requireNonNull(applicationContext).getFileStreamPath("magic.mgc");
        if (!databaseFile.exists())
        {
            try (BufferedInputStream inputStream = new BufferedInputStream(applicationContext.getAssets().open(databaseFile.getName())))
            {
                try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(databaseFile)))
                {
                    byte[] buffer = new byte[512*1024];
                    int numberOfReadBytes;
                    while ((numberOfReadBytes = inputStream.read(buffer)) != -1)
                        outputStream.write(buffer, 0, numberOfReadBytes);
                }
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Cannot initialize libmagic database!", ex);
            }
        }

        String mimeType = Objects.requireNonNull(getFileMimeType(databaseFile.getAbsolutePath(), file.getAbsolutePath()));
        return mimeType.contains(";") ? mimeType.substring(0, mimeType.indexOf(";")) : mimeType;
    }
}
