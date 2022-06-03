package ro.andob.libmagic;

import android.app.Application;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.Objects;

public final class LibMagic
{
    static { System.loadLibrary("magic"); }

    private static String mgcDatabaseFilePath = "";

    private static native @Nullable String getFileMimeType(String mgcFilePath, String filePath);

    public static void initialize(Application applicationContext)
    {
        mgcDatabaseFilePath = new LibMagicDatabaseSetup(applicationContext).getDatabaseFile().getAbsolutePath();
    }

    public static @NotNull String getFileMimeType(File file)
    {
        Objects.requireNonNull(file);

        //some hard to detect mime types
        if (file.getName().endsWith(".csv"))
            return "text/csv";
        if (file.getName().endsWith(".ifc"))
            return "application/octet-stream";

        String mimeType = Objects.requireNonNull(getFileMimeType(mgcDatabaseFilePath, file.getAbsolutePath()));
        return mimeType.contains(";") ? mimeType.substring(0, mimeType.indexOf(";")) : mimeType;
    }
}
