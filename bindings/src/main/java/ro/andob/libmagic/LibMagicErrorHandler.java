package ro.andob.libmagic;

import android.net.Uri;
import android.webkit.MimeTypeMap;
import java.io.File;

public interface LibMagicErrorHandler
{
    boolean shouldThrowOnError();

    void doOnError(File file, Throwable error);

    String determineMimeTypeUsingAlternateMethod(File file);

    static LibMagicErrorHandler getDefault()
    {
        return new LibMagicErrorHandler()
        {
            @Override
            public boolean shouldThrowOnError()
            {
                return true;
            }

            @Override
            public void doOnError(File file, Throwable error) {}

            @Override
            public String determineMimeTypeUsingAlternateMethod(File file)
            {
                return MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            }
        };
    }
}
