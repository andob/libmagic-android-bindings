package ro.andob.libmagic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.util.Objects;

public final class LibMagic
{
    static { System.loadLibrary("magic"); }

    private static native @Nullable String getFileMimeType(String mgcFilePath, String filePath);

    public static @NotNull String getFileMimeType(File file)
    {
        //todo recovery options
        return Objects.requireNonNull(getFileMimeType(null, null));
    }
}
